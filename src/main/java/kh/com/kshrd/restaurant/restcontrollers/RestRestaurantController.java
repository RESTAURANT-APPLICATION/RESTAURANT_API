
package kh.com.kshrd.restaurant.restcontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import kh.com.kshrd.restaurant.enums.ImageType;
import kh.com.kshrd.restaurant.filters.RestaurantFilter;
import kh.com.kshrd.restaurant.forms.RestaurantForm;
import kh.com.kshrd.restaurant.forms.RestaurantFormMultipart;
import kh.com.kshrd.restaurant.locales.MessageSourceService;
import kh.com.kshrd.restaurant.models.Image;
import kh.com.kshrd.restaurant.models.Restaurant;
import kh.com.kshrd.restaurant.models.RestaurantLocation;
import kh.com.kshrd.restaurant.models.Telephone;
import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.services.RestaurantService;
import kh.com.kshrd.restaurant.services.UploadService;
import kh.com.kshrd.restaurant.services.UserService;
import kh.com.kshrd.restaurant.utilities.Pagination;
import kh.com.kshrd.restaurant.validations.RestaurantValidation;
import kh.com.restaurant.exceptions.CustomGenericException;
import kh.com.restaurant.exceptions.CustomGenericMessage;
import kh.com.restaurant.exceptions.ErrorResource;
import kh.com.restaurant.exceptions.FieldErrorResource;
import kh.com.restaurant.exceptions.InvalidRequestException;

@RestController
@RequestMapping(value = "/v1/api/admin/restaurants")
@Api("RESTAURANT MANAGEMENT API")
public class RestRestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	@Qualifier("fileMessageSourceService")
	private MessageSourceService messageSource;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private RestaurantValidation validation;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiResponses(value = {
	        @ApiResponse(code = 9999, message = "RESTAURANTS HAVE NOT FOUND."),
	        @ApiResponse(code = 0000, message = "RESTAURANTS HAS BEEN FIND SUCCESSFULLY.")
	})
	@ApiOperation("TO FIND ALL RESTAURANTS BY FILTER AND PAGINATION.")
	public ResponseEntity<Map<String, Object>> findAllRestaurants(RestaurantFilter filter, 
																  @RequestParam(name="user", required=false) Long id,
																  @RequestParam(name="page", defaultValue="1", required=false) int page,
																  @RequestParam(name="limit", defaultValue="15", required=false) int limit
																  ) {
		Pagination pagination = new Pagination(page, limit);
		filter.setUserId(id);
		Map<String, Object> model = new HashMap<String, Object>();
		List<Restaurant> restaurants = restaurantService.findAllRestaurants(filter, pagination);
		if(restaurants!=null){
			model.put("DATA", restaurants);
			model.put("MESSAGE", "RESTAURANTS HAS BEEN FIND SUCCESSFULLY.");
			model.put("CODE", "0000");
			model.put("PAGINATION", pagination);
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "RESTAURANTS HAVE NOT FOUND.");
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
	public ResponseEntity<Map<String, Object>> addNewRestaurant(
			@Valid @RequestBody RestaurantForm form, 
			/*@ApiParam("MENU_IMAGES") @RequestPart(value="MENU_IMAGES", required=false) List<CommonsMultipartFile> menuRestaurants,
			@ApiParam("RESTAURANT_IMAGES") @RequestPart(value="RESTAURANT_IMAGES", required=false) List<CommonsMultipartFile> restaurantImages,*/
			BindingResult result) {
		
			Map<String, Object> model = new HashMap<String, Object>();
			//TODO: TO CHECK VALIDATION
			if(result.hasErrors()){
				System.err.println("REGISTERING NEW RESTAURANT VALIDATION ERRORS ==> " + result.getAllErrors());
				throw new InvalidRequestException("INVALID RESTAURANT WHEN REGISTERING.", result);
			}
			Restaurant restaurant = new Restaurant();
			restaurant.setName(form.getName());
				
			restaurant.setAddress(form.getAddress());
			User user = userService.findUserBySSID(form.getSsid());
			restaurant.setCreatedBy(user);
			restaurant.setDescription(form.getDescription());
			restaurant.setIsDelivery(form.getIsDelivery());
			restaurant.setStatus(form.getStatus());
			restaurant.setThumbnail("");
			restaurant.setCategory(form.getRestaurantCategory());
			String isThumbnail = "1";
			for(String strTitle : form.getMenuImages()){
				Image image = new Image();
				try{
					image.setTitle(strTitle.substring(0, strTitle.lastIndexOf("/")));
				}catch(Exception ex){
					image.setTitle(strTitle);
				}
				image.setCreatedBy(user);
				image.setType(ImageType.MENU);
				image.setIsThumbnail(isThumbnail);
				image.setStatus("1");
				image.setUrl(strTitle);
				restaurant.getMenus().add(image);
				isThumbnail = "0";	
			}
			
			for(String strTitle : form.getRestaurantImages()){
				Image image = new Image();
				try{
					image.setTitle(strTitle.substring(0, strTitle.lastIndexOf("/")));
				}catch(Exception ex){
					image.setTitle(strTitle);
				}
				image.setCreatedBy(user);
				image.setType(ImageType.INSIDE);
				image.setIsThumbnail("0");
				image.setUrl(strTitle);
				image.setStatus("1");
				restaurant.getRestaurantImages().add(image);
			}
			
			RestaurantLocation location = new RestaurantLocation();
			location.setLatitude(form.getLatitude());
			location.setLongitude(form.getLongitude());
			location.setId(1L);
			String addresses[] = form.getAddress().split("|");
			
			System.err.println("ADDRESS ==> " + addresses + " ADDRESSS SIZE ==> " + addresses.length);
			try{
				location.setProvince(Long.valueOf(addresses[0]));
				location.setDistrict(Long.valueOf(addresses[1]));
				location.setCommune(Long.valueOf(addresses[2]));
				location.setStreet(addresses[3]);
				location.setNo(addresses[4]);
			}catch(Exception ex){
				System.out.println("LOCATION ERROR...");
				ex.printStackTrace();
			}
			restaurant.setLocation(location);
			Telephone telephone = new Telephone();
			telephone.setTelephone(form.getTelephone());
			restaurant.setTelephone(telephone);
			Long restaurantId = restaurantService.addNewRestaurant(restaurant);
			if(restaurantId!=null){
				model.put("MESSAGE", "RESTAURANT HAS BEEN REGISTERED SUCCESSFULLY.");
				model.put("RESTUAURANT_ID", restaurantId);
				model.put("CODE", "0000");
				return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
			}
			model.put("MESSAGE", "RESTAURANT HAS BEEN ERROR WHEN REGISTER.");
			model.put("CODE", "9999");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
	
	@RequestMapping(value="/multiple/register", method = RequestMethod.POST)
	@ApiOperation("TO REGISTER RESTAURANT.")
	public ResponseEntity<Map<String, Object>> addNewRestaurantsMultipleParts(
			@RequestParam(value="NAME", defaultValue="KA RESTAURANT NAME", required=true) String name,
			@RequestParam(value="DESCRIPTION", defaultValue="DESCRIPTION", required=true) String description,
			@RequestParam(value="ADDRESS", defaultValue="ADDRESS", required=false) String address,
			@RequestParam(value="IS_DELIVERY", defaultValue="0", required=true) String isDelivery,
			@RequestParam(value="MENU_IMAGES", required=false) List<CommonsMultipartFile> menuImages,
			@RequestParam(value="RESTAURANT_IMAGES", required=false) List<CommonsMultipartFile> restaurantImages,
			@RequestParam(value="RESTAURANT_CATEGORY", required=false) String category,
			@RequestParam(value="LATITUDE") String latitude,
			@RequestParam(value="LONGITUDE") String longitude,
			@RequestParam(value="TELEPHONE") String phone,
			@RequestParam(value="USER_ID", required=false) Long userId,
			@RequestParam(value="STATUS", defaultValue="1", required=false) String status, 
			HttpServletRequest request) {
		System.out.println("RESTAURANT NAME ===>" + name);
		RestaurantFormMultipart form = new RestaurantFormMultipart();
		form.setName(name);
		form.setDescription(description);
		form.setAddress(address);
		form.setIsDelivery(isDelivery);
		form.setMenuImages(menuImages);
		form.setRestaurantImages(restaurantImages);
		form.setRestaurantCategory(category);
		form.setLatitude(latitude);
		form.setLongitude(longitude);
		form.setTelephone(phone);
		form.setStatus(status);
		Map<String, Object> model = new HashMap<String, Object>();
	
		//Validation code
	    //validation.validate(form, result);
	    
		//TODO: TO CHECK VALIDATION
//		if(result.hasErrors()){
//			System.err.println("REGISTERING NEW RESTAURANT VALIDATION ERRORS ==> " + result.getAllErrors());
//			throw new InvalidRequestException("INVALID RESTAURANT WHEN REGISTERING.", result);
//		}
		
		Restaurant restaurant = new Restaurant();
		restaurant.setName(form.getName());
		restaurant.setAddress(form.getAddress());
		User user = new User();
		user.setId(userId);
		restaurant.setCreatedBy(user);
		restaurant.setDescription(form.getDescription());
		restaurant.setIsDelivery(form.getIsDelivery());
		restaurant.setStatus(form.getStatus());
		restaurant.setThumbnail("");
		restaurant.setCategory(form.getRestaurantCategory());
		String isThumbnail = "1";
		
		if(null!=form.getMenuImages()){
			List<Image> menus = uploadService.uploadMultipart(form.getMenuImages(), request);
			if(menus!=null){
				for(Image menu : menus){
					menu.setCreatedBy(user);
					menu.setType(ImageType.MENU);
					menu.setStatus("1");
					menu.setIsThumbnail("0");
					restaurant.getMenus().add(menu);
				}
			}
		}
		
		if(null!=form.getRestaurantImages()){
			List<Image> images = uploadService.uploadMultipart(form.getRestaurantImages(), request);
			if(images!=null){
				for(Image image : images){
					image.setCreatedBy(user);
					image.setType(ImageType.INSIDE);
					image.setStatus("1");
					image.setIsThumbnail(isThumbnail);
					restaurant.getRestaurantImages().add(image);
					isThumbnail = "0";	
				}
			}
		}
		
		RestaurantLocation location = new RestaurantLocation();
		location.setLatitude(form.getLatitude());
		location.setLongitude(form.getLongitude());
		location.setId(1L);
		String addresses[] = form.getAddress().split("\\|");
		
		System.err.println("ADDRESS ==> " + addresses + " ADDRESSS SIZE ==> " + addresses.length);
		
		for(String add : addresses){
			System.err.println("LOCATION ==> " + add);
		}
		try{
			location.setProvince(Long.valueOf(addresses[0]));
			location.setDistrict(Long.valueOf(addresses[1]));
			location.setCommune(Long.valueOf(addresses[2]));
			location.setStreet(addresses[3]);
			location.setNo(addresses[4]);
		}catch(Exception ex){
			System.out.println("LOCATION ERROR...");
			ex.printStackTrace();
		}
		System.err.println(location);
		restaurant.setLocation(location);
		
		Telephone telephone = new Telephone();
		telephone.setTelephone(form.getTelephone());
		restaurant.setTelephone(telephone);
		
		Long restaurantId = restaurantService.addNewRestaurant(restaurant);
		if(restaurantId!=null){
			model.put("MESSAGE", "RESTAURANT HAS BEEN REGISTERED SUCCESSFULLY.");
			model.put("RESTUAURANT_ID", restaurantId);
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "RESTAURANT HAS BEEN ERROR WHEN REGISTER.");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	@ApiOperation("TO UPDATE RESTAURANT BY ID.")
	public ResponseEntity<Map<String, Object>> updateRestaurant(
			@PathVariable("id") Long id, 
			@RequestParam(value="NAME", defaultValue="KA RESTAURANT NAME", required=false) String name,
			@RequestParam(value="DESCRIPTION", defaultValue="DESCRIPTION", required=false) String description,
			@RequestParam(value="ADDRESS", defaultValue="ADDRESS", required=false) String address,
			@RequestParam(value="IS_DELIVERY", defaultValue="0", required=true) String isDelivery,
			@RequestParam(value="MENU_IMAGES", required=false) List<CommonsMultipartFile> menuImages,
			@RequestParam(value="MENU_IMAGES_DELETED", required=false) String[] menuImagesDeleted,
			@RequestParam(value="RESTAURANT_IMAGES",required=false) List<CommonsMultipartFile> restaurantImages,
			@RequestParam(value="RESTAURANT_IMAGES_DELETED", required=false) String[] restaurantImagesDeleted,
			@RequestParam(value="RESTAURANT_CATEGORY", required=false) String category,
			@RequestParam(value="LATITUDE", required=false) String latitude,
			@RequestParam(value="LONGITUDE", required=false) String longitude,
			@RequestParam(value="TELEPHONE", required=false) String phone,
			@RequestParam(value="USER_ID", required=false) Long userId,
			@RequestParam(value="STATUS", defaultValue="1", required=false) String status, 
			HttpServletRequest request) {
		
		RestaurantFormMultipart form = new RestaurantFormMultipart();
		form.setName(name);
		form.setDescription(description);
		form.setAddress(address);
		form.setIsDelivery(isDelivery);
		form.setMenuImages(menuImages);
		form.setRestaurantImages(restaurantImages);
		form.setRestaurantCategory(category);
		form.setLatitude(latitude);
		form.setLongitude(longitude);
		form.setTelephone(phone);
		form.setStatus(status);
		
//		TODO: TO CHECK VALIDATION
//		if(result.hasErrors()){
//			System.err.println("UPDATING EXISTING RESTAURANT VALIDATION ERRORS ==> " + result.getAllErrors());
//			throw new InvalidRequestException("INVALID RESTAURANT WHEN UPDATING.", result);
//		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		Restaurant restaurant = new Restaurant();
		restaurant.setId(id);
		restaurant.setName(form.getName());
		restaurant.setAddress(form.getAddress());
		User user = new User();
		user.setId(userId);
		restaurant.setUpdatedBy(user);
		restaurant.setDescription(form.getDescription());
		restaurant.setIsDelivery(form.getIsDelivery());
		restaurant.setStatus(form.getStatus());
		restaurant.setThumbnail("");
		restaurant.setCategory(form.getRestaurantCategory());
		restaurant.setRestaurantImagesDeleted(restaurantImagesDeleted);
		restaurant.setMenuImagesDeleted(menuImagesDeleted);
		String isThumbnail = "1";
		
		if(null!=form.getMenuImages()){
			List<Image> menus = uploadService.uploadMultipart(form.getMenuImages(), request);
			if(menus!=null){
				for(Image menu : menus){
					menu.setCreatedBy(user);
					menu.setType(ImageType.MENU);
					menu.setStatus("1");
					menu.setIsThumbnail("0");
					restaurant.getMenus().add(menu);
				}
			}
		}
		
		if(null!=form.getRestaurantImages()){
			List<Image> images = uploadService.uploadMultipart(form.getRestaurantImages(), request);
			if(images!=null){
				for(Image image : images){
					image.setCreatedBy(user);
					image.setType(ImageType.INSIDE);
					image.setStatus("1");
					image.setIsThumbnail(isThumbnail);
					restaurant.getRestaurantImages().add(image);
					isThumbnail = "0";	
				}
			}
		}
		
		RestaurantLocation location = new RestaurantLocation();
		location.setLatitude(form.getLatitude());
		location.setLongitude(form.getLongitude());
		location.setId(1L);
		String addresses[] = form.getAddress().split("\\|");
		
		System.err.println("ADDRESS ==> " + addresses + " ADDRESSS SIZE ==> " + addresses.length);
		
		for(String add : addresses){
			System.err.println("LOCATION ==> " + add);
		}
		try{
			location.setProvince(Long.valueOf(addresses[0]));
			location.setDistrict(Long.valueOf(addresses[1]));
			location.setCommune(Long.valueOf(addresses[2]));
			location.setStreet(addresses[3]);
			location.setNo(addresses[4]);
		}catch(Exception ex){
			System.out.println("LOCATION ERROR...");
			ex.printStackTrace();
		}
		System.err.println(location);
		restaurant.setLocation(location);
		
		Telephone telephone = new Telephone();
		telephone.setTelephone(form.getTelephone());
		restaurant.setTelephone(telephone);
		if(restaurantService.updateExistRestaurant(restaurant)){
			model.put("MESSAGE", "RESTAURANT HAS BEEN UPDATED SUCCESSFULLY.");
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "RESTAURANT HAS BEEN ERROR WHEN UPDATING.");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
	
	/*public ResponseEntity<Map<String, Object>> updateRestaurant(@PathVariable("id") Long id, @RequestBody @Valid RestaurantForm form, BindingResult result) {
		//TODO: TO CHECK VALIDATION
		if(result.hasErrors()){
			System.err.println("UPDATING EXISTING RESTAURANT VALIDATION ERRORS ==> " + result.getAllErrors());
			throw new InvalidRequestException("INVALID RESTAURANT WHEN UPDATING.", result);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		Restaurant restaurant = new Restaurant();
		restaurant.setId(id);
		restaurant.setName(form.getName());
		restaurant.setAddress(form.getAddress());
		User user = new User();
		user.setId(1L);
		restaurant.setUpdatedBy(user);
		restaurant.setDescription(form.getDescription());
		restaurant.setIsDelivery(form.getIsDelivery());
		restaurant.setStatus(form.getStatus());
		restaurant.setThumbnail("");
		restaurant.setCategory(form.getRestaurantCategory());
		String isThumbnail = "1";
		for(String strTitle : form.getMenuImages()){
			Image image = new Image();
			try{
				image.setTitle(strTitle.substring(0, strTitle.lastIndexOf("/")));
			}catch(Exception ex){
				image.setTitle(strTitle);
			}
			image.setCreatedBy(user);
			image.setType(ImageType.MENU);
			image.setIsThumbnail(isThumbnail);
			image.setStatus("1");
			image.setUrl(strTitle);
			restaurant.getMenus().add(image);
			isThumbnail = "0";	
		}
		
		for(String strTitle : form.getRestaurantImages()){
			Image image = new Image();
			try{
				image.setTitle(strTitle.substring(0, strTitle.lastIndexOf("/")));
			}catch(Exception ex){
				image.setTitle(strTitle);
			}
			image.setCreatedBy(user);
			image.setType(ImageType.INSIDE);
			image.setIsThumbnail("0");
			image.setUrl(strTitle);
			image.setStatus("1");
			restaurant.getRestaurantImages().add(image);
		}
		
		RestaurantLocation location = new RestaurantLocation();
		location.setLatitude(form.getLatitude());
		location.setLongitude(form.getLongitude());
		location.setId(1L);
		restaurant.setLocation(location);
		
		Telephone telephone = new Telephone();
		telephone.setTelephone(form.getTelephone());
		restaurant.setTelephone(telephone);
		if(restaurantService.updateExistRestaurant(restaurant)){
			model.put("MESSAGE", "RESTAURANT HAS BEEN UPDATED SUCCESSFULLY.");
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "RESTAURANT HAS BEEN ERROR WHEN UPDATING.");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}*/
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ApiOperation("TO REMOVE RESTAURANT BY ID.")
	public ResponseEntity<Map<String, Object>> removeRestaurant(@PathVariable("id") Long id) {
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
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ InvalidRequestException.class})
	protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
		InvalidRequestException ire = (InvalidRequestException) e;
		List<FieldErrorResource> fieldErrorResources = new ArrayList<>();

		List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			FieldErrorResource fieldErrorResource = new FieldErrorResource();
			fieldErrorResource.setResource(fieldError.getObjectName());
			fieldErrorResource.setField(fieldError.getField());
			fieldErrorResource.setCode(fieldError.getCode().toLowerCase()+"."+fieldError.getObjectName().toLowerCase()+"."+fieldError.getField().toLowerCase());
			fieldErrorResource.setMessage(fieldError.getDefaultMessage());
			fieldErrorResources.add(fieldErrorResource);
		}

		ErrorResource error = new ErrorResource("7777", ire.getMessage());
		error.setFieldErrors(fieldErrorResources);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(RuntimeException e, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String , Object> model = new HashMap<String, Object>();
		model.put("MESSAGE", e.getMessage());
		model.put("CODE", "9999");
		return new ResponseEntity<Object>(model, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler({CustomGenericException.class})
	public ResponseEntity<CustomGenericMessage>handleCustomException(CustomGenericException ex) {
		CustomGenericMessage message = new CustomGenericMessage(ex.getCode(), ex.getMessage());
		return new ResponseEntity<CustomGenericMessage>(message, HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomGenericMessage> handleAllException(Exception ex) {
		CustomGenericMessage message = new CustomGenericMessage("9999", ex.getMessage());
		return new ResponseEntity<CustomGenericMessage>(message, HttpStatus.OK);
	}
	
}
