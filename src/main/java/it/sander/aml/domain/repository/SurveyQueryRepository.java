package it.sander.aml.domain.repository;


import java.util.Date;

import it.sander.aml.domain.model.PaginationResponse;
import it.sander.aml.domain.model.SurveyModel;

public interface SurveyQueryRepository {
	
	final String collectionName = "survey"; 

	PaginationResponse<SurveyModel> findAll(boolean count, int page, int size);
	PaginationResponse<SurveyModel> findBySubjectCode(String subjCode, boolean count, int page, int size);
	PaginationResponse<SurveyModel> findByDate(Date from, Date to, boolean count, int page, int size);
	SurveyModel findById(Long id);

}