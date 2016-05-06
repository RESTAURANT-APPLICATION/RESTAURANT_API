package kh.com.kshrd.restaurant.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.repositories.RestaurantRepository;
import kh.com.kshrd.restaurant.services.RestaurantService;
import kh.com.kshrd.restaurant.utilities.Pagination;

@Service
public class ResturantServiceImpl implements RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public List<Restaurant> findAllRestaurants(RestaurantFilter filter, Pagination pagination) {
		try{
			return restaurantRepository.findAllRestaurants(filter, pagination);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Restaurant findRestaurantById(Long id) {
		return null;
	}

	@Override
	public Boolean addNewRestaurant(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Boolean updateExistRestaurant(Restaurant restaurant) {
		return null;
	}

}
