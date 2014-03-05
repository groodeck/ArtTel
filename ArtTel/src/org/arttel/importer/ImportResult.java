package org.arttel.importer;

import java.util.List;

import org.arttel.controller.vo.BasePageVO;

public class ImportResult<T extends BasePageVO> {

	private final List<T> orderDataList;
	private final List<String> errorList;
	
	public ImportResult(final List<T> orderDataList, final List<String> errorList) {
		 this.orderDataList = orderDataList;
		 this.errorList = errorList;
	}

	public List<T> getDataList() {
		return orderDataList;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public boolean isDataOK() {
		return errorList.isEmpty();
	}
	
}
