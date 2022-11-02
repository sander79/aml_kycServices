package it.sander.aml.infrastructure;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocket {

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

	private Predicate<String> regex(String string) {
		return Pattern.compile(string).asPredicate();
	}

}
