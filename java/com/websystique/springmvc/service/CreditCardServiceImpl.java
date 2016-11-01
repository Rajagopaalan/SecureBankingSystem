package com.websystique.springmvc.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.CreditCardDAO;
import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.model.CreditCard;
import com.websystique.springmvc.model.CreditCardTransc;
import com.websystique.springmvc.model.Transaction;

@Service("creditCardService")
@Transactional
public class CreditCardServiceImpl implements CreditCardService {
	
	@Autowired
	private CreditCardDAO creditCardDAO;
	
	public void setCreditCardDAO(CreditCardDAO creditCardDAO) {
		this.creditCardDAO = creditCardDAO;
	}
	
	@Override
	@Transactional
	public boolean debit(CreditCard card, double amt) {
		return this.creditCardDAO.debit(card,amt);
	}
		
	@Override
	@Transactional
	public void credit(CreditCard card, double amt) {
		this.creditCardDAO.credit(card,amt);
	}

	@Override
	@Transactional
	public String makePayment(String user, int toAcc, double amt) {
		return this.creditCardDAO.makePayment(user, toAcc, amt);
	}

	@Override
	public double interestGeneration(CreditCard card) {
		this.creditCardDAO.interestGeneration(card);
		return 0;
	}

	@Override
	public double latePaymentFee(CreditCard card) {
		this.latePaymentFee(card);
		return 0;
	}

	@Override
	public String newCreditLimit(String user) {
		return this.creditCardDAO.newCreditLimit(user);
	}
	
	@Override
	public List<CreditCard> listCards(String user) {
		return this.creditCardDAO.listCards(user);
	}

	@Override
	public List<CreditCardTransc> getTransaction(String user) {
		return this.creditCardDAO.getTransaction(user);
	}
	
	@Override
	public long getMaxCard() {
		long temp = creditCardDAO.getMaxCard();

		return temp;
	}
	@Override
	public void saveCard(CreditCard c){
		this.creditCardDAO.saveCard(c);
		
	}
	
	@Override
	public String validateAndPay(int otp, String user){
		return this.creditCardDAO.validateAndPay(otp, user);
	}
}
