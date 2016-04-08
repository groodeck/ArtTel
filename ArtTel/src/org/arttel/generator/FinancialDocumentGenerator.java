package org.arttel.generator;

import static org.arttel.util.PropertiesReader.PAPER_PRINT_ON;

import java.util.List;

import org.arttel.util.PropertiesReader;
import org.arttel.util.ZipUtil;

import com.google.common.collect.Iterables;

public abstract class FinancialDocumentGenerator {

	protected String compactResults(final List<String> filePaths, final String sessionId) {
		if(filePaths.size() > 1){
			return zip(filePaths, sessionId);
		} else if(filePaths.size() == 1) {
			return Iterables.getOnlyElement(filePaths);
		} else {
			return null;
		}
	}

	protected boolean isPaperPrintOn() {
		final String isPaperPrintOn = PropertiesReader.getProperty(PAPER_PRINT_ON);
		return Boolean.parseBoolean(isPaperPrintOn);
	}

	private String zip(final List<String> filePaths, final String sessionId) {
		final String zipFilePath = FileGenerator.DOWNLOAD_PATH + sessionId + "/documents.zip";
		ZipUtil.zipFiles(filePaths, zipFilePath);
		return zipFilePath;
	}
}
