package com.cit.kyc.cache.service;

import com.cit.kyc.cache.repository.KYCRecordRepository;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * Created by sharmd01 on 12/28/2017.
 */

@Service
public class DFCreator {

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
    public static Dataset<Row> cacheOrgDF=null;

    public static Dataset<Row> cacheClassificDF=null; //
    public static Dataset<Row> cacheCountriesDF=null;
    public static Dataset<Row> cacheScreeningInfoDF=null;

  //  public static Dataset<Row> cacheClassifDF=null;
   // public static Dataset<Row> cacheorgDF=null;
   // public static Dataset<Row> cacheClassifDF=null;
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
                System.out.println("Party View DB operation starting...");
                 session.read().format("jdbc")
                        .option("url", URL)
                        .option("driver", DRIVER)
                        .option("dbtable", "iv_c_prty") //iv_c_prty
                        .load().createTempView("party");//.limit(100);
                partyDF = session.sql(" select * from party ");
                cachePartyDF = partyDF.cache();
            //    cachePartyDF =cachePartyDF.orderBy("rowid_object");
                System.out.println(" Party data loaded from iv_c_prty. Cache also loaded. "+cachePartyDF.count());
                long t2 = System.currentTimeMillis();
                //   partyDF.show();
                System.out.println("Time consumed in loading: " + (t2 - t1) + ". Row count: " + cachePartyDF.count());
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
                    System.out.println(" Related Party View DB operation starting...");
                    long t1 = System.currentTimeMillis();
                    session.read().format("jdbc")
                            .option("url", URL)
                            .option("driver", DRIVER)
                            .option("dbtable", "iv_c_prty_rltd_prty")
                            .load().createTempView("relatedParty");

                    relPartyDf = session.sql(" select * from relatedParty ");
                    long t2 = System.currentTimeMillis();
                    cacheRelPartyDF = relPartyDf.cache();
                  //  cacheRelPartyDF = cacheRelPartyDF.orderBy("rltd_prty_id");
                    // relPartyDf.show();
                    System.out.println("Time consumed in loading Realted Party: " + (t2 - t1) + ". Row Count: " + cacheRelPartyDF.count());                    //return cacheRelPartyDF;
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
        Dataset<Row> partyAddressDF =null;
         StringBuffer resp = new StringBuffer();
        try {
            if (cachePartyAddressDF != null) {
                System.out.println("Party Addresses --Serving from Cache. Cache Count is: " + cachePartyAddressDF.count());
                return cachePartyAddressDF;

            } else {
                System.out.println(" Party Addresses View DB operation starting...");
             long t1 = System.currentTimeMillis();
            session.read().format("jdbc")
                    .option("url", URL)
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_addr")
                    .load().createTempView("partyAddress");
                partyAddressDF  = session.sql(" select * from partyAddress ");
                long t2 = System.currentTimeMillis();
                cachePartyAddressDF = partyAddressDF.cache();
             System.out.println("Time consumed in loading Party Addresses: " + cachePartyAddressDF.count() + " Rows: " + (t2 - t1));
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
    public Dataset <Row> orgDF(SparkSession session)  {
        Dataset<Row> orgDF =null;

        StringBuffer resp = new StringBuffer();
        try {
            if (cacheOrgDF != null) {
                System.out.println("Party Org data --Serving from Cache. Cache Count is: " + cacheOrgDF.count());
                return cacheOrgDF;

            } else {
                System.out.println(" Party Org View DB operation starting...");
                long t1 = System.currentTimeMillis();
                 session.read().format("jdbc")
                    .option("url", URL)
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_rltd_org")
                    .load().createTempView("partyRelatedOrg");

                orgDF  = session.sql(" select * from partyRelatedOrg ");
                long t2 = System.currentTimeMillis();
                cacheOrgDF = orgDF.cache();

            System.out.println("  Party org data loaded from iv_c_prty_rltd_org");
           System.out.println("Time consumed in loading Party Org: " + cacheOrgDF.count() + " Rows.- " + (t2 - t1));
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
    public Dataset <Row> classifDF(SparkSession session)  {
        Dataset<Row> classifDF =null;
        StringBuffer resp = new StringBuffer();
        try {
            if (cacheClassificDF != null) {
                System.out.println("Party Classification data --Serving from Cache. Cache Count is: " + cacheClassificDF.count());
                return cacheClassificDF;

            } else {
                System.out.println(" Party Classification View DB operation starting...");
                long t1 = System.currentTimeMillis();
               session.read().format("jdbc")
                    .option("url", URL)
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_inds_clsf")
                    .load().createTempView("partyClassification");

                classifDF = session.sql(" select * from partyClassification ");
                long t2 = System.currentTimeMillis();
                cacheClassificDF = classifDF.cache();
            System.out.println("Party Classification data loaded from iv_c_prty_inds_clsf");
            System.out.println("Time consumed in loading Party Classification: " + cacheClassificDF.count() + " Rows: " + (t2 - t1));
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
    public Dataset <Row> countriesDF(SparkSession session)  {
        Dataset<Row> countriesDF =null;

        StringBuffer resp = new StringBuffer();
        try {
            if (cacheCountriesDF != null) {
             System.out.println("Party Countries data --Serving from Cache. Cache Count is: " + cacheCountriesDF.count());
             return cacheCountriesDF;

              } else {
            System.out.println(" Party Countries View DB operation starting...");
            long t1 = System.currentTimeMillis();
             session.read().format("jdbc")
                    .option("url", URL)
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_ctzshp_cntry")
                    .load().createTempView("partyCountries");

            countriesDF = session.sql(" select * from partyCountries ");
           cacheCountriesDF = countriesDF.cache();
            long t2 = System.currentTimeMillis();
            System.out.println("Party Countries data loaded from iv_c_prty_ctzshp_cntry");
           System.out.println("Time consumed in loading Countries Party: " + cacheCountriesDF.count() + " Rows: " + (t2 - t1));
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
        Dataset<Row> screeningInfoDF =null;
         try {
             if (cacheScreeningInfoDF != null) {
                 System.out.println("Party Screening data --Serving from Cache. Cache Count is: " + cacheScreeningInfoDF.count());
                 return cacheScreeningInfoDF;

             } else {
                 System.out.println(" Party Screening View DB operation starting...");
                 long t1 = System.currentTimeMillis();
             session.read().format("jdbc")
                     .option("url", URL)
                     .option("driver", DRIVER)
                     .option("dbtable", "dv_prty_adv_pep_san")
                     .load().createTempView("partyScreeningInfo");
                 screeningInfoDF = session.sql(" select * from partyScreeningInfo ");
                 cacheScreeningInfoDF = screeningInfoDF.cache();
                 long t2 = System.currentTimeMillis();
               System.out.println("Party Address data loaded from dv_prty_adv_pep_san");
              System.out.println("Time consumed in loading Party Screening: " + cacheScreeningInfoDF.count() + " Rows: " + (t2 - t1));
             return cacheScreeningInfoDF;
         }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheScreeningInfoDF;
    }
}
