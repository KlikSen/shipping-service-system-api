package com.example.shippingservicesystemapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.*;
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ShippingServiceSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingServiceSystemApiApplication.class, args);
	}

}
