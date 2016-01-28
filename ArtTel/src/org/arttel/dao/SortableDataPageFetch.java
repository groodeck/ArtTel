package org.arttel.dao;


public abstract class SortableDataPageFetch {

	public static final int MAX_RECORDS_ON_PAGE = 25;

	protected int countPages(final int recordsCount) {
		final int moduloRest = recordsCount % MAX_RECORDS_ON_PAGE;
		if(moduloRest > 0){
			return recordsCount / MAX_RECORDS_ON_PAGE + 1;
		} else {
			return recordsCount / MAX_RECORDS_ON_PAGE;
		}
	}
}
