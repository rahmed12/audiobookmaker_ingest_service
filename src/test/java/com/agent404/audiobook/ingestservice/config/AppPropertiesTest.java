package com.agent404.audiobook.ingestservice.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"mandatoryProperty=testValue"})
public class AppPropertiesTest {

    @Autowired
    private AppProperties appProperties;

    @Test
    void testMandatoryPropertyIsBound() {
        assertThat(appProperties.getMandatoryProperty()).isEqualTo("testValue");
    }

    @Test
    void testLogPropertiesDoesNotThrowException() {
        assertDoesNotThrow(() -> appProperties.logProperties());
    }
}