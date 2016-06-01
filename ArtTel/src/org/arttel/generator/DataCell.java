package org.arttel.generator;

public class DataCell {

	public static final DataCell EMPTY = new DataCell(null, CellType.TEXT);

	private CellType cellType;
	private Object value;

	public DataCell(final Object value, final CellType cellType) {
		this.cellType = cellType;
		this.value = value;
	}

	public CellType getCellType() {
		return cellType;
	}

	public Object getValue() {
		return value;
	}

	public void setCellType(final CellType cellType) {
		this.cellType = cellType;
	}

	public void setValue(final Object value) {
		this.value = value;
	}
}
