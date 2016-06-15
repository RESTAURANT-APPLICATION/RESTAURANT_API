package kh.com.kshrd.restaurant.services;

import kh.com.kshrd.restaurant.models.User;

public interface UserService {
	public User findUserBySSID(String ssid);
	public User save(String ssid);
}
