package com.global.mentorship.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public GroupedOpenApi userManagementApi() {
		String packagesToscan[] = { "com.global.mentorship.user.controller",
				
		};
		return GroupedOpenApi.builder()
		                     .group("User Management API")
							 .packagesToScan(packagesToscan)
							 .addOperationCustomizer(appTokenHeaderParam())
							 .build();
	}
	
	@Bean
	public GroupedOpenApi paymentApi() {
		String packagesToscan[] = { "com.global.mentorship.payment.controller",
				
		};
		return GroupedOpenApi.builder()
		                     .group("Payment API")
							 .packagesToScan(packagesToscan)
							 .addOperationCustomizer(appTokenHeaderParam())
							 .build();
	}
	
	@Bean
	public GroupedOpenApi notificationApi() {
		String packagesToscan[] = { "com.global.mentorship.notification.controller",
				
		};
		return GroupedOpenApi.builder()
		                     .group("Notification API")
							 .packagesToScan(packagesToscan)
							 .addOperationCustomizer(appTokenHeaderParam())
							 .build();
	}
	
	@Bean
	public GroupedOpenApi servicesApi() {
		String packagesToscan[] = { "com.global.mentorship.videocall.controller",
				
		};
		return GroupedOpenApi.builder()
		                     .group("Services API")
							 .packagesToScan(packagesToscan)
							 .addOperationCustomizer(appTokenHeaderParam())
							 .build();
	}
	
	
	@Bean
    public OperationCustomizer appTokenHeaderParam() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            Parameter headerParameter = new Parameter().in(ParameterIn.HEADER.toString()).required(false).
                    schema(new StringSchema()._default("app_token_header_default_value")).name("app_token_header").description("App Token Header");
            operation.addParametersItem(headerParameter);
            return operation;
        };
    }

}
