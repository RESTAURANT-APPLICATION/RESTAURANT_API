package kh.com.kshrd.restaurant.restcontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.forms.RestaurantForm;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.services.RestaurantService;
import kh.com.kshrd.restaurant.utilities.Pagination;
import kh.com.restaurant.exceptions.ErrorResource;
import kh.com.restaurant.exceptions.FieldErrorResource;
import kh.com.restaurant.exceptions.InvalidRequestException;

@RestController
@RequestMapping(value = "/v1/api/restaurants")
@Api("RESTAURANT MANAGEMENT API")
public class RestRestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation("TO FIND ALL RESTAURANTS BY FILTER AND PAGINATION.")
	public ResponseEntity<Map<String, Object>> findAllRestaurants(RestaurantFilter filter, 
																  @RequestParam(name="page", defaultValue="1", required=false) int page,
																  @RequestParam(name="limit", defaultValue="15", required=false) int limit
																  ) {
		Pagination pagination = new Pagination(page, limit);
		Map<String, Object> model = new HashMap<String, Object>();
		List<Restaurant> restaurants = restaurantService.findAllRestaurants(filter, pagination);
		if(restaurants!=null){
			model.put("DATA", restaurants);
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
	@ApiOperation("TO REGISTER RESTAURANT.")
	public ResponseEntity<Map<String, Object>> addNewCategory(@Valid @RequestBody RestaurantForm form, BindingResult result) {
		//TODO: TO CHECK VALIDATION
		if(result.hasErrors()){
			System.err.println("REGISTERING NEW RESTAURANT VALIDATION ERRORS ==> " + result.getAllErrors());
			throw new InvalidRequestException("INVALID USER WHEN REGISTERING.", result);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		Restaurant restaurant = new Restaurant();
		restaurant.setName(form.getName());
		restaurant.setAddress(form.getAddress());
		User user = new User();
		user.setId(1L);
		restaurant.setCreatedBy(user);
		restaurant.setDescription(form.getDescription());
		restaurant.setIsDelivery(form.getIsDelivery());
		restaurant.setStatus(form.getStatus());
		restaurant.setThumbnail("");
		if(restaurantService.addNewRestaurant(restaurant)){
			model.put("MESSAGE", "RESTAURANT HAS BEEN REGISTER SUCCESSFULLY.");
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "RESTAURANT HAS BEEN ERROR WHEN REGISTER.");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	@ApiOperation("TO UPDATE RESTAURANT BY ID.")
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable("id") Long id, @Valid RestaurantForm form, BindingResult result) {
		//TODO: TO CHECK VALIDATION
		if(result.hasErrors()){
			System.err.println("UPDATING EXISTING RESTAURANT VALIDATION ERRORS ==> " + result.getAllErrors());
			throw new InvalidRequestException("INVALID USER WHEN UPDATING.", result);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		Restaurant restaurant = new Restaurant();
		if(restaurantService.updateExistRestaurant(restaurant)){
			model.put("MESSAGE", "RESTAURANT HAS BEEN UPDATED SUCCESSFULLY.");
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "RESTAURANT HAS BEEN ERROR WHEN UPDATING.");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ApiOperation("TO REMOVE RESTAURANT BY ID.")
	public ResponseEntity<Map<String, Object>> removeCategory(@PathVariable("id") Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		if(restaurantService.deleteRestaurant(id)){
			model.put("MESSAGE", "RESTAURANT HAS BEEN DELETED SUCCESSFULLY.");
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "RESTAURANT HAS BEEN ERROR WHEN DELETING.");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
	
}
