package kh.com.kshrd.restaurant.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.restaurant.models.Category;
import kh.com.kshrd.restaurant.models.Location;
import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.repositories.LocationRepository;

@Repository
public class LocationRepositoryImpl implements LocationRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Location> getAllLocationByParentIdAndTypeCode(Long id, String typeCode) {
		try{
			String sql = "SELECT id, "
					   + " 		 type_code, "
					   + "       code, "
					   + "		 CONCAT(type, ' ', khmer_name) AS name, "
					   + "		 parent_id "
					   + "FROM locations "
					   + "WHERE parent_id = ? AND type_code = ?";
			return jdbcTemplate.query(sql, new Object[]{id, typeCode}, new RowMapper<Location>(){
				@Override
				public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
					Location location = new Location();
					location.setId(rs.getLong("id"));
					location.setTypeCode(rs.getString("type_code"));
					location.setCode(rs.getString("code"));
					location.setName(rs.getString("name"));
					location.setParentId(rs.getLong("parent_id"));
					return location;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

}
