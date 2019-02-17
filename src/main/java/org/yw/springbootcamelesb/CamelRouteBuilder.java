package org.yw.springbootcamelesb;

import javax.xml.bind.JAXBContext;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yw.springbootcamelesb.file.CreateFileProcessor;
import org.yw.springbootcamelesb.soap.FileCreationStatus;

@Component
public class CamelRouteBuilder extends RouteBuilder {
    
    public static final String ROUTE_NAME = "SOAP_FILE";
    
    private static final String SOAP_ENDPOINT_URI = "cxf://http://0.0.0.0:{{soap.port}}/soap"
            + "?serviceClass=org.yw.springbootcamelesb.soap.CreateFileService";
    
    @Autowired(required = false)
    LoggingSentEventNotifer loggingSentEventNotifer;
    
    @Autowired
    CreateFileProcessor createFileProcessor;
    
    @Override
    public void configure() throws Exception {
        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        JAXBContext con = JAXBContext.newInstance(FileCreationStatus.class);
        xmlDataFormat.setContext(con);
        from(SOAP_ENDPOINT_URI).routeId(ROUTE_NAME).process(createFileProcessor).id("createFileProcessor")
                .marshal(xmlDataFormat).id("marshal").to("file:target/output").unmarshal(xmlDataFormat).id("unmarshal");
    }
}
