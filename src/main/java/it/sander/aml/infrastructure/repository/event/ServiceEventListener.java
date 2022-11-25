package it.sander.aml.infrastructure.repository.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceEventListener implements ApplicationListener<ServiceEvent> {
	    @Override
	    public void onApplicationEvent(ServiceEvent event) {
	        System.out.println("Received spring custom event - " + event.getMessage());
	    }
	}