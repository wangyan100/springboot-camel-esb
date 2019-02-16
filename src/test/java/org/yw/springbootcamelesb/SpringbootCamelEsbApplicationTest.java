package org.yw.springbootcamelesb;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.EnableRouteCoverage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = SpringbootCamelEsbApplication.class,
    properties = "enable.eventnotifier=true")
@EnableRouteCoverage
public class SpringbootCamelEsbApplicationTest {

    @Autowired
    private CamelContext camelContext;

    private static final String MOCK_CXF_SOAP = "mock:cxf_soap";
    private static final String MOCK_FILE_OUTPUT = "mock:file_output";

    @EndpointInject(uri = MOCK_CXF_SOAP)
    private ProducerTemplate producer;

    @EndpointInject(uri = MOCK_FILE_OUTPUT)
    private MockEndpoint fileOutput;

    @Before
    public void setup() throws Exception {
        camelContext.getRouteDefinition(CamelRouteBuilder.ROUTE_NAME)
                .autoStartup(true)
                .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        replaceFromWith(MOCK_CXF_SOAP);
                        interceptSendToEndpoint("file*")
                                .skipSendToOriginalEndpoint()
                                .to(MOCK_FILE_OUTPUT);
                    }
                });
    }

    @Test
    public void sendMessage() throws Exception {
        fileOutput.expectedMessageCount(1);
        producer.sendBody("A message");
        fileOutput.assertIsSatisfied();
    }

}
