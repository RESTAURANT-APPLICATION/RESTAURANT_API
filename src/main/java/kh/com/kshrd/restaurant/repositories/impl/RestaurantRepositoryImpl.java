package kh.com.kshrd.restaurant.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	public boolean save(Restaurant restaurant) {
		KeyHolder holder = new GeneratedKeyHolder();
		try{
			jdbcTemplate.update(new PreparedStatementCreator() {           
			    @Override
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			        PreparedStatement ps = connection.prepareStatement("INSERT INTO restaurants(name, description, created_date, created_by, status, address, is_delivery) "
									 								 + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
									 								 , Statement.RETURN_GENERATED_KEYS);
			        return ps;
			    }
			}, holder);
			Long request_id = holder.getKey().longValue();
			System.out.println("==> RETURNING ID =="+request_id);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
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
										 + "WHERE A.id = ? "
										 + "LEFT JOIN images B ON A.id = B.restaurant_id AND B.is_thumbnail='1' "
										 , new Object[]{id}, new RowMapper<Restaurant>(){
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
	}

}
