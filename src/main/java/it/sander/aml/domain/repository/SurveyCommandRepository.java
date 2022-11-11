package it.sander.aml.domain.repository;

import java.util.UUID;

import it.sander.aml.domain.model.SurveyModel;

public interface SurveyCommandRepository {
	
	final String collectionName = "survey"; 

	UUID insert(SurveyModel survey) throws RepositoryException;

	boolean confirm(SurveyModel survey) throws RepositoryException;

}
