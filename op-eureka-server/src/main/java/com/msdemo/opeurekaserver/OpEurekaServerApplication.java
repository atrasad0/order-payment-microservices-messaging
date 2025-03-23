package com.msdemo.opeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OpEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpEurekaServerApplication.class, args);
	}

}
