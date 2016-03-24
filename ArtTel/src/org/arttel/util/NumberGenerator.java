package org.arttel.util;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.arttel.exception.DaoException;
import org.joda.time.DateTime;

public abstract class NumberGenerator {

	protected abstract List<String> getDocumentNumbers(final Date date, final String userName);

	public String getNextNumber(final String userName) {
		String result = "";
		try {
			final DateTime startDate = new DateTime().withDayOfMonth(1);
			final int nextNumber = getNextSequence(startDate, userName);
			final String month = String.format("%02d", startDate.getMonthOfYear());
			result = String.format("%s/%s/%s", nextNumber, month, startDate.getYear());
		} catch (final DaoException e) {
			e.printStackTrace();
		}
		return result;
	}

	private int getNextSequence(final DateTime startDate, final String userName) throws DaoException {
		final List<String> invoiceNumbers = getDocumentNumbers(startDate.toDate(), userName);

		int maxInvoiceNumber = 0;
		for(final String invoiceNumber : invoiceNumbers){
			final String numberSequenceStr =
					new StringTokenizer(invoiceNumber).nextToken("/");
			final int numberSequence = Integer.parseInt(numberSequenceStr);
			if(numberSequence > maxInvoiceNumber){
				maxInvoiceNumber = numberSequence;
			}
		}
		return maxInvoiceNumber + 1;
	}
}
