package it.sander.aml.domain.service;

import java.util.UUID;

public interface TransactionService {
	
	public static enum Transaction {
		SUBMIT;
	}
	
	UUID beginTransaction(String processId, Transaction tr);
	UUID continueTransaction(String processId, Transaction tr);
	UUID closeTransaction(String processId, Transaction tr);

}
