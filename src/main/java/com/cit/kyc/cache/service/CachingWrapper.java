package com.cit.kyc.cache.service;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by sharmd01 on 12/21/2017.
 */
public class CachingWrapper {


    @Autowired
    DFCreator dfCreator;

    @Autowired
    SparkSession session;


    public void loadDFstoCache(){

        try{
            Dataset<Row> partyDF =  dfCreator.partyDF(session);
            partyDF.createOrReplaceTempView("PARTY");
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            relatedPartyDF.createOrReplaceTempView("RELATED_PARTY");
            Dataset<Row> partyAddressDF =  dfCreator.partyAddressDF(session);
            partyAddressDF.createOrReplaceTempView("PARTY_ADDRESS");
            Dataset<Row> countriesDF =  dfCreator.countriesDF(session);
            countriesDF.createOrReplaceTempView("COUNTRIES");
            Dataset<Row> classifDF =  dfCreator.classifDF(session);
            classifDF.createOrReplaceTempView("CLASSIFICATION");
            Dataset<Row> orgDF =  dfCreator.orgDF(session);
            orgDF.createOrReplaceTempView("ORGN");
            Dataset<Row> screeningInfoDF =  dfCreator.screeningInfoDF(session);
            screeningInfoDF.createOrReplaceTempView("SCREENING");


        }catch (Exception e){


        }


    }



}

