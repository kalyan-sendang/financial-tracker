package com.project.financialtracker.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI()
    {
        return new OpenAPI()
                .info(new Info().title("Financial Tracker Api")
                        .description("Financial Tracker Application")
                        .version("1.0").contact(new Contact().name("kalyan").email("kalyansendang10@gmail.com"))
                        .license(new License().name("Apache")))
                .externalDocs(new ExternalDocumentation().url("springdocs.com").description("This is external url"));
    }
}