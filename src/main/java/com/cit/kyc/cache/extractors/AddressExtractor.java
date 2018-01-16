package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.cit.kyc.cache.models.PartyAddress;
import com.cit.kyc.cache.models.PartyCitizenshipCountry;
import com.cit.kyc.cache.service.DFCreator;
import com.cit.kyc.cache.service.DFJoiner;
import com.cit.kyc.cache.utils.ExtractorUtil;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

@Service
public class AddressExtractor implements ResultSetExtractor<Multimap<String, PartyAddress>> {

	@Autowired
	DFCreator dfCreator;
	@Autowired
	DFJoiner dfJoiner;
	@Override
	public Multimap<String, PartyAddress> extractData(ResultSet rs) throws SQLException {
		Multimap<String, PartyAddress> multiMapOfAddress = ArrayListMultimap.create();
		while (rs.next()) {
			PartyAddress partyAddress = new PartyAddress();
			partyAddress.setAddressLine1(rs.getString("addr_line_1"));
			partyAddress.setAddressLine2(rs.getString("addr_line_2"));
			partyAddress.setAddressLine3(rs.getString("addr_line_3"));
			partyAddress.setAddressLine4(rs.getString("addr_line_4"));
			partyAddress.setAddressTypeCd(ExtractorUtil.removeMetaData(rs.getString("addr_typ_cd")));
			partyAddress.setCity(rs.getString("city"));
			partyAddress.setCountryCd(rs.getString("cntry_cd"));
			partyAddress.setPartyId(rs.getString("prty_id"));
			partyAddress.setPostalCd(rs.getString("pstl_cd"));
			partyAddress.setRowid(rs.getString("rowid_object"));
			partyAddress.setStateCode(rs.getString("st_cd"));
			partyAddress.setStateKey(ExtractorUtil.removeMetaData(rs.getString("st_key")));			
			multiMapOfAddress.put(partyAddress.getPartyId().trim(), partyAddress);
		}
		return multiMapOfAddress;
	}

	//get related addresses
	public Multimap<String, PartyAddress> getPartyAddresses(SparkSession session){
		StringBuffer resp = new StringBuffer();
		Multimap<String, PartyAddress> multiMapOfAddress = ArrayListMultimap.create();
		try{

			try {
				Dataset<Row> party2ddrssDF =  dfCreator.partyAddressDF(session);
			//	System.out.println("party2ddrssDF: " +party2ddrssDF.count());
				Iterator<Row> itr =	party2ddrssDF.toLocalIterator();

				while(itr.hasNext()){
					PartyAddress partyAddress = new PartyAddress();
						Row row =	itr.next();
					//System.out.println("prty_id: " +row.getAs("prty_id"));
					partyAddress.setPartyId(row.getAs("prty_id"));
				//	System.out.println("Address Ln 1: " +row.getAs("addr_line_1"));
					partyAddress.setAddressLine1(row.getAs("addr_line_1"));
				//	System.out.println("Cntry CD: " +row.getAs("cntry_cd"));
					partyAddress.setAddressLine2(row.getAs("addr_line_2"));
					partyAddress.setAddressLine3(row.getAs("addr_line_3"));
					partyAddress.setAddressLine4(row.getAs("addr_line_4"));
					partyAddress.setAddressTypeCd(ExtractorUtil.removeMetaData(row.getAs("addr_typ_cd")));
					partyAddress.setCity(row.getAs("city"));
					partyAddress.setPostalCd(row.getAs("pstl_cd"));
					partyAddress.setRowid(row.getAs("rowid_object"));
					partyAddress.setStateCode(row.getAs("st_cd"));
					multiMapOfAddress.put(partyAddress.getPartyId().trim(), partyAddress);
				}
			}catch (Exception e){e.printStackTrace();}

			System.out.println("multiMapOfAddress size: " + multiMapOfAddress.size());

		}catch (Exception e){

			e.printStackTrace();
			resp.append(e);
		}

		return multiMapOfAddress;
	}
}