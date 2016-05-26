package kh.com.kshrd.restaurant.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RestaurantValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "First name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.description", "Last name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "error.address", "Email is required.");
		
	}

}
