package org.arttel.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.arttel.generator.report.ReportDetailsVO;

public class BaseXlsGenerator {
	
	protected static final String DATA_DOWNLOAD = "data/download/";
	protected static final String DATA_TEMPLATE = "/data/reportTemplate/";
	
	protected static Workbook getWorkbook(final String templateFileName)
			throws FileNotFoundException, IOException, InvalidFormatException {
		
		final InputStream inp = new FileInputStream(FileGenerator.BASE_DIR + DATA_TEMPLATE + templateFileName);
	    return WorkbookFactory.create(inp);
	}
	
	protected static void fillSingleSheet(final Sheet sheet,
			final DataSheet dataSheet) {
		int rowOffset = dataSheet.getDataRowsOffset();
		int cellOffset = 0;
		for(int rowNumber=0; rowNumber<dataSheet.getRows().size(); rowNumber++){
			final List<DataCell> singleRow = dataSheet.getRow( rowNumber );
			final Row row = getOrCreateRow(sheet, rowOffset + rowNumber);
			for( int cellNumber=0; cellNumber<singleRow.size(); cellNumber++ ){
				final DataCell cellData = dataSheet.getRow(rowNumber).get(cellNumber);
				if(cellData != null){
					final Cell cell = getOrCreateCell(row, cellOffset + cellNumber);
					setCellValue(cell,  cellData);
				}
			}
		}
	}

	private static Cell getOrCreateCell(final Row row, int cellNumber) {
		Cell cell = row.getCell(cellNumber);
		if(cell== null){
			cell = row.createCell(cellNumber);
		}
		return cell;
	}

	private static Row getOrCreateRow(final Sheet sheet, final int rowNumber) {
		Row row = sheet.getRow(rowNumber);
		if(row == null){
			row = sheet.createRow(rowNumber);
		}
		return row;
	}

	protected static void printReportDetails(
			List<ReportDetailsVO> reportDetailsList, Sheet sheet) {
		
		for(ReportDetailsVO reportDetailCell : reportDetailsList){
			Cell cell = sheet.getRow(reportDetailCell.getRow()).getCell(reportDetailCell.getCell());
			if(cell == null) {
				cell = sheet.getRow(reportDetailCell.getRow()).createCell(reportDetailCell.getCell());
			}
			setCellValue(cell, reportDetailCell.getData());
		}
	}

	private static void setCellValue(final Cell xlsCell, final DataCell data) {
		
		switch (data.getCellType()) {
		case TEXT:
			final CreationHelper createHelper = xlsCell.getRow().getSheet().getWorkbook().getCreationHelper();
			xlsCell.setCellValue(createHelper.createRichTextString((String)data.getValue()));
			break;

		case WRAPABLE_TEXT:
			xlsCell.setCellValue((String)data.getValue());
			xlsCell.getCellStyle().setWrapText(true);
			break;
			
		case DATE:
			final Date dateValue = (Date)data.getValue();
			if(dateValue != null){
				xlsCell.setCellValue(dateValue.toString());
			}
			break;

		case DOUBLE:
			xlsCell.setCellValue((Double)data.getValue());
			break;
		
		case INT:
			final Integer intValue = (Integer)data.getValue();
			if(intValue != null){
				xlsCell.setCellValue(intValue.toString());
			}
			break;
		
		default:
			break;
		}
	}
	
	protected static String writeOutputFile(final String outputFileName, final String sessionId, final Workbook wb)
			throws FileNotFoundException, IOException {
		// Write the output to a file
	    final String reportRelatedDir =  DATA_DOWNLOAD + sessionId;
	    final File sessionDir = new File(FileGenerator.BASE_DIR + "/" + reportRelatedDir);
	    sessionDir.mkdir();

	    final FileOutputStream fileOut = new FileOutputStream(sessionDir.getAbsolutePath() + "/" + outputFileName);
	    wb.write(fileOut);
	    fileOut.close();
		return reportRelatedDir;
	}
}
