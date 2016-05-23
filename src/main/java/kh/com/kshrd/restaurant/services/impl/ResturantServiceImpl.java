package kh.com.kshrd.restaurant.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.Category;
import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.repositories.CategoryRepository;
import kh.com.kshrd.restaurant.repositories.ImageRepository;
import kh.com.kshrd.restaurant.repositories.LocationRepository;
import kh.com.kshrd.restaurant.repositories.RestaurantRepository;
import kh.com.kshrd.restaurant.repositories.TelephoneRepository;
import kh.com.kshrd.restaurant.services.RestaurantService;
import kh.com.kshrd.restaurant.utilities.Pagination;
import kh.com.restaurant.exceptions.CustomGenericException;

@Service
@Transactional
public class ResturantServiceImpl implements RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private TelephoneRepository telephoneRepository;

	@Override
	public List<Restaurant> findAllRestaurants(RestaurantFilter filter, Pagination pagination) {
		pagination.setTotalCount(restaurantRepository.count(filter));
		try{
			List<Restaurant> restaurants = restaurantRepository.findAllRestaurants(filter, pagination);
			for(int i=0; i<restaurants.size(); i++){
				List<Image> menus = imageRepository.findAllMenusByRestaurantId(restaurants.get(i).getId());
				List<Category> categories = categoryRepository.getAllCategoriesByRestaurantId(restaurants.get(i).getId());
				List<Image> restaurantImages = imageRepository.findAllRestaurantImagesByRestaurantId(restaurants.get(i).getId());
				restaurants.get(i).setMenus(menus);
				restaurants.get(i).setRestaurantImages(restaurantImages);
				restaurants.get(i).setCategories(categories);
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
				if(imageRepository.save(restaurant.getMenus(), restaurantId)==null){
					System.out.println("RESTAURANT IMAGE(S) NOT SAVED SUCCESSFULLY.");
					return false;
				}else{
					System.out.println("RESTAURANT RESTAURANT(S) SAVED SUCCESSFULLY.");
				}
				
				if(imageRepository.save(restaurant.getRestaurantImages(), restaurantId)==null){
					System.out.println("RESTAURANT IMAGE(S) NOT SAVED SUCCESSFULLY.");
					return false;
				}else{
					System.out.println("RESTAURANT IMAGE(S) SAVED SUCCESSFULLY.");
				}
				restaurant.getLocation().setRestaurant(restaurant);
				if(locationRepository.save(restaurant.getLocation())){
					System.out.println("RESTAURANT LOCATION SAVED SUCCESSFULLY.");
				}else{
					System.out.println("RESTAURANT LOCATION NOT SAVED SUCCESSFULLY.");
					return false;
				}
				
				restaurant.getTelephone().setRestaurant(restaurant);
				
				if(telephoneRepository.save(restaurant.getTelephone()) > 0){
					System.out.println("RESTAURANT TELEPHONE SAVED SUCCESSFULLY.");
				}else{
					System.out.println("RESTAURANT TELEPHONE NOT SAVED SUCCESSFULLY.");
					return false;
				}
			}
			System.out.println("RESTAURANT HAS BEEN SAVED SUCCESSFULLY.");
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean updateExistRestaurant(Restaurant restaurant) {
		if(restaurantRepository.checkRestaurantExist(restaurant.getId())){
			System.out.println("RESTAURANT IS EXIST.");
			if(imageRepository.updateStatusByRestaurantId(restaurant.getId())){
				if(restaurantRepository.update(restaurant)){
					System.out.println("RESTAURANT RESTAURANT SAVED SUCCESSFULLY.");
				}else{
					System.out.println("RESTAURANT RESTAURANT NOT SAVED SUCCESSFULLY.");
					throw new CustomGenericException("1002", "RESTAURANT RESTAURANT NOT SAVED SUCCESSFULLY.");
				}
				
				if(imageRepository.save(restaurant.getMenus(), restaurant.getId())==null){
					System.out.println("RESTAURANT RESTAURANT MENU(S) NOT SAVED SUCCESSFULLY.");
					throw new CustomGenericException("1003", "RESTAURANT RESTAURANT MENU(S) NOT SAVED SUCCESSFULLY.");
				}else{
					System.out.println("RESTAURANT RESTAURANT MENU(S) SAVED SUCCESSFULLY.");
				}
				
				if(imageRepository.save(restaurant.getRestaurantImages(), restaurant.getId())==null){
					System.out.println("RESTAURANT IMAGE(S) NOT SAVED SUCCESSFULLY.");
					throw new CustomGenericException("1004", "RESTAURANT IMAGE(S) NOT SAVED SUCCESSFULLY.");
				}else{
					System.out.println("RESTAURANT IMAGE(S) SAVED SUCCESSFULLY.");
				}

				restaurant.getLocation().setRestaurant(restaurant);
				if(locationRepository.update(restaurant.getLocation())){
					System.out.println("RESTAURANT LOCATION UPDATED SUCCESSFULLY.");
				}else{
					System.out.println("RESTAURANT LOCATION NOT UPDATED SUCCESSFULLY.");
					throw new CustomGenericException("1005", "RESTAURANT LOCATION NOT UPDATED SUCCESSFULLY.");
				}
				
				restaurant.getTelephone().setRestaurant(restaurant);
				
				if(telephoneRepository.update(restaurant.getTelephone())){
					System.out.println("RESTAURANT TELEPHONE UPDATED SUCCESSFULLY.");
					return true;
				}else{
					System.out.println("RESTAURANT TELEPHONE NOT UPDATED SUCCESSFULLY.");
					throw new CustomGenericException("1006", "RESTAURANT TELEPHONE NOT UPDATED SUCCESSFULLY.");
				}
			}
		}else{
			System.out.println("RESTAURANT IS NOT EXIST.");
			throw new CustomGenericException("0001", "RESTAURANT IS NOT EXIST.");
		}
		System.out.println("RESTAURANT HAS BEEN UPDATED SUCCESSFULLY.");
		return true;
	}
	
	@Override
	public Boolean deleteRestaurant(Long id) {
		if(restaurantRepository.checkRestaurantExist(id)){
			if(restaurantRepository.delete(id)){
				return true;
			}
		}else{
			System.out.println("RESTAURANT DOES NOT EXIST.");
			throw new CustomGenericException("1001", "RESTAURANT IS NOT EXIST.");
		}
		return false;
	}
	
	@Override
	public List<Restaurant> getAllRestaurantsByUserId(Long id) {
		return null;
	}

}
