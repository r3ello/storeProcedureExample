package com.example.jdbcexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcexampleApplication implements CommandLineRunner{
	
	@Autowired
	CallFunction callPostgresFunctionExample;

	public static void main(String[] args) {
		SpringApplication.run(JdbcexampleApplication.class, args);
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("JdbcexampleApplication.run()");
		callPostgresFunctionExample.callStoreProcedureExample();
		
	}

}
