package org.arttel.util;

import java.util.List;

import org.arttel.dao.InvoiceDAO;
import org.arttel.exception.DaoException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class InvoiceNumberGenerator extends NumberGenerator {

	@Autowired
	private InvoiceDAO invoiceDao;

	@Override
	protected List<String> getInvoiceNumbers(final DateTime startDate, final String userName) throws DaoException {
		return invoiceDao.getInvoiceNumbers(startDate.toDate(), userName);
	}
}
