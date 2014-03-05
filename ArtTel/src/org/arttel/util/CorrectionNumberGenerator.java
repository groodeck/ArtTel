package org.arttel.util;

import org.arttel.dao.CorrectionDAO;
import org.arttel.exception.DaoException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CorrectionNumberGenerator extends NumberGenerator {

	@Autowired
	private CorrectionDAO correctionDao;
	
	protected String getNextNumber(final DateTime startDate) throws DaoException {
		return correctionDao.getNextCorrectionNumber(startDate.toDate());
	}
}
