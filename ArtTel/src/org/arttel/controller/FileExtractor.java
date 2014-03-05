package org.arttel.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class FileExtractor {
	
private static final String DATA_UPLOAD = "data/upload/";
	
	public void extractFile(HttpServletRequest request, final String filePath, final String fileName) {
		try {
			
			//TODO: przeniesc do innej klasy i zmienic na biblioteke common-fileupload
	        String contentType = request.getContentType();
	        if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
	        	DataInputStream in;
	        	in = new DataInputStream(request.getInputStream());
	        	//we are taking the length of Content type data
	        	int formDataLength = request.getContentLength();
	        	byte dataBytes[] = new byte[formDataLength];
	        	int byteRead = 0;
	        	int totalBytesRead = 0;
	        	//this loop converting the uploaded file into byte code
	        	while (totalBytesRead < formDataLength) {
	        		byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
	        		totalBytesRead += byteRead;
	        	}
	        	String file = new String(dataBytes);
	        	//for saving the file name
	        	String saveFile = file.substring(file.indexOf("filename=\"") + 10);
	        	saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
	        	saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
	        	int lastIndex = contentType.lastIndexOf("=");
	        	String boundary = contentType.substring(lastIndex + 1, contentType.length());
	        	int pos;
	        	//extracting the index of file 
	        	pos = file.indexOf("filename=\"");
	        	pos = file.indexOf("\n", pos) + 1;
	        	pos = file.indexOf("\n", pos) + 1;
	        	pos = file.indexOf("\n", pos) + 1;
	        	int boundaryLocation = file.indexOf(boundary, pos) - 4;
	        	int startPos = ((file.substring(0, pos)).getBytes()).length;
	        	int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
	                
	    	    File sessionDir = new File(filePath);
	    	    sessionDir.mkdir();
	    	    FileOutputStream fileOut = new FileOutputStream(sessionDir.getAbsolutePath() + "/" + fileName);
	        	fileOut.write(dataBytes, startPos, (endPos - startPos));
	        	fileOut.flush();
	        	fileOut.close();
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}