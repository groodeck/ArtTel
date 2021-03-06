package org.arttel.util;

import java.util.Date;
import java.util.List;

import org.arttel.dao.InvoiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class InvoiceNumberGenerator extends NumberGenerator {

	@Autowired
	private InvoiceDAO invoiceDao;

	@Override
	protected List<String> getDocumentNumbers(final Date dateFrom, final String userName) {
		return invoiceDao.getDocumentNumbers(dateFrom, userName);
	}
}
