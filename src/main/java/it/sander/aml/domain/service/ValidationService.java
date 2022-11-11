package it.sander.aml.domain.service;

import java.util.LinkedHashMap;

import it.sander.aml.domain.model.SurveyModel;

public interface ValidationService {
	
	LinkedHashMap<String, String> validateSurvey(SurveyModel survey);

}
