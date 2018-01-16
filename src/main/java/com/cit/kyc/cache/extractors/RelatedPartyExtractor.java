package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.cit.kyc.cache.models.PartyIndustryClassif;
import com.cit.kyc.cache.models.RelatedPartyToParty;
import com.cit.kyc.cache.service.DFCreator;
import com.cit.kyc.cache.service.DFJoiner;
import com.cit.kyc.cache.utils.ExtractorUtil;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

@Service
public class RelatedPartyExtractor implements ResultSetExtractor<Multimap<String, RelatedPartyToParty>> {

	@Autowired
	DFCreator dfCreator;
	@Autowired
	DFJoiner dfJoiner;
	@Override
	public Multimap<String, RelatedPartyToParty> extractData(ResultSet rs) throws SQLException {
		Multimap<String, RelatedPartyToParty> multiMapOfRelPrty = ArrayListMultimap.create();
		while (rs.next()) {
			RelatedPartyToParty relatedPartyToParty = new RelatedPartyToParty();
			relatedPartyToParty.setPartyId(rs.getString("prty_id"));
			relatedPartyToParty.setPartyTypeCd(ExtractorUtil.removeMetaData(rs.getString("prty_rltd_prty_typ_cd")));
			relatedPartyToParty.setRelationshipTypeCd(rs.getString("rel_type_code"));
			relatedPartyToParty.setRelPartyId(rs.getString("rltd_prty_id"));
			relatedPartyToParty.setRowidObject(rs.getString("rowid_object"));
			multiMapOfRelPrty.put(relatedPartyToParty.getPartyId().trim(), relatedPartyToParty);
		}
		return multiMapOfRelPrty;
	}

	public Multimap<String, RelatedPartyToParty> getRelatedPartyToParty(SparkSession session){

		Multimap<String, RelatedPartyToParty> multiMapOfRelPrty = ArrayListMultimap.create();
		try{
			//Dataset<Row> party2ddrssDF =  dfJoiner.Address2Parties(session);
			try {
				dfCreator =	new DFCreator();
				dfJoiner = new DFJoiner();
				Dataset<Row> relatedPartyDF =  dfCreator.relatedPartyDF(session);
				Iterator<Row> itr =	relatedPartyDF.toLocalIterator();

				while(itr.hasNext()){
					RelatedPartyToParty relatedPartyToParty = new RelatedPartyToParty();
					Row rs = itr.next();
					relatedPartyToParty.setPartyId(rs.getAs("prty_id"));
					relatedPartyToParty.setPartyTypeCd(ExtractorUtil.removeMetaData(rs.getAs("prty_rltd_prty_typ_cd")));
					relatedPartyToParty.setRelationshipTypeCd(rs.getAs("rel_type_code"));
					relatedPartyToParty.setRelPartyId(rs.getAs("rltd_prty_id"));
					relatedPartyToParty.setRowidObject(rs.getAs("rowid_object"));
					multiMapOfRelPrty.put(relatedPartyToParty.getPartyId().trim(), relatedPartyToParty);
				}
			}catch (Exception e){e.printStackTrace();}


		//	System.out.println("multiMapOfRelPrty size: " + multiMapOfRelPrty.size());
			//resp.append("\n multiMapOfRelPrty size: "+String.valueOf( multiMapOfRelPrty.size()));
		}catch (Exception e){

			e.printStackTrace();
		//	resp.append(e);
		}

		return multiMapOfRelPrty;//resp.toString();
	}
}
