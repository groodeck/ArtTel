package org.arttel.ui;

import static org.arttel.dao.SortableDataPageFetch.MAX_RECORDS_ON_PAGE;

public class PageInfo {

	private String sortColumn;
	private SortOrder sortOrder;
	private int pageNo;

	public PageInfo(final SortableColumn sortColumn) {
		this.sortColumn = sortColumn.getColumnName();
		sortOrder = sortColumn.getSortOrder();
		pageNo = 0;
	}

	public int getFirstResult(){
		return pageNo * MAX_RECORDS_ON_PAGE;
	}

	public int getPageNo() {
		return pageNo;
	}

	public String getSortColumn() {
		return sortColumn;
	}
	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;
	}

	public void updateSort(final SortableColumn sortColumn) {
		sortOrder = sortColumn.getSortOrder();
		this.sortColumn = sortColumn.getColumnName();
	}
}
