package org.yw.springbootcamelesb;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.yw.springbootcamelesb.file.CreateFileProcessor;
@Component
public class CamelRouteBuilder extends RouteBuilder {

	private static final String SOAP_ENDPOINT_URI = "cxf://http://localhost:{{soapEndpointPort}}/soap"
			+ "?serviceClass=org.yw.springbootcamelesb.soap.CreateFileService";
	
	@Override
	public void configure() throws Exception {
		  from(SOAP_ENDPOINT_URI).process(new CreateFileProcessor());
	}
}
