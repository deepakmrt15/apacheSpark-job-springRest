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
            //1. relationship join--party and related parties
            Dataset<Row> party2RelatedPartyDF =
                    partyDF.join(relatedPartyDF, partyDF.col("rowid_object")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
                            //.select("e.EMPID as eEMP, e.NAME as eName, d.DNAME as dName, d.EMPID as dEMPID ");// ,"inner"
            long joindP2RlPCnt = party2RelatedPartyDF.count();

            System.out.println("party2RelatedPartyDF count: " + String.valueOf(joindP2RlPCnt));

//            party2RelatedPartyDF//"prty_id", "prty_rltd_prty_typ_cd"
//                    .foreach((Row row) -> {
//                        System.out.println("prty_id: " + row.getAs("rowid_object")
//                                +", rltd_prty_id: "+
//                                row.getAs("rltd_prty_id"));
//
//                    });


            Dataset<Row> partyAddressDF =  dfCreator.partyAddressDF(session);

            //2. relationship -party and address
            Dataset<Row> relParty2AddressDF =
                    partyAddressDF.join(relatedPartyDF, partyDF.col("prty_id")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long party2AddressDFCnt = relParty2AddressDF.count();

            System.out.println("relParty2AddressDF count: " + String.valueOf(party2AddressDFCnt));

            //3. join party to screening
            Dataset<Row> screeningInfoDF =  dfCreator.screeningInfoDF(session);

            //2. relationship -party and address
            Dataset<Row> relParty2ScreeningDF =

                    partyAddressDF.join(relatedPartyDF, partyDF.col("prty_id")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long party2ScreeningDFCnt = relParty2ScreeningDF.count();

            System.out.println("party2ScreeningDFCnt count: " + String.valueOf(party2ScreeningDFCnt));





            //resp.append(String.valueOf(party2ScreeningDFCnt));
            resp.append("\n party2RelPartyJoinCount: "+String.valueOf(joindP2RlPCnt));
            resp.append("\n party2AddressJoinCount: "+String.valueOf(party2AddressDFCnt));
            resp.append("\n party2ScreeningJoinCount: "+String.valueOf(party2ScreeningDFCnt));

           // return resp.toString();
//            Dataset<Row> partyAddressDF =  dfCreator.partyAddressDF(session);
//            partyAddressDF.createOrReplaceTempView("PARTY_ADDRESS");
//            Dataset<Row> countriesDF =  dfCreator.countriesDF(session);
//            countriesDF.createOrReplaceTempView("COUNTRIES");
//            Dataset<Row> classifDF =  dfCreator.classifDF(session);
//            classifDF.createOrReplaceTempView("CLASSIFICATION");
//            Dataset<Row> orgDF =  dfCreator.orgDF(session);
//            orgDF.createOrReplaceTempView("ORGN");
//
//            screeningInfoDF.createOrReplaceTempView("SCREENING");






        }catch (Exception e){

         e.printStackTrace();
             resp.append(e);
        }

        return resp.toString();
    }



}

