package kh.com.kshrd.restaurant.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.Category;
import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.repositories.CategoryRepository;
import kh.com.kshrd.restaurant.repositories.ImageRepository;
import kh.com.kshrd.restaurant.repositories.RestaurantRepository;
import kh.com.kshrd.restaurant.services.RestaurantService;
import kh.com.kshrd.restaurant.utilities.Pagination;

@Service
@Transactional
public class ResturantServiceImpl implements RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Restaurant> findAllRestaurants(RestaurantFilter filter, Pagination pagination) {
		try{
			List<Restaurant> restaurants = restaurantRepository.findAllRestaurants(filter, pagination);
			for(int i=0; i<restaurants.size(); i++){
				List<Image> menus = imageRepository.findAllMenusByRestaurantId(restaurants.get(i).getId());
				List<Category> categories = categoryRepository.getAllCategoriesByRestaurantId(restaurants.get(i).getId()); 
				restaurants.get(i).setMenus(menus);
				restaurants.get(i).setCategories(categories);
			}
			pagination.setTotalCount(restaurantRepository.count(filter));
			return restaurants;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Restaurant findRestaurantById(Long id) {
		try{
			Restaurant restaurant = restaurantRepository.findRestaurantById(id);
			restaurant.setMenus(imageRepository.findAllMenusByRestaurantId(restaurant.getId()));
			return restaurant;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public Boolean addNewRestaurant(Restaurant restaurant) {
		try{
			Long restaurantId = restaurantRepository.save(restaurant);
			restaurant.setId(restaurantId);
			if(restaurantId > 0){
				for(Image image :restaurant.getMenus()){
					image.setRestaurant(restaurant);
					if(imageRepository.save(image)>0){
						
					}
				}
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
