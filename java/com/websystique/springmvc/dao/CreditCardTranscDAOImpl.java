package com.websystique.springmvc.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Account;

import com.websystique.springmvc.model.CreditCard;
import com.websystique.springmvc.model.CreditCardTransc;
import com.websystique.springmvc.model.Transaction;
import com.websystique.springmvc.model.Transc_mstr;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.CreditCardService;
import com.websystique.springmvc.service.TransactionService;

@Repository("creditCardTranscDao")
public class CreditCardTranscDAOImpl extends AbstractDao<Integer, CreditCardTransc> implements CreditCardTranscDAO {

	@Override
	public void saveTransaction(CreditCardTransc tx) {
			persist(tx);
	}
		// TODO Auto-generated method stub
		
	
}
