package org.arttel.ui;

public class SortableColumn {

	private SortOrder sortOrder;
	private final String columnKey;
	private final String columnName;
	private final String columnDescription;

	public SortableColumn(final String columnKey, final String columnName, final String columnDescription) {
		this(columnKey, columnName, columnDescription, null);
	}

	public SortableColumn(final String columnKey, final String columnName, final String columnDescription, final SortOrder sortOrder) {
		this.columnKey = columnKey;
		this.columnName = columnName;
		this.columnDescription = columnDescription;
		this.sortOrder = sortOrder;
	}

	public void clear() {
		sortOrder = null;
	}

	public String getColumnDescription() {
		return columnDescription;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public String getColumnName() {
		return columnName;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
}
