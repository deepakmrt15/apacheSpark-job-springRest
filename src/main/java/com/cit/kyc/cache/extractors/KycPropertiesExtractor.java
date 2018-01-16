package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.cit.kyc.cache.models.KycProperties;
import com.cit.kyc.cache.service.DFCreator;
import com.cit.kyc.cache.service.DFJoiner;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

@Service
public class KycPropertiesExtractor implements ResultSetExtractor<Multimap<String, KycProperties>> {

	@Autowired
	DFCreator dfCreator;
	@Autowired
	DFJoiner dfJoiner;

	@Override
	public Multimap<String, KycProperties> extractData(ResultSet rs) throws SQLException {
		Multimap<String, KycProperties> multiMapOfKycProps = ArrayListMultimap.create();
		while (rs.next()) {
			KycProperties kycProperties = new KycProperties();
			kycProperties.setCust_Tp(rs.getString("cust_tp"));
			kycProperties.setExtn_Id(rs.getString("extn_id"));
			kycProperties.setInstance_Id(rs.getString("instance_id"));
			kycProperties.setParty_Id(rs.getString("prty_id"));
			kycProperties.setProperty_Name(rs.getString("charistic_cd"));
			kycProperties.setProperty_Value(rs.getString("charistic_val"));
			kycProperties.setSeq_Num(rs.getString("seq_num"));
			multiMapOfKycProps.put(kycProperties.getParty_Id().trim(), kycProperties);
		}
		return multiMapOfKycProps;
	}

	//get the data through spark
	public Multimap<String, KycProperties> getPartyKyaProperties(SparkSession session)  {
			Multimap<String, KycProperties> multiMapOfKycProps = ArrayListMultimap.create();
		try {
			try {
			//	logger.info(" KYA Properties extract...");
				dfCreator = new DFCreator();
				Dataset<KycProperties> partyKycPropertiesDF  =  dfCreator.kycPropertiesDF(session);


				Iterator< KycProperties> itr =	partyKycPropertiesDF.toLocalIterator();
				while(itr.hasNext()){
					KycProperties kycProperties = new KycProperties();
					kycProperties =	itr.next();
					//logger.info("acct_id:-> " +row.getAs("acct_id"));
					//System.out.println("acct_nm: " +row.getAs("acct_nm"));
				//	logger.info("prty_id: " +row.getAs("prty_id"));

					multiMapOfKycProps.put(kycProperties.getParty_Id().trim(), kycProperties);
				}

			}catch (Exception e){
				e.printStackTrace();
			}
		}
		catch (Exception e){
			e.printStackTrace();

		}
		return multiMapOfKycProps;

	}

}