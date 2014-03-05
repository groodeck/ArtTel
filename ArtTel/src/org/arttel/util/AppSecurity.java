package org.arttel.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class AppSecurity {

	final static Logger log = Logger.getLogger(AppSecurity.class);
	
	public static String hashPassword(String password) {

        MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes());
	 
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        log.debug("Digest(in hex format):: " + sb.toString());
	 
	        //convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	log.debug("Digest(in hex format):: " + hexString.toString());
			
	    	return  hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException", e);
			return null;
		}
	}
}
