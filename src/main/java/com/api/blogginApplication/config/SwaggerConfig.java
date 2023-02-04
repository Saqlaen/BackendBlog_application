package com.api.blogginApplication.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;



@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket( DocumentationType.SWAGGER_2 )
				.apiInfo( getInfo() )
				.securityContexts( securityContext() )
			    .securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis( RequestHandlerSelectors.any() )
				.paths( PathSelectors.any() )
				.build();
	}
	
	private ApiInfo getInfo() {
		return new ApiInfo("Backend blog application", "by MSA", "1.0", "terms of service",
				new Contact("saqlaen", "www.google.com", "123123"),
				"License of apis ", "API license URL", Collections.emptyList() );
	}
	
	private ApiKey apiKey() { 
	    return new ApiKey("JWT", "Authorization", "header"); 
	}
	
	private List<SecurityContext> securityContext(){
		return Arrays.asList( SecurityContext
				.builder()
				.securityReferences( securityRefs() )
				.build() );
	}
	
	private List<SecurityReference> securityRefs(){
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope;
		return Arrays.asList( new SecurityReference( "JWT", authorizationScopes) );
	}
}
 