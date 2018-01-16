package com.cit.kyc.cache.service;

import com.cit.kyc.cache.models.*;

import org.apache.spark.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sharmd01 on 01/09/2018.
 */
@Service
public class DFJoiner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DFCreator dfCreator;

    public static Dataset<Row> cacheRelPartyDF=null;
//    @Autowired
//    SparkSession session;

    /* 1. DF to get party to related parties data
    **/
    public Dataset<Row> rltdParties(SparkSession session){
        StringBuffer resp = new StringBuffer();
        Dataset<Row> party2RelatedPartyDF = null;
        try{
            dfCreator = new DFCreator ();
            Dataset<Row> partyDF =         dfCreator.partyDF(session);
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            // joining --party and related parties
             party2RelatedPartyDF =
                    partyDF.join(relatedPartyDF, partyDF.col("rowid_object")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
           long joindP2RlPCnt = party2RelatedPartyDF.count();
            logger.info("party2RelatedPartyDF count: " + joindP2RlPCnt);
         //   resp.append("\n party2RelPartyJoinCount: "+String.valueOf(joindP2RlPCnt));


        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }
        return party2RelatedPartyDF;
    }

    /** 2. DF to get all parties to Addresses data
    **/
    public Dataset<Row> Address2Parties(SparkSession session)throws Exception{
        StringBuffer resp = new StringBuffer();
        Dataset<Row> relParty2AddressDF =  null;
         try{
             dfCreator = new DFCreator ();
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
        //     System.out.println("relatedPartyDF count: " + String.valueOf(relatedPartyDF.count()));
             Dataset<Row> partyAddressDF =  dfCreator.partyAddressDF(session);
//             System.out.println("partyAddressDF count: " + String.valueOf(partyAddressDF.count()));
            relParty2AddressDF =
                    partyAddressDF.join(relatedPartyDF, partyAddressDF.col("prty_id")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long party2AddressDFCnt = relParty2AddressDF.count();
             logger.info("relParty2AddressDF count: " + String.valueOf(party2AddressDFCnt));
          //  resp.append("\n party2AddressJoinCount: "+String.valueOf(party2AddressDFCnt));
        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }

        return relParty2AddressDF;
    }
    /* 3. DF to get all parties to Screening Info data
    **/
    public Dataset<Row> party2ScreenInfo(SparkSession session){
        StringBuffer resp = new StringBuffer();
        Dataset<Row> relParty2ScreenInfoDF = null;
        try{
            dfCreator = new DFCreator ();
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            Dataset<Row> screenInfoDF =  dfCreator.screeningInfoDF(session);
            relParty2ScreenInfoDF =
                    screenInfoDF.join(relatedPartyDF, screenInfoDF.col("party_id_value")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long relParty2ScreenInfoDFCnt = relParty2ScreenInfoDF.count();
            logger.info("relParty2ScreenInfoDF count: " + relParty2ScreenInfoDFCnt);
         //   resp.append("\n party2ScreeningJoinCount: "+String.valueOf(relParty2ScreenInfoDFCnt));

        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }

        return relParty2ScreenInfoDF;
    }

    /** 4. DF to get all parties to kyc_properties Info data
    **/
    public Dataset<Row> party2KycProperties(SparkSession session){
        StringBuffer resp = new StringBuffer();
        Dataset<Row> relParty2KycPropertiesDF = null;
        try{
            dfCreator = new DFCreator ();
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            Dataset<KycProperties> kycPropertiesDataset =  dfCreator.kycPropertiesDF(session);
            relParty2KycPropertiesDF =
                    kycPropertiesDataset.join(relatedPartyDF, kycPropertiesDataset.col("prty_id")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long relParty2KycPropertiesDFCnt = relParty2KycPropertiesDF.count();
            logger.info("relParty2KycPropertiesDFCnt count: " + relParty2KycPropertiesDFCnt);

          //  resp.append("\n relParty2KycPropertiesDFCnt: "+String.valueOf(relParty2KycPropertiesDFCnt));

        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }

        return relParty2KycPropertiesDF;
    }

    /* 4. DF to get all parties to kya_properties Info data
    **/
    public Dataset<Row> party2KyaProperties(SparkSession session) throws Exception{
        StringBuffer resp = new StringBuffer();
        Dataset<Row> relParty2KyaPropertiesDF = null;
        try{
            dfCreator = new DFCreator ();
             Dataset<Row> relatedPartyDF =       dfCreator.relatedPartyDF(session);
            Dataset<Row> kyaPropDF =  dfCreator.kyaPropertiesDF(session);
             relParty2KyaPropertiesDF =
                    kyaPropDF.join(relatedPartyDF, kyaPropDF.col("prty_id")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long relParty2KyaPropertiesDFCnt = relParty2KyaPropertiesDF.count();
           logger.info("relParty2KyaPropertiesDFCnt count: " + relParty2KyaPropertiesDFCnt);
        //    resp.append("\n relParty2KyaPropertiesDFCnt: "+String.valueOf(relParty2KyaPropertiesDFCnt));

        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }

        return relParty2KyaPropertiesDF;
    }


}


