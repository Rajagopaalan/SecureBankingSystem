package com.websystique.springmvc.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Transaction;

public interface TransactionDAO {
	
	public List<Transaction> getLatestTrxByAccount(int accountNum);
	public void getTransactionsPDF(String loggedinuser);
	public void saveAccount(Transaction tx);
	public List<Transaction> getPendingTransaction();
	public List<Transaction> updateTransaction(int transactionId);
}
