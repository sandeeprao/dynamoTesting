package com.sandeep.dynamo.dao;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.sandeep.dynamo.dao.JobDaoImpl;
import com.sandeep.dynamo.model.Job;
import com.sandeep.dynamo.dao.LocalDbCreationRule;
import org.junit.*;

import java.time.Instant;

/**
 * Created by srao1 on 4/21/18.
 */
public class JobDaoTest {


    @ClassRule
    public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule();

    private static DynamoDBMapper dynamoDBMapper;
    private static AmazonDynamoDB amazonDynamoDB;
    private static final String TABLE_NAME = "Job";
    JobDaoImpl jobDao = new JobDaoImpl(TABLE_NAME);

    @BeforeClass
    public static void setupClass() {

        amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:"+ LocalDbCreationRule.port, "us-west-2"))
                .build();
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }


    @Before
    public void setup() {
        try {

            jobDao.setMapper(dynamoDBMapper);
            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Job.class,
                    new DynamoDBMapperConfig.TableNameOverride(TABLE_NAME).config());
            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
            amazonDynamoDB.createTable(tableRequest);
        } catch (ResourceInUseException e) {
            // Do nothing, table already created
        }
    }

    @Test
    public void testSaveJob(){

        Job testJob = createJob();
        Job job = new Job();
        job.setId("Test");
        job.setCreationTime(testJob.getCreationTime());

        jobDao.saveJob(testJob);
        Job retrievedJob = jobDao.getJob(job);

        Assert.assertEquals(testJob.getId(), retrievedJob.getId());
        Assert.assertEquals(testJob.getType(), retrievedJob.getType());

        //Deleting the data from the Db so that it doesn't affect other tests
        jobDao.deleteJob(retrievedJob);
    }


    private Job createJob(){

        Job job = new Job();
        job.setId("Test");
        job.setCreationTime(Instant.now().getEpochSecond());
        job.setJobProps("K1=V1,K2=V2,K3=V3");
        job.setType("Enhance");
        job.setUserName("Test_User");

        return job;
    }
}
