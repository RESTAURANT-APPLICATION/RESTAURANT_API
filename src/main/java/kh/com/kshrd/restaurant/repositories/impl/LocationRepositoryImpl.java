package kh.com.kshrd.restaurant.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import kh.com.kshrd.restaurant.models.Location;
import kh.com.kshrd.restaurant.repositories.LocationRepository;

public class LocationRepositoryImpl implements LocationRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean save(Location location) {
		try{
			int result = jdbcTemplate.update("INSERT INTO restaurant_locations(location_id, "
												 + "restaurant_id, "
						 						 + "longitude, "
						 						 + "latitude, "
						 						 + "status)"
							 + "VALUES(?, ?, ?, ?, ?)"
							 , new Object[]{
									 		location.getId(),
									 		location.getRestaurant().getId(),
									 		location.getLongitude(),
									 		location.getLatitude(),
									 		location.getStatus()
							 				});
			if(result>0){
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

}
