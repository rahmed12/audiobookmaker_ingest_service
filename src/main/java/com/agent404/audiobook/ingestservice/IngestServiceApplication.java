package com.agent404.audiobook.ingestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties
@SpringBootApplication
public class IngestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngestServiceApplication.class, args);
	}

}
