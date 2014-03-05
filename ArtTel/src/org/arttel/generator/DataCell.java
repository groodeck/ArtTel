package org.arttel.generator;

public class DataCell {
	
	private CellType cellType;
	private Object value;
	
	public DataCell(final Object value, final CellType cellType) {
		this.cellType = cellType;
		this.value = value;
	}

	public CellType getCellType() {
		return cellType;
	}
	
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}
