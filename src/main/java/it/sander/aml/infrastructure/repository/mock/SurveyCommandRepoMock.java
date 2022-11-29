package it.sander.aml.infrastructure.repository.mock;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import it.sander.aml.domain.model.SurveyModel;
import it.sander.aml.domain.repository.RepositoryException;
import it.sander.aml.domain.repository.SurveyCommandRepository;

@Repository
@Profile("mockRepo")
public class SurveyCommandRepoMock implements SurveyCommandRepository {

	@Override
	public UUID insert(SurveyModel survey) throws RepositoryException {
		return survey.getId();
	}

	@Override
	public boolean confirm(UUID id) throws RepositoryException {
		return true;
	}


	


}
