package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Otp;

public interface OtpMailDAO {
	public void saveOtp(Otp otpno);

	public boolean checkOtp(String otp, String user);
}
