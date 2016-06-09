package kh.com.kshrd.restaurant.services;

import java.util.List;

import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.utilities.Pagination;

//TODO: Restaurant Service
public interface RestaurantService {
	
	//TODO: TO FIND ALL RESTAURANT BY FILTER AND PAGINATION
	public List<Restaurant> findAllRestaurants(RestaurantFilter filter, Pagination pagination);
	
	//TODO: TO FIND A RESTAURANT BY ID 
	public Restaurant findRestaurantById(Long id);
	
	//TODO: TO REGISTER A NEW RESTAURANT
	public Long addNewRestaurant(Restaurant restaurant);
	
	//TODO: TO UPDATE AN EXIST RESTAURANT
	public Boolean updateExistRestaurant(Restaurant restaurant);
	
	//TODO: TO DELETE AN EXIST RESTAURANT
	public Boolean deleteRestaurant(Long id);
	
	//TODO: TO GET ALL RESTAURANT BY USER ID
	public List<Restaurant> getAllRestaurantsByUserId(Long id);
	
}
