package org.arttel.generator.report;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.InstallationDeviceModel;
import org.arttel.controller.vo.InstallationVO;
import org.arttel.controller.vo.ReportParamsVO;
import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.controller.vo.filter.InstallationFilterVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.InstalationDAO;
import org.arttel.dictionary.InstallationType;
import org.arttel.exception.DaoException;
import org.arttel.generator.CellType;
import org.arttel.generator.DataCell;
import org.arttel.generator.DataSheet;
import org.arttel.service.InstalationService;
import org.arttel.util.Translator;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstallationReportService {

	@Autowired
	private CityDAO cityDao;

	@Autowired
	private InstalationDAO instalationDao;

	@Autowired
	private InstalationService installationService;

	private static final int DATA_ROWS_OFFSET = 4;

	private int countNotEmpty(final InstallationDeviceModel...devices) {
		int count = 0;
		for(final InstallationDeviceModel device : devices){
			if(device != null && StringUtils.isNotBlank(device.getSerialNumber())){
				count++;
			}
		}
		return count;
	}

	private List<DataCell> extractDataRow(final String worksheetName, final InstallationVO installation, final int xlsRowNumber){
		final List<DataCell> row = new ArrayList<DataCell>();
		row.add(new DataCell(xlsRowNumber, CellType.INT));
		row.add(new DataCell(installation.getAddress(),CellType.TEXT));
		final Integer dtv = installation.getDtvCount();
		final Integer multiroom = installation.getMultiroomCount();
		final Integer net = installation.getNetCount();
		final Integer tel = installation.getTelCount();
		final Integer atv = installation.getAtvCount();
		row.add(new DataCell(dtv,CellType.INT));
		row.add(new DataCell(multiroom,CellType.INT));
		row.add(new DataCell(net,CellType.INT));
		row.add(new DataCell(tel,CellType.INT));
		row.add(new DataCell(atv,CellType.INT));
		final int installationsSum = sum(dtv, multiroom, net, tel, atv);
		if(installationsSum > 0 ){
			row.add(new DataCell(installationsSum, CellType.INT));
			if(isNotHardwareReplacement(installation)){
				row.add(new DataCell(1, CellType.INT));
			}
		} else {
			row.add(DataCell.EMPTY);
			row.add(DataCell.EMPTY);
		}
		final InstallationDeviceModel twoWay1 = installation.getTwoWay1();
		if(twoWay1 != null && StringUtils.isNotBlank(twoWay1.getMacAddress())){
			row.add(new DataCell(1, CellType.INT));
		} else {
			row.add(DataCell.EMPTY);
		}
		row.add(new DataCell(twoWay1.getMacAddress(), CellType.TEXT));
		row.add(new DataCell(Translator.getDouble(twoWay1.getUpstream()), CellType.DOUBLE));
		row.add(new DataCell(Translator.getDouble(twoWay1.getDownstream()), CellType.DOUBLE));
		final int oneWayQuantity = countNotEmpty(installation.getOneWay1(), installation.getOneWay2());
		if(oneWayQuantity > 0){
			row.add(new DataCell(oneWayQuantity,CellType.INT));
		} else {
			row.add(DataCell.EMPTY);
		}
		final InstallationDeviceModel modem = installation.getModem();
		if(modem != null && StringUtils.isNotBlank(modem.getMacAddress())){
			row.add(new DataCell(1,CellType.INT));
		} else {
			row.add(DataCell.EMPTY);
		}
		row.add(new DataCell(modem.getMacAddress(), CellType.TEXT));
		row.add(new DataCell(Translator.getDouble(modem.getUpstream()), CellType.DOUBLE));
		row.add(new DataCell(Translator.getDouble(modem.getDownstream()), CellType.DOUBLE));
		return row;
	}

	private List<InstallationVO> fetchAndProcessReportData(final InstallationFilterVO instalationFilterVO) {
		final List<InstallationVO> rawRecords = installationService.getInstallationList(instalationFilterVO).getRecords();
		final List<InstallationVO> results = Lists.newArrayList();
		for(final InstallationVO singleInstallation : rawRecords){
			results.add(singleInstallation);
			final InstallationDeviceModel secondaryTwoWay = singleInstallation.getTwoWay2();
			if(secondaryTwoWay != null && StringUtils.isNotBlank(secondaryTwoWay.getMacAddress())){
				final InstallationVO secondInstallation = new InstallationVO();
				secondInstallation.setAddress(singleInstallation.getAddress());
				secondInstallation.setTwoWay1(secondaryTwoWay);
				results.add(secondInstallation);
			}
		}
		return results;
	}

	private String getCityDesc(final String cityIdn) throws DaoException {

		final SimpleComboElement city = cityDao.getCityByIdn(cityIdn);
		return city != null ? city.getCityDesc() : "";
	}

	private DataCell getCityInfo(final String cityIdn) throws DaoException {
		final String cityDesc = getCityDesc(cityIdn);
		final String cellText = "Miejscowoœæ: " + Translator.complement(cityDesc, '.', 65);
		return new DataCell( cellText, CellType.TEXT);
	}

	private DataCell getDateInfo(final Date dateFrom, final Date dateTo) {
		final String dateFromStr = dateFrom != null ? dateFrom.toString() : null;
		final String dateToStr = dateTo != null ? dateTo.toString() : null;
		final StringBuilder sb = new StringBuilder("w okresie");
		sb.append(" od ").append(Translator.complement(dateFromStr, '.', 30));
		sb.append(" do ").append(Translator.complement(dateToStr, '.', 30));
		return new DataCell( sb.toString(), CellType.TEXT);
	}

	public ReportDataVO getReportData(final ReportParamsVO reportVO) throws DaoException {

		final ReportDataVO result = new ReportDataVO();
		final InstallationFilterVO instalationFilterVO = new InstallationFilterVO();
		instalationFilterVO.setInstalationType(Lists.newArrayList(
				InstallationType.MONTAZ.getIdn(),
				InstallationType.WYMIANA_SPRZETU.getIdn()));
		final Date dateFrom = reportVO.getDataOd();
		if(dateFrom != null){
			instalationFilterVO.setDateFrom(dateFrom);
		}
		final Date dateTo = reportVO.getDataDo();
		if(dateTo != null){
			instalationFilterVO.setDateTo(dateTo);
		}
		final String cityIdn = reportVO.getCity();
		if(cityIdn != null){
			instalationFilterVO.setCity(cityIdn);
		}
		final List<InstallationVO> installations = fetchAndProcessReportData(instalationFilterVO);

		//TODO: dodaæ dynamiczny zapis metadanych instalacji, komorek niestandardowych do ReportDataVO.reportDetailsList

		int xlsRowNumber = 1, sheetRowsDelimiter = 24;
		DataSheet dataSheet = new DataSheet();
		dataSheet.setDataRowsOffset(DATA_ROWS_OFFSET);
		dataSheet.addDetailsCell(DATA_ROWS_OFFSET - 3, 1, getCityInfo(cityIdn));
		dataSheet.addDetailsCell(DATA_ROWS_OFFSET - 3, 13, getDateInfo(dateFrom, dateTo));

		for(final InstallationVO installation : installations){

			final List<DataCell> row = extractDataRow("Arkusz1", installation, xlsRowNumber++);
			dataSheet.getRows().add(row);

			if(sheetRowsDelimiter-- == 0){
				sheetRowsDelimiter = 24;
				result.addDataSheet(dataSheet);
				dataSheet = new DataSheet();
				dataSheet.setDataRowsOffset(DATA_ROWS_OFFSET);
				dataSheet.addDetailsCell(DATA_ROWS_OFFSET - 3, 1, getCityInfo(cityIdn));
				dataSheet.addDetailsCell(DATA_ROWS_OFFSET - 3, 13, getDateInfo(dateFrom, dateTo));
			}
		}

		result.addDataSheet(dataSheet);

		return result;

	}

	private boolean isNotHardwareReplacement(final InstallationVO installation) {
		return InstallationType.WYMIANA_SPRZETU != installation.getInstallationType();
	}

	private int sum(final Integer... numbers) {
		int result = 0;
		for(final Integer number : numbers){
			result += number == null ? 0 : number;
		}
		return result;
	}

}
