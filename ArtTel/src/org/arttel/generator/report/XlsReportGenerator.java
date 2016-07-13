package org.arttel.generator.report;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.arttel.generator.BaseXlsGenerator;
import org.arttel.generator.DataSheet;

public class XlsReportGenerator extends BaseXlsGenerator {

	private static void cloneSourceSheet(final Workbook wb,
			final String worksheetName, final int sheetElementsCount) {

		final String FIRST_SHEET = " (1)";
		final Sheet sourceSheet = wb.getSheet(worksheetName);
		final int sourceSheetIndex = wb.getSheetIndex(sourceSheet);
		for(int i = 1; i < sheetElementsCount; i++){
			final Sheet newSheet = wb.cloneSheet(sourceSheetIndex);
			wb.setSheetOrder(newSheet.getSheetName(), sourceSheetIndex+i);
			newSheet.getPrintSetup().setLandscape(sourceSheet.getPrintSetup().getLandscape());
		}
		final String sourceSheetName = wb.getSheetName(sourceSheetIndex);
		wb.setSheetName(sourceSheetIndex, sourceSheetName + FIRST_SHEET);
	}

	public static String generate(final String templateFileName, final String outputFileName,
			final Map<String, ReportDataVO> reportDataMap, final String sessionId)
					throws IOException, InvalidFormatException {

		final Workbook wb = getWorkbook(templateFileName);

		final Iterator<String> keys = reportDataMap.keySet().iterator();
		while(keys.hasNext()){
			final String worksheetName = keys.next();
			final ReportDataVO reportData = reportDataMap.get(worksheetName);

			if(reportData.isMultiSheetData()){
				cloneSourceSheet(wb, worksheetName, reportData.getSheetElementsCount());
				for(int i = 0; i < reportData.getSheetElementsCount(); i++){
					final DataSheet dataSheet = reportData.getDataSheet(i);
					final Sheet targetSheet = getTargetSheet(wb, worksheetName, i+1);
					fillSingleSheet(targetSheet, dataSheet);
					printReportDetails(dataSheet.getReportDetailsList(), targetSheet);
				}
			} else {
				final DataSheet dataSheet = reportData.getFirstSheetData();
				final Sheet targetSheet = wb.getSheet(worksheetName);
				fillSingleSheet(targetSheet, dataSheet);
				printReportDetails(dataSheet.getReportDetailsList(), targetSheet);
			}
		}
		wb.getCreationHelper().createFormulaEvaluator().evaluateAll();
		final String reportRelatedDir = writeOutputFile(outputFileName, sessionId, wb);
		return  reportRelatedDir + "/" + outputFileName;
	}

	private static Sheet getTargetSheet(final Workbook wb, final String worksheetName, final int sheetNumber) {

		final String sheetName = String.format(worksheetName.concat(" (%d)"), sheetNumber);
		return wb.getSheet(sheetName);
	}
}
