package org.arttel.generator.report;

import org.arttel.generator.DataCell;

public class ReportDetailsVO {
	
	public ReportDetailsVO(int row, int cell, DataCell data) {
		super();
		this.row = row;
		this.cell = cell;
		this.data = data;
	}
	private int row;
	private int cell;
	private DataCell data;
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCell() {
		return cell;
	}
	public void setCell(int cell) {
		this.cell = cell;
	}
	public DataCell getData() {
		return data;
	}
	public void setData(DataCell data) {
		this.data = data;
	}
}
