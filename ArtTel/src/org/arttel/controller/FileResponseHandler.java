package org.arttel.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class FileResponseHandler {
	private static final Logger log = Logger.getLogger(FileResponseHandler.class.getName());

	public void sendToClient(final String filePath, final HttpServletResponse response) {
		try {
			final ServletOutputStream outputStream = response.getOutputStream();
			final File file = new File(filePath);
			response.reset();
			response.resetBuffer();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "filename=\"" + file.getName() + "\"");

			FileUtils.copyFile(file, outputStream);

			outputStream.flush();
			outputStream.close();

		} catch (final IOException e) {
			log.error("File resend error: ", e);
		}


	}
}
