package com.carta.nbaservice.configurations;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "freenba.api")
public class AppConfig implements InitializingBean {

    private String key;

    @Override
    public void afterPropertiesSet() {
        if (this.key == null) {
            throw new IllegalArgumentException("freenba.api.key property should be set");
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
