package com.writerpad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * <h1>WritePad Application</h1>
 * <p>
 * This is the starting point of the application execution
 * </p>
 *
 * @author Ravindra Pawar
 */
@SpringBootApplication
@EnableMongoAuditing
public class WriterpadAssignmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(WriterpadAssignmentApplication.class, args);
	}

}
