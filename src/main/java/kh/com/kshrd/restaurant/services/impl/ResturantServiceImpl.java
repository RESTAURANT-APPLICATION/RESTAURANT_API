package kh.com.kshrd.restaurant.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.repositories.ImageRepository;
import kh.com.kshrd.restaurant.repositories.RestaurantRepository;
import kh.com.kshrd.restaurant.services.RestaurantService;
import kh.com.kshrd.restaurant.utilities.Pagination;

@Service
public class ResturantServiceImpl implements RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private ImageRepository imageRepository;

	@Override
	public List<Restaurant> findAllRestaurants(RestaurantFilter filter, Pagination pagination) {
		try{
			List<Restaurant> restaurants = restaurantRepository.findAllRestaurants(filter, pagination);
			for(int i=0; i<restaurants.size(); i++){
				List<Image> menus = imageRepository.findAllMenusByRestaurantId(restaurants.get(i).getId());
				restaurants.get(i).setMenus(menus);
			}
			return restaurants;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Restaurant findRestaurantById(Long id) {
		try{
			return restaurantRepository.findRestaurantById(id);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean addNewRestaurant(Restaurant restaurant) {
		try{
			if(restaurantRepository.save(restaurant)>0){
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean updateExistRestaurant(Restaurant restaurant) {
		return null;
	}
	
	@Override
	public Boolean deleteRestaurant(Long id) {
		return null;
	}

}
