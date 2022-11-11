package it.sander.aml.application.rest.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.Max;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.ApiOperation;
import it.sander.aml.application.rest.BaseControllerRest;
import it.sander.aml.application.rest.ResourceNotFoundException;
import it.sander.aml.application.rest.RestResultConditions;
import it.sander.aml.application.rest.dto.SurveyDto;
import it.sander.aml.domain.model.PaginationResponse;
import it.sander.aml.domain.model.SurveyModel;
import it.sander.aml.domain.repository.SurveyQueryRepository;

/**
  
  This controller uses the surveyRepository skipping the service layer.
  The response object belongs in the model layer 
  
 */
@RestController
@RequestMapping(value = "/surveys")
public class SurveyServiceController extends BaseControllerRest {
	
	private final int MAX_PAGESIZE = 25;
	
	@Autowired
	private SurveyQueryRepository repository;
    
    /**
     * 
     * @param model
     * @return All surveys
     */
    @GetMapping(path={"" , "/" }, params = { "count", "page", "size" })
    @ApiOperation(value = "Get surveys", notes = "Get surveys, paginated")
    @Secured("ROLE_GPR_READ")
	public PaginationResponse<SurveyModel> surveys(Authentication auth, @RequestParam("count")boolean count, @RequestParam("page")int page, @RequestParam("size") @Max(MAX_PAGESIZE) int size) {
    	auth.getAuthorities();
    	
		try {
			return RestResultConditions.checkFound(repository.findAll(count, page, size));
			
		} catch (ResourceNotFoundException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found", exc);
		} catch (Exception exc) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "find error", exc);
		}
	}
       
    @GetMapping(value = "/{id}")
    @Secured("ROLE_GPR_READ")
    @Validated
    public SurveyModel findById(Authentication auth, @PathVariable("id") final Long id) {
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
    @Secured("ROLE_GPR_READ")
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
    @Secured("ROLE_GPR_READ")
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