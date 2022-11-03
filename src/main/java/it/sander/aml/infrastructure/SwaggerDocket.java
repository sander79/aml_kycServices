package it.sander.aml.infrastructure;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerDocket {
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(this.apiInfo());
    }
	
	private ApiInfo apiInfo() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title("REST API");
		apiInfoBuilder.description("REST API Generation");
		apiInfoBuilder.version("1.0.0");
		apiInfoBuilder.license("GNU GENERAL PUBLIC LICENSE, Version 3");
		apiInfoBuilder.licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html");
		return apiInfoBuilder.build();
	}
	
/*
	@Bean
	public Docket swaggerSurveyApi10() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("/surveys v1.0").select()
				.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
				.paths(regex("/surveys/v1.0.*")).build().apiInfo(new ApiInfoBuilder().version("1.0").title("surveys API")
						.description("Documentation surveys API v1.0").build());
	}

	@Bean
	public Docket swaggerPersonApi11() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("surveys v1.1").select()
				.apis(RequestHandlerSelectors.basePackage("pl.piomin.services.versioning.controller"))
				.paths(regex("/surveys/v1.1.*")).build().apiInfo(new ApiInfoBuilder().version("1.1").title("surveys API")
						.description("Documentation surveys API v1.1").build());
	}
*/
	private Predicate<String> regex(String string) {
		return Pattern.compile(string).asPredicate();
	}

}
