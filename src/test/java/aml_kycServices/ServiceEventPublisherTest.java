package aml_kycServices;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.sander.aml.domain.service.TransactionService.TransactionState;
import it.sander.aml.infrastructure.repository.event.ServiceEventPublisher;

public class ServiceEventPublisherTest {
	
	//@Autowired
	ServiceEventPublisher publisher;
	
	

	public ServiceEventPublisherTest() {
		super();
	}

	@Test
	public void receiveProfiled() {
		publisher.publishCustomEvent(UUID.randomUUID(), TransactionState.PROFILED);
	}

	@Test
	public void receiveManaged() {
		publisher.publishCustomEvent(UUID.randomUUID(), TransactionState.MANAGED);
	}
	
}
