package org.yw.springbootcamelesb.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class CreateFileService {

	@WebMethod(operationName = "createFile")
	@WebResult(name = "status")
	public FileCreationStatus createFile(@WebParam(name = "fileName") String fileName,
			@WebParam(name = "fileContent") String fileContent,
			@WebParam(name = "createdForPersonName") String createdForPersonName) {

		return null;
	}
}
