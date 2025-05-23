package com.test.runrunbots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients  // 扫描项目中所有标记了 @FeignClient 的接口，并将它们注册为 Spring Bean
@EntityScan(basePackages = "com.test.runrunbots.model")
@EnableJpaRepositories(basePackages = "com.test.runrunbots.repository")
@SpringBootApplication
@EnableScheduling // Enables scheduled tasks
public class RunrunBotsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunrunBotsBackendApplication.class, args);
	}

}
