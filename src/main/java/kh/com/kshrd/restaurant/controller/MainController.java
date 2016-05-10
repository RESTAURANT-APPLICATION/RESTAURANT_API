package kh.com.kshrd.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.annotations.ApiIgnore;

@Controller
public class MainController {

	@ApiIgnore
	@RequestMapping(value={"/swagger",} , method = RequestMethod.GET)
	public String kaAPIPage(ModelMap m){
		m.addAttribute("msg","API PAGE");
		//m.addAttribute("kaapi" , header);
		return "swagger/index";
	}
}