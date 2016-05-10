package kh.com.kshrd.restaurant.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.restaurant.enums.ImageType;
import kh.com.kshrd.restaurant.filters.ImageFilter;
import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.repositories.ImageRepository;
import kh.com.kshrd.restaurant.utilities.Pagination;

@Repository
public class ImageRepositoryImpl implements ImageRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Long save(Image image) {
		try{
			Long id = jdbcTemplate.queryForObject("SELECT nextval('images_id_seq')",Long.class);
			int result = jdbcTemplate.update("INSERT INTO images(id, "
												  + "restaurant_id, "
												  + "title, "
												  + "description, "
												  + "url, "
												  + "type, "
												  + "status, "
												  + "created_date, "
												  + "created_by) "
							 + "VALUES(?, ?, ?, ?, ?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHHMMSS'), ?)",
								new Object[]{
									id,
									image.getRestaurant().getId(),
									image.getTitle(),
									image.getDescription(),
									image.getUrl(),
									image.getType(),
									image.getStatus(),
									image.getCreatedBy().getId()									
								});
			if(result > 0 ){
				return id;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}

	@Override
	public Long save(List<Image> images) {
		String sql = "INSERT INTO images("
									  + "restaurant_id, "
									  + "title, "
									  + "description, "
									  + "url, "
									  + "type, "
									  + "status, "
									  + "created_date, "
									  + "created_by) "
					+ "VALUES(?, ?, ?, ?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHHMMSS'), ?)";
		int results[]= jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
			    @Override
			    public void setValues(PreparedStatement ps, int i) throws SQLException {
			        Image image = images.get(i);
			        ps.setLong(1, image.getRestaurant().getId());
			        ps.setString(2, image.getTitle());
			        ps.setString(3, image.getDescription());
			        ps.setString(4, image.getUrl());
			        ps.setString(5, image.getType().toString());
			        ps.setString(6, image.getStatus());
			        ps.setLong(7, image.getCreatedBy().getId());
			    }
			    
			    @Override
			    public int getBatchSize() {
			        return images.size();
			    }
		  });
		
		return null;
	}

	@Override
	public List<Image> findAllImages(ImageFilter filter, Pagination pagination) {
		try{
			String sql = "SELECT A.id, "
					 + "	   A.restaurant_id, "
					 + " 	   A.title, "
					 + "       A.description, "
					 + "       A.url, "
					 + "       A.type, "
					 + "       A.status, "
					 + "       A.created_date, "		
					 + "       A.created_by,"
					 + "	   A.is_thumbnail "
					 + "FROM image A ";
			return jdbcTemplate.query(sql, 
								    new RowMapper<Image>(){
				@Override
				public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
					Image image = new Image();
					image.setId(rs.getLong("id"));
					image.setTitle(rs.getString("title"));
					image.setDescription(rs.getString("description"));
					image.setUrl(rs.getString("url"));
					image.setType(ImageType.valueOf(rs.getString("type")));
					
					User user = new User();
					user.setId(rs.getLong("created_by"));
					image.setCreatedDate(rs.getString("created_date"));
					image.setCreatedBy(user);
					image.setIsThumbnail(rs.getString("is_thumbnail"));
					return image;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Image findImageById(Long id) {
		try{
			String sql = "SELECT A.id, "
					 + "	   A.restaurant_id, "
					 + " 	   A.title, "
					 + "       A.description, "
					 + "       A.url, "
					 + "       A.type, "
					 + "       A.status, "
					 + "       A.created_date, "		
					 + "       A.created_by, "
					 + "	   A.is_thumbnail "
					 + "FROM images A "
					 + "WHERE A.id = ?";
			return jdbcTemplate.queryForObject(sql, 
									new Object[]{id},
								    new RowMapper<Image>(){
				@Override
				public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
					Image image = new Image();
					image.setId(rs.getLong("id"));
					image.setTitle(rs.getString("title"));
					image.setDescription(rs.getString("description"));
					image.setUrl(rs.getString("url"));
					image.setStatus(rs.getString("status"));
					User user = new User();
					user.setId(rs.getLong("created_by"));
					image.setCreatedDate(rs.getString("created_date"));
					image.setCreatedBy(user);
					image.setIsThumbnail(rs.getString("is_thumbnail"));
					return image;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Image> findAllMenusByRestaurantId(Long id) {
		try{
			String sql = "SELECT A.id, "
					 + "	   A.restaurant_id, "
					 + " 	   A.title, "
					 + "       A.description, "
					 + "       A.url, "
					 + "       A.type, "
					 + "       A.status, "
					 + "       A.created_date, "		
					 + "       A.created_by,"
					 + "	   A.is_thumbnail "
					 + "FROM images A "
					 + "WHERE A.restaurant_id = ? "
					 + "AND A.type = ? ";
			return jdbcTemplate.query(sql,
									new Object[]{ 
											id, 
											ImageType.MENU.ordinal()+""},
								    new RowMapper<Image>(){
				@Override
				public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
					Image image = new Image();
					image.setId(rs.getLong("id"));
					image.setTitle(rs.getString("title"));
					image.setDescription(rs.getString("description"));
					image.setUrl(rs.getString("url"));
					image.setType(ImageType.MENU);
					User user = new User();
					user.setId(rs.getLong("created_by"));
					image.setCreatedDate(rs.getString("created_date"));
					image.setCreatedBy(user);
					image.setIsThumbnail(rs.getString("is_thumbnail"));
					return image;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

}