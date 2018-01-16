package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cit.kyc.cache.models.PartyCitizenshipCountry;
import com.cit.kyc.cache.service.CachingWrapper;
import com.cit.kyc.cache.service.DFCreator;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

@Service
public class CitizenshipCountryExtractor implements ResultSetExtractor<Multimap<String, PartyCitizenshipCountry>> {

	@Autowired
	DFCreator dfCreator;

	@Autowired
	CachingWrapper cachingWrapper;

	@Override
	public Multimap<String, PartyCitizenshipCountry> extractData(ResultSet rs) throws SQLException {
		Multimap<String, PartyCitizenshipCountry> multiMapOfCitizCountries = ArrayListMultimap.create();
		while (rs.next()) {
			PartyCitizenshipCountry partyCitizenshipCountry = new PartyCitizenshipCountry();
		//	partyCitizenshipCountry.setCitizenshipCountryCd(rs.getString("ctzshp_cntry_cd"));
			partyCitizenshipCountry.setPrty_id(rs.getString("prty_id"));
			//partyCitizenshipCountry.setRowid_object(rs.getString("rowid_object"));
			multiMapOfCitizCountries.put(partyCitizenshipCountry.getPrty_id().trim(), partyCitizenshipCountry);
		}
		return multiMapOfCitizCountries;
	}

	//get the data through spark
	public Multimap<String, PartyCitizenshipCountry> getAllCitizenshipCountries(SparkSession session)  {
		Multimap<String, PartyCitizenshipCountry> multiMapOfCitizCountries = ArrayListMultimap.create();
        try {
              try {
				  dfCreator = new DFCreator();
				  Dataset<PartyCitizenshipCountry> partyCitizenshipCountryDF  =  dfCreator.countriesDF(session);
				  Iterator< PartyCitizenshipCountry> itr =	partyCitizenshipCountryDF.toLocalIterator();
				  while(itr.hasNext()){
					  PartyCitizenshipCountry ctznCntries = itr.next();
					  //	System.out.println("ctzshp_cntry_cd: " +ctznCntries.getCtzshp_cntry_cd());
					  //	System.out.println("prty_id: " +ctznCntries.getPrty_id());
					  //	System.out.println("Rowid_object: " +ctznCntries.getPrty_id());
					  multiMapOfCitizCountries.put(ctznCntries.getPrty_id().trim(), ctznCntries);
				  }

			  }catch (Exception e){
              	e.printStackTrace();
			  }
		}
		catch (Exception e){

            e.printStackTrace();

		}

return multiMapOfCitizCountries;

	}


}