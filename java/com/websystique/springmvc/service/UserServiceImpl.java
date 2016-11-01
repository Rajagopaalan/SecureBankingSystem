package com.websystique.springmvc.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.UserDao;
import  com.websystique.springmvc.model.User;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao dao;

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User findById(int id) {
		return dao.findById(id);
	}

	public User findBySSO(String sso) {
		User user = dao.findBySSO(sso);
		return user;
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if(entity!=null){
			entity.setSsoId(user.getSsoId());
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setUserProfiles(user.getUserProfiles());
		}
	}

	
	public void deleteUserBySSO(String sso) {
		dao.deleteBySSO(sso);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	public boolean isUserSSOUnique(Integer id, String sso) {
		User user = findBySSO(sso);
		return ( user == null || ((id != null) && (user.getId() == id)));
	}

	@Override
	public int getMaxId() {
		int maxId = dao.getMaxId();
		// TODO Auto-generated method stub
		return maxId;
	}

	@Override
	public boolean updatePassword(String pass, String email) {
		// TODO Auto-generated method stub
		return dao.updatePassword(pass,email);
	}
	
	@Override
	public boolean isPhoneUnique(String phone) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> usersList = session.createQuery("from User where phone like '"+phone+"'").list();
		if (usersList.size() >0){
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEmailUnique(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> usersList = session.createQuery("from User where email like '"+email+"'").list();
		if (usersList.size() >0){
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}
	
}
