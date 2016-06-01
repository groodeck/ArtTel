package org.arttel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.arttel.dao.UserDAO;
import org.arttel.dictionary.ReportType;
import org.arttel.entity.User;
import org.arttel.view.ComboElement;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ReportService {

	private final static Logger log = Logger.getLogger(ReportService.class);

	@Autowired
	private UserDAO userDao;

	public List<ComboElement> getReportDictionary(final String userName) {
		final User user = userDao.findUserByName(userName);
		final List<ComboElement> results = Lists.newArrayList();
		if (user != null) {
			final List<ReportType> reports = user.getUserReports();
			for (final ReportType userReport : reports) {
				results.add(userReport);
			}
		}
		return results;
	}
}
