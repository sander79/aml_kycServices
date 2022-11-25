package it.sander.aml.domain.service.azure;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusException;
import com.azure.messaging.servicebus.ServiceBusFailureReason;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;

import it.sander.aml.domain.service.TransactionService.TransactionState;
import it.sander.aml.infrastructure.repository.event.ServiceEvent;
import it.sander.aml.infrastructure.repository.event.ServiceEventPublisher;

/**
 * 
		https://learn.microsoft.com/en-us/azure/service-bus-messaging/service-bus-java-how-to-use-queues
 *
 */

@Component
@Profile("azure")
public class AzureServiceProcessorClient {
	
	@Autowired
	ServiceEventPublisher publisher;
	
	private ServiceBusProcessorClient processorClient;
	
    final String resourceGroup = "RG1";
    final String namespace = "namespace";
    final String queue = "kyc_queue";
	private final String connectionString = null;
    
	
	public AzureServiceProcessorClient() {
		CountDownLatch countdownLatch = new CountDownLatch(1);

	    // Create an instance of the processor through the ServiceBusClientBuilder
	    ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
	        .connectionString(connectionString)
	        .processor()
	        .queueName(queue)
	        .processMessage(context -> processMessage(context))
	        .processError(context -> processError(context, countdownLatch))
	        .buildProcessorClient();
	    
	    System.out.println("Starting the processor");
	    processorClient.start();

	}
	
	private void processMessage(ServiceBusReceivedMessageContext context) {
	    ServiceBusReceivedMessage message = context.getMessage();
	    System.out.printf("Processing message. Session: %s, Sequence #: %s. Contents: %s%n", message.getMessageId(),
	        message.getSequenceNumber(), message.getBody());
	    
	    String amlMessage = message.getBody().toString();
	    String[] amlBody = amlMessage.split(";");
	    
	    if(amlBody[1].equals(TransactionState.MANAGED.getAction()))
	    	publisher.publishCustomEvent(amlBody[0], amlBody[1]);
	    
	    context.complete();
	}
	
	private void processError(ServiceBusErrorContext context, CountDownLatch countdownLatch) {
	    System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
	        context.getFullyQualifiedNamespace(), context.getEntityPath());

	    if (!(context.getException() instanceof ServiceBusException)) {
	        System.out.printf("Non-ServiceBusException occurred: %s%n", context.getException());
	        return;
	    }

	    ServiceBusException exception = (ServiceBusException) context.getException();
	    ServiceBusFailureReason reason = exception.getReason();

	    if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
	        || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
	        || reason == ServiceBusFailureReason.UNAUTHORIZED) {
	        System.out.printf("An unrecoverable error occurred. Stopping processing with reason %s: %s%n",
	            reason, exception.getMessage());

	        countdownLatch.countDown();
	    } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
	        System.out.printf("Message lock lost for message: %s%n", context.getException());
	    } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
	        try {
	            // Choosing an arbitrary amount of time to wait until trying again.
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	            System.err.println("Unable to sleep for period of time");
	        }
	    } else {
	        System.out.printf("Error source %s, reason %s, message: %s%n", context.getErrorSource(),
	            reason, context.getException());
	    }
	}  	
	
	public void close() {
	    System.out.println("Stopping and closing the processor");
	    processorClient.close();    
	}

}
