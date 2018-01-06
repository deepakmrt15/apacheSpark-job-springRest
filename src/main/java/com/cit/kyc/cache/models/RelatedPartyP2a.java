package com.cit.kyc.cache.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

//@Entity
//@Table(name="iv_prty_acct_rel_prty")
public class RelatedPartyP2a implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//@Id
	//@Column(name = "pmid")
	private String pmid;
	
	//@Column(name = "account_id")
	private String accountId;
	
	//@Column(name = "rel_party_id")
	private String relatedPartyId;
	
	//@Column(name = "prty_rltd_acct_typ_cd")
	private String relatedPartyAccountRelationship;
	
	//@Column(name = "account_num")
	private String accountNumber;
	
	//@Column(name = "prod_typ")
	private String productType;
	
	//@Column(name = "adv_status")
	private String advStatus;
	
	//@Column(name = "adv_category_code")
	private String advCategoryCode;
	
	//@Column(name = "adv_sub_category")
	private String advSubCategory;
	
	//@Column(name = "adv_event_desc")
	private String advEventDesc;
	
	//@Column(name = "pep_status")
	private String pepStatus;
	
	//@Column(name = "pep_type")
	private String pepType;

	//@Column(name = "pep_level")
	private String pepLevel;
	
	//@Column(name = "pep_add_status")
	private String pepAddStatus;
	
	//@Column(name = "pep_risk_level")
	private String pepRiskLevel;
	
	//@Column(name = "san_status")
	private String sanStatus;
	
	//@Column(name = "san_alert_state")
	private String sanAlertState;
	
	//@Column(name = "san_alert_status")
	private String sanAlertStatus;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getRelatedPartyId() {
		return relatedPartyId;
	}
	public void setRelatedPartyId(String relatedPartyId) {
		this.relatedPartyId = relatedPartyId;
	}
	public String getRelatedPartyAccountRelationship() {
		return relatedPartyAccountRelationship;
	}
	public void setRelatedPartyAccountRelationship(String relatedPartyAccountRelationship) {
		this.relatedPartyAccountRelationship = relatedPartyAccountRelationship;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getAdvStatus() {
		return advStatus;
	}
	public void setAdvStatus(String advStatus) {
		this.advStatus = advStatus;
	}
	public String getAdvCategoryCode() {
		return advCategoryCode;
	}
	public void setAdvCategoryCode(String advCategoryCode) {
		this.advCategoryCode = advCategoryCode;
	}
	public String getAdvSubCategory() {
		return advSubCategory;
	}
	public void setAdvSubCategory(String advSubCategory) {
		this.advSubCategory = advSubCategory;
	}
	public String getAdvEventDesc() {
		return advEventDesc;
	}
	public void setAdvEventDesc(String advEventDesc) {
		this.advEventDesc = advEventDesc;
	}
	public String getPepStatus() {
		return pepStatus;
	}
	public void setPepStatus(String pepStatus) {
		this.pepStatus = pepStatus;
	}
	public String getPepType() {
		return pepType;
	}
	public void setPepType(String pepType) {
		this.pepType = pepType;
	}
	public String getSanStatus() {
		return sanStatus;
	}
	public void setSanStatus(String sanStatus) {
		this.sanStatus = sanStatus;
	}
	public String getSanAlertState() {
		return sanAlertState;
	}
	public void setSanAlertState(String sanAlertState) {
		this.sanAlertState = sanAlertState;
	}
	public String getSanAlertStatus() {
		return sanAlertStatus;
	}
	public void setSanAlertStatus(String sanAlertStatus) {
		this.sanAlertStatus = sanAlertStatus;
	}
	public String getPepRiskLevel() {
		return pepRiskLevel;
	}
	public void setPepRiskLevel(String pepRiskLevel) {
		this.pepRiskLevel = pepRiskLevel;
	}
	public String getPmid() {
		return pmid;
	}
	public void setPmid(String pmid) {
		this.pmid = pmid;
	}
	public String getPepAddStatus() {
		return pepAddStatus;
	}
	public void setPepAddStatus(String pepAddStatus) {
		this.pepAddStatus = pepAddStatus;
	}
	public String getPepLevel() {
		return pepLevel;
	}
	public void setPepLevel(String pepLevel) {
		this.pepLevel = pepLevel;
	}
	
}
