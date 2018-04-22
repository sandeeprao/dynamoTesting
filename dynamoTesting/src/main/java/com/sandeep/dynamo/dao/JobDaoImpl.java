package com.sandeep.dynamo.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.sandeep.dynamo.model.Job;

import java.util.Objects;


/**
 * Created by srao1 on 4/21/18.
 */
public class JobDaoImpl implements JobDao {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    DynamoDBMapper mapper = new DynamoDBMapper(client);
    DynamoDBMapperConfig jobMapperConfig;

    public JobDaoImpl(String tableName) {
        Objects.requireNonNull(tableName, "tableName");
        jobMapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
                .build();
    }


    public void saveJob(Job job) {
        try {
            mapper.save(job,jobMapperConfig );
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    public Job getJob(Job job) {
        return  mapper.load(job,jobMapperConfig);
    }

    public void deleteJob(Job job) {
            mapper.delete(job,jobMapperConfig);
    }

    public DynamoDBMapper getMapper() {
        return mapper;
    }

    public void setMapper(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }
}
