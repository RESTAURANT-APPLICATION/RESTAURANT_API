package kh.com.kshrd.restaurant.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.restaurant.models.Telephone;
import kh.com.kshrd.restaurant.repositories.TelephoneRepository;

@Repository
public class TelephoneRepositoryImpl implements TelephoneRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Long save(Telephone telephone) {
		try{
			Long id = jdbcTemplate.queryForObject("SELECT nextval('telephones_id_seq')",Long.class);
			int result = jdbcTemplate.update("INSERT INTO telephones(id, "
												 + "restaurant_id, "
						 						 + "telephone, "
						 						 + "status)"
							 + "VALUES(?, ?, ?, ?)"
							 , new Object[]{
									 id,
									 telephone.getRestaurant().getId(),
									 telephone.getTelephone(),
									 telephone.getStatus()
							 });
			if(result>0){
				return id;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}

}
