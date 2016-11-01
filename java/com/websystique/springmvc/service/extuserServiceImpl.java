package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.ExtUserDAO;

import com.websystique.springmvc.model.Transc_mstr;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.acc_mstr;

@Service("extuserService")
public class extuserServiceImpl implements extuserservice{

	@Autowired
	private ExtUserDAO ExtUserDAO;


	public void setExtUserDAO(ExtUserDAO ExtUserDAO) {
		this.ExtUserDAO = ExtUserDAO;
	}


	@Override
	@Transactional
	public List<Transc_mstr> getTransaction(int transc_acc_num) {
		// TODO Auto-generated method stub

		return this.ExtUserDAO.getTransaction(transc_acc_num);
	}

	@Override
	@Transactional
	public void removeUser(int userId) {
		// TODO Auto-generated method stub

	}


	@Override
	@Transactional
	public List<String> getuseraccountnumbers(int EXT_USER_ID) {
		// TODO Auto-generated method stub
		System.out.println("in service");
		return this.ExtUserDAO.getuseraccountnumbers(EXT_USER_ID) ;

		//return null;
	}


	@Override
	@Transactional
	public String Updatetransactions(int acc_id, int amount, String Transactiontype) {
		// TODO Auto-generated method stub
		return this.ExtUserDAO.Updatetransactions(acc_id, amount, Transactiontype) ;

	}


	@Transactional
	@Override
	public String UpdateBalance(int acc_id, int amount, String Transactiontype) {
		// TODO Auto-generated method stub

		return this.ExtUserDAO.UpdateBalance(acc_id, amount, Transactiontype) ;
	}


	@Override
	@Transactional

	public String getuserdetails(int acc_id) {
		// TODO Auto-generated method stub
		return this.ExtUserDAO.getUserdetails(acc_id);
	}


	@Override
	@Transactional
	public String getusertype(int acc_id) {
		// TODO Auto-generated method stub
		return this.ExtUserDAO.getUsertype(acc_id);
	}


	@Override
	@Transactional
	public String merchantpaymentforuser(String user, int acc_id, int amount) {
		// TODO Auto-generated method stub
		return this.ExtUserDAO.merchantpaymentforuser(user, acc_id, amount);
	}


	@Override
	@Transactional
	public List<Transc_mstr> userauthorization(int acc_id) {
		// TODO Auto-generated method stub
		return this.ExtUserDAO.userauthorization(acc_id) ;
	}


	@Override
	@Transactional
	public String userapproval(int transc_id) {
		// TODO Auto-generated method stub
		return this.ExtUserDAO.userapproval(transc_id);
	}


	@Override
	@Transactional
	public int getUserId(String user) {
		System.out.println("in service");
		return this.ExtUserDAO.getUserId(user) ;
	}
}
