package com.cit.kyc.cache.service;

import com.cit.kyc.cache.models.KycInfoRecord;
import com.cit.kyc.cache.repository.KYCRecordRepository;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sharmd01 on 12/13/2017.
 */
@Service
public class KYCInfoCacheLoader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static Dataset<Row> cacheDF=null;

    @Autowired
    private KYCRecordRepository kycRecordRepository;

    public String loadKycInfoDataFrame(SparkSession session) {
        StringBuffer resp = new StringBuffer();
        try {

            if (cacheDF!=null){
                System.out.println("Serving from Cache.");
                long t1 = System.currentTimeMillis();
             //   cacheDF.write().json("RDC_INQUIRY");
                long t2 = System.currentTimeMillis();
                System.out.println("Time consumed in loading and writing to file from Cache: " + (t2 - t1) + " Cache count: " + cacheDF.cache().count());
                resp.append("Time consumed in loading and writing to file from Cache: ");
                resp.append((t2 - t1));
                resp.append(" Row count: ");
                resp.append(cacheDF.count());
                return resp.toString();

            }else {
                System.out.println("Cache is null..Now loading it.");
                Map<String, String> jdbcOptions = new HashMap<String, String>();
                jdbcOptions.put("url", "jdbc:oracle:thin:scott/scott@localhost:1521:orcl");
                jdbcOptions.put("dbtable", "( select * from SCOTT.RDC_INQUIRY )"); //"(WHERE rownum<201) as t"
                //jdbcOptions.put("fetchSize", "250");
                // jdbcOptions.put("user", "scott");
                //  jdbcOptions.put("password", "scott");
                long t1 = System.currentTimeMillis();
                Dataset<Row> kycInfoDataFrame = session.read().format("jdbc").options(jdbcOptions).load();
                cacheDF = kycInfoDataFrame;
                System.out.println("KYC INFO data set loaded.");
                cacheDF.printSchema();

                cacheDF.cache();
             //   cacheDF.write().json("RDC_INQUIRY"); partyDF.createOrReplaceTempView("PARTY");
             //   session.sql("SELECT * FROM PARTY").toLocalIterator().next();
                cacheDF.alias("sample").select("*")//("TRACKING_ID", "CUSTOMER_TYPE", "CUSTOMER_NAME", "REVIEW_STATUS")
                        .foreach((Row row) -> {
                    System.out.println("ID_TYPE: " + row.getAs("ID_TYPE")+", TRACKING_ID: " + row.getAs("TRACKING_ID")
                    +", CUSTOMER_TYPE: "+
                            row.getAs("CUSTOMER_TYPE")+ ", CUSTOMER_NAME: "+
                            row.getAs("CUSTOMER_NAME")+ ", REVIEW_STATUS: "+
                            row.getAs("REVIEW_STATUS"));

                          //  resp.append(row.toString());
                });



                long t2 = System.currentTimeMillis();
                resp.append("Time consumed in loading and writing to file: ");
                resp.append((t2 - t1));
                resp.append(" Row count: ");
                resp.append(cacheDF.count());

                System.out.println("Time consumed in loading and writing to file: " + (t2 - t1) + " count: " + cacheDF.cache().count());
                return resp.toString();
            }
        } catch (Exception e) {
            System.out.println("Error while loading from DB: "+ e.getMessage());
            e.printStackTrace();
            resp.append(e);
            System.out.println("Error while loading from DB: "+ e);
        }
       return resp.toString();
    }

    public void denodoDataLoader(SparkSession session)  {
        // Load properties from file
        try {
        Properties dbProperties = new Properties();
       // dbProperties.load(new FileInputStream(new File("application.yml")));
      //  String jdbcUrl = dbProperties.getProperty("spring.targetdatasource.url");

            System.out.println("A DataFrame loaded from the entire contents of a table over JDBC.");
            long t1 = System.currentTimeMillis();
            Dataset<Row> entireDF = session.read().format("jdbc")
                    .option("url", "jdbc:vdb://t53dnoapp001t0x:9999/cmpl_proj?user=sharmd01&password=DnoD45!#")
                    .option("driver", "com.denodo.vdp.jdbc.Driver")
                    //.option("fetchSize", 500)
                    .option("dbtable", "RDC_INQUIRY")
                    .load();
                    //.option("dbtable", "(SELECT * from RDC_INQUIRY where customer_id IN ( '000000000062944112', '000000000065887395', '000000000066515646', '000000000062944109', '000000000062694542'))").load();
            entireDF.printSchema();
            entireDF.cache();
            entireDF.show();
           // entireDF.reg
          //  entireDF.write().json("/u1/apps/Spark/KYC_file");//RDC_INQUIRY ///u1/apps/Spark/KYC_file
            long t2 = System.currentTimeMillis();
            System.out.println("Time consumed in loading: " + (t2 - t1) + "count: " + entireDF.count());
            // String srcTable = "SCOTT.RDC_INQUIRY";
            // Dataset<Row> entireDF = spark.read().jdbc(jdbcUrl, srcTable, dbProperties);
         //   entireDF.printSchema();


            //     spark.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



 /**
  * */

    public String denodoLoader(SparkSession session)  {
          Dataset<Row> cachedData =null;
        StringBuffer resp = new StringBuffer();

        try {
            if (cacheDF!=null){
                System.out.println("Serving from Cache.");
                long t1 = System.currentTimeMillis();
                cacheDF.write().json("/u1/apps/Spark/KYC_file");
                long t2 = System.currentTimeMillis();
                System.out.println("Time consumed in loading and writing to file from Cache: " + (t2 - t1) + " Cache count: " + cacheDF.cache().count());
                resp.append("Time consumed in loading and writing to file from Cache: ");
                resp.append((t2 - t1));
                resp.append(" Row count: ");
                resp.append(cacheDF.count());
                return resp.toString();

            }else{
                System.out.println("Cache is null...Reloading it now...");
                System.out.println(" DataFrame loaded from the entire contents of a table.");
                long t1 = System.currentTimeMillis();
                Dataset<Row> entireDF = session.read().format("jdbc")
                        .option("url", "jdbc:vdb://t53dnoapp001t0x:9999/cmpl_proj?user=sharmd01&password=DnoD45!#")
                        .option("driver", "com.denodo.vdp.jdbc.Driver")
                        //.option("fetchSize", 500)
                        .option("dbtable", "RDC_INQUIRY")
                        .load();
                //.option("dbtable", "(SELECT * from RDC_INQUIRY where customer_id IN ( '000000000062944112', '000000000065887395', '000000000066515646', '000000000062944109', '000000000062694542'))").load();
                entireDF.printSchema();
                cacheDF  =    entireDF.cache();
                cacheDF.write().json("/u1/apps/Spark/KYC_file");
                long t2 = System.currentTimeMillis();
                cacheDF.show();
                System.out.println("Time consumed in loading: " + (t2 - t1) + "count: " + cacheDF.count());
                resp.append("Time consumed in loading and writing to file from DB: ");
                resp.append((t2 - t1));
                resp.append(" Row count: ");
                resp.append(cacheDF.count());
                return resp.toString();

            }
          } catch (Exception e) {
            e.printStackTrace();
        }
        return resp.toString();
      }

    }