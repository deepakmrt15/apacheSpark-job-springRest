package com.cit.kyc.cache.controllers;

import com.cit.kyc.cache.extractors.*;
import com.cit.kyc.cache.repository.KYCRecordRepository;
import com.cit.kyc.cache.service.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    DFCreator cacheBuilder;

    @Autowired
    CachingWrapper cachingWrapper;

    @Autowired
    DFJoiner dfJoiner;
@Autowired
AddressExtractor addr;

    @Autowired
    KyaPropertiesExtractor kyaPrp;

    @Autowired
    RelatedOrgExtractor rltdOrg; //ScreenInfoExtractor screenExtrctr

    @Autowired
    PartyMapExtractor partyMap;

    @Autowired
     ScreenInfoExtractor screenExtrctr ;

    @Autowired
    RelatedPartyExtractor rltdPartyExtctr ;

    @Autowired
    CitizenshipCountryExtractor ctznExtctr ; //CitizenshipCountryExtractor

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
        IndustryClassifExtractor indsEx = new IndustryClassifExtractor();
        StringBuffer finalCount = new StringBuffer();
        String rtnCnt = new String();
      //  rtnCnt= kycInfoCacheLoader.loadKycInfoDataFrame(getOrCreateSparkSession());
        // rtnCnt= kycInfoCacheLoader.denodoLoader(getOrCreateSparkSession());

        long partyInfoCnt = partyMap.getPartyInfo(getOrCreateSparkSession()).size();
        long rltdPartyCnt= rltdPartyExtctr.getRelatedPartyToParty(getOrCreateSparkSession()).size();
      // rtnCnt=cachingWrapper.getPartyKyaProperties(getOrCreateSparkSession());
        long partyAddressCnt = addr.getPartyAddresses(getOrCreateSparkSession()).size();
        long indsCnt =   indsEx.getPartyIndustryClassif(getOrCreateSparkSession()).size();
        long ctznCnt =   ctznExtctr.getAllCitizenshipCountries(getOrCreateSparkSession()).size();
        //long kyaCnt = kyaPrp.getPartyKyaProperties(getOrCreateSparkSession()).size();
        //long orgCnt = rltdOrg.getPartyOrg(getOrCreateSparkSession()).size(); //partyMap
       long partyScreeningInfoCnt =screenExtrctr.getScreeningInfo(getOrCreateSparkSession()).size(); //

        finalCount.append("Party Count: "+partyInfoCnt);
        finalCount.append(System.getProperty("line.separator")+" Related Party Count: "+rltdPartyCnt);
        finalCount.append(System.getProperty("line.separator")+" Party Address Count: "+partyAddressCnt);
        finalCount.append(System.getProperty("line.separator")+" Party Industry Classification Count: "+indsCnt);
        finalCount.append(System.getProperty("line.separator")+" Party Citizen Count: "+ctznCnt);
        finalCount.append(System.getProperty("line.separator")+" Party Screening Info Count: "+partyScreeningInfoCnt);

       rtnCnt = " KYC data loaded: "+ finalCount.toString();

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
