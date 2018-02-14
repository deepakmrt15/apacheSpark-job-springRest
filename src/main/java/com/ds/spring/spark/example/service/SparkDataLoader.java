package com.ds.spring.spark.example.service;


import com.ds.spring.spark.example.config.ApplicationProperties;
import org.apache.calcite.avatica.Meta;
import org.apache.spark.sql.*;
import org.apache.spark.util.SizeEstimator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ds on 12/13/2017.
 */
@Service
@Configuration
public class SparkDataLoader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static Dataset<Row> cacheDF=null;

public String loadCSV2DB(SparkSession session){
    String cnt= new String();

    StringBuffer resp = new StringBuffer();
    String url = "jdbc:mysql://localhost:3306/ds";
    String table = "inspection";//
    String user = "root";
    String password = "";
    String path="inspections_plus.csv";

Properties prop= new Properties();
prop.setProperty("user", user);
prop.setProperty("password", password);


    try {
        if (cacheDF!=null){
            System.out.println("Serving from Cache.");
            long t1 = System.currentTimeMillis();
            cnt = String.valueOf(cacheDF.count());
            long t2 = System.currentTimeMillis();
            System.out.println("Time consumed in loading from Cache: " + (t2 - t1));
            resp.append("Time consumed in loading from Cache: ");
            resp.append((t2 - t1));
            resp.append(" Cache contains rows: ");
            resp.append(cnt);
            //return resp.toString();

        }else{
            System.out.println("Cache is null..Now loading it.");
            long t1 = System.currentTimeMillis();
//read the data from csv file and insert into a table. Table is created if not exists, if exists then data is appended.
            //since we are checking for cached data above at line 44 so append will not be in effect after the very 1st call.
            session.read().format("jdbc").csv(path).write()
                    .mode("append").jdbc(url,table,prop);

            Map<String, String> jdbcOptions = new HashMap<String, String>();
            jdbcOptions.put("url", url);
            jdbcOptions.put("user", user);
            jdbcOptions.put("password", password);
            jdbcOptions.put("dbtable", table);

//read the data from the table.
            cacheDF = session.read().jdbc(url,table,prop).cache();
            // businessDS.printSchema();
//action count is called on the dataset
            cnt = String.valueOf(cacheDF.count());
            System.out.println("INFO data set loaded."+ cnt);
            long t2 = System.currentTimeMillis();
            resp.append("Time consumed in Loading csv data, create table, insert data and reading from the table to load cache: ");
            resp.append((t2 - t1));
            resp.append(" Cache contains rows: ");
            resp.append(cnt);
            System.out.println("TTime consumed in Loading csv data, create table, insert data and reading from the table to load cache: " + (t2 - t1) );

        }

    }catch (Exception e){
 System.out.println("Exception in load data from csv to table: "+e.getLocalizedMessage());
    }
    return resp.toString();
}


    }