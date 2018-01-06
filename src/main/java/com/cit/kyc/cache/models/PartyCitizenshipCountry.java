package com.cit.kyc.cache.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "iv_c_prty_ctzshp_cntry")
public class PartyCitizenshipCountry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6487678553576658706L;

	//@Id
//	@Column(name = "rowid_object")
	private String rowId;

//	@Column(name = "prty_id")
	private String partyId;

	//@Column(name = "ctzshp_cntry_cd")
	private String citizenshipCountryCd;

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

	public String getCitizenshipCountryCd() {
		return citizenshipCountryCd;
	}

	public void setCitizenshipCountryCd(String citizenshipCountryCd) {
		this.citizenshipCountryCd = citizenshipCountryCd;
	}
	
	

}
