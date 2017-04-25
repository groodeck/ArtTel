package org.arttel.generator.invoice;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.arttel.generator.BaseXlsGenerator;
import org.arttel.generator.DataSheet;

public class XlsInvoiceGenerator extends BaseXlsGenerator {

	public static String generate(final String templateFileName, final String outputFileName,
			final DataSheet dataSheet, final String sessionId, final int vatDetailsListSize)
					throws IOException, InvalidFormatException {

		final Workbook wb = getWorkbook(templateFileName);

		final Sheet targetSheet = wb.getSheetAt(0);
		final int rowsToInsert = dataSheet.getRows().size()-1;
		shiftRowsIfRequired(targetSheet, dataSheet.getDataRowsOffset(), rowsToInsert);
		shiftRowsIfRequired(targetSheet, dataSheet.getDataRowsOffset() + rowsToInsert + 2, vatDetailsListSize);
		fillSingleSheet(targetSheet, dataSheet);
		printReportDetails(dataSheet.getReportDetailsList(), targetSheet);

		final String reportRelatedDir = writeOutputFile(outputFileName, sessionId, wb);
		return  reportRelatedDir + "/" + outputFileName;
	}

	private static void shiftRowsIfRequired(final Sheet sheet, final int rowInsertOffset, final int rowsToInsert) {
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

				sheet.addMergedRegion(new CellRangeAddress(newRowIndex, newRowIndex, 5, 6));
				sheet.addMergedRegion(new CellRangeAddress(newRowIndex, newRowIndex, 7, 8));
				sheet.addMergedRegion(new CellRangeAddress(newRowIndex, newRowIndex, 10, 11));
				sheet.addMergedRegion(new CellRangeAddress(newRowIndex, newRowIndex, 12, 13));
			}
		}
	}
}
