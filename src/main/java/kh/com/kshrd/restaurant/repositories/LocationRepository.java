package kh.com.kshrd.restaurant.repositories;

import java.util.List;

import kh.com.kshrd.restaurant.models.Location;

public interface LocationRepository {

	public List<Location> getAllLocationByParentIdAndTypeCode(Long id, String typeCode);
}
