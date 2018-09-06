package org.yw.springbootcamelesb.camel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yw.springbootcamelesb.soap.FileCreationStatus;

public class CreateFileProcessor implements Processor {
	private static final Logger LOG = LoggerFactory.getLogger(CreateFileProcessor.class);
	public void process(Exchange exchange) throws Exception {
		FileCreationStatus status=new FileCreationStatus();
		// Get the parameters list which element is the holder.
		try {
        MessageContentsList msgList = (MessageContentsList) exchange.getIn().getBody();
        String fileName = (String) msgList.get(0);
        String fileContent = (String) msgList.get(1);
        String createdForPersonName = (String) msgList.get(2);
        
        status.setFileName(fileName);
        status.setCreatedForPersonName(createdForPersonName);
        createFile(fileName,fileContent);
        
        status.setStatus("File is Created Successfully");
		}catch(Exception e) {
			LOG.error("erorr happened ",e);
		}
	}
	
	private void createFile(String fileName, String text) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(fileName);
		out.println(text);
	}
}
