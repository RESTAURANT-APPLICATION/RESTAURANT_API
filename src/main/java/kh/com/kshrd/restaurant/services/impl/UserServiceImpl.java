package kh.com.kshrd.restaurant.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
		try{
			Long id = userRepository.findBySSID(ssid);
			if(id != null){
				User user = new User();
				user.setId(id);
				user.setSsid(ssid);
				return user;					
			}
		}catch(Exception ex){
			
		}
		return null;
	}
	
	@Override
	public User save(String ssid) {
		User user = new User();
		try{
			Long id = userRepository.findBySSID(ssid);
			if(id != null){
				user.setId(id);
			}else{
				user.setSsid(ssid);
				userRepository.save(user);
			}
			return user;					
		}catch(Exception ex){
			
		}
		return null;
	}
	
}
