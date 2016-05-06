package kh.com.kshrd.restaurant.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={ 
		"kh.com.kshrd.restaurant.configurations",
		"kh.com.kshrd.restaurant.repositories",
		"kh.com.kshrd.restaurant.services"
		})
public class RootContextConfiguration {

}
