package com.cit.kyc.cache.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name="dv_prty_adv_pep_san")
public class ScreenInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7976236366025732775L;
	
	//@Id
	//@Column(name = "party_id_value")
	private String partyId;

	//@Column(name = "adv_status")
	private String advStatus;

	//@Column(name = "adv_category_code")
	private String advCategoryCode;

	//@Column(name = "adv_sub_category")
	private String advSubCategory;

	//@Column(name = "adv_event_desc")
	private String advEventDesc;

	//@Column(name = "san_status")
	private String sanStatus;

	//@Column(name = "san_alert_state")
	private String sanAlertState;

	//@Column(name = "san_alert_status")
	private String sanAlertStatus;

	//@Column(name = "pep_status")
	private String pepStatus;

	//@Column(name = "pep_level")
	private String pepLevel;

	//@Column(name = "pep_add_status")
	private String pepAddStatus;

	//@Column(name = "pep_type")
	private String pepType;

	//@Column(name = "pep_risk_level")
	private String pepRiskLevel;

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
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

	public String getPepStatus() {
		return pepStatus;
	}

	public void setPepStatus(String pepStatus) {
		this.pepStatus = pepStatus;
	}

	public String getPepLevel() {
		return pepLevel;
	}

	public void setPepLevel(String pepLevel) {
		this.pepLevel = pepLevel;
	}

	public String getPepAddStatus() {
		return pepAddStatus;
	}

	public void setPepAddStatus(String pepAddStatus) {
		this.pepAddStatus = pepAddStatus;
	}

	public String getPepType() {
		return pepType;
	}

	public void setPepType(String pepType) {
		this.pepType = pepType;
	}

	public String getPepRiskLevel() {
		return pepRiskLevel;
	}

	public void setPepRiskLevel(String pepRiskLevel) {
		this.pepRiskLevel = pepRiskLevel;
	}
}
