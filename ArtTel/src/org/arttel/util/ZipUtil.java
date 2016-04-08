package org.arttel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class ZipUtil {

	public static void zipFiles(final List<String> filePaths, final String zipFilePath) {
		try {

			// Wrap a FileOutputStream around a ZipOutputStream
			// to store the zip stream to a file. Note that this is
			// not absolutely necessary
			final FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
			final ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

			for(final String filePath : filePaths){
				final File file = new File(filePath);
				// a ZipEntry represents a file entry in the zip archive
				// We name the ZipEntry after the original file's name
				final ZipEntry zipEntry = new ZipEntry(file.getName());
				zipOutputStream.putNextEntry(zipEntry);

				final FileInputStream fileInputStream = new FileInputStream(file);
				final byte[] buf = new byte[1024];
				int bytesRead;

				// Read the input file by chucks of 1024 bytes
				// and write the read bytes to the zip stream
				while ((bytesRead = fileInputStream.read(buf)) > 0) {
					zipOutputStream.write(buf, 0, bytesRead);
				}

				log.info("Regular file :" + file.getCanonicalPath()+" is zipped to archive :" + zipFilePath);

				// close ZipEntry to store the stream to the file
				zipOutputStream.closeEntry();
			}

			zipOutputStream.close();
			fileOutputStream.close();


		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	private static final Logger log = Logger.getLogger(ZipUtil.class);
}
