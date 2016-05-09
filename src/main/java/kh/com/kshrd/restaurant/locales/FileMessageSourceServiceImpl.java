package kh.com.kshrd.restaurant.locales;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service("fileMessageSourceService")
public class FileMessageSourceServiceImpl implements MessageSourceService{

	@Autowired
	private MessageSource messageSource;

	@Override
	public String getMessage(String id) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(id, null, locale);
	}

	@Override
	public MessageSource getMessageSource() {
		return this.messageSource;
	}
	
	@Override
	public String getMessage(FieldError error) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(error.getCode().toLowerCase()+"."+error.getObjectName().toLowerCase()+"."+error.getField().toLowerCase(), null, locale);
	}
}
