package com.ssafy.homer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HomerApplication {


	public static void main(String[] args) {
		SpringApplication.run(HomerApplication.class, args);
	}

}
