package it.sander.aml.domain.service.mock;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import it.sander.aml.domain.service.TransactionService;

@Service
@Profile("mockQueue")
public class TransactionServiceMock implements TransactionService {
	
	private static final Logger log = LogManager.getLogger(TransactionServiceMock.class);
    
    private final String resourceGroup = "RG1";
    private final String namespace = "namespace";
    private final String queue = "kyc_queue";
    
    public TransactionServiceMock() {
    }

	@Override
	public void notifyTransaction(UUID id, TransactionState tr) {
		String message = id.toString() + ";" + tr.getAction();	
	}

}
