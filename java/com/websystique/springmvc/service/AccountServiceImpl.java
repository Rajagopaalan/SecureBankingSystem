package com.websystique.springmvc.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.AccountDAO;
import com.websystique.springmvc.model.Account;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDAO accountDAO;
	
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
	@Override
	@Transactional
	public boolean debit(Account acc, double amt) {
		return this.accountDAO.debit(acc,amt);
	}
	
	@Override
	@Transactional
	public Account getAccountById(int id){
		return  this.accountDAO.getAccountById(id);
		//return c;
				//(Account)this.accountDAO.getAccountById(id);
	}
	
	@Override
	@Transactional
	public void addAccount(Account c) {
		this.accountDAO.addAccount(c);
	}

	
	@Override
	@Transactional
	public void updateAccount(Account c) {
		this.accountDAO.updateAccount(c);
	}
	
	
	@Override
	@Transactional
	public void credit(Account acc, double amt) {
		this.accountDAO.credit(acc,amt);
	}
	
	public List<Account> listAccounts(String user) {
		return this.accountDAO.listAccounts(user);
	}
	
	@Override
	public boolean transferFund(String user,int toAcc, double amt) {
		return this.accountDAO.transferFund(user,toAcc, amt);
	}
		
	public String payCreditCard(String user, double amount) {
		return this.accountDAO.payCreditCard(user, amount);
	}

	@Override
	public void saveAccount(Account account) {
		accountDAO.save(account);
	}
	@Override
	public void transferFundAfterAuthorize(String user , int toAcc, double amt){
		accountDAO.transferFundAfterAuthorize(user, toAcc, amt);
	}
	public boolean transferFundUsingEmail(String user, String email, double amount){
		return accountDAO.transferFundUsingEmail(user, email, amount);
	}

	@Override
	public boolean transferFundUsingPhone(String user, long phone,
			double amount) {
		// TODO Auto-generated method stub
		return accountDAO.transferFundUsingPhone(user, phone, amount);
	}
	
	@Override
	public int getMaxTransaction() {
		 int Value = accountDAO.getMaxTransaction();
		// TODO Auto-generated method stub
		return Value;
	}
}
