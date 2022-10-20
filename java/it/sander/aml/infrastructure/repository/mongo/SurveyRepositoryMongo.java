package it.sander.aml.infrastructure.repository.mongo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import it.sander.aml.domain.model.PaginationResponse;
import it.sander.aml.domain.model.SurveyModel;
import it.sander.aml.domain.repository.SurveyRepository;
import it.sander.aml.infrastructure.repository.dbm.SurveyDbModel;

@Component
public class SurveyRepositoryMongo implements SurveyRepository {
    
	protected final DozerBeanMapper mapper = new DozerBeanMapper();
    final MongoTemplate mongoTemplate;

	public SurveyRepositoryMongo(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public SurveyModel findById(Long id) {
		SurveyDbModel dbModel = mongoTemplate.findById(id, SurveyDbModel.class, collectionName);
		return mapper.map(dbModel, SurveyModel.class);
	}
	
	@Override
	public PaginationResponse<SurveyModel> findAll(boolean count, int page, int size) {
		PaginationResponse<SurveyModel> resultPage;  
		if(count) {
			long total = mongoTemplate.count(null, collectionName);
			resultPage = new PaginationResponse<SurveyModel>(total);
		} else {
			Query q = paginationQuery("_id", page, size);
			List<SurveyDbModel> result = mongoTemplate.find(q, SurveyDbModel.class);
			resultPage = new PaginationResponse<SurveyModel>(this.map(result));
		}
		return resultPage;
	}

	@Override
	public PaginationResponse<SurveyModel> findBySubjectCode(String subjCode, boolean count, int page, int size) {	
		PaginationResponse<SurveyModel> resultPage;  
		if(count) {
			long total = mongoTemplate.count(null, collectionName);
			resultPage = new PaginationResponse<SurveyModel>(total);
		} else {
			Query q = paginationQuery("_id", page, size);
			q.addCriteria(Criteria.where("subjectCode").is(subjCode));
			List<SurveyDbModel> result = mongoTemplate.find(q, SurveyDbModel.class);
			resultPage = new PaginationResponse<SurveyModel>(this.map(result));
		}
		return resultPage;
	}

	@Override
	public PaginationResponse<SurveyModel> findByDate(Date from, Date to, boolean count, int page, int size) {
		PaginationResponse<SurveyModel> resultPage;  
		if(count) {
			long total = mongoTemplate.count(null, collectionName);
			resultPage = new PaginationResponse<SurveyModel>(total);
		} else {
			Query q = paginationQuery("_id", page, size);
			q.addCriteria(Criteria.where("creationDate").gte(from));
			q.addCriteria(Criteria.where("creationDate").lte(to));
			List<SurveyDbModel> result = mongoTemplate.find(q, SurveyDbModel.class);
			resultPage = new PaginationResponse<SurveyModel>(this.map(result));
		}
		return resultPage;
	}
	
	/**
	 * 
	 *    .find().sort("field": 1).skip(<size>*(<page>-1)).limit(<size>)
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	private Query paginationQuery(String sortField, int page, int size) {
		return new Query()
				.with(Sort.by(Sort.Direction.ASC, sortField))
				.skip(size*(page-1))
				.limit(size);
	}
	
	private List<SurveyModel> map(List<SurveyDbModel> list) {
		List<SurveyModel> mappedList = new LinkedList<SurveyModel>();
		for(SurveyDbModel model : list) {
			mappedList.add(mapper.map(model, SurveyModel.class));
		}
		return mappedList;
	}


	}
