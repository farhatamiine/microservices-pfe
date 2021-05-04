package com.ali.microhps.apigatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f
//								.addRequestHeader("MyHeader", "MyURI")
//								.addRequestParameter("Param", "MyValue"))
//						.uri("http://httpbin.org:80")) 
				.route(p -> p
						.path("/merchant-device/**))")
						.uri("lb:/device-merchant-service/"))
				.route(p -> p
						.path("/drop-transaction/**")
						.uri("lb://drop-service/"))
				.route(p -> p
						.path("/removal-transaction/**")
						.uri("lb://removal-service/"))
				.route(p -> p
						.path("/verification-transaction/**")
						.uri("lb://verification-service/"))
				.route(p -> p
						.path("/exception-validation/**")
						.uri("lb://exception-validation-service"))
				.build();
	}

}
