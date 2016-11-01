package com.websystique.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.OtpMailDAO;
import com.websystique.springmvc.dao.TransactionDAO;
import com.websystique.springmvc.model.Otp;


@Service("otpMailService")
@Transactional
public class OtpMailServiceImpl implements OtpMailService {

	@Autowired
	private OtpMailDAO otpMailDao;
	
	@Override
	public void saveOtp(Otp otpno) {
		// TODO Auto-generated method stub
		this.otpMailDao.saveOtp(otpno);
	}

	@Override
	public boolean checkOtp(String otp, String user) {
		// TODO Auto-generated method stub
		return this.otpMailDao.checkOtp(otp,user);
	}

}
