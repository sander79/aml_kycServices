package it.sander.aml.domain.service.azure;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.resourcemanager.AzureResourceManager;

import it.sander.aml.application.config.AmlConfiguration;
import it.sander.aml.application.config.AmlConfigurationError;
import it.sander.aml.domain.service.SurveyService;
import it.sander.aml.domain.service.TransactionService;
import it.sander.aml.domain.service.ValidationService;

@Service
@Profile("azureQueue")
public class TransactionServiceAzureServiceBus implements TransactionService {
	
	private static final Logger log = LogManager.getLogger(TransactionServiceAzureServiceBus.class);
	
	@Autowired	
	AmlConfiguration config;

	private final ServiceBusSenderClient sender;
    
	//@Value("${servicebus.connectionString}")
	private String connectionString = "Endpoint=sb://servicebusaml.servicebus.windows.net/;SharedAccessKeyName=aml-servicebus;SharedAccessKey=1zykiEelm3O/mU/hEsQHublVvtnxyh9ta3bKQoTSsXo=";
	
	@Value("${servicebus.resourceGroup}")
	private String resourceGroup;

	@Value("${servicebus.namespace}")
	private String namespace;
	
	@Value("${servicebus.statemachine.queue}")
	private String stateMachineQueue = "aml_statemachine_queue";

    AzureResourceManager azureResourceManager;
    
    public TransactionServiceAzureServiceBus() throws AmlConfigurationError {
    	/*
    	AzureProfile profile = new AzureProfile(AzureEnvironment.AZURE);
    	TokenCredential credential = new DefaultAzureCredentialBuilder()
            .authorityHost(profile.getEnvironment().getActiveDirectoryEndpoint())
            .build();

        AzureResourceManager azureResourceManager = AzureResourceManager
            .configure()
            .withLogLevel(HttpLogDetailLevel.BASIC)
            .authenticate(credential, profile)
            .withDefaultSubscription();
    	*/
    	
    	//connectionString = config.getAmlConfiguration("servicebus.connectionString");
    	        
        sender = new ServiceBusClientBuilder()
            .connectionString(connectionString)
            .sender()
            .queueName(stateMachineQueue)
            .buildClient();

        sender.getClass();
        //sender.close();
    }

	@Override
	public void notifyTransaction(UUID id, TransactionState tr) {
		String message = id.toString() + ";" + tr.getAction();	
		sender.sendMessage(new ServiceBusMessage(message));
	}


}
