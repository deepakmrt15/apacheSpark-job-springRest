package com.cit.kyc.cache.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name="iv_c_prty_rltd_org")
public class PartyRelatedOrganization implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924969664684600474L;
	
	//@Id
	//@Column(name = "rowid_object")
	private String rowId;
	
	//@Column(name = "prty_id")
	private String partyId;
	
//	@Column(name = "org_unit_id")
	private String orgUnitId;
	
//	@Column(name = "prty_rltd_org_typ_cd")
	private String partyRelatedOrgTypeCd;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getOrgUnitId() {
		return orgUnitId;
	}

	public void setOrgUnitId(String orgUnitId) {
		this.orgUnitId = orgUnitId;
	}

	public String getPartyRelatedOrgTypeCd() {
		return partyRelatedOrgTypeCd;
	}

	public void setPartyRelatedOrgTypeCd(String partyRelatedOrgTypeCd) {
		this.partyRelatedOrgTypeCd = partyRelatedOrgTypeCd;
	}
	
	
}
