package com.example.estore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.example.estore")
@EnableJpaRepositories("com.example.estore.repository")
@EntityScan("com.example.estore.entity")
@Configuration
public class EStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(EStoreApplication.class, args);
	}

}
