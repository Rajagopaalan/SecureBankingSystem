package com.websystique.springmvc.dao;

import java.util.List;

import org.springframework.ui.Model;

import com.websystique.springmvc.model.Transc_mstr;
import com.websystique.springmvc.model.acc_mstr;


public interface ExtUserDAO {

public List<Transc_mstr> getTransaction(int transc_acc_num);
	
	public List<String> getuseraccountnumbers(int EXT_USER_ID);
	public String Updatetransactions(int acc_id,int amount , String Transactiontype);
	public String UpdateBalance(int acc_id,int amount , String Transactiontype);
	public String getUserdetails(int acc_id); 
	public String getUsertype(int acc_id); 
	public String merchantpaymentforuser(String user, int acc_id, int amount);
	public 	List<Transc_mstr>  userauthorization(int acc_id);
	public 	String  userapproval(int transc_id);
	public int getUserId(String user);

}
