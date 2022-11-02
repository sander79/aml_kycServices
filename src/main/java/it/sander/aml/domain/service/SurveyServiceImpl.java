package it.sander.aml.domain.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sander.aml.application.AmlKycServiceApplication.SurveyRepoFactory;
import it.sander.aml.domain.model.PaginationResponse;
import it.sander.aml.domain.model.SurveyModel;
import it.sander.aml.domain.repository.SurveyRepository;

@Service
public class SurveyServiceImpl implements SurveyService {
	
	/* initialization block  --> */
	@Autowired
	SurveyRepoFactory surveyRepo;
	private SurveyRepository repository;
	@PostConstruct
	private void initRepository() {
		repository = surveyRepo.getRepository();
	}
	/* <--  initialization block */

	@Override
	public SurveyModel findById(long id) {
		return repository.findById(id);
	}

	@Override
	public PaginationResponse<SurveyModel> findBySubjectCode(String subjectCode, boolean count, int page, int size) {
		return repository.findBySubjectCode(subjectCode, count, page, size);
	}

	@Override
	public PaginationResponse<SurveyModel> findAll(boolean count, int page, int size) {
		return repository.findAll(count, page, size);
	}
	
}
