package org.arttel.util;

import java.util.StringTokenizer;

import org.arttel.exception.DaoException;
import org.joda.time.DateTime;

public abstract class NumberGenerator {

	public String getNextNumber() {
		String result = "";
		try {
			final DateTime startDate = new DateTime().withDayOfMonth(1);
			final int nextNumber = getNextSequence(startDate);
			final String month = String.format("%02d", startDate.getMonthOfYear());
			result = String.format("%s/%s/%s", nextNumber, month, startDate.getYear());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return result;
	}

	private int getNextSequence(DateTime startDate) throws DaoException {
		final int nextNumber;
		final String lastNumber = getNextNumber(startDate);
		if(lastNumber != null){
			final String lastNumberSequence = 
					new StringTokenizer(lastNumber).nextToken("/");
			nextNumber = Integer.parseInt(lastNumberSequence) + 1; 
		} else {
			nextNumber = 1;
		}
		return nextNumber;
	}

	protected abstract String getNextNumber(final DateTime startDate) 
			throws DaoException ;
}
