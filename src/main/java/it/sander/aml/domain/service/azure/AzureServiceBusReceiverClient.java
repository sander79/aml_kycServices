package it.sander.aml.domain.service.azure;

/**
 * 
      https://azuresdkartifacts.blob.core.windows.net/azure-sdk-for-java/staging/apidocs/com/azure/messaging/servicebus/models/ServiceBusReceiveMode.html
      
      https://azuresdkartifacts.blob.core.windows.net/azure-sdk-for-java/staging/apidocs/com/azure/messaging/servicebus/ServiceBusReceiverAsyncClient.html
 *
 */

public class AzureServiceBusReceiverClient {
/*	
	ServiceBusReceiverAsyncClient consumer;
	
/*    
	public AzureServiceBusReceiverClient() {
		consumer = new ServiceBusClientBuilder()
			     .connectionString(connectionString)
			     .receiver()
			     .queueName(processorQueue)
			     .buildAsyncClient();

	}
/*	
	public void receive() {
		Disposable subscription = consumer.receiveMessages()
			     .subscribe(message -> {
			         System.out.printf("Received Seq #: %s%n", message.getSequenceNumber());
			         System.out.printf("Contents of message as string: %s%n", message.getBody());
			     },
			         error -> System.out.println("Error occurred: " + error),
			         () -> System.out.println("Receiving complete."));

			 // When program ends, or you're done receiving all messages.
			 subscription.dispose();
	}
	
	public void close() {
		 consumer.close();
	}
*/
}
