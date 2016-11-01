package com.websystique.springmvc.dao;

import java.util.List;


import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.model.Transc_mstr;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.acc_mstr;
import com.websystique.springmvc.model.app_user_user_profile;
import com.websystique.springmvc.model.user_profile;


@Repository("ExtUserDAO")
public class ExtUserDAOImpl implements ExtUserDAO{

	private static final Logger logger = LoggerFactory.getLogger(ExtUserDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override	
	@Transactional
	public List<Transc_mstr> getTransaction(int transc_acc_num){
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Transc_mstr> usersList = session.createQuery("from Transc_mstr where transc_acc_num="+transc_acc_num).list();
         if(usersList.size()<=0)
        	 {return null;}
		for(Transc_mstr transaction : usersList){
			logger.info("User List::"+transaction );

		}
		return usersList ;
	}




	@Override
	@Transactional
	public List<String> getuseraccountnumbers(int EXT_USER_ID) {
		// TODO Auto-generated method stub
		System.out.println("in extuserdao");

		Session session1 = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")

		Query q = session1.createQuery("select acc_id from acc_mstr where id="+EXT_USER_ID);


		List<String> usersList = q.list();

		for(String transaction : usersList){
			logger.info("User List::"+transaction );

		}
		return usersList ;

	}

	@Override
	@Transactional
	public String Updatetransactions(int acc_id, int amount,String Transactiontype) {
		Session session = this.sessionFactory.getCurrentSession();
		List<acc_mstr> balance=session.createQuery(" from acc_mstr where acc_id="+acc_id).list() ;
		int amtbalance = balance.get(0).getBALANCE() ;
		if(Transactiontype.equals("debit")){
			int diff =amtbalance - amount ;
			if(diff < 0)
				return "insufficient balance";

			Session s2 =sessionFactory.getCurrentSession() ;
			Transc_mstr trans = new Transc_mstr();
			trans.setBalance(amtbalance);
			trans.setTransc_acc_num(acc_id);
			trans.setTransc_amt(amount);
			trans.setTransc_desc("debit");
			trans.setTransc_fee(0);
			trans.setTransc_merchant("NONE");
			trans.setTransc_status_Id(3);
			trans.setTransc_type_id(2);
			java.util.Date utilDate = new java.util.Date();
			java.sql.Date dtd =  new java.sql.Date(utilDate.getTime());
			String date = dtd.toString() ;
			trans.setTrasc_date(date);
			s2.save(trans) ;
			return "debited" ;
		}

		if(Transactiontype.equals("credit")){
			Session s1 =this.sessionFactory.getCurrentSession() ;
			Transc_mstr trans = new Transc_mstr();
			trans.setBalance(amtbalance);
			trans.setTransc_acc_num(acc_id);
			trans.setTransc_amt(amount);
			trans.setTransc_desc("credit");
			trans.setTransc_fee(0);
			trans.setTransc_merchant("NONE");
			trans.setTransc_status_Id(3);
			trans.setTransc_type_id(1);
			java.util.Date utilDate = new java.util.Date();
			java.sql.Date dtd =  new java.sql.Date(utilDate.getTime());
			String date = dtd.toString() ;
			trans.setTrasc_date(date);
			s1.save(trans) ;
			return "credited" ;
		}
		return null;
	}


	@Override
	@Transactional
	public String UpdateBalance(int acc_id, int amount, String Transactiontype) {
		// TODO Auto-generated method stub
		Session session1 = sessionFactory.getCurrentSession();

		if(Transactiontype.equals("debit")){
			session1.createQuery("Update acc_mstr set Balance = Balance-"+amount +"where acc_id="+acc_id).executeUpdate();
			//	 session1.getTransaction().commit(); 
			//	 session1.close() ;
			return "transaction success" ;
		}
		if(Transactiontype.equals("credit")){
			session1.createQuery("Update acc_mstr set Balance = Balance+"+amount +"where acc_id="+acc_id).executeUpdate();
			// session1.getTransaction().commit(); 
			// session1.close()  ;
			return "transaction success" ;
		}
		return null;
	}


	@Override
	@Transactional
	public String getUserdetails(int acc_id) {
		// TODO Auto-generated method stub

		Session session2 = sessionFactory.getCurrentSession();
		List<User> users = 	session2.createQuery("from APP_USER  where id="+acc_id).list() ;



		return users.get(0).getAddress();
	}


	@Override
	@Transactional
	public String getUsertype(int acc_id) {
		// TODO Auto-generated method stub
		Session session2 = sessionFactory.getCurrentSession();
		List<app_user_user_profile> extuser = 	session2.createQuery("from app_user_user_profile  where user_id="+acc_id).list() ;


		int typeid = extuser.get(0).getUser_profile_id() ;
		Session session3 = sessionFactory.getCurrentSession();
		List<user_profile> extusertype = 	session3.createQuery("from  user_profile  where id="+typeid).list() ;

		
		return  extusertype.get(0).getType() ;
	}


	@Override
	@Transactional
	public String merchantpaymentforuser(String user, int acc_id, int amount) {
		Session session4 = this.sessionFactory.getCurrentSession();

		List<User> usersList = session4.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		List<Account> accountsList = session4.createQuery("from Account where id = "+id).list();
		Integer accNo = 0;
		if(accountsList.size()>0)
			accNo = accountsList.get(0).getAccount_no();
		if(accountsList.size()<=0)
			return "invalid account number .please enter a vaild account number" ;
			
		List<acc_mstr> balance=session4.createQuery(" from acc_mstr where acc_id="+acc_id).list() ;
		int amtbalance = balance.get(0).getBALANCE() ;
		System.out.println("amount"+amtbalance);
		int diff =amtbalance -amount ;
		if(diff <= 0){return "insufficient balance " ;}

		Session s2 =sessionFactory.getCurrentSession() ;

		Transc_mstr trans = new Transc_mstr();
		trans.setBalance(amtbalance);
		trans.setTransc_acc_num(acc_id);
		trans.setTransc_amt(amount);
		trans.setTransc_desc("debit");
		trans.setTransc_fee(0);
		trans.setTransc_merchant(accNo.toString());
		trans.setTransc_status_Id(5);
		trans.setTransc_type_id(2);
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date dtd =  new java.sql.Date(utilDate.getTime());
		String date = dtd.toString() ;
		trans.setTrasc_date(date);
		s2.save(trans) ;
		return "payment submitted for user approval" ;
	}


	@Override
	@Transactional
	public 	List<Transc_mstr> userauthorization(int acc_id) {
		Session session5 = this.sessionFactory.getCurrentSession();
		List<Transc_mstr> Merchantrequests=session5.createQuery(" from Transc_mstr where Transc_acc_num="+acc_id + " and  transc_status_id = 5").list() ;
		return Merchantrequests;
	}


	@Override
	public String userapproval(int transc_id) {
		Session session5 = this.sessionFactory.getCurrentSession();
		session5.createQuery("Update transc_mstr set Transc_Status_id= "+ 2 +"where transc_id="+transc_id).executeUpdate();
		List<Transc_mstr> transList = session5.createQuery("from Trans_mstr where transc_id = "+transc_id).list();
		Integer accNo = 0;
		Integer merNo = 0;
		Integer amount = 0;
		if(transList.size()>0){
			accNo = transList.get(0).getTransc_acc_num();
			merNo = Integer.parseInt(transList.get(0).getTransc_merchant());
			amount = transList.get(0).getTransc_amt();
		}
		session5.createQuery("Update Account set balance= balance +"+ amount +"where account_no="+merNo).executeUpdate();
		session5.createQuery("Update Account set balance= balance -"+ amount +"where account_no="+accNo).executeUpdate();
		return "approved";
	}


	@Override
	public int getUserId(String user) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> usersList = session.createQuery("from User where ssoId = '"+user+"'").list();
		int id = 0;
		if(usersList.size()>0)
			id = usersList.get(0).getId();
		return id;
	}



}
