package com.msdemo.oppayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OpPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpPaymentApplication.class, args);
	}

}
