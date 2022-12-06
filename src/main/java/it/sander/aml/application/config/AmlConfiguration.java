package it.sander.aml.application.config;

import org.springframework.stereotype.Component;

@Component
public interface AmlConfiguration {
	
	static final String SERVICEBUS_CONNECTIONSTRING = "servicebus.connectionString";
	static final String SERVICEBUS_RESOURCEGROUP = "servicebus.resourceGroup";
	static final String SERVICEBUS_NAMESPACE = "servicebus.namespace";
	static final String SERVICEBUS_STATEMACHINE_QUEUE = "servicebus.statemachine.queue";
	static final String SERVICEBUS_PROCESSOR_QUEUE = "servicebus.processor.queue";
	
	public String getAmlConfiguration(String key) throws AmlConfigurationError;

}
