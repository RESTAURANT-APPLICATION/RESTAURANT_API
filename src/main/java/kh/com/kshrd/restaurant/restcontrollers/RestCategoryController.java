package kh.com.kshrd.restaurant.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/api/categories")
public class RestCategoryController {

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAllCategories(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("MESSAGE", "WELCOME TO RESTAURANT APPLICATION");
		model.put("CODE", "0000");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
	}
}
