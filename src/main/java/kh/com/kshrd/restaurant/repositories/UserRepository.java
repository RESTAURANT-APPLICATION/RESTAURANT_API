package kh.com.kshrd.restaurant.repositories;

import kh.com.kshrd.restaurant.models.User;

public interface UserRepository {
	
	public Long save(User user);
	public Long findBySSID(String ssid);
}
