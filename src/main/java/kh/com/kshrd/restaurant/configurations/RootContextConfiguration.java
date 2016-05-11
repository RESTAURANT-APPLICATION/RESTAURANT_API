package kh.com.kshrd.restaurant.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={ 
		"kh.com.kshrd.restaurant",
		"kh.com.kshrd.restaurant.exceptions"
		})
public class RootContextConfiguration {

}
