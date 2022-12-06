package it.sander.aml.domain.service.mock;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.sander.aml.application.config.AmlConfiguration;
import it.sander.aml.domain.service.TransactionService.TransactionState;
import it.sander.aml.infrastructure.repository.event.ServiceEventPublisher;

@RestController
@Profile("mockQueue")
@RequestMapping(value = "/queue")
public class processorMock {
	
	@Autowired
	ServiceEventPublisher publisher;
	
	@Autowired	
	AmlConfiguration config;
	
    @PostMapping(path={"/message" })
    @ApiOperation(value = "put message to a queue", notes = "")
    @Secured("ROLE_GPR_WRITE")
	public boolean messageMock(@RequestBody String message) {

		processMessage(message.toString());
		return true;

	}
    
	private void processMessage(String amlMessage) {
	    System.out.printf("Processing message.  Content: %s", amlMessage);
	    
	    String[] amlBody = amlMessage.split(":");
	    
	    TransactionState status = TransactionState.actionOf(amlBody[1]);
	    switch(status) {
	       case MANAGED:
	       case PROFILED:
	          publisher.publishCustomEvent(UUID.fromString(amlBody[0]), status);
	          
	       default:
	    	   //context.abandon();
	    }
	    
	    //context.complete();
	}

}
