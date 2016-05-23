package kh.com.kshrd.restaurant.repositories;

import kh.com.kshrd.restaurant.models.Telephone;

public interface TelephoneRepository {

	public Long save(Telephone telephone);
	public boolean update(Telephone telephone);
}
