package com.carta.nbaservice.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "freenba.api")
public class AppConfig {

    private String host;
    private String baseUrl;
    private String key;
    private Map<String, String> headers;
}