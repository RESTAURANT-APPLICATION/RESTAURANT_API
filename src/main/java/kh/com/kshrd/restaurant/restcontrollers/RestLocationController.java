package kh.com.kshrd.restaurant.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kh.com.kshrd.restaurant.services.LocationService;

@RestController
public class RestLocationController {
	
	@Autowired
	private LocationService locationService;

	@RequestMapping(value="/v1/api/admin/cities", method= RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAllCities(@PathVariable("countryId") Long countryId){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("DATA", locationService.getAllCities());
		model.put("MESSAGE", "ALL CITIES HAVE BEEN FIND SUCCESSFULLY.");
		model.put("CODE", "0000");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
	
	@RequestMapping(value="/v1/api/admin/cities/{cityId}", method= RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAllDistrict(@PathVariable("cityId")Long cityId){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("DATA", locationService.getAllDistrictsByCityId(cityId));
		model.put("MESSAGE", "ALL DISTRICTS HAVE BEEN FIND SUCCESSFULLY.");
		model.put("CODE", "0000");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
	
	@RequestMapping(value="/v1/api/admin/cities/{cityId}/districts/{districtId}", method= RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAllDistrict(@PathVariable("cityId")Long cityId,@PathVariable("districtId") Long districtId){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("DATA", locationService.getAllCommunesByDistrictById(districtId));
		model.put("MESSAGE", "ALL COMMUNES HAVE BEEN FIND SUCCESSFULLY.");
		model.put("CODE", "0000");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
}
