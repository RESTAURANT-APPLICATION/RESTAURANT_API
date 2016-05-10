package kh.com.kshrd.restaurant.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
@EnableWebMvc
public class SwaggerConfiguration {

	private SpringSwaggerConfig springSwaggerConfig;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation(){

        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
               .includePatterns(".*api.*"); // assuming the API lives at something like http://myapp/api
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "KhmerAcademy  REST API" ,
//              "API KEY : "+header+"  (eg. header {'Authorization' , 'Basic "+header+"'} )",
                "API KEY : Please contact to WS Developer for Key",
                "KA API",
                "info.kshrd@gmail.com",
                "API License",
                "http://khmeracademy.org"
        );        
        return apiInfo;
    }
}
