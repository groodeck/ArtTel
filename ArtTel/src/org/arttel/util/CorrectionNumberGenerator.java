package org.arttel.util;

import java.util.Date;
import java.util.List;

import org.arttel.dao.CorrectionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CorrectionNumberGenerator extends NumberGenerator {

	@Autowired
	private CorrectionDAO correctionDao;

	@Override
	protected List<String> getDocumentNumbers(final Date date, final String userName) {
		return correctionDao.getDocumentNumbers(date, userName);
	}
}
