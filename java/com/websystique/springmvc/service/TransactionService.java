package com.websystique.springmvc.service;

import java.sql.Date;
import java.util.List;

import com.websystique.springmvc.model.Transaction;

public interface TransactionService {
	
	public List<Transaction> getLatestTrnsactions(int accountNum);
	public void getTransactionsPDF(String user);
	public void saveAccount(Transaction tx);
	public List<Transaction> getPendingTransactions();
	public List<Transaction> updateTransaction(int transactionId);

}
	