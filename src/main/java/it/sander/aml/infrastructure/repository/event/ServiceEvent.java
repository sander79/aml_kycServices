package it.sander.aml.infrastructure.repository.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import it.sander.aml.domain.service.TransactionService.TransactionState;

public class ServiceEvent extends ApplicationEvent {
    private UUID id;
    private TransactionState status;

    public ServiceEvent(Object source, UUID id, TransactionState status) {
        super(source);
        this.id = id;
        this.status = status;
    }
    

	public UUID getId() {
		return id;
	}


	public TransactionState getStatus() {
		return status;
	}

}

