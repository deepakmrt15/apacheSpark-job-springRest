package com.cit.kyc.cache.service;

import com.cit.kyc.cache.models.*;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
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

//    @Autowired
//    SparkSession session;

    /* 1. DF to get party to related parties data
    **/
    public String rltdParties(SparkSession session){
        StringBuffer resp = new StringBuffer();
        try{
            Dataset<Row> partyDF =  dfCreator.partyDF(session);
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            // joining --party and related parties
            Dataset<Row> party2RelatedPartyDF =
                    partyDF.join(relatedPartyDF, partyDF.col("rowid_object")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long joindP2RlPCnt = party2RelatedPartyDF.count();
            System.out.println("party2RelatedPartyDF count: " + String.valueOf(joindP2RlPCnt));
            resp.append("\n party2RelPartyJoinCount: "+String.valueOf(joindP2RlPCnt));


        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }
        return resp.toString();
    }

    /** 2. DF to get all parties to Addresses data
    **/
    public String Address2Parties(SparkSession session){
        StringBuffer resp = new StringBuffer();
         try{
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            Dataset<PartyAddress> partyAddressDF =  dfCreator.partyAddressDF(session);
            Dataset<Row> relParty2AddressDF =
                    partyAddressDF.join(relatedPartyDF, partyAddressDF.col("prty_id")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long party2AddressDFCnt = relParty2AddressDF.count();
            System.out.println("relParty2AddressDF count: " + String.valueOf(party2AddressDFCnt));
            resp.append("\n party2AddressJoinCount: "+String.valueOf(party2AddressDFCnt));
        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }

        return resp.toString();
    }
    /* 3. DF to get all parties to Screening Info data
    **/
    public String party2ScreenInfo(SparkSession session){
        StringBuffer resp = new StringBuffer();
        try{
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            Dataset<ScreenInfo> screenInfoDF =  dfCreator.screeningInfoDF(session);
            Dataset<Row> relParty2ScreenInfoDF =
                    screenInfoDF.join(relatedPartyDF, screenInfoDF.col("prty_id")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long relParty2ScreenInfoDFCnt = relParty2ScreenInfoDF.count();
            System.out.println("relParty2ScreenInfoDF count: " + String.valueOf(relParty2ScreenInfoDFCnt));
            resp.append("\n party2ScreeningJoinCount: "+String.valueOf(relParty2ScreenInfoDFCnt));

        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }

        return resp.toString();
    }

    /** 4. DF to get all parties to kyc_properties Info data
    **/
    public String party2KycProperties(SparkSession session){
        StringBuffer resp = new StringBuffer();

        try{
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            Dataset<KycProperties> kycPropertiesDataset =  dfCreator.kycPropertiesDF(session);
            Dataset<Row> relParty2KycPropertiesDF =
                    kycPropertiesDataset.join(relatedPartyDF, kycPropertiesDataset.col("prty_id")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long relParty2KycPropertiesDFCnt = relParty2KycPropertiesDF.count();
            System.out.println("relParty2KycPropertiesDFCnt count: " + String.valueOf(relParty2KycPropertiesDFCnt));

            resp.append("\n relParty2KycPropertiesDFCnt: "+String.valueOf(relParty2KycPropertiesDFCnt));

        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }

        return resp.toString();
    }

    /* 4. DF to get all parties to kya_properties Info data
    **/
    public String party2KyaProperties(SparkSession session){
        StringBuffer resp = new StringBuffer();
        try{
            Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
            Dataset<KyaProperties> kyaPropDF =  dfCreator.kyaPropertiesDF(session);
            Dataset<Row> relParty2KyaPropertiesDF =
                    kyaPropDF.join(relatedPartyDF, kyaPropDF.col("prty_id")
                            .equalTo(relatedPartyDF.col("rltd_prty_id")),"inner");
            long relParty2KyaPropertiesDFCnt = relParty2KyaPropertiesDF.count();
            System.out.println("relParty2KyaPropertiesDFCnt count: " + String.valueOf(relParty2KyaPropertiesDFCnt));
            resp.append("\n relParty2KyaPropertiesDFCnt: "+String.valueOf(relParty2KyaPropertiesDFCnt));

        }catch (Exception e){

            e.printStackTrace();
            resp.append(e);
        }

        return resp.toString();
    }


}


