package kh.com.kshrd.restaurant.repositories;

import kh.com.kshrd.restaurant.models.Location;

public interface LocationRepository {

	public boolean save(Location location);
	public boolean update(Location location);
}
