package com.websystique.springmvc.dao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.configuration.MailConfiguration;
import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.model.CreditCard;
import com.websystique.springmvc.model.Otp;
import com.websystique.springmvc.model.Transaction;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.OtpMailService;
import com.websystique.springmvc.service.TransactionService;



@Repository
public class AccountDAOImpl extends AbstractDao<Integer, Account> implements AccountDAO {
	//static public int accNo = 0;
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	OtpMailService otpMailService;
	
	

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public Account getAccountById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Account c = (Account) session.load(Account.class, new Integer(id));
		return c;
	}


	@Override
	public void addAccount(Account c) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(c);
		//logger.info("Person saved successfully, Person Details="+p);
	}

	@Override
	public void updateAccount(Account c) {
		Session session = this.sessionFactory.getCurrentSession();
		System.out.println("session");
		session.update(c);
		//logger.info("Person updated successfully, Person Details="+p);
	}


	@Override
	public boolean debit(Account acc, double amt) {

		Session session = this.sessionFactory.getCurrentSession();
		if(amt<=acc.getBalance()){
			acc.setBalance(acc.getBalance()-amt);
			session.update(acc);
			return true;
		}  
		return false;
	}

	@Override
	public void credit(Account acc, double amt) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		acc.setBalance(acc.getBalance()+ amt);
		session.update(acc);


	}

	@Override
	public List<Account> listAccounts(String user) {
		Session session = this.sessionFactory.getCurrentSession();
		int id=0;
		System.out.println("============"+user);
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		System.out.println("=========user size is "+usersList.size() );
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		System.out.println("+++++++++++++++id is "+id);
		
		List<Account> accountsList = session.createQuery("from Account where uid="+id).list();
		for(Account account : accountsList) {
			System.out.println("Account no retrieved is "+account.getAccount_no());
		}
		return accountsList;
	}


	@Override
	public boolean transferFund(String user , int toAcc, double amt) {
		Session session = this.sessionFactory.getCurrentSession();
		//	System.out.println("From Acc is "+fromAcc+" To Acc is "+toAcc);
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		System.out.println("===================== id is"+id);
		List<Account> accountsList = session.createQuery("from Account where uid="+id).list();
		List<Account> toAccList = session.createQuery("from Account where account_no="+toAcc).list();
		if(toAccList.size()==0) return false;
		System.out.println("====================== Acc List"+accountsList);
		System.out.println("====================== to List"+toAccList);
		//accNo=accountsList.get(0).getAccount_no();
		if(amt<=accountsList.get(0).getBalance() && toAccList.size()>0){
			Transaction tx = new Transaction();
			tx.setTrxAccNum(accountsList.get(0).getAccount_no());
			tx.setTrxAmount(amt);
			tx.setId(id);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			String strDate=dateFormat.format(date);
			//System.out.println(dateFormat.format(date));
			tx.setTrxDate(strDate);
			tx.setTrxTypeId(2);
			tx.setTrxMerchant(String.valueOf(toAcc));
			tx.setTrxFee(0);
			tx.setTrxDesc("Fund Transfer");
			tx.setBalance(0);
			tx.setTrxStatusId(4);
			if(amt<=10000) {
				accountsList.get(0).setBalance(accountsList.get(0).getBalance()-amt);
				toAccList.get(0).setBalance(toAccList.get(0).getBalance()+amt);
				session.update(accountsList.get(0));
				session.update(toAccList.get(0));
				tx.setTrxStatusId(3);
				transactionService.saveAccount(tx);
				return true;
			}
			else {
				List<Transaction> transList = session.createQuery("from Transaction where trxAccNum="+accountsList.get(0).getAccount_no()+ " AND transc_status_id=4").list();
				if(transList.size()>0){
					return false;
				}
				Random rand = new Random();
				String otp = String.format("%04d", rand.nextInt(10000));
				MailConfiguration config = new MailConfiguration();
				JavaMailSenderImpl mail= (JavaMailSenderImpl) config.javaMailService();
				SimpleMailMessage msg = new SimpleMailMessage();
				String mailid = usersList.get(0).getEmail(); 
				System.out.println("====================== mail id"+mailid);
				msg.setTo(mailid);
				msg.setSubject("State Bank of Arizona OTP");
				msg.setText("Your OTP is "+otp);
				mail.send(msg);
				transactionService.saveAccount(tx);
				Otp otpno = new Otp();
				otpno.setAccountId(accountsList.get(0).getAccount_no());
				otpno.setOtp(otp);
				otpno.setPhone(usersList.get(0).getPhone());
				//otpno.setTransc_id(transc_id);
				//OtpMailDAOImpl obj = new OtpMailDAOImpl();
				System.out.println("=====================before calling function");
				otpMailService.saveOtp(otpno);
				System.out.println("===================otp saved");
				transactionService.saveAccount(tx);
				return true;
			}	
		}
		return false;
	}
	public void transferFundAfterAuthorize(String user , int toAcc, double amt) {
		Session session = this.sessionFactory.getCurrentSession();
		//	System.out.println("From Acc is "+fromAcc+" To Acc is "+toAcc);
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		System.out.println("===================== id is"+id);
		List<Account> accountsList = session.createQuery("from Account where uid="+id).list();
		List<Account> toAccList = session.createQuery("from Account where account_no="+toAcc).list();
		accountsList.get(0).setBalance(accountsList.get(0).getBalance()-amt);
		toAccList.get(0).setBalance(toAccList.get(0).getBalance()+amt);
		session.update(accountsList.get(0));
		session.update(toAccList.get(0));
		
		
		System.out.println("====================== Acc List"+accountsList);
		System.out.println("====================== to List"+toAccList);
		//accNo=accountsList.get(0).getAccount_no();
		
	}
	
	@Override
	public boolean transferFundUsingPhone(String user, long phone, double amt) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		//	System.out.println("From Acc is "+fromAcc+" To Acc is "+toAcc);
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		System.out.println("===================== id is"+id);
		List<User> toUsers = session.createQuery("from User where phone = "+phone).list();
		if(toUsers.size()==0) return false;
		int toId= toUsers.get(0).getId();
		List<Account> toAccList = session.createQuery("from Account where uid="+toId).list();
		
		List<Account> accountsList = session.createQuery("from Account where uid="+id).list();
		//List<Account> toAccList = session.createQuery("from Account where account_no="+toAcc).list();
		System.out.println("====================== Acc List"+accountsList);
		//System.out.println("====================== to List"+toAccList);
		//accNo=accountsList.get(0).getAccount_no();
		if(amt<=accountsList.get(0).getBalance() && toAccList.size()>0){
			Transaction tx = new Transaction();
			tx.setTrxAccNum(accountsList.get(0).getAccount_no());
			tx.setTrxAmount(amt);
			tx.setId(id);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			String strDate=dateFormat.format(date);
			//System.out.println(dateFormat.format(date));
			tx.setTrxDate(strDate);
			tx.setTrxTypeId(2);
			tx.setTrxMerchant(String.valueOf(toAccList.get(0).getAccount_no()));
			tx.setTrxFee(0);
			tx.setTrxDesc("Fund Transfer");
			tx.setBalance(0);
			tx.setTrxStatusId(4);
			if(amt<=10000) {
				accountsList.get(0).setBalance(accountsList.get(0).getBalance()-amt);
				toAccList.get(0).setBalance(toAccList.get(0).getBalance()+amt);
				session.update(accountsList.get(0));
				session.update(toAccList.get(0));
				tx.setTrxStatusId(3);
				transactionService.saveAccount(tx);
				return true;
			}
			else {
				List<Transaction> transList = session.createQuery("from Transaction where trxAccNum="+accountsList.get(0).getAccount_no()+ " AND transc_status_id=4").list();
				if(transList.size()>0){
					return false;
				}
				Random rand = new Random();
				String otp = String.format("%04d", rand.nextInt(10000));
				MailConfiguration config = new MailConfiguration();
				JavaMailSenderImpl mail= (JavaMailSenderImpl) config.javaMailService();
				SimpleMailMessage msg = new SimpleMailMessage();
				String mailid = usersList.get(0).getEmail(); 
				System.out.println("====================== mail id"+mailid);
				msg.setTo(mailid);
				msg.setSubject("State Bank of Arizona OTP");
				msg.setText("Your OTP is "+otp);
				mail.send(msg);
				transactionService.saveAccount(tx);
				Otp otpno = new Otp();
				otpno.setAccountId(accountsList.get(0).getAccount_no());
				otpno.setOtp(otp);
				otpno.setPhone(usersList.get(0).getPhone());
				//otpno.setTransc_id(transc_id);
				//OtpMailDAOImpl obj = new OtpMailDAOImpl();
				System.out.println("=====================before calling function");
				otpMailService.saveOtp(otpno);
				System.out.println("===================otp saved");
				transactionService.saveAccount(tx);
				return true;

			}	
		}
		return false;
	}

	public boolean transferFundUsingEmail(String user, String email, double amt){
		Session session = this.sessionFactory.getCurrentSession();
		//	System.out.println("From Acc is "+fromAcc+" To Acc is "+toAcc);
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		System.out.println("===================== id is"+id);
		List<User> toUsers = session.createQuery("from User where email = '"+email+"'").list();
		if(toUsers.size()==0) return false;
		int toId= toUsers.get(0).getId();
		List<Account> toAccList = session.createQuery("from Account where uid="+toId).list();
		
		List<Account> accountsList = session.createQuery("from Account where uid="+id).list();
		//List<Account> toAccList = session.createQuery("from Account where account_no="+toAcc).list();
		System.out.println("====================== Acc List"+accountsList);
		//System.out.println("====================== to List"+toAccList);
		//accNo=accountsList.get(0).getAccount_no();
		if(amt<=accountsList.get(0).getBalance() && toAccList.size()>0 && amt>0){
			Transaction tx = new Transaction();
			tx.setTrxAccNum(accountsList.get(0).getAccount_no());
			tx.setTrxAmount(amt);
			tx.setId(id);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			String strDate=dateFormat.format(date);
			//System.out.println(dateFormat.format(date));
			tx.setTrxDate(strDate);
			tx.setTrxTypeId(2);
			tx.setTrxMerchant(String.valueOf(toAccList.get(0).getAccount_no()));
			tx.setTrxFee(0);
			tx.setTrxDesc("Fund Transfer By Email");
			tx.setBalance(0);
			tx.setTrxStatusId(4);
			if(amt<=10000) {
				accountsList.get(0).setBalance(accountsList.get(0).getBalance()-amt);
				toAccList.get(0).setBalance(toAccList.get(0).getBalance()+amt);
				session.update(accountsList.get(0));
				session.update(toAccList.get(0));
				tx.setTrxStatusId(3);
				transactionService.saveAccount(tx);
				return true;
			}
			else {
				List<Transaction> transList = session.createQuery("from Transaction where trxAccNum="+accountsList.get(0).getAccount_no()+ " AND transc_status_id=4").list();
				if(transList.size()>0){
					return false;
				}
				Random rand = new Random();
				
				String otp = String.format("%04d", rand.nextInt(10000));
				MailConfiguration config = new MailConfiguration();
				JavaMailSenderImpl mail= (JavaMailSenderImpl) config.javaMailService();
				SimpleMailMessage msg = new SimpleMailMessage();
				String mailid = usersList.get(0).getEmail(); 
				System.out.println("====================== mail id"+mailid);
				msg.setTo(mailid);
				msg.setSubject("State Bank of Arizona OTP");
				msg.setText("Your OTP is "+otp);
				mail.send(msg);
				transactionService.saveAccount(tx);
				Otp otpno = new Otp();
				otpno.setAccountId(accountsList.get(0).getAccount_no());
				otpno.setOtp(otp);
				otpno.setPhone(usersList.get(0).getPhone());
				//otpno.setTransc_id(transc_id);
				//OtpMailDAOImpl obj = new OtpMailDAOImpl();
				System.out.println("=====================before calling function");
				otpMailService.saveOtp(otpno);
				System.out.println("===================otp saved");
				transactionService.saveAccount(tx);
				return true;
			}	
		}
		return false;
	}
	
	
	@Override
	public String payCreditCard(String user, double amount) {
		Session session = this.sessionFactory.getCurrentSession();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd"); 
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String strDate=dateFormat.format(date);
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		List<CreditCard> cardsList = session.createQuery("from CreditCard where uid = "+id).list();
		//List<CreditCard> cardsList = session.createQuery("from CreditCard where cardId="+card).list();
		List<Account> accountsList = session.createQuery("from Account where uid="+id).list();
		double balance = (cardsList.get(0).getCreditLimit() - cardsList.get(0).getAmountSpent());
		if(amount<=balance && cardsList.size()>0){
			Transaction tx = new Transaction();
			tx.setTrxAccNum(accountsList.get(0).getAccount_no());
			tx.setTrxAmount(amount);
			tx.setId(id);
			tx.setTrxDate(strDate);
			tx.setTrxTypeId(2);
			tx.setTrxMerchant(" ");
			tx.setTrxFee(0);
			tx.setTrxDesc("Credit Card Bill Payment");
			tx.setTrxStatusId(3);
			tx.setBalance(0);	
			cardsList.get(0).setAmountSpent(cardsList.get(0).getAmountSpent() - amount);
			accountsList.get(0).setBalance(accountsList.get(0).getBalance() - amount);
			session.update(cardsList.get(0));
			session.update(accountsList.get(0));
			transactionService.saveAccount(tx);
			return "Bill Payment Done";	
		}
		return "Bill Payment Denied";
	}

	@Override
	public void save(Account account) {
		persist(account);
	}

	@Override
	public int getMaxTransaction() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Account> AccountList = session.createQuery("from Account").list();
		int max_value = 0;
		for(Account a : AccountList){
			System.out.println(a.getAccount_no());
			int value = a.getAccount_no();
			if (value>max_value){
				max_value = value;
			}
		}
		System.out.println(AccountList.get(0).getAccount_no());
		System.out.println("Max Value"+max_value);
		// TODO Auto-generated method stub
		return max_value;
	}
}
