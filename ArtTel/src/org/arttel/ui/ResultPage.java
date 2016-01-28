package org.arttel.ui;

import java.util.ArrayList;
import java.util.List;

public class ResultPage <T>{

	private int pageNo;
	private int pageCount;
	protected List<T> records;

	public ResultPage(){
		this.records = new ArrayList<T>();
		this.pageNo = 0;
		this.pageCount = 0;
	}

	public ResultPage(final List<T> resultList, final int pageNo, final int pageCount) {
		this.records = resultList;
		this.pageNo = pageNo;
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setPageCount(final int pageCount) {
		this.pageCount = pageCount;
	}

	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;
	}

	public void setRecords(final List<T> records) {
		this.records = records;
	}
}
