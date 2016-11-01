package com.websystique.springmvc.service;


import java.util.List;





import com.websystique.springmvc.model.Transc_mstr;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.acc_mstr;

public interface extuserservice {

	public List<Transc_mstr> getTransaction(int transc_acc_num);
	public List<String> getuseraccountnumbers(int EXT_USER_ID);
	public void removeUser(int userId);
	public String Updatetransactions(int acc_id,int amount , String Transactiontype);
	public String UpdateBalance(int acc_id,int amount , String Transactiontype);
	public String getuserdetails(int acc_id) ;
	public String getusertype(int acc_id) ;
	public String merchantpaymentforuser(String user, int acc_id,int amount) ;
	public 	List<Transc_mstr>  userauthorization(int acc_id)  ;
	public String userapproval(int transc_id) ;
	public int getUserId(String user);
}
