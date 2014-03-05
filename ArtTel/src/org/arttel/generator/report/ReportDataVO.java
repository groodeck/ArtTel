package org.arttel.generator.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.arttel.generator.DataSheet;

import com.google.common.collect.Iterables;

public class ReportDataVO {
	
	private List<DataSheet> dataSheetList;

	public ReportDataVO(){
		dataSheetList = new ArrayList<DataSheet>();
	}
	
	public void addDataSheet(final DataSheet dataSheet){
		dataSheetList.add(dataSheet);
	}
	
	public DataSheet getDataSheet(final int index){
		return dataSheetList.get(index);
	}
	
	public int getSheetElementsCount(){
		return dataSheetList.size();
	}

	public boolean isMultiSheetData() {
		return dataSheetList.size() > 1;
	}
	
	public DataSheet getFirstSheetData(){
		return Iterables.getFirst(dataSheetList, null);
	}
}
