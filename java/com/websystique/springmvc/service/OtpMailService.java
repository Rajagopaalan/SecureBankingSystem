package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Otp;

public interface OtpMailService {
	public void saveOtp(Otp otpno);

	public boolean checkOtp(String otp, String user);
}
