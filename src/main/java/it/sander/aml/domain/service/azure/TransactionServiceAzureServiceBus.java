package it.sander.aml.domain.service.azure;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.resourcemanager.AzureResourceManager;

import it.sander.aml.application.config.AmlConfiguration;
import it.sander.aml.application.config.AmlConfigurationError;
import it.sander.aml.domain.service.TransactionService;

@Service
@Profile("azureQueue")
public class TransactionServiceAzureServiceBus implements TransactionService {
	
	private static final Logger log = LogManager.getLogger(TransactionServiceAzureServiceBus.class);
	
	@Autowired	
	AmlConfiguration config;

	private ServiceBusSenderClient sender;

    AzureResourceManager azureResourceManager;
    
	@PostConstruct
	private void init() throws AmlConfigurationError {
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
    	
    	String connectionString = config.getAmlConfiguration(AmlConfiguration.SERVICEBUS_CONNECTIONSTRING);
    	String stateMachineQueue = config.getAmlConfiguration(AmlConfiguration.SERVICEBUS_STATEMACHINE_QUEUE);   
    	
        sender = new ServiceBusClientBuilder()
            .connectionString(connectionString)
            .sender()
            .queueName(stateMachineQueue)
            .buildClient();

        log.info("AzureServiceBus started");
        //sender.close();
	}


	@Override
	public void notifyTransaction(UUID id, TransactionState tr) {
		String message = id.toString() + ";" + tr.getAction();	
		sender.sendMessage(new ServiceBusMessage(message));
	}


}
