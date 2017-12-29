package com.cit.kyc.cache.service;

import com.cit.kyc.cache.repository.KYCRecordRepository;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sharmd01 on 12/28/2017.
 */

@Configuration
public class DFCreator {

   // @Autowired
 //   private Environment env;


    /**
     * 1.Load the Party data from Denodo
     * */
    public static Dataset<Row> cacheDF=null;

    private String URL ="jdbc:vdb://q53dnoapp001t0x:9999/qmdm_kyc?user=sv_kyc_app&password=App123!@";
    private String DRIVER ="com.denodo.vdp.jdbc.Driver";

    @Autowired
    private KYCRecordRepository kycRecordRepository;


    public Dataset <Row> partyDF(SparkSession session)  {
        Dataset<Row> partyDF =null;
        StringBuffer resp = new StringBuffer();

        try {

                System.out.println(" DataFrame loaded from the entire contents of a table.");
              //  long t1 = System.currentTimeMillis();
                 partyDF = session.read().format("jdbc")
                        .option("url",URL )
                        .option("driver", DRIVER)
                        //.option("fetchSize", 500)
                        .option("dbtable", "iv_score_prty")
                        .load();
                      partyDF.printSchema();
            partyDF  =    partyDF.cache();
            System.out.println(" Party data loaded from iv_score_prty.");
           // long t2 = System.currentTimeMillis();
            partyDF.show();
            //    System.out.println("Time consumed in loading: " + (t2 - t1) + "count: " + cacheDF.count());
                return partyDF;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return partyDF;
    }

    /**
     * 2. Load the Related Party data from Denodo
     * */
    public Dataset <Row> relatedPartyDF(SparkSession session)  {
        Dataset<Row> relPartyDf =null;
        StringBuffer resp = new StringBuffer();
        try {
          //  long t1 = System.currentTimeMillis();
               relPartyDf = session.read().format("jdbc")
                        .option("url",URL )
                        .option("driver", DRIVER)
                        .option("dbtable", "iv_score_prty_rltd_prty")
                        .load();
            System.out.println(" Realted Party data loaded from iv_score_prty_rltd_prty");
                relPartyDf.printSchema();
           //    long t2 = System.currentTimeMillis();
            relPartyDf.show();
              //  System.out.println("Time consumed in loading Realted Party: " + cacheDF.count() + " Rows: " + (t2 - t1));
                return relPartyDf;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return relPartyDf;
    }

    /**
     * 3. Load the Party address data from Denodo
     * */
    public Dataset <Row> partyAddressDF(SparkSession session)  {
        Dataset<Row> partyAddressDF =null;
         StringBuffer resp = new StringBuffer();
        try {
            //  long t1 = System.currentTimeMillis();
            partyAddressDF = session.read().format("jdbc")
                    .option("url",URL )
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_addr")
                    .load();
            System.out.println("  Party Address data loaded from iv_c_prty_addr");
            partyAddressDF.printSchema();
            //    long t2 = System.currentTimeMillis();
            partyAddressDF.show();
            //  System.out.println("Time consumed in loading Realted Party: " + cacheDF.count() + " Rows: " + (t2 - t1));
            return partyAddressDF;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return partyAddressDF;
    }

    /**
     * 4. Load the Org data from Denodo
     * */
    public Dataset <Row> orgDF(SparkSession session)  {
        Dataset<Row> orgDF =null;

        StringBuffer resp = new StringBuffer();
        try {
            //  long t1 = System.currentTimeMillis();
            orgDF = session.read().format("jdbc")
                    .option("url",URL )
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_rltd_org")
                    .load();
            System.out.println("  Party Address data loaded from iv_c_prty_rltd_org");
            orgDF.printSchema();
            //    long t2 = System.currentTimeMillis();
            orgDF.show();
            //  System.out.println("Time consumed in loading Realted Party: " + cacheDF.count() + " Rows: " + (t2 - t1));
            return orgDF;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return orgDF;
    }

    /**
     * 5. Load the classif data from Denodo
     * */
    public Dataset <Row> classifDF(SparkSession session)  {
        Dataset<Row> classifDF =null;

        StringBuffer resp = new StringBuffer();
        try {
            //  long t1 = System.currentTimeMillis();
            classifDF = session.read().format("jdbc")
                    .option("url",URL )
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_inds_clsf")
                    .load();
            System.out.println("Party Address data loaded from iv_c_prty_inds_clsf");
            classifDF.printSchema();
            //    long t2 = System.currentTimeMillis();
            classifDF.show();
            //  System.out.println("Time consumed in loading Realted Party: " + cacheDF.count() + " Rows: " + (t2 - t1));
            return classifDF;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return classifDF;
    }


    /**
     * 6. Load the countries data from Denodo
     * */
    public Dataset <Row> countriesDF(SparkSession session)  {
        Dataset<Row> countriesDF =null;

        StringBuffer resp = new StringBuffer();
        try {
            //  long t1 = System.currentTimeMillis();
            countriesDF = session.read().format("jdbc")
                    .option("url",URL )
                    .option("driver", DRIVER)
                    .option("dbtable", "iv_c_prty_ctzshp_cntry")
                    .load();
            System.out.println("Party Address data loaded from iv_c_prty_ctzshp_cntry");
            countriesDF.printSchema();
            //    long t2 = System.currentTimeMillis();
            countriesDF.show();
            //  System.out.println("Time consumed in loading Realted Party: " + cacheDF.count() + " Rows: " + (t2 - t1));
            return countriesDF;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return countriesDF;
    }


    /**
     * 7. Load the screeningInfo data from Denodo
     * */
    public Dataset <Row> screeningInfoDF(SparkSession session)  {
        Dataset<Row> screeningInfoDF =null;
         try {
            //  long t1 = System.currentTimeMillis();
            screeningInfoDF = session.read().format("jdbc")
                    .option("url",URL )
                    .option("driver", DRIVER)
                    .option("dbtable", "dv_prty_adv_pep_san")
                    .load();
            System.out.println("Party Address data loaded from dv_prty_adv_pep_san");
            screeningInfoDF.printSchema();
            //    long t2 = System.currentTimeMillis();
            screeningInfoDF.show();
            //  System.out.println("Time consumed in loading Realted Party: " + cacheDF.count() + " Rows: " + (t2 - t1));
            return screeningInfoDF;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return screeningInfoDF;
    }
}
