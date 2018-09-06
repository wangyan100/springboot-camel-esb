package org.yw.springbootcamelesb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootCamelEsbApplication {

	public static void main(String[] args) {
		System.setProperty("soapEndpointPort", "9006");
		SpringApplication.run(SpringbootCamelEsbApplication.class, args);
	}
}
