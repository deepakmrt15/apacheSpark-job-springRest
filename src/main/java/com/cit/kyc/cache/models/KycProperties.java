package com.cit.kyc.cache.models;

import java.io.Serializable;

public class KycProperties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4532053376668032886L;
	private String partyId;
	private String instanceId;
	private String extnId;
	private String propertyName;
	private String seqNum;
	private String propertyValue;
	private String custTp;
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
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getCustTp() {
		return custTp;
	}
	public void setCustTp(String custTp) {
		this.custTp = custTp;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	
}
