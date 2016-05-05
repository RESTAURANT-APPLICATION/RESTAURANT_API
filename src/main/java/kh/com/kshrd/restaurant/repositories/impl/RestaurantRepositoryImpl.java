package kh.com.kshrd.restaurant.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.repositories.RestaurantRepository;

public class RestaurantRepositoryImpl implements RestaurantRepository {

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

}
