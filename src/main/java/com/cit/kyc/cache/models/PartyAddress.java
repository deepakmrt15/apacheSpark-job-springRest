package com.cit.kyc.cache.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name="iv_c_prty_addr")
public class PartyAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4668505959852143406L;

	//@Id
	//@Column(name = "rowid_object")
	private String rowid;

	//@Column(name = "prty_id")
	private String partyId;

	//@Column(name = "addr_typ_cd")
	private String addressTypeCd;

	//@Column(name = "addr_line_1")
	private String addressLine1;

	//@Column(name = "addr_line_2")
	private String addressLine2;

	//@Column(name = "addr_line_3")
	private String addressLine3;

	//@Column(name = "addr_line_4")
	private String addressLine4;

	//@Column(name = "cntry_cd")
	private String countryCd;

	//@Column(name = "st_cd")
	private String stateCode;

	//@Column(name = "st_key")
	private String stateKey;

	//@Column(name = "city")
	private String city;

	//@Column(name = "pstl_cd")
	private String postalCd;

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getAddressTypeCd() {
		return addressTypeCd;
	}

	public void setAddressTypeCd(String addressTypeCd) {
		this.addressTypeCd = addressTypeCd;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getCountryCd() {
		return countryCd;
	}

	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateKey() {
		return stateKey;
	}

	public void setStateKey(String stateKey) {
		this.stateKey = stateKey;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCd() {
		return postalCd;
	}

	public void setPostalCd(String postalCd) {
		this.postalCd = postalCd;
	}
	
	
}
