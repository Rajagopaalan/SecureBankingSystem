package com.websystique.springmvc.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.TransactionDAO;
import com.websystique.springmvc.model.Transaction;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionDAO transactionDAO;

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	@Override
	public List<Transaction> getLatestTrnsactions(int accountNum) {
		return this.transactionDAO.getLatestTrxByAccount(accountNum);
	}

	@Override	
	public void getTransactionsPDF(String loggedinuser){
		this.transactionDAO.getTransactionsPDF(loggedinuser);
	}

	@Override
	public void saveAccount(Transaction tx) {
		this.transactionDAO.saveAccount(tx);
	}

	@Override
	public List<Transaction> getPendingTransactions() {
		return this.transactionDAO.getPendingTransaction();
		// TODO Auto-generated method stub

	}
	@Override
	public List<Transaction> updateTransaction(int transactionId){
		return this.transactionDAO.updateTransaction(transactionId);
	}


}
