package com.websystique.springmvc.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.configuration.MailConfiguration;
import com.websystique.springmvc.model.Account;

import com.websystique.springmvc.model.CreditCard;
import com.websystique.springmvc.model.CreditCardTransc;
import com.websystique.springmvc.model.Otp;
import com.websystique.springmvc.model.Transaction;
import com.websystique.springmvc.model.Transc_mstr;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.CreditCardService;
import com.websystique.springmvc.service.CreditCardTranscService;
import com.websystique.springmvc.service.OtpMailService;
import com.websystique.springmvc.service.TransactionService;



@Repository("creditCardDao")
public class CreditCardDAOImpl extends AbstractDao<Integer, CreditCard> implements CreditCardDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	OtpMailService otpMailService;
	
	
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private CreditCardTranscService creditCardTranscService;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public boolean debit(CreditCard card, double amt) {
		Session session = this.sessionFactory.getCurrentSession();
		double balance = card.getCreditLimit() - card.getAmountSpent();
		if(amt <= balance){
			card.setAmountSpent(card.getAmountSpent() + amt);
			session.update(card);
			return true;
		}  
		return false;
	}

	@Override
	public void credit(CreditCard card, double amt) {
		Session session = this.sessionFactory.getCurrentSession();
		card.setAmountSpent(card.getAmountSpent() - amt);
		session.update(card);
	}

	@Override
	public String validateAndPay(int otp, String user){
		Session session = this.sessionFactory.getCurrentSession();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd"); 
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		List<Transaction> transList = session.createQuery("from Transaction where id = "+id +" AND trxStatusId=4").list();
		if(transList.size()==0) return "no transaction waiting for otp";
		transList.get(0).setTrxStatusId(3);	
		double amt=transList.get(0).getTrxAmount();
		transactionService.saveAccount(transList.get(0));
		List<CreditCard> cardsList = session.createQuery("from CreditCard where uid="+id).list();
		CreditCardTransc ccTransc = new CreditCardTransc();
		cardsList.get(0).setAmountSpent(cardsList.get(0).getAmountSpent() + amt);
		List<Account> toAccList = session.createQuery("from Account where account_no="+Integer.parseInt(transList.get(0).getTrxMerchant())).list();
		toAccList.get(0).setBalance(toAccList.get(0).getBalance() + amt);
		session.update(cardsList.get(0));
		session.update(toAccList.get(0));
	
		ccTransc.setCcId(cardsList.get(0).getCardId());
		ccTransc.setAmount(amt);
		ccTransc.setId(cardsList.get(0).getUid());
		ccTransc.setAcc_id(cardsList.get(0).getAccountId());
		ccTransc.setDate(df.format(new Date()));
		ccTransc.setTrxDesc("Making Payment");
		ccTransc.setMerchant(Integer.parseInt(transList.get(0).getTrxMerchant()));
		creditCardTranscService.saveTransaction(ccTransc);
		return "Payment Done";
		
	}
	@Override
	public String makePayment(String user, int toAcc, double amt) {
		Session session = this.sessionFactory.getCurrentSession();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd"); 
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		List<CreditCard> cardsList = session.createQuery("from CreditCard where uid="+id).list();
		List<Account> toAccList = session.createQuery("from Account where account_no="+toAcc).list();	
		List<Account> accountsList = session.createQuery("from Account where uid="+id).list();
		double balance = (cardsList.get(0).getCreditLimit() - cardsList.get(0).getAmountSpent());
		if(amt<=balance && toAccList.size()>0){
			Transaction tx = new Transaction();
			tx.setTrxAccNum(accountsList.get(0).getAccount_no());
			tx.setTrxAmount(amt);
			tx.setId(1);
			tx.setTrxDate(df.format(new Date()));
			tx.setTrxTypeId(1);
			Integer acc = toAcc;
			tx.setTrxMerchant(acc.toString());
			tx.setTrxFee(0);
			tx.setTrxDesc("Credit Card Payment");
			tx.setTrxStatusId(4);
			tx.setBalance(0);
			transactionService.saveAccount(tx);
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
			otpMailService.saveOtp(otpno);
			return "OTP sent to Email";	
		}
		return "Payment Failure";
	}

	@Override
	public double interestGeneration(CreditCard card) {
		Session session = this.sessionFactory.getCurrentSession();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		Date curDate = new Date();
		Date dueDate;
		double interestRate = 0;
		try {
			dueDate = format.parse(card.getDueDate());
			int numOfDays = (int)(dueDate.getTime() - curDate.getTime());
			if(numOfDays > 0) {
				interestRate = (card.getCreditLimit() - card.getAmountSpent())*0.1;		
			}
			card.setInterestAmount(interestRate);
			session.update(card);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public double latePaymentFee(CreditCard card) {
		Session session = this.sessionFactory.getCurrentSession();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		Date curDate = new Date();
		Date dueDate;
		double latePayment = 0;
		try {
			dueDate = format.parse(card.getDueDate());
			int numOfDays = (int)(dueDate.getTime() - curDate.getTime());
			if(numOfDays > 0) {
				latePayment = (card.getCreditLimit() - card.getAmountSpent())*0.1;		
			}
			card.setLateFee(latePayment);
			session.update(card);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String newCreditLimit(String user) {
		Session session = this.sessionFactory.getCurrentSession();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		List<CreditCard> cardsList = session.createQuery("from CreditCard where uid="+id).list();
		String lastUpdate = null;
		String nextUpdate = df.format(new Date());
		if(cardsList.size()>0)
			lastUpdate = cardsList.get(0).getLastLimitUpdate();
		try {
			int diff = dateDifference(df.parse(lastUpdate), df.parse(nextUpdate));
			if(diff > 60) {
				session.createQuery("Update CreditCard set lastLimitUpdate = '"+ nextUpdate +"'where uid="+id).executeUpdate();
				session.createQuery("Update CreditCard set creditLimit = credit_card_limit"+ 500 +" where uid="+id).executeUpdate();
				return "Credit Limit Increased";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		return "Credit Limit Not Increased";
	}

	@Override
	public List<CreditCard> listCards(String user){
		Session session = this.sessionFactory.getCurrentSession();
		int id=0;
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		if(usersList.size()>0)
			id = usersList.get(0).getId();

		List<CreditCard> cardsList = session.createQuery("from CreditCard where uid="+id).list();
		for(CreditCard card : cardsList) {
			//System.out.println("Account no retrieved is "+account.getAccount_no());
		}
		return cardsList;
	}

	@Override	
	@Transactional
	public List<CreditCardTransc> getTransaction(String user){
		Session session = this.sessionFactory.getCurrentSession();
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		List<CreditCardTransc> ccTrxList = session.createQuery("from CreditCardTransc where id="+id).list();
		return ccTrxList ;
	}
	
	@Override
	public long getMaxCard() {
		Session session = this.sessionFactory.getCurrentSession();
		List<CreditCard> CreditCardList = session.createQuery("from CreditCard").list();
		long max_value = 0;
		for(CreditCard c : CreditCardList){
			System.out.println(c.getCardId());
			long value = c.getCardId();
			if (value>max_value){
				max_value = value;
			}
		}
		System.out.println(CreditCardList.get(0).getCardId());
		System.out.println("Max Value"+max_value);
		// TODO Auto-generated method stub
		return max_value;
		}
	@Override
	public void saveCard(CreditCard c){
		persist(c);
	}

	
	private int dateDifference(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int diffDay = 0;
		if (c1.before(c2)) 
		{
			int returnInt = 0;
			while (!c1.after(c2)) {
				c1.add(Calendar.DAY_OF_MONTH, 1);
				returnInt++;
			}
			if (returnInt > 0) {
				returnInt = returnInt - 1;
			}
			diffDay = returnInt;
		} 
		else {
			int returnInt = 0;
			while (!c2.after(c1)) {
				c2.add(Calendar.DAY_OF_MONTH, 1);
				returnInt++;
			}
			if (returnInt > 0) {
				returnInt = returnInt - 1;
			}
			diffDay = returnInt;
		}
		return diffDay;
	}
}
