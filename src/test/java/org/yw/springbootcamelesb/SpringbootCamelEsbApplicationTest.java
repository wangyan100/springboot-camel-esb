package org.yw.springbootcamelesb;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.EnableRouteCoverage;
import org.junit.Before;
import org.junit.Ignore;
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

    private static final String DIRECT_CXF_SOAP = "direct:cxf_soap";
    private static final String MOCK_FILE_OUTPUT = "mock:file_output";

    @EndpointInject(uri = DIRECT_CXF_SOAP)
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
                        replaceFromWith(DIRECT_CXF_SOAP);
                        weaveById("createFileProcessor").replace().to("mock:createFileProcessor");
                        weaveById("marshal").replace().to("mock:marshal");
                        weaveById("unmarshal").replace().to("mock:unmarshal");
                        interceptSendToEndpoint("file*")
                                .skipSendToOriginalEndpoint()
                                .to(MOCK_FILE_OUTPUT);
                    }
                });
    }

    @Ignore
    @Test
    public void sendMessage() throws Exception {
        fileOutput.expectedMessageCount(1);
        producer.requestBody("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.springbootcamelesb.yw.org/\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <soap:createFile>\n"
                + "         <!--Optional:-->\n"
                + "         <fileName>test.txt</fileName>\n"
                + "         <!--Optional:-->\n"
                + "         <fileContent>test content</fileContent>\n"
                + "         <!--Optional:-->\n"
                + "         <createdForPersonName>W.Y</createdForPersonName>\n"
                + "      </soap:createFile>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>").toString();
        fileOutput.assertIsSatisfied();

    }

}
