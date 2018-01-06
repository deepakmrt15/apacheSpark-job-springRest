package com.cit.kyc.cache.models;

import java.io.Serializable;

public class KyaProperties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8783817558772724934L;
	private String accountId;
	private String partyId;
	private String instanceId;
	private String extnId;
	private String accountName;
	private String propertyName;
	private String seqNum;
	private String propertyValue;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getExtnId() {
		return extnId;
	}
	public void setExtnId(String extnId) {
		this.extnId = extnId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	
}
