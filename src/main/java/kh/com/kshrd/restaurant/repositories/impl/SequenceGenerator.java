package kh.com.kshrd.restaurant.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SequenceGenerator {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static Long generate(final String sequenceName){
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		try{
			return sequenceGenerator.jdbcTemplate.queryForObject("SELECT nextval(?)", new Object[]{ sequenceName }, Long.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
