package kh.com.kshrd.restaurant.restcontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import kh.com.kshrd.restaurant.filters.CategoryFilter;
import kh.com.kshrd.restaurant.forms.CategoryForm;
import kh.com.kshrd.restaurant.locales.MessageSourceService;
import kh.com.kshrd.restaurant.models.Category;
import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.services.CategoryService;
import kh.com.kshrd.restaurant.utilities.Pagination;
import kh.com.restaurant.exceptions.ErrorResource;
import kh.com.restaurant.exceptions.FieldErrorResource;
import kh.com.restaurant.exceptions.InvalidRequestException;

@RestController
@RequestMapping(value = "/v1/api/admin/categories")
@Api("CATEGORY MANAGEMENT API")
public class RestCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	@Qualifier("fileMessageSourceService")
	private MessageSourceService messageSource;

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation("TO FIND ALL CATEGORIES WITH FILTER AND PAGINATION.")
	public ResponseEntity<Map<String, Object>> findAllCategories(@RequestParam(name="name", defaultValue="", required=false) String name, 
																@RequestParam(name="page", defaultValue="1", required=false) int page,
																@RequestParam(name="limit", defaultValue="15", required=false) int limit){
		Pagination pagination = new Pagination(page, limit);
		CategoryFilter filter = new CategoryFilter();
		filter.setName(name);
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<Category> categories = categoryService.getAllCategories(filter, pagination);
		if(categories!=null){
			model.put("DATA", categories);
			model.put("MESSAGE", "CATEGORIES HAS BEEN FIND SUCCESSFULLY.");
			model.put("CODE", "0000");
			model.put("PAGINATION", pagination);
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "CATEGORIES HAVE NOT FOUND...");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.NOT_FOUND);
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
	public ResponseEntity<Map<String, Object>> addNewCategory(@RequestBody @Valid CategoryForm form, BindingResult result) {
		//TODO: TO CHECK VALIDATION
		if(result.hasErrors()){
			System.err.println("REGISTERING NEW CATEGORY VALIDATION ERRORS ==> " + result.getAllErrors());
			throw new InvalidRequestException("INVALID CATEGORY WHEN REGISTERING.", result);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		Category category = new Category();
		category.setName(form.getName());
		category.setDescription(form.getDescription());
		category.setIndex(form.getIndex());
		category.setStatus(form.getStatus());
		
		Category parentCategory = new Category();
		parentCategory.setId(form.getParentId());
		category.setParent(parentCategory);
		
		User user = new User();
		user.setId(1L);
		category.setCreatedBy(user);
		
		if(categoryService.addNewCategory(category)){
			model.put("MESSAGE", "CATEGORY HAS BEEN REGISTERED SUCCESSFULLY.");
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "CATEGORY HAS BEEN ERROR WHEN REGISTER.");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	@ApiOperation("TO UPDATE CATEGORY BY ID.")
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable("id") Long id, @RequestBody @Valid CategoryForm form, BindingResult result) {
		//TODO: TO CHECK VALIDATION
		if(result.hasErrors()){
			System.err.println("UPDATING CATEGORY VALIDATION ERRORS ==> " + result.getAllErrors());
			throw new InvalidRequestException("INVALID CATEGORY WHEN UPDATING.", result);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		Category category = new Category();
		category.setId(id);
		category.setName(form.getName());
		category.setDescription(form.getDescription());
		category.setIndex(form.getIndex());
		category.setLevel(form.getLevel());
		category.setStatus(form.getStatus());
		
		User user = new User();
		user.setId(1L);
		category.setUpdatedBy(user);
		
		Category parentCategory = new Category();
		parentCategory.setId(form.getParentId());
		category.setParent(parentCategory);
		if(categoryService.updateCategory(category)){
			model.put("MESSAGE", "CATEGORY HAS BEEN UPDATED SUCCESSFULLY.");
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "CATEGORY HAS BEEN ERROR WHEN UPDATING.");
		model.put("CODE", "9999");
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
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ InvalidRequestException.class })
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
	
}
