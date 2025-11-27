package com.tablekok.swagger;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@AutoConfiguration
@OpenAPIDefinition(
	info = @Info(
		title = "Tablekok Common API",
		version = "v1.0",
		description = "공통 Swagger 설정"
	),
	security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
	name = "bearerAuth",
	type = SecuritySchemeType.HTTP,
	scheme = "bearer",
	bearerFormat = "JWT"
)
public class SwaggerAutoConfig {

	@Bean
	public OpenAPI commonOpenAPI() {

		OpenAPI openAPI = new OpenAPI()
			.info(new io.swagger.v3.oas.models.info.Info()
				.title("Tablekok API 문서")
				.version("v1.0")
				.description("공통 Swagger 자동 구성"));

		// 환경변수 / application.yml에서 서버 목록 주입
		// 예: http://localhost:20100:Prod,http://localhost:8080:Local
		Server localServer = new Server();
		localServer.setDescription("local");
		localServer.setUrl("http://localhost:8080");

		Server testServer = new Server();
		testServer.setDescription("test");
		testServer.setUrl("https://서버주소");
		openAPI.setServers(Arrays.asList(localServer, testServer));

		return openAPI;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOriginPatterns("*")
					.allowedMethods("*")
					.allowedHeaders("*")
					.allowCredentials(true);
			}
		};
	}

}
