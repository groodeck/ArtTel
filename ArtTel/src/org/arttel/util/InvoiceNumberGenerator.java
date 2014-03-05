package org.arttel.util;

import org.arttel.dao.InvoiceDAO;
import org.arttel.exception.DaoException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class InvoiceNumberGenerator extends NumberGenerator {

	@Autowired
	private InvoiceDAO invoiceDao;
	
	protected String getNextNumber(final DateTime startDate) throws DaoException {
		return invoiceDao.getLastInvoiceNumber(startDate.toDate());
	}

}
