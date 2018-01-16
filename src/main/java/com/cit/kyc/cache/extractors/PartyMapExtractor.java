package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cit.kyc.cache.models.PartyInfo;
import com.cit.kyc.cache.models.PartyRelatedOrganization;
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
public class PartyMapExtractor implements ResultSetExtractor<Map<String, PartyInfo>> {
	@Autowired
	DFCreator dfCreator;
	@Autowired
	DFJoiner dfJoiner;

	@Override
	public Map<String, PartyInfo> extractData(ResultSet rs) throws SQLException {
		Map<String, PartyInfo> partyMap = new HashMap<>();
		while (rs.next()) {
			PartyInfo partyInfo = new PartyInfo();
			partyInfo.setBirthDt(rs.getString("brth_dt"));
			partyInfo.setBusActvTpCd(ExtractorUtil.removeMetaData(rs.getString("bus_actv_typ_cd")));
			partyInfo.setCmphId(rs.getString("cmph_ind"));
			System.out.println("cmph_ind:- "+rs.getString("cmph_ind") );
			partyInfo.setConsTurnoverAmt(rs.getString("cons_tovr_amt"));
			partyInfo.setCustLifecycleStatCd(ExtractorUtil.removeMetaData(rs.getString("cust_lcycl_stat_cd")));
			partyInfo.setDatValdnStatCd(ExtractorUtil.removeMetaData(rs.getString("dat_valdn_stat_cd")));
			partyInfo.setFirstAcctOpenDt(rs.getString("frst_acct_opn_dt"));
			partyInfo.setFirstName(rs.getString("frst_nm"));
			partyInfo.setFullName(rs.getString("full_nm"));
			partyInfo.setGender(rs.getString("gndr"));
			partyInfo.setIncorpCountryCd(rs.getString("incrptn_cntry_cd"));
			partyInfo.setIncorpDt(rs.getString("incrptn_dt"));
			partyInfo.setIncorpStateCd(rs.getString("incrptn_st_cd"));
			partyInfo.setIncorpStateKey(rs.getString("incrptn_st_key"));
			//partyInfo.setIntermediaryLcyclStatCd(intermediaryLcyclStatCd);
			partyInfo.setLastName(rs.getString("last_nm"));
			partyInfo.setLckStatCd(ExtractorUtil.removeMetaData(rs.getString("lck_stat_cd")));
			partyInfo.setLegalName(rs.getString("lgl_nm"));
			partyInfo.setLegalTypeCd(ExtractorUtil.removeMetaData(rs.getString("lgl_typ_cd")));
			partyInfo.setMiddleName(rs.getString("mdl_nm"));
			partyInfo.setMtchValdnStatCd(ExtractorUtil.removeMetaData(rs.getString("mtch_valdn_stat_cd")));
			partyInfo.setNameSuffix(rs.getString("nm_sfx"));
			partyInfo.setNonresidentAlienStatus(rs.getString("nonrsdnt_alien_stat_cd"));
			partyInfo.setParentPublicEntityFlag(rs.getString("prnt_pblc_enty_flg"));
			partyInfo.setPartyId(rs.getString("rowid_object").trim());
			partyInfo.setPartyTypeCd(ExtractorUtil.removeMetaData(rs.getString("prty_typ_cd")));
			partyInfo.setPrivateClientEffDt(rs.getString("pvt_clnt_eff_dt"));
			partyInfo.setPublicEntityFlag(rs.getString("pblc_enty_flg"));
			partyInfo.setResidenceCountryCd(rs.getString("resdnc_cntry_cd"));
			partyInfo.setRltdPrtyLcyclStatCd(ExtractorUtil.removeMetaData(rs.getString("rltd_prty_lcycl_stat_cd")));
			partyInfo.setSourceCustomerTypeCd(rs.getString("src_cust_typ_cd"));
			partyInfo.setTaxIdNum(rs.getString("tax_id_num"));
			partyInfo.setTaxIdNumTypeCd(rs.getString("tax_id_num_typ_cd"));
			partyInfo.setTaxResidenceCountryCd(rs.getString("tax_resdnc_cntry_cd"));
			partyMap.put(partyInfo.getPartyId().trim(), partyInfo);
		}
		return partyMap;
	}

	//get related Party org
	public Map<String, PartyInfo> getPartyInfo(SparkSession session){
		StringBuffer resp = new StringBuffer();
		Map<String, PartyInfo> partyMap = new HashMap<>();
		try{
			try {
				dfCreator =	new DFCreator();
				Dataset<Row> organizationDataset =  dfCreator.partyDF(session);
				Iterator<Row> itr =	organizationDataset.toLocalIterator();

				while(itr.hasNext()){
					PartyInfo partyInfo = new PartyInfo();
					Row rs = itr.next();
				//	partyInfo.setBirthDt(rs.getAs("brth_dt"));
					partyInfo.setBusActvTpCd(ExtractorUtil.removeMetaData(rs.getAs("bus_actv_typ_cd")));
					partyInfo.setCmphId(rs.getAs("cmph_ind"));
			//		partyInfo.setConsTurnoverAmt(rs.getAs("cons_tovr_amt"));
					partyInfo.setCustLifecycleStatCd(ExtractorUtil.removeMetaData(rs.getAs("cust_lcycl_stat_cd")));
					partyInfo.setDatValdnStatCd(ExtractorUtil.removeMetaData(rs.getAs("dat_valdn_stat_cd")));
				//	partyInfo.setFirstAcctOpenDt(rs.getAs("frst_acct_opn_dt"));
					partyInfo.setFirstName(rs.getAs("frst_nm"));
					partyInfo.setFullName(rs.getAs("full_nm"));
					partyInfo.setGender(rs.getAs("gndr"));
					partyInfo.setIncorpCountryCd(rs.getAs("incrptn_cntry_cd"));
				//	partyInfo.setIncorpDt(rs.getAs("incrptn_dt"));
					partyInfo.setIncorpStateCd(rs.getAs("incrptn_st_cd"));
					partyInfo.setIncorpStateKey(rs.getAs("incrptn_st_key"));
					//partyInfo.setIntermediaryLcyclStatCd(intermediaryLcyclStatCd);
					partyInfo.setLastName(rs.getAs("last_nm"));
					partyInfo.setLckStatCd(ExtractorUtil.removeMetaData(rs.getAs("lck_stat_cd")));
					partyInfo.setLegalName(rs.getAs("lgl_nm"));
					partyInfo.setLegalTypeCd(ExtractorUtil.removeMetaData(rs.getAs("lgl_typ_cd")));
					partyInfo.setMiddleName(rs.getAs("mdl_nm"));
					partyInfo.setMtchValdnStatCd(ExtractorUtil.removeMetaData(rs.getAs("mtch_valdn_stat_cd")));
					partyInfo.setNameSuffix(rs.getAs("nm_sfx"));
					partyInfo.setNonresidentAlienStatus(rs.getAs("nonrsdnt_alien_stat_cd"));
					partyInfo.setParentPublicEntityFlag(rs.getAs("prnt_pblc_enty_flg"));
					partyInfo.setPartyId(rs.getAs("rowid_object"));
					partyInfo.setPartyTypeCd(ExtractorUtil.removeMetaData(rs.getAs("prty_typ_cd")));
					//partyInfo.setPrivateClientEffDt(rs.getAs("pvt_clnt_eff_dt"));
					partyInfo.setPublicEntityFlag(rs.getAs("pblc_enty_flg"));
					partyInfo.setResidenceCountryCd(rs.getAs("resdnc_cntry_cd"));
					partyInfo.setRltdPrtyLcyclStatCd(ExtractorUtil.removeMetaData(rs.getAs("rltd_prty_lcycl_stat_cd")));
					partyInfo.setSourceCustomerTypeCd(rs.getAs("src_cust_typ_cd"));
					partyInfo.setTaxIdNum(rs.getAs("tax_id_num"));
					partyInfo.setTaxIdNumTypeCd(rs.getAs("tax_id_num_typ_cd"));
					partyInfo.setTaxResidenceCountryCd(rs.getAs("tax_resdnc_cntry_cd"));

					partyMap.put(partyInfo.getPartyId().trim(), partyInfo);

				}
			}catch (Exception e){e.printStackTrace();}


			System.out.println("partyMap size: " + partyMap.size());
			resp.append("\n partyMap size: "+String.valueOf( partyMap.size()));
		}catch (Exception e){

			e.printStackTrace();
			resp.append(e);
		}

		return partyMap;//resp.toString();
	}


}
