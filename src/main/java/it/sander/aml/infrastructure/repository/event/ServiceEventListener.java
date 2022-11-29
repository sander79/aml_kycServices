package it.sander.aml.infrastructure.repository.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public abstract class ServiceEventListener implements ApplicationListener<ServiceEvent> {
	
	@Autowired
	ServiceEventPublisher publisher;
	
	@Override
	public abstract void onApplicationEvent(ServiceEvent event);
}