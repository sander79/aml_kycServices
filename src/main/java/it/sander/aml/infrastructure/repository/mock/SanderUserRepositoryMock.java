package it.sander.aml.infrastructure.repository.mock;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import it.sander.aml.domain.model.PaginationResponse;
import it.sander.aml.domain.model.SurveyModel;
import it.sander.aml.domain.repository.SurveyRepository;

@Repository
public class SanderUserRepositoryMock implements SurveyRepository {

	@Override
	public SurveyModel findById(Long id) {
		return new SurveyModel(236654, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime());
	}

	@Override
	public PaginationResponse<SurveyModel> findAll(boolean count, int page, int size) {
		if(count)
			return  new PaginationResponse<>(5);
		
		List<SurveyModel> list = new LinkedList<>();
		list.add(new SurveyModel(231337, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(229884, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(227655, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(221001, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(200422, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		return new PaginationResponse<>(list);
		
	}

	@Override
	public PaginationResponse<SurveyModel> findBySubjectCode(String subjCode, boolean count, int page, int size) {
		if(count)
			return  new PaginationResponse<>(5);
		
		List<SurveyModel> list = new LinkedList<>();
		list.add(new SurveyModel(231337, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(229884, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(227655, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(221001, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(200422, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		return new PaginationResponse<>(list);
	}

	@Override
	public PaginationResponse<SurveyModel> findByDate(Date from, Date to, boolean count, int page, int size) {
		if(count)
			return  new PaginationResponse<>(5);
		
		List<SurveyModel> list = new LinkedList<>();
		list.add(new SurveyModel(231337, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(229884, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(227655, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(221001, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		list.add(new SurveyModel(200422, "GROUP","ABI","ONLINE",GregorianCalendar.getInstance().getTime()));
		return new PaginationResponse<>(list);
	}
	


}
