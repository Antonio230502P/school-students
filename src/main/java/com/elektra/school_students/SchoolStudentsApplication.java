package com.elektra.school_students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SchoolStudentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolStudentsApplication.class, args);
	}

}
