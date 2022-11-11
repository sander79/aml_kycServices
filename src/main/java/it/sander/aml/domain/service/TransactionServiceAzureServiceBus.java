package it.sander.aml.domain.service;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.Region;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.servicebus.models.AuthorizationKeys;
import com.azure.resourcemanager.servicebus.models.NamespaceAuthorizationRule;
import com.azure.resourcemanager.servicebus.models.NamespaceSku;
import com.azure.resourcemanager.servicebus.models.ServiceBusNamespace;


public class TransactionServiceAzureServiceBus implements TransactionService {
	
	
	private static final Logger log = LogManager.getLogger(TransactionServiceAzureServiceBus.class);
	
    final AzureProfile profile = new AzureProfile(AzureEnvironment.AZURE);
    final TokenCredential credential;
    final ServiceBusSenderClient sender;
    
    final String resourceGroup = "RG1";
    final String namespace = "namespace";
    final String queue = "kyc_queue";

    AzureResourceManager azureResourceManager;
    
    public TransactionServiceAzureServiceBus() {
        credential = new DefaultAzureCredentialBuilder()
            .authorityHost(profile.getEnvironment().getActiveDirectoryEndpoint())
            .build();

        AzureResourceManager azureResourceManager = AzureResourceManager
            .configure()
            .withLogLevel(HttpLogDetailLevel.BASIC)
            .authenticate(credential, profile)
            .withDefaultSubscription();

        log.info("Selected subscription: {}", azureResourceManager.subscriptionId());
        
        
        
      
        
        ServiceBusNamespace serviceBusNamespace = azureResourceManager.serviceBusNamespaces()
                .define(namespace)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(resourceGroup)
                .withSku(NamespaceSku.BASIC)
                .withNewQueue(queue, 1024)
                .create();

        System.out.println("Created service bus " + serviceBusNamespace.name());
            
        PagedIterable<NamespaceAuthorizationRule> namespaceAuthorizationRules = serviceBusNamespace.authorizationRules().list();
        
        AuthorizationKeys keys = namespaceAuthorizationRules.iterator().next().getKeys();
        
        sender = new ServiceBusClientBuilder()
                .connectionString(keys.primaryConnectionString())
                .sender()
                .queueName(queue)
                .buildClient();
        
            sender.sendMessage(new ServiceBusMessage("Hello World").setSessionId("23424"));
            sender.close();
    }

	@Override
	public UUID beginTransaction(String processId, Transaction tr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID continueTransaction(String processId, Transaction tr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID closeTransaction(String processId, Transaction tr) {
		// TODO Auto-generated method stub
		return null;
	}

}
