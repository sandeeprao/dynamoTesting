package com.sandeep.dynamo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Created by srao1 on 4/10/18.
 */

@DynamoDBTable(tableName = "REPLACED_BY_VALUE_IN_PROPERTIES_FILE")
public class Job {

    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBRangeKey(attributeName = "creationTime")
    private long creationTime;
    @DynamoDBAttribute(attributeName = "type")
    private String type;
    @DynamoDBAttribute(attributeName = "jobProps")
    private String jobProps;
    @DynamoDBAttribute(attributeName = "userName")
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJobProps() {
        return jobProps;
    }

    public void setJobProps(String jobProps) {
        this.jobProps = jobProps;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
