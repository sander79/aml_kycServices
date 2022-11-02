package it.sander.aml.application.rest.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SurveyDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private long id;
    
    private String group;
    private String abi;
    private String serviceCode;
    private Date creationDate;
    private Date confirmDate;
    
    private String status;
    private String subjectCode;
    private String subjectType;
    private String name;
	
	public SurveyDto() {}

	public SurveyDto(long id, String group, String abi, String serviceCode, String subjectCode) {
		super();
		this.id = id;
		this.group = group;
		this.abi = abi;
		this.serviceCode = serviceCode;
		this.subjectCode = subjectCode;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public long getId() {
		return id;
	}

	public String getGroup() {
		return group;
	}

	public String getAbi() {
		return abi;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
