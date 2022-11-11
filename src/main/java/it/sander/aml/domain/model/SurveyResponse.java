package it.sander.aml.domain.model;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 *   Response of Submitting Survey process 
 *
 */
public class SurveyResponse {
	
	private UUID uid;
	private LinkedHashMap<String, String> errors = null;

	public SurveyResponse(UUID uid, LinkedHashMap<String, String> errors) {
		this.uid = uid;
		this.errors = errors;
	}

	public UUID getUid() {
		return uid;
	}
	
	public void addErrorItem(String error, String value) {
		if(errors == null)
			errors = new LinkedHashMap<>();
		
		errors.put(error, value);
	}

	public LinkedHashMap<String, String> getErrors() {
		return errors;
	}
	
}
