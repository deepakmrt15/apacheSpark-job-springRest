package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.cit.kyc.cache.models.PartyAddress;
import com.cit.kyc.cache.models.PartyIndustryClassif;
import com.cit.kyc.cache.service.DFCreator;
import com.cit.kyc.cache.service.DFJoiner;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;



import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

@Service
public class IndustryClassifExtractor implements ResultSetExtractor<Multimap<String, PartyIndustryClassif>> {

	@Autowired
	DFCreator dfCreator;
	@Autowired
	DFJoiner dfJoiner;

	@Override
	public Multimap<String, PartyIndustryClassif> extractData(ResultSet rs) throws SQLException {
		Multimap<String, PartyIndustryClassif> multiMapOfClassifs = ArrayListMultimap.create();
		while (rs.next()) {
//			PartyIndustryClassif partyIndustryClassif = new PartyIndustryClassif();
//			partyIndustryClassif.setIndustryClassifId(rs.getString("inds_clsf_id"));
//			partyIndustryClassif.setPrty_id(rs.getString("prty_id"));
//			partyIndustryClassif.setPartyIndustryClassifNum(rs.getString("prty_inds_clsf_num"));
//			partyIndustryClassif.setRowid_object(rs.getString("rowid_object"));
//			multiMapOfClassifs.put(partyIndustryClassif.getPrty_id().trim(), partyIndustryClassif);
//
}
		return multiMapOfClassifs;
	}

	//get related PartyIndustryClassif
	public Multimap<String, PartyIndustryClassif> getPartyIndustryClassif(SparkSession session){
		StringBuffer resp = new StringBuffer();
		Multimap<String, PartyIndustryClassif> multiMapOfClassifs = ArrayListMultimap.create();
		try{

			try {
				dfCreator =	new DFCreator();
				Dataset<PartyIndustryClassif> partyIndClassifiDF =  dfCreator.classifDF(session);

				Iterator<PartyIndustryClassif> itr =	partyIndClassifiDF.toLocalIterator();

				while(itr.hasNext()){
					PartyIndustryClassif prtyIndsClassifi = (PartyIndustryClassif)itr.next();
					//System.out.println("prty_id: " +prtyIndsClassifi.getPrty_id());
					//System.out.println("IndustryClassifId: " +prtyIndsClassifi.getInds_clsf_id());
				//	System.out.println("PartyIndustryClassifNum: " +prtyIndsClassifi.getPrty_inds_clsf_num());
					multiMapOfClassifs.put(prtyIndsClassifi.getPrty_id().trim(), prtyIndsClassifi);
				}
			}catch (Exception e){e.printStackTrace();}

		//	System.out.println("multiMapOfClassifs size: " + multiMapOfClassifs.size());
			//resp.append("\n multiMapOfClassifs size: "+multiMapOfClassifs.size());
		}catch (Exception e){

			e.printStackTrace();
			resp.append(e);
		}

		return multiMapOfClassifs;//resp.toString();
	}
}