package org.arttel.generator.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.arttel.controller.vo.ReportParamsVO;
import org.arttel.dao.AgreementDAO;
import org.arttel.dao.DealingDAO;
import org.arttel.dao.InstalationDAO;
import org.arttel.dao.OrderDAO;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportsGenerator {

	@Autowired
	private AgreementDAO agreementDao;

	@Autowired
	private OrderDAO orderDao;

	@Autowired
	private InstalationDAO instalationDao;

	@Autowired
	private DealingDAO dealingDao;

	@Autowired
	private InstallationReportService installationReportService;

	public String generateReport( final ReportParamsVO reportVO, final String sessionId ) throws DaoException,IOException,InvalidFormatException {

		String reportPath = null;

		final Map<String,ReportDataVO> reportDataMap = new HashMap<String,ReportDataVO>();

		switch ( reportVO.getReportType() ) {

		case INSTALATION:

			//TODO: zamienic wszystkie syouty na logger

			//TODO: w tym switchu powinna zawieraæ sie ca³a logika wyciagania sheetów raportu,
			// do XLSGeneratora przekazujemy wszystko w parametrach

			final ReportDataVO repData_tel = installationReportService.getReportData(reportVO);
			reportDataMap.put("Arkusz1", repData_tel);

			reportPath = XlsReportGenerator.generate("InstallationsTemplate.xlsx", "Instalacje.xlsx", reportDataMap, sessionId);
			break;
		case ORDER:
			final ReportDataVO repData_orders = orderDao.getReportData("Zlecenia", reportVO.getDataOd(), reportVO.getDataDo(), reportVO.getCity());
			reportDataMap.put("Zlecenia", repData_orders);
			reportPath = XlsReportGenerator.generate("OrderTemplate.xlsx", "Zlecenia.xlsx", reportDataMap, sessionId);

			break;

		case AGREEMENT:
			final ReportDataVO repData_agreement = agreementDao.getReportData("Raport sprzeda¿y", reportVO.getDataOd(), reportVO.getDataDo(), reportVO.getCity(),6);
			reportDataMap.put("Raport sprzeda¿y", repData_agreement);
			reportPath = XlsReportGenerator.generate("AgreementTemplate.xlsx", "Umowy.xlsx", reportDataMap, sessionId);
			break;

		case DEALING:
			final ReportDataVO repData_dealing = dealingDao.getReportData("Raport sprzeda¿y", reportVO.getDataOd(), reportVO.getDataDo(), reportVO.getCity());
			reportDataMap.put("Raport sprzeda¿y", repData_dealing);
			reportPath = XlsReportGenerator.generate("DealingTemplate.xlsx", "Obrot.xlsx", reportDataMap, sessionId);

			break;
		default:
			break;
		}

		return reportPath;
	}
}
