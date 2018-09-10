package org.yw.springbootcamelesb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootCamelEsbApplication {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpringbootCamelEsbApplication.class);
	public static void main(String[] args) {
		LOG.info("SpringbootCamelEsbApplication is starting ......");
		SpringApplication.run(SpringbootCamelEsbApplication.class, args);
	}
}
