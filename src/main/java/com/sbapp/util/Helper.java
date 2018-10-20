package com.sbapp.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class Helper {
	/**
     * This method creates the file along with directory if
     * it doesn't exist and returns the reader after adding 
     * default content.
     * @param directory - Directory Path
     * @param fileName - Name of the file
     * @param content - Content to add to file could be null
     * @return File
     */
	public static File createFileWithDirectory(String directory, 
			String fileName, String content) throws IOException{
		
		Reader reader;
			
		File file = new File(directory + "/" + fileName);
		if (file.isFile()) {System.out.println(file.getAbsolutePath());
			return file;
		}
		File dir = new File(directory);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		File newFile = new File(directory + "/" + fileName);
		    
		if (content != null) {
			try(FileWriter writer = new FileWriter(file)) {
				writer.write(content);
			}
		}		    
		return newFile;
	}
}
