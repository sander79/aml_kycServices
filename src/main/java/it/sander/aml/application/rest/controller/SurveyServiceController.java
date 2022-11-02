package it.sander.aml.application.rest.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.sander.aml.application.AmlKycServiceApplication.SurveyRepoFactory;
import it.sander.aml.application.rest.BaseControllerRest;
import it.sander.aml.application.rest.ResourceNotFoundException;
import it.sander.aml.application.rest.RestResultConditions;
import it.sander.aml.application.rest.dto.SurveyDto;
import it.sander.aml.domain.model.PaginationResponse;
import it.sander.aml.domain.model.SurveyModel;
import it.sander.aml.domain.repository.SurveyRepository;

/**
  
  This controller uses the surveyRepository skipping the service layer.
  The response object belongs in the model layer 
  
 */
@RestController
@RequestMapping(value = "/surveys/v1.0")
public class SurveyServiceController extends BaseControllerRest {
	
	private final int MAX_PAGESIZE = 25;
	
	//@Autowired
	//private SurveyService surveyService;
	
	/* initialization block  --> */
	@Autowired
	SurveyRepoFactory surveyRepo;
	private SurveyRepository repository;
	@PostConstruct
	private void initRepository() {
		repository = surveyRepo.getRepository();
	}
	/* <--  initialization block */
    
    /**
     * 
     * @param model
     * @return All surveys
     */
    @GetMapping(path={"" , "/" }, params = { "count", "page", "size" })
	public PaginationResponse<SurveyModel> surveys(@RequestParam("count")boolean count, @RequestParam("page")int page, @RequestParam("size") @Max(MAX_PAGESIZE) int size) {
		try {
			return RestResultConditions.checkFound(repository.findAll(count, page, size));
			
		} catch (ResourceNotFoundException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found", exc);
		} catch (Exception exc) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "find error", exc);
		}
	}
       
    @GetMapping(value = "/{id}")
    @Validated
    public SurveyModel findById(@PathVariable("id") final Long id) {
        try {
            return RestResultConditions.checkFound(repository.findById(id));

        }
        catch (ResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "findById Not Found", exc);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "findById error", exc);
        }
    }
    
    @GetMapping(value = "/{subjectCode}", params = { "count", "page", "size" })
    public PaginationResponse<SurveyModel> findBySubjectCode(@PathVariable("subjectCode") final String subjectCode, @RequestParam("count")boolean count, @RequestParam("page")int page, @RequestParam("size") @Max(MAX_PAGESIZE) int size) {
    	
        try {
            return RestResultConditions.checkFound(repository.findBySubjectCode(subjectCode, count, page, size));
        }
        catch (ResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "findBySubjectCode Not Found", exc);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "findBySubjectCode error", exc);
        }
    }
    
    @GetMapping(value = "/{subjectCode}", params = { "dateFrom", "dateTo", "count", "page", "size" })
    public PaginationResponse<SurveyModel> findBySubjectCode(@PathVariable("dateFrom") final Date dateFrom, @PathVariable("dateTo") final Date dateTo, 
    		@RequestParam("count")boolean count, @RequestParam("page")int page, @RequestParam("size") @Max(MAX_PAGESIZE) int size) {
        try {
            return RestResultConditions.checkFound(repository.findByDate(dateFrom, dateTo, count, page, size));
        }
        catch (ResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "findBySubjectCode Not Found", exc);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "findBySubjectCode error", exc);
        }
    }
    
    private List<SurveyDto> mapList(List<SurveyModel> surveyDtoList) {
    	List<SurveyDto> mappedList = new LinkedList<>();
		for (SurveyModel survey : surveyDtoList) {
			mappedList.add(mapper.map(survey, SurveyDto.class));
		}
		return mappedList;
    }

}