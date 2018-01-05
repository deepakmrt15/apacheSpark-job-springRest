package com.cit.kyc.cache.service;


import com.cit.kyc.cache.models.KycInfoRecord;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by sharmd01 on 12/21/2017.
 */
@Service
public class CachingWrapper {


    @Autowired
    DFCreator dfCreator;

//    @Autowired
//    SparkSession session;


    public String loadDFsToCache(SparkSession session){
        StringBuffer resp = new StringBuffer();
        try{
            Dataset<Row> partyDF =  dfCreator.partyDF(session);
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            Dataset<Row> party2RelatedPartyDF =
                    partyDF.alias("e").join(relatedPartyDF.alias("d"), partyDF.col("rowid_object")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
                            //.select("e.EMPID as eEMP, e.NAME as eName, d.DNAME as dName, d.EMPID as dEMPID ");// ,"inner"
            long joindCnt = party2RelatedPartyDF.count();

            System.out.println("party2RelatedPartyDF count: " + String.valueOf(joindCnt));
            party2RelatedPartyDF//"prty_id", "prty_rltd_prty_typ_cd"
                    .foreach((Row row) -> {
                        System.out.println("prty_id: " + row.getAs("rowid_object")
                                +", rltd_prty_id: "+
                                row.getAs("rltd_prty_id"));

                    });

            resp.append(String.valueOf(joindCnt));

           // return resp.toString();
//            Dataset<Row> partyAddressDF =  dfCreator.partyAddressDF(session);
//            partyAddressDF.createOrReplaceTempView("PARTY_ADDRESS");
//            Dataset<Row> countriesDF =  dfCreator.countriesDF(session);
//            countriesDF.createOrReplaceTempView("COUNTRIES");
//            Dataset<Row> classifDF =  dfCreator.classifDF(session);
//            classifDF.createOrReplaceTempView("CLASSIFICATION");
//            Dataset<Row> orgDF =  dfCreator.orgDF(session);
//            orgDF.createOrReplaceTempView("ORGN");
//            Dataset<Row> screeningInfoDF =  dfCreator.screeningInfoDF(session);
//            screeningInfoDF.createOrReplaceTempView("SCREENING");






        }catch (Exception e){

         e.printStackTrace();
             resp.append(e);
        }

        return resp.toString();
    }



}

