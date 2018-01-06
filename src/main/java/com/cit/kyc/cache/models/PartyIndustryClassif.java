package com.cit.kyc.cache.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name="iv_c_prty_inds_clsf")
public class PartyIndustryClassif implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5056137346759077854L;
	
	//@Id
	//@Column(name = "rowid_object")
	private String rowId;
	
	//@Column(name = "prty_id")
	private String partyId;
	
	//@Column(name = "inds_clsf_id")
	private String industryClassifId;
	
	//@Column(name = "prty_inds_clsf_num")
	private String partyIndustryClassifNum;

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

	public String getIndustryClassifId() {
		return industryClassifId;
	}

	public void setIndustryClassifId(String industryClassifId) {
		this.industryClassifId = industryClassifId;
	}

	public String getPartyIndustryClassifNum() {
		return partyIndustryClassifNum;
	}

	public void setPartyIndustryClassifNum(String partyIndustryClassifNum) {
		this.partyIndustryClassifNum = partyIndustryClassifNum;
	}

}
