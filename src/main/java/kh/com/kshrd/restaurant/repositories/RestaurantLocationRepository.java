package kh.com.kshrd.restaurant.repositories;

import kh.com.kshrd.restaurant.models.RestaurantLocation;

public interface RestaurantLocationRepository {

	public boolean save(RestaurantLocation location);
	public boolean update(RestaurantLocation location);
}
