package it.sander.aml.domain.service;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sander.aml.domain.model.SurveyModel;
import it.sander.aml.domain.model.SurveyResponse;
import it.sander.aml.domain.repository.RepositoryException;
import it.sander.aml.domain.repository.SurveyCommandRepository;
import it.sander.aml.domain.service.TransactionService.TransactionState;

@Service
public class SurveyServiceImpl implements SurveyService {
	

	
	@Autowired
	ValidationService validator;
	
	@Autowired
	SurveyCommandRepository repository;
	
	@Autowired
	TransactionService transaction;

	@Override
	public SurveyResponse submitSurvey(SurveyModel survey) {
		
		UUID id = UUID.randomUUID();
		survey.setId(id);
		
		// STEP 1 - validation
		SurveyResponse submitResponse = this.validateSurvey(survey);	

		try {
			repository.insert(survey);
			
			transaction.notifyTransaction(id, TransactionState.INSERTED);
			
		} catch (RepositoryException e) {
			submitResponse.addErrorItem("SERVICE", e.getMessage());
		}
		
		return submitResponse;
	}
	
	/**
	 *  Validation 
	 */
	@Override
	public SurveyResponse validateSurvey(SurveyModel survey) {
		LinkedHashMap<String, String> validationResponse = validator.validateSurvey(survey);	
		return new SurveyResponse(survey.getId(), validationResponse);
	}

	@Override
	public SurveyResponse confirmSurvey(UUID id) {
		SurveyResponse response = new SurveyResponse(id, null);
		try {
			repository.confirm(id);
			
			transaction.notifyTransaction(id, TransactionState.CONFIRMED);
			
		} catch (RepositoryException e) {
			response.addErrorItem("SERVICE", e.getMessage());
		}
		return response;
	}
	
}
