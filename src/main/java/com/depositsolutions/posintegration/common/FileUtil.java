package com.depositsolutions.posintegration.common;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtil {
	/**
     * Initializes the file identified by the given name.
     *
     * Files are identified by names only - no need to pass in relative paths.     
     *  
     * @param fileName
     *            The name of the file to initialize.
     * @return The file object for the given name. null will be returned if such file does not exist or error occurs.
     */
    public static File getFileByName(String fileName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(fileName);
        File loadedFile = null;
        try {
            loadedFile = new File(url.toURI());
        } catch (URISyntaxException e) {
        	LoggerUtil.logErrorMessage("Url cannot be parsed!", e);
        }
        
        if (loadedFile != null && !loadedFile.exists()) {
            loadedFile = null;
        }
        
        return loadedFile;
    }
}
