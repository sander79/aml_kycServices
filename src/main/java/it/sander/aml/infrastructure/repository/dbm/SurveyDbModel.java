package it.sander.aml.infrastructure.repository.dbm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "survey")
public class SurveyDbModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private long id;

	@Field("GROUP")
    private String group;
	
	@Field("ABI")
    private String abi;
	
	@Field("SERVICE_CODE")
    private String serviceCode;
	
	@Field("CREATION_DATE")
    private Date creationDate;
	
	@Field("CONFIRM_DATE")
    private Date confirmDate;
    
	@Field("STATUS")
    private int status;
	
	@Field("SUBJECT_CODE")
    private String subjectCode;
    
	@Field("SUBJECT_TYPE")
	private String subjectType;
	
	@Field("NAME")
    private String name;
	
	public SurveyDbModel() {}

	public SurveyDbModel(long id, String group, String abi, String serviceCode, String subjectCode) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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
