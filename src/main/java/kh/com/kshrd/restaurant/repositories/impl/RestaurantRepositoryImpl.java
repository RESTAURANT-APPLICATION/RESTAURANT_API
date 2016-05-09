package kh.com.kshrd.restaurant.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.repositories.RestaurantRepository;
import kh.com.kshrd.restaurant.utilities.Pagination;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Long save(Restaurant restaurant) {
		try{
			Long id = jdbcTemplate.queryForObject("SELECT nextval('restaurants_id_seq')",Long.class);
			int result = jdbcTemplate.update("INSERT INTO restaurants(id, "
													 + "name, "
							 						 + "description, "
							 						 + "created_date, "
							 						 + "created_by, "
							 						 + "status, "
							 						 + "address, "
							 						 + "is_delivery) "
							 + "VALUES(?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHHMMSS'), ?, ?, ?, ?)"
							 , new Object[]{
									 		id,
									 		restaurant.getName(),
									 		restaurant.getDescription(),
									 		restaurant.getCreatedBy().getId(),
									 		restaurant.getStatus(),
									 		restaurant.getAddress(),
									 		restaurant.getIsDelivery()
							 				});
			if(result>0){
				System.out.println(id);
				return id;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}
	
	@Override
	public List<Restaurant> findAllRestaurants(RestaurantFilter filter, Pagination pagination) {
		try{
			return jdbcTemplate.query("SELECT * FROM restaurants", new RowMapper<Restaurant>(){
				@Override
				public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
					Restaurant restaurant = new Restaurant();
					restaurant.setName(rs.getString("name"));
					restaurant.setDescription(rs.getString("description"));
					restaurant.setAddress(rs.getString("address"));
					restaurant.setId(rs.getLong("id"));
					restaurant.setIsDelivery(rs.getString("is_delivery"));
					restaurant.setStatus(rs.getString("status"));
					restaurant.setCreatedDate(rs.getString("created_date"));
					User user = new User();
					user.setId(rs.getLong("created_by"));
					restaurant.setCreatedBy(user);
					return restaurant;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Restaurant findRestaurantById(Long id) {
		try{
			return jdbcTemplate.queryForObject("SELECT A.id, "
											 + "	   A.name, "
											 + " 	   A.description, "
											 + "       A.address, "
											 + "       A.is_delivery, "
											 + "       A.created_date, "
											 + "       A.created_by, "
											 + "       B.url AS thumbnail, "		
											 + "       A.status "
											 + "FROM restaurants A "
											 + "LEFT JOIN images B ON A.id = B.restaurant_id AND B.is_thumbnail='1' "
											 + "WHERE A.id = ? "
											 , new Object[]{id}
											 , new RowMapper<Restaurant>(){
				@Override
				public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
					Restaurant restaurant = new Restaurant();
					restaurant.setName(rs.getString("name"));
					restaurant.setDescription(rs.getString("description"));
					restaurant.setAddress(rs.getString("address"));
					restaurant.setId(rs.getLong("id"));
					restaurant.setIsDelivery(rs.getString("is_delivery"));
					restaurant.setStatus(rs.getString("status"));
					restaurant.setCreatedDate(rs.getString("created_date"));
					restaurant.setThumbnail(rs.getString("thumbnail"));
					User user = new User();
					user.setId(rs.getLong("created_by"));
					restaurant.setCreatedBy(user);
					return restaurant;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

}
