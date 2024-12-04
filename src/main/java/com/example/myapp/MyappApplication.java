package com.example.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.example.myapp.model")
@SpringBootApplication
public class MyappApplication  {
	public static void main(String[] args) {
		SpringApplication.run(MyappApplication.class, args);
		System.out.println("HELLLOOO WOOOOOOOOOORLD!");
	}
}