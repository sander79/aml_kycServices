package it.sander.aml.infrastructure.repository.event;

import org.springframework.context.ApplicationEvent;

public class ServiceEvent extends ApplicationEvent {
    private String id;
    private String status;

    public ServiceEvent(Object source, String id, String status) {
        super(source);
        this.id = id;
        this.status = status;
    }
    

	public String getId() {
		return id;
	}


	public String getStatus() {
		return status;
	}

}

