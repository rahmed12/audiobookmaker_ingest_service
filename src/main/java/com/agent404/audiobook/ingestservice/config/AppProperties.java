package com.agent404.audiobook.ingestservice.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;


@Configuration
@ConfigurationProperties
public class AppProperties {

    Logger logger = LoggerFactory.getLogger(AppProperties.class);

    @Autowired
    private Environment environment;

    @NotBlank // This annotation ensures the property is not blank
    private String mandatoryProperty;



    public String getMandatoryProperty() {
        return mandatoryProperty;
    }

    public void setMandatoryProperty(String mandatoryProperty) {
        this.mandatoryProperty = mandatoryProperty;
    }

    @PostConstruct
    public void logProperties() {

        if (!(environment instanceof ConfigurableEnvironment configurableEnv)) {
            logger.error("Environment is not configurable.");
            return;
        }

        logger.info("==== Effective Spring Properties ====");
        Map<String, String> finalProperties = new LinkedHashMap<>();
        Map<String, List<String>> origins = new LinkedHashMap<>();

        for (PropertySource<?> source : configurableEnv.getPropertySources()) {
            if (source instanceof EnumerablePropertySource<?> enumerableSource) {
                for (String name : enumerableSource.getPropertyNames()) {
                    Object value = enumerableSource.getProperty(name);
                    if (value != null) {
                        //record the first value Spring actually used
                        finalProperties.putIfAbsent(name, value.toString());
                        // see every place that mentioned the same setting — not just the one Spring picked.
                        origins.computeIfAbsent(name, k -> new ArrayList<>())
                               .add(source.getName());
                    }
                }
            }
        }

        for (Map.Entry<String, String> entry : finalProperties.entrySet()) {
            logger.info("Property:{} | Value: {} | Source(s): {}",
                    entry.getKey(), entry.getValue(), origins.get(entry.getKey()));
        }

        logger.info("==== End of Property Listing ====");
    }

}