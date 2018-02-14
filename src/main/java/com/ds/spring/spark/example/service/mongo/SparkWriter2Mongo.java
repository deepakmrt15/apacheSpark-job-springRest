package com.ds.spring.spark.example.service.mongo;

import com.ds.spring.spark.example.config.SparkSessionConfig;
import com.mongodb.spark.MongoSpark;
import org.apache.spark.api.java.JavaSparkContext;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ds on 01/21/2018.
 */
@Service
public class SparkWriter2Mongo {
@Autowired
SparkSessionConfig sparkSessionConfig;

public void writeToMongdo(){

    sparkSessionConfig = new SparkSessionConfig();


    JavaSparkContext jsc = new JavaSparkContext(sparkSessionConfig.getOrCreateSparkSession().sparkContext());


    Dataset<Row> ds=null;
    MongoSpark.write(ds).option("collection", "cachedCollection").mode("overwrite").save();

    //jsc.close();
}

}
