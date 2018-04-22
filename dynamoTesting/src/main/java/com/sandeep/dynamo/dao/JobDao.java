package com.sandeep.dynamo.dao;

import com.sandeep.dynamo.model.Job;

/**
 * Created by srao1 on 4/10/18.
 */
public interface JobDao {

    void saveJob(Job job);
    Job getJob(Job job);
    void deleteJob(Job job);
}
