package com.websystique.springmvc.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.websystique.springmvc.dao.CreditCardTranscDAO;
import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.model.CreditCard;
import com.websystique.springmvc.model.CreditCardTransc;
import com.websystique.springmvc.model.Transaction;

@Service("creditCardTranscService")
@Transactional
public class CreditCardTranscServiceImpl implements CreditCardTranscService {


	@Autowired
	private CreditCardTranscDAO creditCardTranscDAO;
	
	@Override
	public void saveTransaction(CreditCardTransc tx) {
		creditCardTranscDAO.saveTransaction(tx);
	}
	
	
}
