package org.yw.springbootcamelesb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootCamelEsbApplication {

    @Bean
    LoggingSentEventNotifer loggingSentEventNotifer() {
        return new LoggingSentEventNotifer();
    }

    private static final Logger LOG = LoggerFactory.getLogger(SpringbootCamelEsbApplication.class);

    public static void main(String[] args) {
        LOG.info("SpringbootCamelEsbApplication is starting ......");
        SpringApplication.run(SpringbootCamelEsbApplication.class, args);
    }
}
