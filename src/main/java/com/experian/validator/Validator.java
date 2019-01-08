/**
 * 
 */
package com.experian.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.experian.exception.FileStorageException;

/**
 * @author manchanda.a
 *
 */
@Component
public class Validator {
	private static final Logger logger = LoggerFactory.getLogger(Validator.class);
	
	/**
	 * This method will  validate file based on extension type and invalid characters.
	 * @param file
	 */
	public void validateFile(MultipartFile file) {
		
		logger.debug("File name is "+file.getOriginalFilename());
		// Validate file format
		if(!file.getOriginalFilename().endsWith("xlsx")) {
			throw new FileStorageException("Sorry! Invalid file format only accepted format is xlsx " + file.getOriginalFilename());
		}	
		
		// Check if the file's name contains invalid characters
        if(file.getOriginalFilename().contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + file.getOriginalFilename());
        }
	}
	
	/**
	 * This method will validate search text.
	 * @param searchText
	 */
	public void validateSearchText(String searchText) {
		if(StringUtils.isEmpty(searchText)) {
			// NEED TO HANDLE EXCEPTION
		}
	}
}
