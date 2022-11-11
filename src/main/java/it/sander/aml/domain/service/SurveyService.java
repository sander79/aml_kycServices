package it.sander.aml.domain.service;

import org.springframework.stereotype.Service;

import it.sander.aml.domain.model.SurveyModel;
import it.sander.aml.domain.model.SurveyResponse;

@Service
public interface SurveyService {
	
	/**
	 *   submitting a survey is a transactional process <br>
	 *     - Validation   (KYC) <br>
	 *     - profilation  (RPM) <br>
	 *     - dossier management (RPM) <br>
	 *     - Confirm	(KYC)
    */
	SurveyResponse submitSurvey(SurveyModel survey);

	/**  Validation */
	SurveyResponse validateSurvey(SurveyModel survey);
	
	/**  Confirm */
	SurveyResponse confirmSurvey(SurveyModel survey);

}
