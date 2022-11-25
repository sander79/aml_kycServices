package it.sander.aml.application.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import it.sander.aml.domain.service.SurveyService;
import it.sander.aml.infrastructure.repository.event.ServiceEvent;
import it.sander.aml.infrastructure.repository.event.ServiceEventPublisher;

@Component
public class eventController implements ApplicationListener<ServiceEvent> {
	
	@Autowired
	SurveyService service;
	
	@Autowired
	ServiceEventPublisher publisher;

	@Override
	public void onApplicationEvent(ServiceEvent event) {
		System.out.println("Received spring custom event - " + event.getMessage());
		
		publisher.
		
	}

}
