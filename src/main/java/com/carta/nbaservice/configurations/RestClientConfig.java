package com.carta.nbaservice.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final AppConfig appConfig;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
            .defaultHeaders(httpHeaders -> appConfig.getHeaders().forEach(httpHeaders::set))
            .build();
    }
}
