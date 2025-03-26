package com.test.runrunbots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@EntityScan(basePackages = "com.test.runrunbots.model")
@EnableJpaRepositories(basePackages = "com.test.runrunbots.repository")
@SpringBootApplication
public class RunrunBotsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunrunBotsBackendApplication.class, args);
	}

}
