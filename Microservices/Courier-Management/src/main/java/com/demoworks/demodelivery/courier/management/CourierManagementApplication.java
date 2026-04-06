package com.demoworks.demodelivery.courier.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CourierManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierManagementApplication.class, args);
	}

}
