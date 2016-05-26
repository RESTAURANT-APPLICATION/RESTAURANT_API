package kh.com.kshrd.restaurant.services;

import java.util.List;

import kh.com.kshrd.restaurant.models.Location;

public interface LocationService {

	List<Location> getAllCities();
	
	List<Location> getAllDistrictsByCityId(Long id);
	
	List<Location> getAllCommunesByDistrictById(Long id);
}
