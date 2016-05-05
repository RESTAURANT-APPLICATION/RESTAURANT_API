package kh.com.kshrd.restaurant.bootstraps;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import kh.com.kshrd.restaurant.configurations.MvcConfiguration;
import kh.com.kshrd.restaurant.configurations.RootContextConfiguration;

public class RestaurantApiBootstrap extends AbstractAnnotationConfigDispatcherServletInitializer {
 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootContextConfiguration.class};
    	//return null;
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
    	return null;
        //return new Class[] { MvcConfiguration.class };
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
 
}
