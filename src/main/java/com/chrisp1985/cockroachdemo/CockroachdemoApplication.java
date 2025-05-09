package com.chrisp1985.cockroachdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CockroachdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CockroachdemoApplication.class, args);
	}

}
