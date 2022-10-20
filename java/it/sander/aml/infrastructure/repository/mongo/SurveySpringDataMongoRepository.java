package it.sander.aml.infrastructure.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.sander.aml.domain.model.SurveyModel;

@Repository
public interface SurveySpringDataMongoRepository extends MongoRepository<SurveyModel, Long> {
}
