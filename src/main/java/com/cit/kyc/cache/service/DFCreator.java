package com.cit.kyc.cache.service;

import com.cit.kyc.cache.models.*;
import com.cit.kyc.cache.repository.KYCRecordRepository;
import org.apache.spark.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sharmd01 on 12/28/2017.
 */

@Service
public class DFCreator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
   // @Autowired
 //   private Environment env;


    /**
     * 1.Load the Party data from Denodo
     * */
    public static Dataset<Row> cacheDF=null;

    private String URL ="jdbc:vdb://q53dnoapp001t0x:9999/qmdm_kyc?user=sv_kyc_app&password=App123!@";
    private String DRIVER ="com.denodo.vdp.jdbc.Driver";

    private String LocalURL ="jdbc:oracle:thin:scott/scott@localhost:1521:orcl";
   // private String LocalDRIVER ="com.denodo.vdp.jdbc.Driver";

    @Autowired
    private KYCRecordRepository kycRecordRepository;

    public static Dataset<Row> cachePartyDF=null;
    public static Dataset<Row> cacheRelPartyDF=null;
    public static Dataset<Row> cachePartyAddressDF=null;
    public static Dataset<PartyRelatedOrganization> cacheOrgDF=null;

    public static Dataset<PartyIndustryClassif> cacheClassificDF=null; //
    public static Dataset<PartyCitizenshipCountry> cacheCountriesDF=null;
    public static Dataset<Row> cacheScreeningInfoDF=null;

   public static Dataset<Row> cacheKyaPropDF =null; //kya_properties_party_sql
   public static Dataset<KycProperties> cacheKycPropDF=null;
    public static Dataset<Row> cacheParty2AccntfDF=null;
   // public static Dataset<Row> cacheClassifDF=null;



    public Dataset <Row> partyDF(SparkSession session)  {
        Dataset<Row> partyDF =null;
        StringBuffer resp = new StringBuffer();

        try {
            if (cachePartyDF!=null){
                System.out.println("Party--Serving from Cache. Cache Count is: "+cachePartyDF.count());
                return cachePartyDF;

            }else {
                //  System.out.println(" DataFrame loaded from the entire contents of a table.");
                long t1 = System.currentTimeMillis();
                logger.info("Party View DB operation starting...");
                 session.read().format("jdbc")
                        .option("url", URL)
                        .option("driver", DRIVER)
                        .option("dbtable", "iv_c_prty")
                        .load().createTempView("party");
                partyDF = session.sql(" select * from party ");

                cachePartyDF = partyDF.cache();
                long cnt= cachePartyDF.count();

                logger.info(" Party data loaded from iv_c_prty. Cache also loaded. "+cnt);//+cachePartyDF.count()
                long t2 = System.currentTimeMillis();

             //   System.out.println("Time consumed in loading: " + (t2 - t1) + ". Row count: " + cachePartyDF.count());
                return cachePartyDF;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cachePartyDF;
    }

    /**
     * 2. Load the Related Party data from Denodo
     * */
    public Dataset <Row> relatedPartyDF(SparkSession session) {
        Dataset<Row> relPartyDf = null;
        StringBuffer resp = new StringBuffer();
            try {
                if (cacheRelPartyDF != null) {
                    System.out.println("Related Party--Serving from Cache. Cache Count is: " + cacheRelPartyDF.count());
                    return cacheRelPartyDF;

                } else {
                    logger.info(" Related Party View DB operation starting...");
                    long t1 = System.currentTimeMillis();
                    session.read().format("jdbc")
                            .option("url", URL)
                            .option("driver", DRIVER)
                            .option("dbtable", "iv_c_prty_rltd_prty")
                            .load().createTempView("relatedParty");

                    relPartyDf = session.sql(" select * from relatedParty ").orderBy("rltd_prty_id");

                     cacheRelPartyDF = relPartyDf.cache();
                    long cnt =     cacheRelPartyDF.count();
                    long t2 = System.currentTimeMillis();
                  //  cacheRelPartyDF = cacheRelPartyDF.orderBy("rltd_prty_id");
                // cacheRelPartyDF.show();
                 logger.info("Time consumed in loading Realted Party: " + (t2 - t1) + ". Row Count: " + cacheRelPartyDF.count());                    //return cacheRelPartyDF;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cacheRelPartyDF;
        }


    /**
     * 3. Load the Party address data from Denodo
     * */
    public Dataset <Row> partyAddressDF(SparkSession session)  {

         StringBuffer resp = new StringBuffer();
        Encoder<PartyAddress> partyAddressEncoder = Encoders.bean(PartyAddress.class);
        Dataset<Row> partyAddressDF =null;

        try {
            if (cachePartyAddressDF != null) {
                logger.info("Party Addresses --Serving from Cache. Cache Count is: " + cachePartyAddressDF.count());
                return cachePartyAddressDF;

            } else {
                logger.info(" Party Addresses View DB operation starting...");
             long t1 = System.currentTimeMillis();
            session.read().format("jdbc")
                    .option("url", URL)
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_addr")
                    .load().createTempView("partyAddress");
                partyAddressDF  = session.sql(" select * from partyAddress ").orderBy("prty_id");//.as(partyAddressEncoder);

               cachePartyAddressDF = partyAddressDF.cache();
                long cnt = cachePartyAddressDF.count();
                long t2 = System.currentTimeMillis();
                logger.info("Time consumed in loading Party Addresses: " + cnt + " Rows: " + (t2 - t1));
            return cachePartyAddressDF;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cachePartyAddressDF;
    }

    /**
     * 4. Load the Org data from Denodo
     * */
    public Dataset <PartyRelatedOrganization> orgDF(SparkSession session)  {

        Encoder<PartyRelatedOrganization> partyRltdOrgEncoder = Encoders.bean(PartyRelatedOrganization.class);
        Dataset<PartyRelatedOrganization> orgDF =null;

        StringBuffer resp = new StringBuffer();
        try {
            if (cacheOrgDF != null) {
                logger.info("Party Org data --Serving from Cache. Cache Count is: " + cacheOrgDF.count());
                return cacheOrgDF;

            } else {
                logger.info(" Party Org View DB operation starting...");
                long t1 = System.currentTimeMillis();
                 session.read().format("jdbc")
                    .option("url", URL)
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_rltd_org")
                    .load().createTempView("partyRelatedOrg");

                orgDF  = session.sql(" select * from partyRelatedOrg ").as(partyRltdOrgEncoder);

                cacheOrgDF = orgDF.cache();
                long cnt = cacheOrgDF.count();
                long t2 = System.currentTimeMillis();
                logger.info("  Party org data loaded from iv_c_prty_rltd_org");
                logger.info("Time consumed in loading cache - Party Org: " + cnt + " Rows.- " + (t2 - t1));
            return cacheOrgDF;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheOrgDF;
    }

    /**
     * 5. Load the classif data from Denodo
     * */
    public Dataset <PartyIndustryClassif> classifDF(SparkSession session)  {

        Encoder<PartyIndustryClassif> partyIndClEncoder = Encoders.bean(PartyIndustryClassif.class);
        Dataset<PartyIndustryClassif> classifDF =null;
        StringBuffer resp = new StringBuffer();
        try {
            if (cacheClassificDF != null) {
               logger.info("Party Classification data --Serving from Cache. Cache Count is: " + cacheClassificDF.count());
                return cacheClassificDF;

            } else {
                logger.info(" Party Classification View DB operation starting...");
                long t1 = System.currentTimeMillis();
               session.read().format("jdbc")
                    .option("url", URL)
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_inds_clsf")
                    .load().createTempView("partyClassification");

                classifDF = session.sql(" select * from partyClassification ").as(partyIndClEncoder);

                cacheClassificDF = classifDF.cache();
                long cnt = cacheClassificDF.count();
                long t2 = System.currentTimeMillis();
                logger.info("Party Classification data loaded from iv_c_prty_inds_clsf");
                logger.info("Time consumed in loading Party Classification: " + cnt + " Rows: " + (t2 - t1));
            return cacheClassificDF;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheClassificDF;
    }


    /**
     * 6. Load the countries data from Denodo
     * */
    public Dataset <PartyCitizenshipCountry> countriesDF(SparkSession session) throws Exception {

        Encoder<PartyCitizenshipCountry> partyCitizenshipCountryEncoder = Encoders.bean(PartyCitizenshipCountry.class);

        Dataset<Row> countriesDF =null;
        StringBuffer resp = new StringBuffer();
        try {
            if (cacheCountriesDF != null) {
                logger.info("Party Countries data --Serving from Cache. Cache Count is: " + cacheCountriesDF.count());
             return cacheCountriesDF;

              } else {
                logger.info(" Party Countries View DB operation starting...");
            long t1 = System.currentTimeMillis();
               session.read().format("jdbc")
                    .option("url", URL)
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_ctzshp_cntry")//iv_c_prty_ctzshp_cntry
                   // .schema(partyCitizenshipCountryEncoder.schema())
                    .load().createTempView("partyCountries");
            countriesDF = session.sql(" select ctzshp_cntry_cd, prty_id, rowid_object  from partyCountries ");//.as(partyCitizenshipCountryEncoder);
           cacheCountriesDF = countriesDF.cache().as(partyCitizenshipCountryEncoder);
                long cnt = cacheCountriesDF.count();
            long t2 = System.currentTimeMillis();
                logger.info("Party Countries data loaded from iv_c_prty_ctzshp_cntry");
                logger.info("Time consumed in loading Countries Party: " + cnt + " Rows: " + (t2 - t1));
            return cacheCountriesDF;
          }
         } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheCountriesDF;
    }


    /**
     * 7. Load the screeningInfo data from Denodo countriesDF screeningInfoDF
     * */
    public Dataset <Row> screeningInfoDF(SparkSession session)  {

        Encoder<ScreenInfo> partyScrnEncoder = Encoders.bean(ScreenInfo.class);
        Dataset<Row> screeningInfoDF =null;
         try {
             if (cacheScreeningInfoDF != null) {
                 logger.info("Party Screening data --Serving from Cache. Cache Count is: " );//+ cacheScreeningInfoDF.count()
              //   return cacheScreeningInfoDF;

             } else {
                 logger.info(" Party Screening View DB operation starting...");
                 long t1 = System.currentTimeMillis();
                 session.read().format("jdbc")
                        .option("url", URL)
                        .option("driver", DRIVER)
                        .option("dbtable", "dv_prty_adv_pep_san")
                        .load().createTempView("partyScreeningInfo");
                 screeningInfoDF = session.sql(" select * from partyScreeningInfo ");//.as(partyScrnEncoder);

                 cacheScreeningInfoDF = screeningInfoDF.cache();
                 long cnt = cacheScreeningInfoDF.count();
                 long t2 = System.currentTimeMillis();//dv_prty_adv_pep_san
                 logger.info("Party Address data loaded from dv_prty_adv_pep_san");
                 logger.info("Time consumed in loading Party Screening: " + cnt + " Rows: " + (t2 - t1));
           //  return cacheScreeningInfoDF;
         }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheScreeningInfoDF;
    }


    /**
     * 8. Load the KYA_properties_party_sql data from Denodo countriesDF screeningInfoDF
     * */
    public Dataset <Row> kyaPropertiesDF(SparkSession session)  {
        Encoder<KyaProperties> kyaPropertiesEncoder = Encoders.bean(KyaProperties.class);

        Dataset<Row> kyaPropertiesDF =null;
        try {

             if (cacheKyaPropDF != null) {
                 logger.info("KYA Properties data --Serving from Cache. Cache Count is: " );//+ cacheKyaPropDF.count()
                //   return cacheScreeningInfoDF;

            } else {
                 logger.info(" KYA Properties View DB operation starting...");
                 logger.info(" KYA Properties View DB operation starting...");
                long t1 = System.currentTimeMillis();
                session.read().format("jdbc")
                        .option("url", URL)
                        .option("driver", DRIVER)
                        .option("dbtable", "iv_score_acct_char_val")
                        .load().createTempView("kyaProperties");
                 kyaPropertiesDF = session.sql(" select * from kyaProperties ").orderBy("prty_id");//.as(kyaPropertiesEncoder);

                 cacheKyaPropDF = kyaPropertiesDF.cache();
                 long cnt = cacheKyaPropDF.count();

                long t2 = System.currentTimeMillis();
                 logger.info("KYA Properties data loaded from iv_score_acct_char_val");
               //  logger.info(" KYA Properties View DB operation ended..."+(t2 - t1));
                 logger.info("Time consumed in loading KYA Properties data: " + cnt + " Rows: " + (t2 - t1));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheKyaPropDF;
    }

    /**
     * 9. Load the KYC_properties data from Denodo countriesDF screeningInfoDF
     * */
    public Dataset <KycProperties> kycPropertiesDF(SparkSession session)  {
        Encoder<KycProperties> kycPropertiesEncoder = Encoders.bean(KycProperties.class);

        Dataset<KycProperties> kycPropertiesDF =null;
        try {

            if (cacheKycPropDF != null) {
                logger.info("KYC Properties data --Serving from Cache. Cache Count is: " + cacheKycPropDF.count());
                //   return cacheScreeningInfoDF;

            } else {
                logger.info(" KYC Properties View DB operation starting...");
                long t1 = System.currentTimeMillis();
                session.read().format("jdbc")
                        .option("url", URL)
                        .option("driver", DRIVER)
                        .option("dbtable", "" +
                                "iv_score_party_char_val")
                        .load().createTempView("kycProperties");
                kycPropertiesDF = session.sql(" select * from kycProperties ").as(kycPropertiesEncoder);
                cacheKycPropDF = kycPropertiesDF.cache();
                long cnt = cacheKycPropDF.count();
                long t2 = System.currentTimeMillis();
                logger.info("KYC Properties data loaded from iv_score_acct_char_val");
                logger.info("Time consumed in loading KYC Properties data: " + cnt + " Rows: " + (t2 - t1));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheKycPropDF;
    }
//iv_score_prty_acct_rel_prty
    /**
     * 10. Load the Party to Account data from Denodo countriesDF screeningInfoDF
     * */
    public Dataset <Row> party2AccntDF(SparkSession session)  {
        Encoder<RelatedPartyP2a> party2AccntEncoder = Encoders.bean(RelatedPartyP2a.class);

        Dataset<Row> party2AccntDF =null;
        try {

            if (cacheParty2AccntfDF != null) {
                logger.info("Party2Account data --Serving from Cache. Cache Count is: " + cacheParty2AccntfDF.count());
                //   return cacheScreeningInfoDF;

            } else {
                logger.info(" Party2Account View DB operation starting...");
                long t1 = System.currentTimeMillis();
                session.read().format("jdbc")
                        .option("url", URL)
                        .option("driver", DRIVER)
                        .option("dbtable", "" +
                                "iv_score_prty_acct_rel_prty")
                        .load().createTempView("party2Account");
                party2AccntDF = session.sql(" select * from party2Account ");//.as(kycPropertiesEncoder);
                cacheParty2AccntfDF = party2AccntDF.cache();
                long cnt = cacheParty2AccntfDF.count();
                long t2 = System.currentTimeMillis();
                logger.info("Party2Account data loaded from iv_score_acct_char_val");
                logger.info("Time consumed in loading Party2Account data: " + cnt + " Rows: " + (t2 - t1));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheParty2AccntfDF;
    }








}
