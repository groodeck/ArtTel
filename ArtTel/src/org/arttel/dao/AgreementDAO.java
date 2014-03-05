package org.arttel.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.AgreementVO;
import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.controller.vo.filter.AgreementFilterVO;
import org.arttel.dictionary.Status;
import org.arttel.exception.DaoException;
import org.arttel.generator.CellType;
import org.arttel.generator.DataCell;
import org.arttel.generator.DataSheet;
import org.arttel.generator.report.ReportDataVO;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgreementDAO extends BaseDao {

	@Autowired
	private CityDAO cityDao;

	public String create( final AgreementVO agreementVO, final String userName ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate("insert into agreement(status, signDate, name, surname, street, city, agreementNumber, client, " +
							"house, apartment, phone, bundle, signPlace, agentDeal, installationDate, evidenceEntryDate, salesmanCode, " +
							"subscriberNumber, agreementEndDate, comments, additionalComments, user) " +
							"values("
							+ "'" + agreementVO.getStatus().getIdn() + "', " 
							+ (agreementVO.getSignDate()!=null ? "'"+agreementVO.getSignDate()+"'" : "null") + ", "
							+ "'" + agreementVO.getName() + "', "
							+ "'" + agreementVO.getSurname() + "', "
							+ "'" + agreementVO.getStreet() + "', "
							+ "'" + agreementVO.getCity() + "', "
							+ "'" + agreementVO.getAgreementNumber() + "', "
							+ "'" + agreementVO.getClient() + "', "
							+ "'" + agreementVO.getHouse() + "', "
							+ "'" + agreementVO.getApartment() + "', "
							+ "'" + agreementVO.getPhone() + "', "
							+ "'" + agreementVO.getBundle() + "', "
							+ "'" + agreementVO.getSignPlace() + "', "
							+ "'" + agreementVO.getAgentDeal() + "', "
							+ (agreementVO.getInstallationDate()!=null ? "'"+agreementVO.getInstallationDate()+"'" : "null") + ", "
							+ (agreementVO.getEvidenceEntryDate()!=null ? "'"+agreementVO.getEvidenceEntryDate()+"'" : "null") + ", "
							+ "'" + agreementVO.getSalesmanCode() + "', "
							+ "'" + agreementVO.getSubscriberNumber() + "', "
							+ (agreementVO.getAgreementEndDate()!=null ? "'"+agreementVO.getAgreementEndDate()+"'" : "null") + ", "
							+ "'" + agreementVO.getComments() + "', "
							+ "'" + agreementVO.getAdditionalComments() + "', "
							+ "'" + userName + "')");
			
		} catch (SQLException e) {
			throw new DaoException("AgreementDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	public String save(AgreementVO agreementVO, String userName) throws DaoException {
		if(agreementVO.getAgreementId() != null && !"".equals(agreementVO.getAgreementId())){
			return update(agreementVO, userName);
		} else {
			return create(agreementVO, userName);
		}
	}

	private String update(  final AgreementVO agreementVO, final String userName ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate("UPDATE `agreement` SET " +
							"status = '" + agreementVO.getStatus().getIdn() + "', " +
							"signDate = " + (agreementVO.getSignDate() != null ? "'" +agreementVO.getSignDate()+"'" : "null") + ", " +
							"name = '" + agreementVO.getName() + "', " + 
							"surname = '" + agreementVO.getSurname() + "', " +
							"street = '" + agreementVO.getStreet() + "', " +
							"city = '" + agreementVO.getCity() + "', " +
							"agreementNumber = '" + agreementVO.getAgreementNumber() + "', " +
							"client = '" + agreementVO.getClient() + "', " +
							"house = '" + agreementVO.getHouse() + "', " +
							"apartment = '" + agreementVO.getApartment() + "', " +
							"phone = '" + agreementVO.getPhone() + "', " +
							"bundle = '" + agreementVO.getBundle() + "', " +
							"signPlace = '" + agreementVO.getSignPlace() + "', " +
							"agentDeal = '" + agreementVO.getAgentDeal() + "', " +
							"installationDate = " + (agreementVO.getInstallationDate() != null ? "'" +agreementVO.getInstallationDate()+"'" : "null") + ", " +
							"evidenceEntryDate = " + (agreementVO.getEvidenceEntryDate() != null ? "'" +agreementVO.getEvidenceEntryDate()+"'" : "null") + ", " +
							"salesmanCode = '" + agreementVO.getSalesmanCode() + "', " +
							"subscriberNumber = '" + agreementVO.getSubscriberNumber() + "', " +
							"agreementEndDate = " + (agreementVO.getAgreementEndDate() != null ? "'" +agreementVO.getAgreementEndDate()+"'" : "null") + ", " +
							"comments = '" + agreementVO.getComments() + "', " +
							"additionalComments = '" + agreementVO.getAdditionalComments() + "', " +
							"user = '" + userName + "' " +
							"WHERE agreementId = " + agreementVO.getAgreementId() );
							
		} catch (SQLException e) {
			throw new DaoException("AgreementDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	public List<AgreementVO> getAgreementList(AgreementFilterVO agreementFilterVO) throws DaoException {
		final List<AgreementVO> resultList = new ArrayList<AgreementVO>();
		if(agreementFilterVO == null){
			return resultList;
		}
		
		final String query = prepareQuery(agreementFilterVO);

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				resultList.add(extractAgreement(rs));
			} 
		} catch (SQLException e) {
			throw new DaoException("AgreementDAO exception", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}

	private String prepareQuery(final AgreementFilterVO agreementFilterVO) {
		
		final StringBuilder query = new StringBuilder(
				AGREEMENT_QUERY + 
				" where " +
				"	true "
		);
		
		if(StringUtils.isNotEmpty(agreementFilterVO.getPhrase())){
			query.append(" and  ")
				.append("(")
					.append(" surname like '%" + agreementFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append(" name like '%" + agreementFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append(" street like '%" + agreementFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append(" city like '%" + agreementFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append(" agreementNumber like '%" + agreementFilterVO.getPhrase() + "%'")
				.append(")");
		} else {
			query.append(agreementFilterVO.getSurname() != null   ?  " and  surname='" + agreementFilterVO.getSurname() + "' or name='" + agreementFilterVO.getSurname() + "'" : "")
				.append(agreementFilterVO.getDateFrom() != null ?  " and signDate >= '" + agreementFilterVO.getDateFrom() + "'" : "" )
				.append(agreementFilterVO.getDateTo() != null ?  " and signDate <= '" + agreementFilterVO.getDateTo() + "'" : "" );
		
		}
		query.append(" order by signDate desc, agreementId desc ");
		return query.toString();
	}

	private static final String AGREEMENT_QUERY = " " +
			" select agreementId, status, signDate, name, surname, street, city, agreementNumber, client, " +
			" house, apartment, phone, bundle, signPlace, agentDeal, installationDate, evidenceEntryDate, " +
			" salesmanCode, subscriberNumber, agreementEndDate, comments, additionalComments, user " +
			" from `Agreement` ";

	public AgreementVO getAgreementById(String agreementId) throws DaoException {
		AgreementVO result = null;
		if (agreementId != null && !"".equals(agreementId)) {
			final String query = AGREEMENT_QUERY.concat(String.format(
					"WHERE agreementId = %s", agreementId));
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					result = extractAgreement(rs);
				}
			} catch (SQLException e) {
				throw new DaoException("AgreementDAO exception", e);
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	private AgreementVO extractAgreement(ResultSet rs) throws SQLException {
		final AgreementVO singleAgreement = new AgreementVO();
		singleAgreement.setAgreementId(rs.getString(1));
		final String statusIdn = rs.getString(2);
		if(statusIdn != null){
			singleAgreement.setStatus(Status.getValueByIdn(statusIdn));
		}
		singleAgreement.setSignDate(rs.getDate(3));
		singleAgreement.setName(rs.getString(4));
		singleAgreement.setSurname(rs.getString(5));
		singleAgreement.setStreet(rs.getString(6));
		singleAgreement.setCity(rs.getString(7));
		singleAgreement.setAgreementNumber(rs.getString(8));
		singleAgreement.setClient(rs.getString(9));
		singleAgreement.setHouse(rs.getString(10));
		singleAgreement.setApartment(rs.getString(11));
		singleAgreement.setPhone(rs.getString(12));
		singleAgreement.setBundle(rs.getString(13));
		singleAgreement.setSignPlace(rs.getString(14));
		singleAgreement.setAgentDeal(rs.getString(15));
		singleAgreement.setInstallationDate(rs.getDate(16));
		singleAgreement.setEvidenceEntryDate(rs.getDate(17));
		singleAgreement.setSalesmanCode(rs.getString(18));
		singleAgreement.setSubscriberNumber(rs.getString(19));
		singleAgreement.setAgreementEndDate(rs.getDate(20));
		singleAgreement.setComments(rs.getString(21));
		singleAgreement.setAdditionalComments(rs.getString(22));
		singleAgreement.setUser(rs.getString(23));
		
		return singleAgreement;
	}
	
	public void deleteAgreementById(String agreementId) throws DaoException {
		if (agreementId != null && !"".equals(agreementId)) {
			final String query = String.format("DELETE FROM `Agreement` WHERE agreementId = %s", agreementId);
			try {
				getConnection().createStatement().executeUpdate(query);
			} catch (SQLException e) {
				throw new DaoException("AgreementDAO exception", e);
			} finally {
				disconnect(null, null);
			}
		}
	}

	public ReportDataVO getReportData(final String worksheetName, final Date dateFrom, final Date dateTo, final String cityIdn, 
			final int dataRowsOffset) throws DaoException {
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			final ReportDataVO result = new ReportDataVO();
			String dataQuery = " SELECT " +
					"	bundle, signDate, agreementNumber, concat(surname,' ',name), street, house, apartment, phone, city, signPlace, " +
					"	agentDeal, installationDate, evidenceEntryDate, salesmanCode, subscriberNumber " +
					" FROM Agreement " +
					" WHERE true " ;
			if(dateFrom != null){
				dataQuery = dataQuery.concat(" AND installationDate >='" + dateFrom + "'");
			}
			if(dateTo != null){
				dataQuery = dataQuery.concat(" AND installationDate <='" + dateTo + "'");
			}
			if(cityIdn != null){
				dataQuery = dataQuery.concat(" AND city ='" + cityIdn + "'");
			}
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(dataQuery);
			
			//TODO: dodaæ dynamiczny zapis metadanych instalacji, komorek niestandardowych do ReportDataVO.reportDetailsList
			
			int xlsRowNumber = 1, sheetRowsDelimiter = 29;
			DataSheet dataSheet = new DataSheet();
			dataSheet.setDataRowsOffset(dataRowsOffset);
			dataSheet.addDetailsCell(dataRowsOffset - 3, 10, getCityInfo(cityIdn));
			dataSheet.addDetailsCell(dataRowsOffset - 3, 13, getDateInfo(dateTo));
			dataSheet.addDetailsCell(dataRowsOffset - 5, 4, getMonthInfo(dateFrom, dateTo));
			
			while(rs.next()){
				
				final List<DataCell> row = extractDataRow(worksheetName, rs, xlsRowNumber++);
				dataSheet.getRows().add(row);
				
				if(sheetRowsDelimiter-- == 0){
					sheetRowsDelimiter = 29;
					result.addDataSheet(dataSheet);
					dataSheet = new DataSheet();
					dataSheet.setDataRowsOffset(dataRowsOffset);
					dataSheet.addDetailsCell(dataRowsOffset - 3, 10, getCityInfo(cityIdn));
					dataSheet.addDetailsCell(dataRowsOffset - 3, 13, getDateInfo(dateTo));
					dataSheet.addDetailsCell(dataRowsOffset - 5, 4, getMonthInfo(dateFrom, dateTo));
				}
			}
			result.addDataSheet(dataSheet);
			
			return result;
			
		} catch (SQLException e) {
			throw new DaoException("DecoderDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
	}
	
	private DataCell getDateInfo(final Date date) {
		final String dateStr = getDateStr(date); 
		return new DataCell( dateStr, CellType.TEXT);
	}

	private String getDateStr(final Date date) {
		final String result;
		if(date == null){
			result = "";
		} else {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(date);
		}
		return result;
	}
	
	private List<DataCell> extractDataRow(final String worksheetName, final ResultSet rs, int xlsRowNumber) throws SQLException {
		
		final List<DataCell> row = new ArrayList<DataCell>();
		row.add(new DataCell(xlsRowNumber, CellType.INT));
		row.add(new DataCell(rs.getString(1),CellType.TEXT));
		row.add(new DataCell(rs.getDate(2),CellType.DATE));
		row.add(new DataCell(rs.getString(3),CellType.TEXT));
		row.add(new DataCell(rs.getString(4),CellType.TEXT));
		row.add(new DataCell(rs.getString(5),CellType.TEXT));
		row.add(new DataCell(rs.getString(6),CellType.TEXT));
		row.add(new DataCell(rs.getString(7),CellType.TEXT));
		row.add(new DataCell(rs.getString(8),CellType.TEXT));
		row.add(new DataCell(rs.getString(9),CellType.TEXT));
		row.add(new DataCell(rs.getString(10),CellType.TEXT));
		boolean agentDeal = rs.getBoolean(11);
		if(agentDeal){
			row.add(new DataCell("Tak",CellType.TEXT));
		} else {
			row.add(new DataCell("Nie",CellType.TEXT));
		}
		row.add(new DataCell(rs.getDate(12),CellType.DATE));
		row.add(new DataCell(rs.getDate(13),CellType.DATE));
		row.add(new DataCell(rs.getString(14),CellType.TEXT));
		row.add(new DataCell(rs.getString(15),CellType.TEXT));
		
		return row;
	}
		
	private DataCell getCityInfo(final String cityIdn) throws DaoException {
		final String city = getCityDesc(cityIdn);
		return new DataCell( city, CellType.TEXT);
	}

	private String getCityDesc(final String cityIdn) throws DaoException {
		final SimpleComboElement city = cityDao.getCityByIdn(cityIdn);
		return city != null ? city.getCityDesc() : "";
	}
	
	private DataCell getMonthInfo(final Date dateFrom, final Date dateTo) {
		final DataCell result;
		if(dateFrom != null && dateTo != null){
			String monthName = getMonthName(dateFrom, dateTo);
			result = new DataCell( monthName, CellType.TEXT);
		}else {
			result = new DataCell("", CellType.TEXT);
		}
		return result;
	}

	private String getMonthName(final Date dateFrom, final Date dateTo) {
		final String result;
		final Calendar calendarDayFrom = getCalendarDay(dateFrom);
		final Calendar calendarDayTo = getCalendarDay(dateTo);
		if(calendarDayFrom.get(Calendar.MONTH) == calendarDayTo.get(Calendar.MONTH)){
			result = Translator.getCalendarMonthName(calendarDayFrom);
		} else {
			result = "";
		}
		return result;
	}

	private Calendar getCalendarDay(final Date dateFrom) {
		Calendar result = GregorianCalendar.getInstance();
		result.setTime(dateFrom);
		return result;
	}
}
