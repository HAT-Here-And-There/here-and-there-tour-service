package com.hat.hereandthere.tourservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TourServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourServiceApplication.class, args);
	}
}
