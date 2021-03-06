/**
 * 
 */
package com.experian.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.experian.dto.FileUploadResponseList;
import com.experian.service.FileReaderService;
import com.experian.validator.Validator;

/**
 * @author manchanda.a
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders="*", allowCredentials="true",  origins = "*", maxAge = 3600)
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private Validator validate;
	
	@Autowired
	private FileReaderService fileReaderService;
	
	@RequestMapping(value="/uploadFile", method=RequestMethod.POST)
	public FileUploadResponseList uploadFile(@RequestParam("file") MultipartFile file) {
		validate.validateFile(file);
		FileUploadResponseList fileUploadResponseList = fileReaderService.readFile(file);
		return fileUploadResponseList;
	}

}
