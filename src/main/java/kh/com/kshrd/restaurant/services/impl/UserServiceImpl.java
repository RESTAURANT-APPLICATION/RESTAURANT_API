package kh.com.kshrd.restaurant.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.repositories.UserRepository;
import kh.com.kshrd.restaurant.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findUserBySSID(String ssid) {
		User user = new User();
		try{
			Long id = userRepository.findBySSID(ssid);
			user.setSsid(ssid);
			if(id != null){
				user.setId(id);
				return user;					
			}
		}catch(EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public User save(String ssid) {
		User user = new User();
		try{
			user.setSsid(ssid);
			user.setId(userRepository.save(user));
			return user;					
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}
