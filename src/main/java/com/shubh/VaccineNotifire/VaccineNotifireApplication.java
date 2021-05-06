package com.shubh.VaccineNotifire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
public class VaccineNotifireApplication {

	public static ConfigurableApplicationContext context;


	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(VaccineNotifireApplication.class);
		builder.headless(false);
		context = builder.run(args);
	}

}
