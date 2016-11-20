package com.webgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.webgo", "Controllers", "Services", "Entities", "Dao"})
public class WebGoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WebGoApplication.class, args);
	}
}
