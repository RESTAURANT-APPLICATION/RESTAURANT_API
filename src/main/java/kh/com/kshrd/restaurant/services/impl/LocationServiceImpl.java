package kh.com.kshrd.restaurant.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.com.kshrd.restaurant.models.Location;
import kh.com.kshrd.restaurant.repositories.LocationRepository;
import kh.com.kshrd.restaurant.services.LocationService;

@Service
@Transactional
public class LocationServiceImpl implements LocationService{
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Override
	public List<Location> getAllCities() {
		try{
			return locationRepository.getAllLocationByParentIdAndTypeCode(0L, "0");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Location> getAllDistrictsByCityId(Long cityId) {
		try{
			return locationRepository.getAllLocationByParentIdAndTypeCode(cityId, "1");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Location> getAllCommunesByDistrictById(Long districtId) {
		try{
			return locationRepository.getAllLocationByParentIdAndTypeCode(districtId, "2");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Location> getAllVillagesByCommuneId(Long communeId) {
		try{
			return locationRepository.getAllLocationByParentIdAndTypeCode(communeId, "3");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

}
