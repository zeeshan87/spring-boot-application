package com.sbapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
/*
 * Class to store constants from config file.
 */
public class ApplicationConstants {
	public static String FILE_DIR;
    public static String FILENAME;
    
    @Value("${app.filedir}")
    public void setFileDir(String fileDir) {
        FILE_DIR = fileDir;
    }

    @Value("${app.filename}")	
	public void setFileName(String fileName) {
		FILENAME = fileName;
	}
}
