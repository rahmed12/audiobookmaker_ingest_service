package com.agent404.audiobook.ingestservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import jakarta.validation.constraints.NotBlank;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @NotBlank // This annotation ensures the property is not blank
    private String mandatoryProperty;

    public String getMandatoryProperty() {
        return mandatoryProperty;
    }

    public void setMandatoryProperty(String mandatoryProperty) {
        this.mandatoryProperty = mandatoryProperty;
    }
}