package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.cit.kyc.cache.models.PartyRelatedOrganization;
import com.cit.kyc.cache.service.DFCreator;
import com.cit.kyc.cache.service.DFJoiner;
import com.cit.kyc.cache.utils.ExtractorUtil;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

@Service
public class RelatedOrgExtractor implements ResultSetExtractor<Multimap<String, PartyRelatedOrganization>> {
	@Autowired
	DFCreator dfCreator;
	@Autowired
	DFJoiner dfJoiner;

	@Override
	public Multimap<String, PartyRelatedOrganization> extractData(ResultSet rs) throws SQLException {
		Multimap<String, PartyRelatedOrganization> multiMapOfOrg = ArrayListMultimap.create();
		while (rs.next()) {
			PartyRelatedOrganization relatedOrganization = new PartyRelatedOrganization();
			relatedOrganization.setOrg_unit_id(ExtractorUtil.removeMetaData(rs.getString("org_unit_id")));
			relatedOrganization.setPrty_id(rs.getString("prty_id"));
			relatedOrganization.setPrty_rltd_org_typ_cd(ExtractorUtil.removeMetaData(rs.getString("prty_rltd_org_typ_cd")));
			relatedOrganization.setRowid_object(rs.getString("rowid_object"));
			multiMapOfOrg.put(relatedOrganization.getPrty_id().trim(), relatedOrganization);
		}
		return multiMapOfOrg;
	}

	//get related Party org
	public Multimap<String, PartyRelatedOrganization> getPartyOrg(SparkSession session){
		StringBuffer resp = new StringBuffer();
		Multimap<String, PartyRelatedOrganization> multiMapOfOrg = ArrayListMultimap.create();
		try{
			//Dataset<Row> party2ddrssDF =  dfJoiner.Address2Parties(session);
			try {
				dfCreator =	new DFCreator();
				Dataset<PartyRelatedOrganization> organizationDataset =  dfCreator.orgDF(session);

				Iterator<PartyRelatedOrganization> itr =	organizationDataset.toLocalIterator();

				while(itr.hasNext()){
					PartyRelatedOrganization relatedOrganization = new PartyRelatedOrganization();
					 relatedOrganization = itr.next();
				//	System.out.println("prty_id: " +relatedOrganization.getPrty_id());
				//	System.out.println("Org_unit_id: " +ExtractorUtil.removeMetaData(relatedOrganization.getOrg_unit_id()));
				//	System.out.println("Prty_rltd_org_typ_cd: " +ExtractorUtil.removeMetaData(relatedOrganization.getPrty_rltd_org_typ_cd()));

					relatedOrganization.setOrg_unit_id(ExtractorUtil.removeMetaData(relatedOrganization.getOrg_unit_id()));
					relatedOrganization.setPrty_id(relatedOrganization.getPrty_id());
					relatedOrganization.setPrty_rltd_org_typ_cd(ExtractorUtil.removeMetaData(relatedOrganization.getPrty_rltd_org_typ_cd()));
					relatedOrganization.setRowid_object(relatedOrganization.getRowid_object());

					multiMapOfOrg.put(relatedOrganization.getPrty_id().trim(), relatedOrganization);

				}
			}catch (Exception e){e.printStackTrace();}


			System.out.println("multiMapOfOrg size: " + multiMapOfOrg.size());
			resp.append("\n multiMapOfOrg size: "+String.valueOf( multiMapOfOrg.size()));
		}catch (Exception e){

			e.printStackTrace();
			resp.append(e);
		}

		return multiMapOfOrg;//resp.toString();
	}
}