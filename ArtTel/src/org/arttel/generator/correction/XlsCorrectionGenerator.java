package org.arttel.generator.correction;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.arttel.generator.BaseXlsGenerator;
import org.arttel.generator.DataSheet;

public class XlsCorrectionGenerator extends BaseXlsGenerator {
	
	public static String generate(final String templateFileName, final String outputFileName, 
			final DataSheet dataSheet, final String sessionId) 
					throws IOException, InvalidFormatException {

		final Workbook wb = getWorkbook(templateFileName);
	   
	    final Sheet targetSheet = wb.getSheetAt(0);
	    int rowsToInsert = (dataSheet.getRows().size())-3;
		shiftRowsIfRequired(targetSheet, dataSheet.getDataRowsOffset(), rowsToInsert);
	    fillSingleSheet(targetSheet, dataSheet);
	    printReportDetails(dataSheet.getReportDetailsList(), targetSheet);

	    final String reportRelatedDir = writeOutputFile(outputFileName, sessionId, wb);
	    return  reportRelatedDir + "/" + outputFileName;
	}

	private static void shiftRowsIfRequired(final Sheet sheet, int rowInsertOffset, final int rowsToInsert) {
		if(rowsToInsert > 0){
			sheet.shiftRows(rowInsertOffset, sheet.getLastRowNum(), rowsToInsert);
			final Row sourceRow = sheet.getRow(rowInsertOffset + rowsToInsert);
			for(int j = 0; j < rowsToInsert; j++){
				final int newRowIndex = rowInsertOffset + j;
				final Row newRow = sheet.createRow(newRowIndex);
				for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
					final Cell newCell = newRow.createCell(i);
				    newCell.setCellStyle(sourceRow.getCell(i).getCellStyle());
				}
				if(j%3 == 0){
					sheet.addMergedRegion(new CellRangeAddress(newRowIndex, newRowIndex+2, 0, 0));
					sheet.addMergedRegion(new CellRangeAddress(newRowIndex+1, newRowIndex+2, 1, 1));
				}
				sheet.addMergedRegion(new CellRangeAddress(newRowIndex, newRowIndex, 6, 7));
				sheet.addMergedRegion(new CellRangeAddress(newRowIndex, newRowIndex, 8, 9));
				sheet.addMergedRegion(new CellRangeAddress(newRowIndex, newRowIndex, 11, 12));
				sheet.addMergedRegion(new CellRangeAddress(newRowIndex, newRowIndex, 13, 14));
			}
		}
	}
}
