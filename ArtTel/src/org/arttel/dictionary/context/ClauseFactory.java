package org.arttel.dictionary.context;


public class ClauseFactory {

	public String getWhereClauseFor(DictionaryPurpose usedFor){	
		final String result;
		if(usedFor == null){
			result = "";
		} else {
			result = String.format(" AND %s =1 ", usedFor.name());
		}
		return result;
	}
}
