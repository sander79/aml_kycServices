package it.sander.aml.application.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.sander.aml.domain.service.SurveyService;
import it.sander.aml.infrastructure.repository.event.ServiceEvent;
import it.sander.aml.infrastructure.repository.event.ServiceEventListener;

@Component
public class eventController extends ServiceEventListener {
	
	@Autowired
	SurveyService service;

	@Override
	public void onApplicationEvent(ServiceEvent event) {
		System.out.println("Received spring custom event - " + event.getStatus());
		
	    switch(event.getStatus()) {
	       case MANAGED:
	    	   service.confirmSurvey(event.getId());
		          break;
	       case PROFILED:
	          break;
	          
	       default:
	    	   
	    }
		
	}

}
