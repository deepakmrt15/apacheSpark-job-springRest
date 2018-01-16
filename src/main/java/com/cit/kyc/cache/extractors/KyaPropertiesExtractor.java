package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.cit.kyc.cache.models.KyaProperties;
import com.cit.kyc.cache.service.DFCreator;
import com.cit.kyc.cache.service.DFJoiner;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

@Service
public class KyaPropertiesExtractor implements ResultSetExtractor<Multimap<String, KyaProperties>> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DFCreator dfCreator;
	@Autowired
	DFJoiner dfJoiner;
	@Override
	public Multimap<String, KyaProperties> extractData(ResultSet rs) throws SQLException {
		Multimap<String, KyaProperties> multiMapOfKyaProps = ArrayListMultimap.create();
		while (rs.next()) {
			KyaProperties kyaProperties = new KyaProperties();
			kyaProperties.setAcct_id(rs.getString("acct_id"));
			kyaProperties.setAcct_nm(rs.getString("acct_nm"));
			kyaProperties.setExtn_id(rs.getString("extn_id"));
			kyaProperties.setInstance_id(rs.getString("instance_id"));
			kyaProperties.setPrty_id(rs.getString("prty_id"));
			kyaProperties.setCharistic_cd(rs.getString("charistic_cd"));
			kyaProperties.setCharistic_val(rs.getString("charistic_val"));
			kyaProperties.setSeq_num(rs.getString("seq_num"));
			multiMapOfKyaProps.put(kyaProperties.getPrty_id().trim(), kyaProperties);
		}
		return multiMapOfKyaProps;
	}


	//get the data through spark
	public Multimap<String, KyaProperties> getPartyKyaProperties(SparkSession session)  {

		StringBuffer resp = new StringBuffer();
		Multimap<String, KyaProperties> multiMapOfKyaProperties = ArrayListMultimap.create();
		try {
			try {
				logger.info(" KYA Properties extract...");
				dfJoiner = new DFJoiner();
				Dataset<Row> partyKyaPropertiesDF  =  dfJoiner.party2KyaProperties(session);

				Iterator< Row> itr =	partyKyaPropertiesDF.toLocalIterator();
				while(itr.hasNext()){
					KyaProperties kyaProperties = new KyaProperties();
						Row row =	itr.next();
					logger.info("acct_id:-> " +row.getAs("acct_id"));
					System.out.println("acct_nm: " +row.getAs("acct_nm"));
					logger.info("prty_id: " +row.getAs("prty_id"));

					kyaProperties.setAcct_id(row.getAs("acct_id"));
					kyaProperties.setAcct_nm(row.getAs("acct_nm"));
					kyaProperties.setExtn_id(row.getAs("extn_id"));
					kyaProperties.setInstance_id(row.getAs("instance_id"));
					kyaProperties.setPrty_id(row.getAs("prty_id"));
					kyaProperties.setCharistic_cd(row.getAs("charistic_cd"));
					kyaProperties.setCharistic_val(row.getAs("charistic_val"));
					kyaProperties.setSeq_num(row.getAs("seq_num"));

					multiMapOfKyaProperties.put(kyaProperties.getPrty_id().trim(), kyaProperties);
				}

			}catch (Exception e){
				e.printStackTrace();
			}
		}
		catch (Exception e){

			e.printStackTrace();
			resp.append(e);
		}

		return multiMapOfKyaProperties;

	}
}