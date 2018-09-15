package org.yw.springbootcamelesb;

import javax.xml.bind.JAXBContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;
import org.yw.springbootcamelesb.file.CreateFileProcessor;
import org.yw.springbootcamelesb.soap.FileCreationStatus;

@Component
public class CamelRouteBuilder extends RouteBuilder {

    private static final String SOAP_ENDPOINT_URI = "cxf://http://localhost:{{soapEndpointPort}}/soap"
            + "?serviceClass=org.yw.springbootcamelesb.soap.CreateFileService";

    @Override
    public void configure() throws Exception {
        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        JAXBContext con = JAXBContext.newInstance(FileCreationStatus.class);
        xmlDataFormat.setContext(con);
        from(SOAP_ENDPOINT_URI).process(new CreateFileProcessor())
                .marshal(xmlDataFormat).to("file:target/output").unmarshal(xmlDataFormat);
    }
}
