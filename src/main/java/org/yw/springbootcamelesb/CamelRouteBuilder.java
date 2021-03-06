package org.yw.springbootcamelesb;

import javax.xml.bind.JAXBContext;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yw.springbootcamelesb.file.CreateFileProcessor;
import org.yw.springbootcamelesb.soap.FileCreationStatus;

@Component
public class CamelRouteBuilder extends RouteBuilder implements MeterBinder {

    public static final String ROUTE_NAME = "SOAP_FILE";
    
    private static final String SOAP_ENDPOINT_URI = "cxf://http://0.0.0.0:{{soap.port}}/soap"
            + "?serviceClass=org.yw.springbootcamelesb.soap.CreateFileService";
    
    @Autowired(required = false)
    LoggingSentEventNotifer loggingSentEventNotifer;
    
    @Autowired
    CreateFileProcessor createFileProcessor;

    private Counter webServiceCounter = null;

    @Override
    public void configure() throws Exception {
        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        JAXBContext con = JAXBContext.newInstance(FileCreationStatus.class);
        xmlDataFormat.setContext(con);
        
        from(SOAP_ENDPOINT_URI).routeId(ROUTE_NAME).process(createFileProcessor).id("createFileProcessor").process(exchange->{
            webServiceCounter.increment();
        }).marshal(xmlDataFormat).id("marshal").to("file:target/output").unmarshal(xmlDataFormat).id("unmarshal");
        

        //from(SOAP_ENDPOINT_URI).routeId(ROUTE_NAME).process(createFileProcessor);


        //from("file://target/input").log(LoggingLevel.INFO, " file is moved to file  ").to("file://target/output");



        //http://localhost:8161/admin/queues.jsp
        from("file://target/input2queue").log(LoggingLevel.INFO, " file is moved to queue").to("activemq:queue:testqueue");
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.webServiceCounter = meterRegistry.counter("webservice.count");
    }
}
