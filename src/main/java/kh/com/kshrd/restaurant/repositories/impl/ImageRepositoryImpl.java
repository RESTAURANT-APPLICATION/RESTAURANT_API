package kh.com.kshrd.restaurant.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
												  + "created_by, "
												  + "is_thumbnail) "
							 + "VALUES(?, ?, ?, ?, ?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHH24MMSS'), ?, ?)",
								new Object[]{
									id,
									image.getRestaurant().getId(),
									image.getTitle(),
									image.getDescription(),
									image.getUrl(),
									(image.getType().ordinal()+1)+"",
									image.getStatus(),
									image.getCreatedBy().getId(),
									image.getIsThumbnail()
								});
			if(result > 0 ){
				return id;
			}
		}catch(org.springframework.dao.DataIntegrityViolationException  ex)
        {
            System.out.println("data integrity ex="+ex.getMessage());
            Throwable innerex = ex.getMostSpecificCause();
            if(innerex instanceof java.sql.BatchUpdateException)
            {
                java.sql.BatchUpdateException batchex = (java.sql.BatchUpdateException) innerex ;
                SQLException current = batchex;
                int count=1;
                   do {

                       System.out.println("inner ex " + count + " =" + current.getMessage());
                       count++;

                   } while ((current = current.getNextException()) != null);
            }

            throw ex;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}

	@Override
	public int[] save(List<Image> images, Long restaurantId) {
		try{
			String sql = "INSERT INTO images("
												  + "restaurant_id, "
												  + "title, "
												  + "description, "
												  + "url, "
												  + "type, "
												  + "status, "
												  + "created_date, "
												  + "created_by, "
												  + "is_thumbnail) "
						 + "VALUES(?, ?, ?, ?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHH24MMSS'), ?, ?);";
			int results[]= jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
				    @Override
				    public void setValues(PreparedStatement ps, int i) throws SQLException {
				        ps.setLong(1, restaurantId);
				        ps.setString(2, images.get(i).getTitle());
				        ps.setString(3, images.get(i).getDescription());
				        ps.setString(4, images.get(i).getUrl());
				        ps.setString(5, (images.get(i).getType().ordinal()+1)+"");
				        ps.setString(6, images.get(i).getStatus());
				        ps.setLong(7, images.get(i).getCreatedBy().getId());
				        ps.setString(8, images.get(i).getIsThumbnail());
				    }
				    
				    @Override
				    public int getBatchSize() {
				        return images.size();
				    }
			  });
			return results;
		}catch(org.springframework.dao.DataIntegrityViolationException  ex)
        {
            System.out.println("data integrity ex="+ex.getMessage());
            Throwable innerex = ex.getMostSpecificCause();
            if(innerex instanceof java.sql.BatchUpdateException)
            {
                java.sql.BatchUpdateException batchex = (java.sql.BatchUpdateException) innerex ;
                SQLException current = batchex;
                int count=1;
                   do {

                       System.out.println("inner ex " + count + " =" + current.getMessage());
                       count++;

                   } while ((current = current.getNextException()) != null);
            }

            throw ex;
		}catch(Exception ex){
			ex.printStackTrace();
		}
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
					 + "FROM image A "
					 + "WHERE status = '1'";
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
					 + "	   A.is_thumbnail, "
					 + "FROM images A "
					 + "WHERE A.id = ? "
					 + "AND A.status = '1'";
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
					 + "AND A.type = ? "
					 + "AND A.status = '1'";
			return jdbcTemplate.query(sql,
									new Object[]{ 
											id, 
											(ImageType.MENU.ordinal()+1)+""},
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
	
	@Override
	public List<Image> findAllRestaurantImagesByRestaurantId(Long id) {
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
					 + "AND A.type = ? "
					 + "AND A.status = '1'";
			return jdbcTemplate.query(sql,
									new Object[]{ 
											id, 
											(ImageType.INSIDE.ordinal()+1)+""},
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

	@Override
	public boolean updateStatusByRestaurantId(Long id, String[] strDeleted) {
		
		try{
			int result = jdbcTemplate.update("UPDATE images "
										   + "SET status = '0' "
				 						   + "WHERE id IN("+ String.join(",", strDeleted)+") "
				 						   + "AND restaurant_id = ?"
						 					, new Object[]{
						 							id
							 				});
			if(result>0){
				System.out.println(id);
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		ArrayList<String> list1 = new ArrayList<>();
		list1.add("Apple");
		list1.add("Book");
		list1.add("Car");
		list1.add("Door");
		list1.add("Eye");
		list1.add("Flag");
		list1.add("Girl");
		list1.add("Hand");
		list1.add("Ice");
		list1.add("Jelly");
		list1.add("Key");

		
		ArrayList<String> list2 = new ArrayList<>();
		list2.add("Eye");
		list2.add("Flag");
		list2.add("Girl");
		list2.add("Hand");
		list2.add("Ice");
		list2.add("Jelly");
		list2.add("Key");
		list2.add("1");
		list2.add("2");
		list2.add("3");
		
		ArrayList<String> temp = new ArrayList<>();
	
		ArrayList<String> inserted = new ArrayList<String>();
		
		for(String str : list2){
			if(!list1.remove(str)){
				inserted.add(str);
			}
		}
		System.out.println(list1);
		
		System.out.println(inserted);
		
	}

}
