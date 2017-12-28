package com.cit.kyc.cache.controllers;

import com.cit.kyc.cache.models.KycInfoRecord;
import com.cit.kyc.cache.repository.KYCRecordRepository;
import com.cit.kyc.cache.service.DataLoader;
import com.cit.kyc.cache.service.KYCInfoCacheLoader;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KycInfoController {

    private final String sparkCluster = "spark://kyccache.local:7077";
    private final String sparklocal = "local[*]";
    private final String tempWareHouse = "/u1/apps/Spark"; ///u1/apps/Spark C:\dev\sparkWarehouse

    private JavaSparkContext sparkContext;
    private SparkSession sparkSession;

    @Autowired
    private KYCRecordRepository trxRecordRepository;

    @Autowired
    private DataLoader dataLoader;

    @Autowired
    private KYCInfoCacheLoader kycInfoCacheLoader;

    @RequestMapping("/")
    public String index() {
        return "Hey!! What's up? Want a sparky cahce?";
    }

//    @RequestMapping(method= RequestMethod.GET, path="/trx")
//    public @ResponseBody Iterable<KycInfoRecord> fetchPagedTransactionRecords() {
//        return trxRecordRepository.findAll();
//    }

    @RequestMapping(method=RequestMethod.GET, path = "/kyc/spark/load-cache")
    public String loadDBDataToSpark() {
        String rtnCnt = new String();
        rtnCnt=  kycInfoCacheLoader.loadKycInfoDataFrame(getOrCreateSparkSession());
        //kycInfoCacheLoader.denodoDataLoader(getOrCreateSparkSession());
        return "KYC data loaded. \n"+ rtnCnt;
    }


    @RequestMapping(method=RequestMethod.GET, path = "/kyc/spark/testCache")
    public String testCache() {
        String rtnCnt = new String();
      //  rtnCnt= kycInfoCacheLoader.loadKycInfoDataFrame(getOrCreateSparkSession());
        rtnCnt= kycInfoCacheLoader.denodoLoader(getOrCreateSparkSession());
        rtnCnt ="KYC data loaded." + "\n"+ rtnCnt;
        return rtnCnt;
    }
//    @RequestMapping(method=RequestMethod.GET, path = "/kyc/spark/load")
//    public String loadDataToSpark() {
//        kycInfoCacheLoader.loadTransactions(getOrCreateSparkContext());
//        return "data loaded";
//    }


    private JavaSparkContext getOrCreateSparkContext() {
        if (this.sparkContext != null)
            return this.sparkContext;
        return new JavaSparkContext(getSparkConf());
    }

    private SparkSession getOrCreateSparkSession() {
        if (this.sparkSession != null)
            return this.sparkSession;
        return getSparkSession();
    }

    private SparkConf getSparkConf() {
        return new SparkConf()
                .setAppName("KycInfoCacheLoader")
                .setMaster(sparkCluster);
    }

    private SparkSession getSparkSession() {
        return SparkSession.builder()
                .appName("KycInfoCacheLoaderSQL")
                .master(sparklocal)//(sparkCluster)
                .config("spark.sql.warehouse.dir", tempWareHouse)
                .getOrCreate();
    }

}
