package com.techxtor.consumeapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebServiceConfig {

    @Value("${jsonplaceholder.url}")
    private String jsonplaceholderURL;

    // We use the @Bean annotation on a method to define a bean. If we don't specify a custom name,
    // then the bean name will default to the method name.
    @Bean("jsonplaceholderWebClient")
    public WebClient getJsonPlaceholderClient() {
        return WebClient.builder()
                .baseUrl(jsonplaceholderURL)
                .build();
    }
}
