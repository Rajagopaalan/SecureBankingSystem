package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.configuration.MailConfiguration;
import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.model.Transaction;
import com.websystique.springmvc.model.User;



@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public User findById(int id) {
		User user = getByKey(id);
		if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	public User findBySSO(String sso) {
		logger.info("SSO : {}", sso);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		User user = (User)crit.uniqueResult();
		if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<User> users = (List<User>) criteria.list();
		
		// No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
		// Uncomment below lines for eagerly fetching of userProfiles if you want.
		/*
		for(User user : users){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return users;
	}

	public void save(User user) {
		persist(user);
	}

	public void deleteBySSO(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		User user = (User)crit.uniqueResult();
		delete(user);
	}

	@Override
	public int getMaxId() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> UserList = session.createQuery("from User").list();
		int max_value = 0;
		for(User a : UserList){
			System.out.println(a.getId());
			int value = a.getId();
			if (value>max_value){
				max_value = value;
			}
		}
		System.out.println(UserList.get(0).getId());
		System.out.println("Max Value"+max_value);
		// TODO Auto-generated method stub
		return max_value;
	}

	@Override
	public boolean updatePassword(String pass, String email) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<User> userList = session.createQuery("from User where email= '"+email+"'").list();
		if(userList.size()==0) return false;
		MailConfiguration config = new MailConfiguration();
		JavaMailSenderImpl mail= (JavaMailSenderImpl) config.javaMailService();
		SimpleMailMessage msg = new SimpleMailMessage();
		String mailid = userList.get(0).getEmail(); 
		System.out.println("====================== mail id"+mailid);
		msg.setTo(mailid);
		msg.setSubject("State Bank of Arizona new Password");
		msg.setText("Your new Password is "+pass);
		mail.send(msg);
		userList.get(0).setPassword(passwordEncoder.encode(pass));
		session.update(userList.get(0));
		return true;
	}

}
