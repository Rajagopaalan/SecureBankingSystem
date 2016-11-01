package com.websystique.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.model.CreditCard;
import com.websystique.springmvc.model.CreditCardTransc;

public interface CreditCardTranscDAO {
	public void saveTransaction(CreditCardTransc tx);

}
