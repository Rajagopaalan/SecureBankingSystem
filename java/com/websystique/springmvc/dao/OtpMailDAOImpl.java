package com.websystique.springmvc.dao;

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
import com.websystique.springmvc.model.Otp;
import com.websystique.springmvc.model.Transaction;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.TransactionService;


@Repository
public class OtpMailDAOImpl extends AbstractDao<Integer, Otp> implements  OtpMailDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	TransactionService transactionService;
	
	@Override
	public void saveOtp(Otp otpno) {
		Session session = this.sessionFactory.getCurrentSession();
		System.out.println("+++++++++++++++++++++++in otpdaoimpl");
		// TODO Auto-generated method stub
		persist(otpno);
	}

	@Override
	public boolean checkOtp(String otp, String user) {
		Session session = this.sessionFactory.getCurrentSession();
		// TODO Auto-generated method stub
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = usersList.get(0).getId();
		List<Account> accountList = session.createQuery("from Account where uid = "+id).list();
		long accNo = accountList.get(0).getAccount_no();
		System.out.println("===========acc is "+accNo);
		List<Otp> otpList = session.createQuery("from Otp where accountId = "+accNo).list();
		if(otp.equals(otpList.get(0).getOtp())){
			System.out.println("=================is equal");
			List<Transaction> transactionList = session.createQuery("from Transaction where trxAccNum = "+accNo + " AND transc_status_id = 4").list();
			transactionList.get(0).setTrxStatusId(1);
			System.out.println("==================size is "+transactionList.size());
			transactionService.saveAccount(transactionList.get(0));  
			System.out.println("============================before delete");
			System.out.println("===========acc is "+accNo);
			
			session.delete(otpList.get(0));
			return true;
		}else {
			List<Transaction> transactionList = session.createQuery("from Transaction where trxAccNum = "+accNo + " AND transc_status_id = 4").list();
			transactionList.get(0).setTrxStatusId(2);
			session.delete(otpList.get(0));
			System.out.println("==================size is "+transactionList.size());
			transactionService.saveAccount(transactionList.get(0));  
			return false;
		}
		
		
	
	}

	
	/*@Override
	public  void sendOTP() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		String otp = String.format("%04d", rand.nextInt(10000));
		MailConfiguration config = new MailConfiguration();
		JavaMailSenderImpl mail= (JavaMailSenderImpl) config.javaMailService();
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("archithit@gmail.com");
		msg.setSubject("State Bank of Arizona OTP");
		msg.setText("Your OTP is "+otp);
		mail.send(msg);
		
		
	}
	public static void main(String[] args){
		OtpMailDAOImpl obj = new OtpMailDAOImpl();
		obj.sendOTP();
	}*/

}
