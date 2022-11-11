package it.sander.aml.domain.model;

import java.util.Date;
import java.util.UUID;

/**
 * 
 *  Business layer model
 *  
 */
public class SurveyModel {
    private UUID id;
    
    private String group;
    private String abi;
    private String serviceCode;
    private Date creationDate;
    private Date confirmDate;
    
    private String status;
    private String subjectCode;
    private String subjectType;
    private String name;
    
	public SurveyModel() {}
	
	public SurveyModel(UUID id, String group, String abi, String serviceCode, Date creationDate) {
		super();
		this.id = id;
		this.group = group;
		this.abi = abi;
		this.serviceCode = serviceCode;
		this.creationDate = creationDate;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAbi() {
		return abi;
	}

	public void setAbi(String abi) {
		this.abi = abi;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
    
}
