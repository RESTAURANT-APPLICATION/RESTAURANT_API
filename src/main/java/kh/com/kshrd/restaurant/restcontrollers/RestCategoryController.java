package kh.com.kshrd.restaurant.restcontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/api/categories")
@Api("CATEGORY MANAGEMENT API")
public class RestCategoryController {

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation("TO FIND ALL CATEGORIES WITH FILTER AND PAGINATION.")
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
	
	@RequestMapping(value = "/{id}/restaurants", method = RequestMethod.GET)
	@ApiOperation("TO FIND ALL RESTAURANTS BY ID KEY.")
	public ResponseEntity<Map<String, Object>> findAllRestaurantsByCategoryHash(@PathVariable("id") Long id) {
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
	@ApiOperation("TO ADD NEW CATEGORY.")
	public ResponseEntity<Map<String, Object>> addNewCategory() {
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
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	@ApiOperation("TO UPDATE CATEGORY BY ID.")
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable("id") Long id) {
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
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ApiOperation("TO REMOVE CATEGORY BY ID.")
	public ResponseEntity<Map<String, Object>> removeCategory(@PathVariable("id") Long id) {
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
	
}
