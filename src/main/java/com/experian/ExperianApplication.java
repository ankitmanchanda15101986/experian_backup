package com.experian;


import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class ExperianApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExperianApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		builder.build().getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		builder.build().getMessageConverters().add(new StringHttpMessageConverter());
	   return builder.build();
	}
}

