package kh.com.kshrd.restaurant.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.repositories.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Long save(User user) {
		try{
			Long id = jdbcTemplate.queryForObject("SELECT nextval('users_id_seq')",Long.class);
			int result = jdbcTemplate.update("INSERT INTO users("
												 + "id, "
												 + "ssid, "
						 						 + "status ) "
							 + "VALUES(?, ?, '1')"
							 , new Object[]{
								id,
								user.getSsid()
							 });
			if(result>0){
				return id;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Long findBySSID(String ssid) {
		try{
			return jdbcTemplate.queryForObject("SELECT id FROM users WHERE ssid = ? LIMIT 1",  new Object[]{ ssid}, Long.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	

}
