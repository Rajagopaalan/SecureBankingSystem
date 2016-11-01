package com.websystique.springmvc.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Account;

@Repository("accountDAO")
public interface AccountDAO {
	
		public boolean debit(Account acc, double amt);
		public void credit(Account acc, double amt);
		public boolean transferFund(String user, int toAcc, double amt);
		public String payCreditCard(String user, double amount);
		public List<Account> listAccounts(String user);
		public Account getAccountById(int id);
		public void addAccount(Account c);
		public void updateAccount(Account c); 
		public void save(Account account);
		public void transferFundAfterAuthorize(String user , int toAcc, double amt);
		public boolean transferFundUsingEmail(String user, String email, double amount);
		public boolean transferFundUsingPhone(String user, long phone,
				double amt);
		public int getMaxTransaction();
}
