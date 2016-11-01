package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.CreditCard;
import com.websystique.springmvc.model.CreditCardTransc;

public interface CreditCardService {
	public boolean debit(CreditCard card, double amt);
	public void credit(CreditCard card, double amt);
	public String makePayment(String user, int toAcc, double amt);
	public double interestGeneration(CreditCard card);
	public double latePaymentFee(CreditCard card);
	public String newCreditLimit(String user); 
	public List<CreditCard> listCards(String user); 
	public List<CreditCardTransc> getTransaction(String user); 	
	public long getMaxCard();	
	public void saveCard(CreditCard card);
	String validateAndPay(int otp, String user);
}
