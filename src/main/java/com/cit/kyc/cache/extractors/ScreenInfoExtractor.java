package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cit.kyc.cache.models.PartyAddress;
import com.cit.kyc.cache.models.ScreenInfo;
import com.cit.kyc.cache.service.DFCreator;
import com.cit.kyc.cache.service.DFJoiner;
import com.cit.kyc.cache.utils.ExtractorUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;


@Service
public class ScreenInfoExtractor implements ResultSetExtractor<Map<String, ScreenInfo>> {

	@Autowired
	DFCreator dfCreator;
	@Autowired
	DFJoiner dfJoiner;

	@Override
	public Map<String, ScreenInfo> extractData(ResultSet rs) throws SQLException {
		Map<String, ScreenInfo> mapOfScreenInfo = new HashMap<>();
		while (rs.next()) {
			ScreenInfo screenInfo = new ScreenInfo();
			screenInfo.setAdvCategoryCode(rs.getString("adv_category_code"));
			screenInfo.setAdvEventDesc(rs.getString("adv_event_desc"));
			screenInfo.setAdvStatus(rs.getString("adv_status"));
			screenInfo.setAdvSubCategory(rs.getString("adv_sub_category"));
			screenInfo.setPartyId(rs.getString("party_id_value"));
			screenInfo.setPepAddStatus(rs.getString("pep_add_status"));
			screenInfo.setPepLevel(rs.getString("pep_level"));
			screenInfo.setPepRiskLevel(rs.getString("pep_risk_level"));
			screenInfo.setPepStatus(rs.getString("pep_status"));
			screenInfo.setPepType(rs.getString("pep_type"));
			screenInfo.setSanAlertState(rs.getString("san_alert_state"));
			screenInfo.setSanAlertStatus(rs.getString("san_alert_status"));
			screenInfo.setSanStatus(rs.getString("san_status"));
			
			mapOfScreenInfo.put(screenInfo.getPartyId().trim(), screenInfo);
		}
		return mapOfScreenInfo;
	}

	//get screening info
	public Map<String, ScreenInfo> getScreeningInfo(SparkSession session){
		//StringBuffer resp = new StringBuffer();
		Map<String, ScreenInfo> mapOfScreenInfo = new HashMap<>();
		try{
			dfCreator =	new DFCreator();
			dfJoiner = new DFJoiner();
			try {
				Dataset<Row> party2ScreenInfoDF =  dfCreator.screeningInfoDF(session);
				//System.out.println("party2ScreenInfoDF: " +party2ScreenInfoDF.count());
				Iterator<Row> itr =	party2ScreenInfoDF.toLocalIterator();
				while(itr.hasNext()){
					ScreenInfo screenInfo = new ScreenInfo();
					Row row  =	itr.next();
					screenInfo.setAdvCategoryCode(row.getAs("adv_category_code"));
					screenInfo.setAdvEventDesc(row.getAs("adv_event_desc"));
					screenInfo.setAdvStatus(row.getAs("adv_status"));
					screenInfo.setAdvSubCategory(row.getAs("adv_sub_category"));
					screenInfo.setPartyId(row.getAs("party_id_value"));
					screenInfo.setPepAddStatus(row.getAs("pep_add_status"));
					screenInfo.setPepLevel(row.getAs("pep_level"));
					screenInfo.setPepRiskLevel(row.getAs("pep_risk_level"));
					screenInfo.setPepStatus(row.getAs("pep_status"));
					screenInfo.setPepType(row.getAs("pep_type"));
					screenInfo.setSanAlertState(row.getAs("san_alert_state"));
					screenInfo.setSanAlertStatus(row.getAs("san_alert_status"));
					screenInfo.setSanStatus(row.getAs("san_status"));
				//	System.out.println("adv_category_code: " +row.getAs("adv_category_code"));

					mapOfScreenInfo.put(screenInfo.getPartyId().trim(), screenInfo);
				}
			}catch (Exception e){e.printStackTrace();}
		//	System.out.println("mapOfScreenInfo size: " + mapOfScreenInfo.size());
		//	resp.append("\n mapOfScreenInfo: "+String.valueOf( mapOfScreenInfo.size()));
		}catch (Exception e){

			e.printStackTrace();
		//	resp.append(e);
		}
		return mapOfScreenInfo;
	}

}