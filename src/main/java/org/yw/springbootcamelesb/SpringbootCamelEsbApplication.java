package org.yw.springbootcamelesb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.yw.springbootcamelesb.file.CreateFileProcessor;

@SpringBootApplication
public class SpringbootCamelEsbApplication {
    @ConditionalOnExpression("${enable.eventnotifier:true}")
    @Bean
    LoggingSentEventNotifer loggingSentEventNotifer() {
        return new LoggingSentEventNotifer();
    }
    
    @Bean
    CreateFileProcessor createFileProcessor(){
        return new CreateFileProcessor();
    }
    private static final Logger LOG = LoggerFactory.getLogger(SpringbootCamelEsbApplication.class);

    public static void main(String[] args) {
        LOG.info("SpringbootCamelEsbApplication is starting ......");
        SpringApplication.run(SpringbootCamelEsbApplication.class, args);
    }
}
