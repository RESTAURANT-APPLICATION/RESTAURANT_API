package kh.com.kshrd.restaurant.locales;

import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

public interface MessageSourceService {

	public String getMessage(String id);
	
	public String getMessage(FieldError error);
	
	public MessageSource getMessageSource();
}
