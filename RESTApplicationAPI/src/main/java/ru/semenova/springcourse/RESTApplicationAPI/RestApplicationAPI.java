package ru.semenova.springcourse.RESTApplicationAPI;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestApplicationAPI {

	public static void main(String[] args) {
		SpringApplication.run(RestApplicationAPI.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
