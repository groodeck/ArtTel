package org.arttel.generator;

import java.util.ArrayList;
import java.util.List;

import org.arttel.generator.report.ReportDetailsVO;

public class DataSheet {

	private List<ReportDetailsVO> reportDetailsList;
	private List<List<DataCell>> rows;
	private int dataRowsOffset;

	public DataSheet(){
		rows = new ArrayList<List<DataCell>>();
		reportDetailsList = new ArrayList<ReportDetailsVO>();
	}
	
	public List<List<DataCell>> getRows() {
		return rows;
	}
	public List<DataCell> getRow(final int rowIndex){
		return rows.get(rowIndex);
	}
	
	public DataCell getCell(final int rowIndex, final int colIndex){
		return rows.get(rowIndex).get(colIndex);
	}

	public List<ReportDetailsVO> getReportDetailsList() {
		return reportDetailsList;
	}

	public void addDetailsCell(int row, int col, final DataCell dataCell) {
		reportDetailsList.add(new ReportDetailsVO(row, col, dataCell));
	}

	public int getDataRowsOffset() {
		return dataRowsOffset;
	}

	public void setDataRowsOffset(int dataRowsOffset) {
		this.dataRowsOffset = dataRowsOffset;
	}
}
