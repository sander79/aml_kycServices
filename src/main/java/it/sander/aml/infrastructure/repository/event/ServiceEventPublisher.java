package it.sander.aml.infrastructure.repository.event;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import it.sander.aml.domain.service.TransactionService.TransactionState;

@Component
public class ServiceEventPublisher {
	    @Autowired
	    private ApplicationEventPublisher applicationEventPublisher;

	    public void publishCustomEvent(final UUID id, TransactionState status) {
	        System.out.println("Publishing custom event. ");
	        ServiceEvent customSpringEvent = new ServiceEvent(this, id, status);
	        applicationEventPublisher.publishEvent(customSpringEvent);
	    }
	}