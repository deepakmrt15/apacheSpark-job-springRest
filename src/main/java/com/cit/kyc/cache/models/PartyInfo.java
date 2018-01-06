package com.cit.kyc.cache.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


//@Table(name="iv_score_prty")
public class PartyInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3403844295158878701L;
	
	//@Id
	////@Column(name = "rowid_object")
	private String partyId;

	////@Column(name = "prty_typ_cd")
	private String partyTypeCd;

	////@Column(name = "lgl_typ_cd")
	private String legalTypeCd;

	//@Column(name = "bus_actv_typ_cd")
	private String busActvTpCd;

	//@Column(name = "resdnc_cntry_cd")
	private String residenceCountryCd;

	//@Column(name = "brth_dt")
	private String birthDt;

	//@Column(name = "nm_sfx")
	private String nameSuffix;

	//@Column(name = "frst_nm")
	private String firstName;

	//@Column(name = "last_nm")
	private String lastName;

	//@Column(name = "mdl_nm")
	private String middleName;

	//@Column(name = "full_nm")
	private String fullName;

	//@Column(name = "lgl_nm")
	private String legalName;

	//@Column(name = "tax_id_num")
	private String taxIdNum;

	//@Column(name = "tax_id_num_typ_cd")
	private String taxIdNumTypeCd;

	//@Column(name = "frst_acct_opn_dt")
	private String firstAcctOpenDt;

	//@Column(name = "incrptn_cntry_cd")
	private String incorpCountryCd;

	//@Column(name = "incrptn_st_cd")
	private String incorpStateCd;

	//@Column(name = "incrptn_st_key")
	private String incorpStateKey;

	//@Column(name = "incrptn_dt")
	private String incorpDt;

	//@Column(name = "gndr")
	private String gender;

	//@Column(name = "pblc_enty_flg")
	private String publicEntityFlag;

	//@Column(name = "prnt_pblc_enty_flg")
	private String parentPublicEntityFlag;

	//@Column(name = "nonrsdnt_alien_stat_cd")
	private String nonresidentAlienStatus;

	//@Column(name = "dat_valdn_stat_cd")
	private String datValdnStatCd;

	//@Column(name = "pvt_clnt_eff_dt")
	private String privateClientEffDt;

	//@Column(name = "cmph_ind")
	private String cmphId;

	//@Column(name = "lck_stat_cd")
	private String lckStatCd;

	//@Column(name = "mtch_valdn_stat_cd")
	private String mtchValdnStatCd;

	//@Column(name = "cust_lcycl_stat_cd")
	private String custLifecycleStatCd;

	//@Column(name = "rltd_prty_lcycl_stat_cd")
	private String rltdPrtyLcyclStatCd;

	//@Column(name = "tax_resdnc_cntry_cd")
	private String taxResidenceCountryCd;

	//@Column(name = "cons_tovr_amt")
	private String consTurnoverAmt;

	//@Column(name = "src_cust_typ_cd")
	private String sourceCustomerTypeCd;

	@Transient
	private String intermediaryLcyclStatCd;
	
	@Transient
	private String relationShipTypeCd;
	
	public String getIntermediaryLcyclStatCd() {
		return intermediaryLcyclStatCd;
	}

	public void setIntermediaryLcyclStatCd(String intermediaryLcyclStatCd) {
		this.intermediaryLcyclStatCd = intermediaryLcyclStatCd;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPartyTypeCd() {
		return partyTypeCd;
	}

	public void setPartyTypeCd(String partyTypeCd) {
		this.partyTypeCd = partyTypeCd;
	}

	public String getLegalTypeCd() {
		return legalTypeCd;
	}

	public void setLegalTypeCd(String legalTypeCd) {
		this.legalTypeCd = legalTypeCd;
	}

	public String getBusActvTpCd() {
		return busActvTpCd;
	}

	public void setBusActvTpCd(String busActvTpCd) {
		this.busActvTpCd = busActvTpCd;
	}

	public String getResidenceCountryCd() {
		return residenceCountryCd;
	}

	public void setResidenceCountryCd(String residenceCountryCd) {
		this.residenceCountryCd = residenceCountryCd;
	}

	public String getBirthDt() {
		return birthDt;
	}

	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getTaxIdNum() {
		return taxIdNum;
	}

	public void setTaxIdNum(String taxIdNum) {
		this.taxIdNum = taxIdNum;
	}

	public String getTaxIdNumTypeCd() {
		return taxIdNumTypeCd;
	}

	public void setTaxIdNumTypeCd(String taxIdNumTypeCd) {
		this.taxIdNumTypeCd = taxIdNumTypeCd;
	}

	public String getFirstAcctOpenDt() {
		return firstAcctOpenDt;
	}

	public void setFirstAcctOpenDt(String firstAcctOpenDt) {
		this.firstAcctOpenDt = firstAcctOpenDt;
	}

	public String getIncorpCountryCd() {
		return incorpCountryCd;
	}

	public void setIncorpCountryCd(String incorpCountryCd) {
		this.incorpCountryCd = incorpCountryCd;
	}

	public String getIncorpStateCd() {
		return incorpStateCd;
	}

	public void setIncorpStateCd(String incorpStateCd) {
		this.incorpStateCd = incorpStateCd;
	}

	public String getIncorpStateKey() {
		return incorpStateKey;
	}

	public void setIncorpStateKey(String incorpStateKey) {
		this.incorpStateKey = incorpStateKey;
	}

	public String getIncorpDt() {
		return incorpDt;
	}

	public void setIncorpDt(String incorpDt) {
		this.incorpDt = incorpDt;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPublicEntityFlag() {
		return publicEntityFlag;
	}

	public void setPublicEntityFlag(String publicEntityFlag) {
		this.publicEntityFlag = publicEntityFlag;
	}

	public String getParentPublicEntityFlag() {
		return parentPublicEntityFlag;
	}

	public void setParentPublicEntityFlag(String parentPublicEntityFlag) {
		this.parentPublicEntityFlag = parentPublicEntityFlag;
	}

	public String getNonresidentAlienStatus() {
		return nonresidentAlienStatus;
	}

	public void setNonresidentAlienStatus(String nonresidentAlienStatus) {
		this.nonresidentAlienStatus = nonresidentAlienStatus;
	}

	public String getDatValdnStatCd() {
		return datValdnStatCd;
	}

	public void setDatValdnStatCd(String datValdnStatCd) {
		this.datValdnStatCd = datValdnStatCd;
	}

	public String getPrivateClientEffDt() {
		return privateClientEffDt;
	}

	public void setPrivateClientEffDt(String privateClientEffDt) {
		this.privateClientEffDt = privateClientEffDt;
	}

	public String getCmphId() {
		return cmphId;
	}

	public void setCmphId(String cmphId) {
		this.cmphId = cmphId;
	}

	public String getLckStatCd() {
		return lckStatCd;
	}

	public void setLckStatCd(String lckStatCd) {
		this.lckStatCd = lckStatCd;
	}

	public String getMtchValdnStatCd() {
		return mtchValdnStatCd;
	}

	public void setMtchValdnStatCd(String mtchValdnStatCd) {
		this.mtchValdnStatCd = mtchValdnStatCd;
	}

	public String getCustLifecycleStatCd() {
		return custLifecycleStatCd;
	}

	public void setCustLifecycleStatCd(String custLifecycleStatCd) {
		this.custLifecycleStatCd = custLifecycleStatCd;
	}

	public String getRltdPrtyLcyclStatCd() {
		return rltdPrtyLcyclStatCd;
	}

	public void setRltdPrtyLcyclStatCd(String rltdPrtyLcyclStatCd) {
		this.rltdPrtyLcyclStatCd = rltdPrtyLcyclStatCd;
	}

	public String getTaxResidenceCountryCd() {
		return taxResidenceCountryCd;
	}

	public void setTaxResidenceCountryCd(String taxResidenceCountryCd) {
		this.taxResidenceCountryCd = taxResidenceCountryCd;
	}

	public String getConsTurnoverAmt() {
		return consTurnoverAmt;
	}

	public void setConsTurnoverAmt(String consTurnoverAmt) {
		this.consTurnoverAmt = consTurnoverAmt;
	}

	public String getSourceCustomerTypeCd() {
		return sourceCustomerTypeCd;
	}

	public void setSourceCustomerTypeCd(String sourceCustomerTypeCd) {
		this.sourceCustomerTypeCd = sourceCustomerTypeCd;
	}

	public String getRelationShipTypeCd() {
		return relationShipTypeCd;
	}

	public void setRelationShipTypeCd(String relationShipTypeCd) {
		this.relationShipTypeCd = relationShipTypeCd;
	}
	
}
