package it.sander.aml.domain.service;

import java.util.LinkedHashMap;

import it.sander.aml.domain.model.SurveyModel;

public class ValidationServiceImpl implements ValidationService {

	@Override
	public LinkedHashMap<String, String> validateSurvey(SurveyModel survey) {		
		return validation(survey);
	}
	
	private LinkedHashMap<String, String> validation(SurveyModel survey) {
		return new LinkedHashMap<String, String>();
		
	}

}
