package kh.com.kshrd.restaurant.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kh.com.kshrd.restaurant.forms.UserForm;
import kh.com.kshrd.restaurant.models.User;
import kh.com.kshrd.restaurant.services.UserService;

@RestController
@RequestMapping(value = "/v1/api/admin/users")
public class RestUserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> authentication(@RequestBody UserForm form){
		Map<String, Object> model = new HashMap<String, Object>();
		User user = userService.findUserBySSID(form.getSsid());
		if(user!=null){
			model.put("DATA", user);
			model.put("MESSAGE", "USER HAS BEEN LOGGED IN SUCCESSFULLY.");
			model.put("CODE", "0000");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		}
		model.put("MESSAGE", "USER HAS BEEN LOGGED IN FAILURE.");
		model.put("CODE", "9999");
		return new ResponseEntity<Map<String, Object>>(model, HttpStatus.NOT_FOUND);
	}
}
