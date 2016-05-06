package kh.com.kshrd.restaurant.restcontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.services.RestaurantService;
import kh.com.kshrd.restaurant.utilities.Pagination;

@RestController
@RequestMapping(value = "/v1/api/restaurants")
@Api("RESTAURANT MANAGEMENT API")
public class RestRestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation("TO FIND ALL RESTAURANTS BY FILTER AND PAGINATION.")
	public ResponseEntity<Map<String, Object>> findAllRestaurants(RestaurantFilter filter, Pagination pagination) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseData = new HashMap<String, Object>();
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants = restaurantService.findAllRestaurants(filter, pagination);
		if(restaurants!=null){
			responseData.put("RESTAURANTS", restaurants);
			responseData.put("MENUS", "");
			model.put("DATA", responseData);
			model.put("MESSAGE", "RESTAURANTS HAS BEEN FIND SUCCESSFULLY.");
			model.put("CODE", "0000");
			model.put("PAGINATION", pagination);
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "RESTAURANTS HAVE NOT FOUND...");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.NOT_FOUND);
		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ApiOperation("TO FIND A RESTAURANT BY ID")
	public ResponseEntity<Map<String, Object>> findRestaurantById(@PathVariable("id") Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseData = new HashMap<String, Object>();
		Restaurant restaurant = restaurantService.findRestaurantById(id);
		if(restaurant!=null){
			responseData.put("RESTAURANT", restaurant);
			responseData.put("MENUS", "");
			model.put("DATA", responseData);
			model.put("MESSAGE", "RESTAURANT HAS BEEN FIND SUCCESSFULLY.");
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "RESTAURANT HAS NOT FOUND...");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.NOT_FOUND);
		
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addNewCategory(Restaurant restaurant) {
		Map<String, Object> model = new HashMap<String, Object>();
		restaurantService.addNewRestaurant(restaurant);
		model.put("DATA", "");
		model.put("MESSAGE", "RESTAURANTS HAS BEEN REGISTER SUCCESSFULLY.");
		model.put("CODE", "0000");
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
