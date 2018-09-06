package org.yw.springbootcamelesb.soap;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class FileCreationStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String fileName;
	private java.util.Date creationTime;
	private String createdForPersonName;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getCreatedForPersonName() {
		return createdForPersonName;
	}
	public void setCreatedForPersonName(String createdForPersonName) {
		this.createdForPersonName = createdForPersonName;
	}
	public java.util.Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}

}
