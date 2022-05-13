package com.carta.nbaservice.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "freenba.api")
@Data
public class AppConfig {

    private String host;
    private String baseUrl;
    private String key;
    private Map<String, String> headers;
}