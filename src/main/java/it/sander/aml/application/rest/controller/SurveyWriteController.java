package it.sander.aml.application.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.sander.aml.application.rest.BaseControllerRest;
import it.sander.aml.domain.model.SurveyModel;
import it.sander.aml.domain.model.SurveyResponse;
import it.sander.aml.domain.service.SurveyService;

/**
  
  This controller uses the surveyRepository skipping the service layer.
  The response object belongs in the model layer 
  
 */
@RestController
@RequestMapping(value = "/surveys")
public class SurveyWriteController extends BaseControllerRest {
	
	@Autowired
	private SurveyService service;
    
    /**
     * 
     * @param SurveyModel
     * @return SurveyResponse
     */
    @PostMapping(path={"/submit" })
    @ApiOperation(value = "submit survey", notes = "Submitting SurveyModel, response with SurveyResponse ")
    @Secured("ROLE_GPR_WRITE")
	public @ResponseBody SurveyResponse submitSurvey(@RequestBody SurveyModel survey) {
    	
    	return service.submitSurvey(survey);

	}
    
    /**
     * 
     * @param SurveyModel
     * @return SurveyResponse
     */
    @PostMapping(path={"/validation" })
    @ApiOperation(value = "submit survey", notes = "Validation SurveyModel, response with SurveyResponse ")
    @Secured("ROLE_GPR_WRITE")
	public @ResponseBody SurveyResponse validationSurvey(@RequestBody SurveyModel survey) {
    	
    	return service.confirmSurvey(survey.getId());

	}
    
    /**
     * 
     * @param SurveyModel
     * @return SurveyResponse
     */
    @PutMapping(path={"/confirm" })
    @ApiOperation(value = "submit survey", notes = "Validation SurveyModel, response with SurveyResponse ")
    @Secured("ROLE_GPR_WRITE")
	public @ResponseBody SurveyResponse confirmSurvey(@RequestBody SurveyModel survey) {
    	
    	return service.confirmSurvey(survey.getId());

	}

}