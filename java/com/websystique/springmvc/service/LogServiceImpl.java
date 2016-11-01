package com.websystique.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.LogDAO;
import com.websystique.springmvc.dao.OtpMailDAO;


@Service("logService")
@Transactional
public class LogServiceImpl implements LogService {
	
	@Autowired
	private LogDAO logDAO;

	@Override
	public void updateLogs(String info) {
		// TODO Auto-generated method stub
		System.out.println("***************** service update log");
		this.logDAO.updateLogs(info);
			
	}

}
