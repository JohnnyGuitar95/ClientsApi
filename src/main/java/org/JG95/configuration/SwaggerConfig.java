package org.JG95.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clients API")
                        .version("1.0")
                        .description("API for clients and contacts management")
                        .contact(new Contact()
                                .name("Sergey Dorofeev")
                                .email("dorofeevsn7@gmail.com")
                                .url("https://github.com/JohnnyGuitar95")));
    }
}
