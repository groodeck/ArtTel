package org.arttel.util;

import java.util.List;

import org.arttel.dao.CorrectionDAO;
import org.arttel.exception.DaoException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CorrectionNumberGenerator extends NumberGenerator {

	@Autowired
	private CorrectionDAO correctionDao;

	@Override
	protected List<String> getInvoiceNumbers(final DateTime startDate, final String userName) throws DaoException {
		return correctionDao.getCorrectionNumbers(startDate.toDate(), userName);
	}
}
