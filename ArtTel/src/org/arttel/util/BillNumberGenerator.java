package org.arttel.util;

import java.util.Date;
import java.util.List;

import org.arttel.dao.BillDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BillNumberGenerator extends NumberGenerator {

	@Autowired
	private BillDAO billDao;

	@Override
	protected List<String> getDocumentNumbers(final Date dateFrom, final String userName) {
		return billDao.getDocumentNumbers(dateFrom, userName);
	}
}
