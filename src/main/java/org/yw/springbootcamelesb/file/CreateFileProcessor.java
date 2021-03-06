package org.yw.springbootcamelesb.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yw.springbootcamelesb.soap.FileCreationStatus;

public class CreateFileProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(CreateFileProcessor.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss.SSS");

    public void process(Exchange exchange) throws Exception {
        FileCreationStatus status = new FileCreationStatus();
        // Get the parameters list which element is the holder.
        try {
            if (exchange.getIn() != null&&exchange.getIn().getBody()!=null) {
                exchange.getIn().getHeaders().forEach((k, v) -> LOG.debug("Item : " + k + " Count : " + v));
                MessageContentsList msgList = (MessageContentsList) exchange.getIn().getBody();
                if (msgList != null & msgList.size() > 0) {
                    String fileName = (String) msgList.get(0);
                    String fileContent = (String) msgList.get(1);
                    String createdForPersonName = (String) msgList.get(2);

                    LOG.debug("fileName : {} , createdForPersonName : {} ", fileName, createdForPersonName);

                    status.setFileName(fileName);
                    status.setCreatedForPersonName(createdForPersonName);
                    createFile(fileName, fileContent);
                    status.setCreationTime(sdf.format(new Date()));
                    status.setStatus("File is Created Successfully");
                }
            }
        } catch (Exception e) {
            status.setStatus("File Creation Failed");
            LOG.error("erorr happened ", e);
        }
        exchange.getOut().setBody(status);
    }

    private void createFile(String fileName, String text) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName);
        out.println(text);
        out.flush();
        out.close();
    }
}
