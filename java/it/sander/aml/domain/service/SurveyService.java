package it.sander.aml.domain.service;

import org.springframework.stereotype.Service;

import it.sander.aml.domain.model.PaginationResponse;
import it.sander.aml.domain.model.SurveyModel;

@Service
public interface SurveyService {
	
	SurveyModel findById(long id);

	PaginationResponse<SurveyModel> findAll(boolean count, int page, int size);

	PaginationResponse<SurveyModel> findBySubjectCode(String subjectCode, boolean count, int page, int size);

}
