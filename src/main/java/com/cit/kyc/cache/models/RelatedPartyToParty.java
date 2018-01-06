package com.cit.kyc.cache.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name="iv_score_prty_rltd_prty")
public class RelatedPartyToParty implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 647220212258352190L;
	
	//@Id
	//@Column(name="rowid_object")
	private String rowidObject;
	
	//@Column(name="prty_id")
	private String partyId;
	
	//@Column(name="prty_rltd_prty_typ_cd")
	private String partyTypeCd;
	
	//@Column(name="rltd_prty_id")
	private String relPartyId;
	
	//@Column(name="rel_type_code")
	private String relationshipTypeCd;

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

	public String getRelPartyId() {
		return relPartyId;
	}

	public void setRelPartyId(String relPartyId) {
		this.relPartyId = relPartyId;
	}

	public String getRelationshipTypeCd() {
		return relationshipTypeCd;
	}

	public void setRelationshipTypeCd(String relationshipTypeCd) {
		this.relationshipTypeCd = relationshipTypeCd;
	}

	public String getRowidObject() {
		return rowidObject;
	}

	public void setRowidObject(String rowidObject) {
		this.rowidObject = rowidObject;
	}
	
	
}
