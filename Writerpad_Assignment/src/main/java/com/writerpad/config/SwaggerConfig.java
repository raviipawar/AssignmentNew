package com.writerpad.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <h1>SwaggerConfig</h1>
 * <p>
 * This is the SwaggerConfig, it consists of docket bean and api-info
 * </p>
 *
 * @author Ravindra Pawar
 */
@Component
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	ApiInfo apiInfo;

	private String host;

	/**
	 * <p>
	 * The method returns the docket bean object by setting up the values of apis
	 * and path
	 * </p>
	 *
	 * @return the Docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).host(host).select()
				.apis(RequestHandlerSelectors.basePackage("com.writerpad.controller")).paths(PathSelectors.any())
				.build().apiInfo(metaData());
	}

	/**
	 * <p>
	 * This method returns the ApiInfo
	 * </p>
	 *
	 * @return the ApiInfo
	 */

	private ApiInfo metaData() {
		apiInfo = new ApiInfo("Xebia Assignment", "Xebia Assignment Writerpad Services", "1.0", "Terms of Services",
				new Contact("Ravindra Pawar", "github.com/raviipawar", "ravibalajipawar@gmail.com"), "Apache License",
				"www.google.com", Collections.emptyList());
		return apiInfo;
	}
}