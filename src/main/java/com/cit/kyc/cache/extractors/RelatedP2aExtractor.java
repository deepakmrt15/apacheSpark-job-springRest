package com.cit.kyc.cache.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cit.kyc.cache.models.RelatedPartyP2a;
import com.cit.kyc.cache.utils.ExtractorUtil;
import org.springframework.jdbc.core.ResultSetExtractor;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class RelatedP2aExtractor implements ResultSetExtractor<Multimap<String, RelatedPartyP2a>> {

	@Override
	public Multimap<String, RelatedPartyP2a> extractData(ResultSet rs) throws SQLException {
		Multimap<String, RelatedPartyP2a> multiMapOfP2a = ArrayListMultimap.create();
		while (rs.next()) {
			RelatedPartyP2a relatedPartyP2a = new RelatedPartyP2a();
			relatedPartyP2a.setAccountId(rs.getString("account_id"));
			relatedPartyP2a.setAccountNumber(rs.getString("account_num"));
			relatedPartyP2a.setAdvCategoryCode(rs.getString("adv_category_code"));
			relatedPartyP2a.setAdvEventDesc(rs.getString("adv_event_desc"));
			relatedPartyP2a.setAdvStatus(rs.getString("adv_status"));
			relatedPartyP2a.setAdvSubCategory(rs.getString("adv_sub_category"));
			relatedPartyP2a.setPepAddStatus(rs.getString("pep_add_status"));
			relatedPartyP2a.setPepRiskLevel(rs.getString("pep_risk_level"));
			relatedPartyP2a.setPepLevel(rs.getString("pep_level"));
			relatedPartyP2a.setPepStatus(rs.getString("pep_status"));
			relatedPartyP2a.setPepType(rs.getString("pep_type"));
			relatedPartyP2a.setPmid(rs.getString("pmid"));
			relatedPartyP2a.setProductType(rs.getString("prod_typ"));
			relatedPartyP2a.setRelatedPartyAccountRelationship(ExtractorUtil.removeMetaData(rs.getString("prty_rltd_acct_typ_cd")));
			relatedPartyP2a.setRelatedPartyId(rs.getString("rel_party_id"));
			relatedPartyP2a.setSanAlertState(rs.getString("san_alert_state"));
			relatedPartyP2a.setSanAlertStatus(rs.getString("san_alert_status"));
			relatedPartyP2a.setSanStatus(rs.getString("san_status"));
			multiMapOfP2a.put(relatedPartyP2a.getPmid().trim(), relatedPartyP2a);
		}
		return multiMapOfP2a;
	}
}