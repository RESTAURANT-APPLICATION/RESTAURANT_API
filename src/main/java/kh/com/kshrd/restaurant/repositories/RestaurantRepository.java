package kh.com.kshrd.restaurant.repositories;

import java.util.List;

import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.utilities.Pagination;

public interface RestaurantRepository {

	public Long save(Restaurant restaurant);
	public List<Restaurant> findAllRestaurants(RestaurantFilter filter, Pagination pagination);
	public Restaurant findRestaurantById(Long id);
	public int count(RestaurantFilter filter);
}
