package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.model.User;


public interface AccountService {
	
	public boolean debit(Account acc, double amt);
	public void credit(Account acc, double amt);
	public boolean transferFund(String user, int toAcc, double amt);
	public String payCreditCard(String user, double amount);
	public List<Account> listAccounts(String User);
	public Account getAccountById(int id);
	public void addAccount(Account c);
	public void updateAccount(Account c);
	void saveAccount(Account account);
	public void transferFundAfterAuthorize(String user , int toAcc, double amt);
	public boolean transferFundUsingEmail(String user, String parameter, double parseDouble);
	public boolean transferFundUsingPhone(String user, long phone, double parseDouble);
	public int getMaxTransaction();
}
