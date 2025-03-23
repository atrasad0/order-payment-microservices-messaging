package com.msdemo.oporder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class OpOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpOrderApplication.class, args);
	}

}
