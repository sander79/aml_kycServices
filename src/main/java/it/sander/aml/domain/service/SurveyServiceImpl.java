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
		
		UUID processId = UUID.randomUUID();
		survey.setId(processId);
		
		// STEP 1 - validation
		SurveyResponse submitResponse = this.validateSurvey(survey);	

		try {
			repository.insert(survey);
			
			transaction.notifyTransaction(processId.toString(), TransactionState.INSERTED);
			
		} catch (RepositoryException e) {
			submitResponse.addErrorItem("REPO", e.getMessage());
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
	public SurveyResponse confirmSurvey(SurveyModel survey) {
		SurveyResponse response = new SurveyResponse(survey.getId(), null);
		try {
			repository.confirm(survey);
			
			transaction.notifyTransaction(survey.getId().toString(), TransactionState.CONFIRMED);
			
		} catch (RepositoryException e) {
			response.addErrorItem("REPO", e.getMessage());
		}
		return response;
	}
	
}
