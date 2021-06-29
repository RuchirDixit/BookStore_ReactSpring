package com.bridgelabz.bookstore.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Model Mapper Class

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

	@Bean
	public ModelMapper modelMapper() {
	ModelMapper modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	return modelMapper;
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200");
    }
}
