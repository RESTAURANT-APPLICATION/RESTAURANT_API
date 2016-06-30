package kh.com.kshrd.restaurant.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.RestaurantLocation;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.models.Telephone;
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
						 						 + "is_delivery, "
						 						 + "category) "
							 + "VALUES(?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?)"
							 , new Object[]{
									 		id,
									 		restaurant.getName(),
									 		restaurant.getDescription(),
									 		restaurant.getCreatedBy().getId(),
									 		restaurant.getStatus(),
									 		restaurant.getAddress(),
									 		restaurant.getIsDelivery(),
									 		restaurant.getCategory().trim()
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
			return jdbcTemplate.query("SELECT A.id, "
									 + "	  A.name, "
									 + " 	  A.description, "
									 + "      (SELECT address"
									 + "	  FROM view_address V_ADD"
									 + "	  WHERE  V_ADD.id = C.commune) AS address, "
									 + "      A.is_delivery, "
									 + "      A.created_date, "
									 + "      A.created_by, "
									 + "      (SELECT thumbnail_url FROM images WHERE restaurant_id=A.id AND type='4' AND status='1' ORDER BY created_date LIMIT 1) AS thumbnail, "	
									 + "	  A.category, "
									 + "      A.status,"
									 + "      C.longitude,"
									 + "      C.latitude,"
									 + "	  C.province,"
									 + "	  C.district,"
									 + "	  C.commune,"
									 + "	  C.street,"
									 + "	  C.no,"
									 + "      D.telephone "
									 + "FROM restaurants A "
									 + "LEFT JOIN restaurant_locations C ON A.id = C.restaurant_id AND C.status = '1' "
									 + "LEFT JOIN telephones D ON A.id= D.restaurant_id AND D.status = '1' "
									 + "WHERE A.created_by = ? "
									 + "AND A.status = '1' "
									 + "ORDER BY created_date DESC "
									 + "LIMIT ? "
									 + "OFFSET ? "									 
									,new Object[]{
										filter.getUserId(),
										pagination.getLimit(), 
										pagination.offset() }
									,new RowMapper<Restaurant>(){
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
					restaurant.setCategory(rs.getString("category"));
					restaurant.setThumbnail(rs.getString("thumbnail"));
					
					RestaurantLocation location = new RestaurantLocation();
					location.setLatitude(rs.getString("latitude"));
					location.setLongitude(rs.getString("longitude"));
					location.setProvince(rs.getLong("province"));
					location.setDistrict(rs.getLong("district"));
					location.setCommune(rs.getLong("commune"));
					location.setStreet(rs.getString("street"));
					location.setNo(rs.getString("no"));
					restaurant.setLocation(location);
					
					Telephone telephone = new Telephone();
					telephone.setTelephone(rs.getString("telephone"));
					restaurant.setTelephone(telephone);
					
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
											 + "       CONCAT('ផ្ទះលេខ ' ,C.NO, ', ផ្លូវលេខ ', C.street, ', ',(SELECT address"
											 + "	   FROM view_address V_ADD"
											 + "	   WHERE  V_ADD.id = C.commune)) AS address, "
											 + "       A.is_delivery, "
											 + "       A.created_date, "
											 + "       A.created_by, "
											 + "      (SELECT thumbnail_url FROM images WHERE restaurant_id=A.id AND type='4' AND status='1' ORDER BY created_date LIMIT 1) AS thumbnail, "	
											 + "	   A.category, "
											 + "       A.status, "
											 + "       C.longitude,"
											 + "       C.latitude,"
											 + "	   C.province,"
											 + "	   C.district,"
											 + "	   C.commune,"
											 + "	   C.street,"
											 + "	   C.no,"
											 + "       D.telephone "
											 + "FROM restaurants A "
//											 + "LEFT JOIN images B ON A.id = B.restaurant_id AND B.is_thumbnail='1' AND B.status ='1' "
											 + "LEFT JOIN restaurant_locations C ON A.id = C.restaurant_id AND C.status = '1' "
											 + "LEFT JOIN telephones D ON A.id= D.restaurant_id AND D.status = '1' "
											 + "WHERE A.id = ? AND A.status = '1'"
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
					restaurant.setCategory(rs.getString("category"));
					
					RestaurantLocation location = new RestaurantLocation();
					location.setLatitude(rs.getString("latitude"));
					location.setLongitude(rs.getString("longitude"));
					location.setProvince(rs.getLong("province"));
					location.setDistrict(rs.getLong("district"));
					location.setCommune(rs.getLong("commune"));
					location.setStreet(rs.getString("street"));
					location.setNo(rs.getString("no"));
					restaurant.setLocation(location);
					
					Telephone telephone = new Telephone();
					telephone.setTelephone(rs.getString("telephone"));
					restaurant.setTelephone(telephone);
					
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

	@Override
	public int count(RestaurantFilter filter) {
		try{
			return jdbcTemplate.queryForObject(
					  "SELECT COUNT(1) "
					+ "FROM restaurants "
					+ "WHERE created_by = ? AND status ='1' ",  
					new Object[]{
						filter.getUserId()
					}, 
					Integer.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
		
	}
	
	@Override
	public boolean checkRestaurantExist(Long restaurantId) {
		try{
			return jdbcTemplate.queryForObject(
					  "SELECT COUNT(1) "
					+ "FROM restaurants "
					+ "WHERE id = ? "
					+ "AND status ='1'",  new Object[]{ restaurantId}, Integer.class) > 0;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(Restaurant restaurant) {
		try{
			int result = jdbcTemplate.update("UPDATE restaurants "
											 + "SET name = ?, "
					 						 + "	description = ?, "
					 						 + "	updated_date = TO_CHAR(NOW(),'YYYYMMDDHH24MISS'), "
					 						 + "	updated_by = ?, "
					 						 + "	status = ?, "
					 						 + "	address = ?, "
					 						 + "	is_delivery = ?, "
					 						 + "	category = ? "
					 						 + "WHERE id = ?"
				 						 , new Object[]{
									 		restaurant.getName(),
									 		restaurant.getDescription(),
									 		restaurant.getUpdatedBy().getId(),
									 		restaurant.getStatus(),
									 		restaurant.getAddress(),
									 		restaurant.getIsDelivery(),
									 		restaurant.getCategory(),
									 		restaurant.getId()
							 				});
			if(result>0){
				System.out.println(restaurant);
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean delete(Long id) {
		System.out.println("DELETING RESTAURANT ID = " + id);
		try{
			int result = jdbcTemplate.update("UPDATE restaurants "
												 + "SET status = '0', "
												 + "	updated_date = TO_CHAR(NOW(),'YYYYMMDDHH24MISS'), "
												 + "	updated_by = 1 "
						 						 + "WHERE id = ?"
				 						 , new Object[]{
				 								 id
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
