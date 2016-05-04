package kh.com.kshrd.restaurant.restcontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/v1/api/restaurants")
@Api("RESTAURANT MANAGEMENT API")
public class RestRestaurantController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAllCategories() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> strs = new ArrayList<String>();
		strs.add("PIRANG");
		strs.add("KOKPHENG");
		model.put("DATA", strs);
		model.put("MESSAGE", "WELCOME TO RESTAURANT APPLICATION");
		model.put("CODE", "0000");
		model.put("PAGINATION", null);
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addNewCategory() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> strs = new ArrayList<String>();
		strs.add("PIRANG");
		strs.add("KOKPHENG");
		model.put("DATA", strs);
		model.put("MESSAGE", "WELCOME TO RESTAURANT APPLICATION");
		model.put("CODE", "0000");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
}
