package org.arttel.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.arttel.util.PropertiesReader;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactoryImp;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class FileGenerator {

	public static final String BASE_DIR = PropertiesReader.getProperty("system.baseDir");
	private static final String RESOURCE_DIR = PropertiesReader.getProperty("system.resourceDir");
	public static final String DOWNLOAD_PATH = BASE_DIR + RESOURCE_DIR;

	public String createFile(final String sessionId, final String documentNumber, final String documentContent) {
		try {
			final Document document = new Document();
			final String dir = createSessionDir(sessionId);
			final String filePath = String.format("%s/%s.pdf" ,dir, documentNumber.replace("/", "_"));
			final PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(filePath)));

			document.open();
			final InputStream in = IOUtils.toInputStream(documentContent, "UTF-8");
			final class DefaultFontProvider extends FontFactoryImp {
				@Override
				public Font getFont(final String fontName, final String encoding, final boolean embedded,
						final float size, final int style, final BaseColor color, final boolean cached) {
					return super.getFont(BaseFont.HELVETICA, BaseFont.CP1257, embedded, size, style, color, cached);
				}
			}
			final XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			worker.parseXHtml(writer, document, in, null, Charsets.toCharset("UTF-8"), new DefaultFontProvider());

			document.close();
			return filePath;
		} catch (final DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String createSessionDir(final String sessionId) {
		final String pathname = DOWNLOAD_PATH + sessionId;
		final File sessionDir = new File(pathname);
		sessionDir.mkdir();
		return pathname;
	}

}
