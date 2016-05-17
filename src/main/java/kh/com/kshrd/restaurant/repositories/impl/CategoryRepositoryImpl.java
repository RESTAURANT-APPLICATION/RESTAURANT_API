package kh.com.kshrd.restaurant.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.restaurant.filters.CategoryFilter;
import kh.com.kshrd.restaurant.models.Category;
import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.repositories.CategoryRepository;
import kh.com.kshrd.restaurant.utilities.Pagination;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Category> getAllCategories(CategoryFilter filter, Pagination pagination) {
		try{
			String sql = "SELECT A.id, "
					  + "	    A.name, "
					  + "       A.description, "
					  + "		A.index, "
					  + "       B.name AS parent_category, "
					  + "		B.id AS parent_id, "	
					  + "		A.status, "
					  + "		A.level, "
					  + "		A.created_date, "
					  + "		A.created_by "
					  + "FROM categories A "
					  + "LEFT JOIN categories B ON A.parent_id = B.id "
					  + "WHERE A.parent_id IS NOT NULL";
			return jdbcTemplate.query(sql, new RowMapper<Category>(){
				@Override
				public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
					Category category = new Category();
					category.setId(rs.getLong("id"));
					category.setName(rs.getString("name"));
					category.setDescription(rs.getString("description"));
					category.setIndex(rs.getInt("index"));
					category.setStatus(rs.getString("status"));
					category.setLevel(rs.getString("level"));
					category.setCreatedDate(rs.getString("created_date"));
					
					User user = new User();
					user.setId(rs.getLong("created_by"));
					category.setCreatedBy(user);
					
					Category parentCategory = new Category();
					parentCategory.setId(rs.getLong("parent_id"));
					parentCategory.setName(rs.getString("parent_category"));
					category.setParent(parentCategory);
					return category;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Category> getAllCategoriesByRestaurantId(Long id) {
		try{
			String sql = "SELECT A.id, "
					  + "	    A.name, "
					  + "       A.description, "
					  + "		A.index, "
					  + "       B.name AS parent_category, "
					  + "		B.id AS parent_id, "	
					  + "		A.status, "
					  + "		A.level, "
					  + "		A.created_date, "
					  + "		A.created_by "
					  + "FROM categories A "
					  + "LEFT JOIN categories B ON A.parent_id = B.id "
					  + "INNER JOIN restaurant_categories C ON A.id = C.category_id "
					  + "INNER JOIN restaurants D ON D.id = C.restaurant_id "
					  + "WHERE D.id = ?";
			return jdbcTemplate.query(sql, new Object[]{ id }, new RowMapper<Category>(){
				@Override
				public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
					Category category = new Category();
					category.setId(rs.getLong("id"));
					category.setName(rs.getString("name"));
					category.setDescription(rs.getString("description"));
					category.setIndex(rs.getInt("index"));
					category.setStatus(rs.getString("status"));
					category.setLevel(rs.getString("level"));
					category.setCreatedDate(rs.getString("created_date"));
					
					User user = new User();
					user.setId(rs.getLong("created_by"));
					category.setCreatedBy(user);
					
					Category parentCategory = new Category();
					parentCategory.setId(rs.getLong("parent_id"));
					parentCategory.setName(rs.getString("parent_category"));
					
					category.setParent(parentCategory);
					
					return category;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Long save(Category category) {
		try{
			String sql = "INSERT INTO categories("
							 + "id, "
						 	 + "name, "
							 + "description, "
							 + "parent_id, "
							 + "index, "
							 + "created_date, "
							 + "created_by, "
							 + "status, "
							 + "level) "
							 + "VALUES(?, ?, ?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHHMISS'), ?, ?, ?)";
			Long id = jdbcTemplate.queryForObject("SELECT nextval('restaurants_id_seq')",Long.class);
			int result = jdbcTemplate.update(sql, new Object[]{
									 	id,
									 	category.getName(),
									 	category.getDescription(),
									 	category.getParent().getId(),
									 	category.getIndex(),
									 	category.getCreatedBy().getId(),
									 	category.getStatus(),
									 	category.getLevel()
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
	public boolean update(Category category) {
		try{
			String sql = "UPDATE categories "
						   + "SET name = ?, "
						   + "	  description = ?, "
						   + "	  updated_date=  TO_CHAR(NOW(),'YYYYMMDDHHMISS'), "
						   + "	  updated_by = ?, "
						   + "    status = ?, "
						   + "	  index = ?, "
						   + "	  level = ?, "
						   + "	  parent_id = ? "
						   + "WHERE id = ? ";
			int result = jdbcTemplate.update(sql, new Object[]{
									 	category.getName(),
									 	category.getDescription(),
									 	category.getUpdatedBy().getId(),
									 	category.getStatus(),
									 	category.getIndex(),
									 	category.getLevel(),
									 	category.getParent().getId(),
									 	category.getId()
					 				});
			if(result>0){
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Category getCategoryById(Long id) {
		try{
			String sql = "SELECT A.id, "
					  + "	    A.name, "
					  + "       A.description, "
					  + "		A.index, "
					  + "       B.name AS parent_category, "
					  + "		B.id AS parent_id, "	
					  + "		A.status, "
					  + "		A.level, "
					  + "		A.created_date, "
					  + "		A.created_by "
					  + "FROM categories A "
					  + "LEFT JOIN categories B ON A.parent_id = B.id "
					  + "WHERE C.restaurant_id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{ id }, new RowMapper<Category>(){
				@Override
				public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
					Category category = new Category();
					category.setId(rs.getLong("id"));
					category.setName(rs.getString("name"));
					category.setDescription(rs.getString("description"));
					category.setIndex(rs.getInt("index"));
					category.setStatus(rs.getString("status"));
					category.setLevel(rs.getString("level"));
					category.setCreatedDate(rs.getString("created_date"));
					
					User user = new User();
					user.setId(rs.getLong("created_by"));
					category.setCreatedBy(user);
					
					Category parentCategory = new Category();
					parentCategory.setId(rs.getLong("parent_id"));
					parentCategory.setName(rs.getString("parent_category"));
					
					category.setParent(parentCategory);
					return category;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}
