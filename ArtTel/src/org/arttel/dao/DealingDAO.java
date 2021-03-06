package org.arttel.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.DealingVO;
import org.arttel.controller.vo.SqueezeVO;
import org.arttel.controller.vo.filter.DealingFilterVO;
import org.arttel.dictionary.DealingType;
import org.arttel.dictionary.DocumentType;
import org.arttel.exception.DaoException;
import org.arttel.generator.CellType;
import org.arttel.generator.DataCell;
import org.arttel.generator.DataSheet;
import org.arttel.generator.report.ReportDataVO;
import org.arttel.util.Translator;
import org.springframework.stereotype.Component;

@Component
public class DealingDAO extends BaseDao {

	private static final String DEALING_QUERY = " " +
			" select "
			+ "d.dealingId, "
			+ "d.dealingType, "
			+ "d.date, "
			+ "d.corporateCosts, "
			+ "d.privateCosts, "
			+ "d.fuel, "
			+ "d.fuelLiters, "
			+ "d.income, "
			+ "d.amount, "
			+ "d.comments1, "
			+ "d.comments2, "
			+ "d.comments3, "
			+ "d.machine, "
			+ "d.city, "
			+ "d.userId, "
			+ "c.clientDesc, "
			+ "d.documentId, "
			+ "d.documentType, "
			+ "i.documentNumber, "
			+ "b.documentNumber "
			+ " from `Dealing` d "
			+ " left join Client c on d.income = c.clientId "
			+ " left join Invoice i on i.documentId = d.documentId "
			+ " left join Bill b on b.documentId = d.documentId "
			+ " where true ";

	public String create( final DealingVO dealingVO, final String userName ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			final int rowsInserted = stmt
					.executeUpdate("insert into dealing(dealingType,date,corporateCosts,privateCosts,income,fuel," +
							"fuelLiters,amount,comments1,comments2,comments3,machine,city,userId, documentId, documentType) " +
							"values("
							+ "'" + dealingVO.getDealingType().getIdn() + "', "
							+ (dealingVO.getDate()!=null ? "'"+Translator.toString(dealingVO.getDate())+"'" : "null") + ", "
							+ "'" + dealingVO.getCorporateCosts() + "', "
							+ "'" + dealingVO.getPrivateCosts() + "', "
							+ "'" + dealingVO.getIncomeClientId() + "', "
							+ "'" + dealingVO.getFuel() + "', "
							+ dealingVO.getFuelLiters() + ", "
							+ dealingVO.getAmount() + ", "
							+ "'" + dealingVO.getComments1() + "', "
							+ "'" + dealingVO.getComments2() + "', "
							+ "'" + dealingVO.getComments3() + "', "
							+ "'" + dealingVO.getMachine() + "', "
							+ "'" + dealingVO.getCity() + "', "
							+ "'" + userName + "', "
							+ dealingVO.getDocumentId() + ", "
							+ (dealingVO.getDocumentType() != null ?
									"'" + dealingVO.getDocumentType().getIdn() + "'" : "null")
									+ ")");

		} catch (final SQLException e) {
			throw new DaoException("DealingDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	public void deleteDealingById(final String dealingId) throws DaoException {
		if (dealingId != null && !"".equals(dealingId)) {
			final String query = String.format("DELETE FROM `Dealing` WHERE dealingId = %s", dealingId);
			try {
				final int rowsDeleted = getConnection().createStatement().executeUpdate(query);
			} catch (final SQLException e) {
				throw new DaoException("DealingDAO exception", e);
			} finally {
				disconnect(null, null);
			}
		}
	}

	private List<DataCell> extractDataRow(final String worksheetName, final ResultSet rs, final int xlsRowNumber) throws SQLException {

		final List<DataCell> row = new ArrayList<DataCell>();
		row.add(new DataCell(xlsRowNumber, CellType.INT));
		final DealingType dealingType =  DealingType.getValueByIdn(rs.getString(1));
		if(dealingType != null){
			row.add(new DataCell(dealingType.getDesc(),CellType.TEXT)); //dealing type
		}
		row.add(new DataCell(rs.getDate(2),CellType.DATE)); // date
		row.add(new DataCell(rs.getString(3),CellType.TEXT)); // city
		row.add(new DataCell(rs.getString(4),CellType.TEXT)); // corporate Costs
		row.add(new DataCell(rs.getString(5),CellType.TEXT)); // private costs
		row.add(new DataCell(rs.getString(6),CellType.TEXT)); //fuel
		row.add(new DataCell(rs.getString(7),CellType.TEXT)); //amount
		row.add(new DataCell(rs.getString(8),CellType.TEXT)); //comments1
		row.add(new DataCell(rs.getString(9),CellType.TEXT)); //comments2
		row.add(new DataCell(rs.getString(10),CellType.TEXT)); //comments3

		return row;
	}

	private DealingVO extractDealing(final ResultSet rs) throws SQLException {
		final DealingVO singleDealing = new DealingVO();
		singleDealing.setDealingId(rs.getString(1));
		final String dealingTypeIdn = rs.getString(2);
		if(dealingTypeIdn != null){
			singleDealing.setDealingType(DealingType.getValueByIdn(dealingTypeIdn));
		}
		singleDealing.setDate(rs.getDate(3));
		if(singleDealing.getDealingType()==DealingType.COSTS){
			singleDealing.setCorporateCosts(rs.getString(4));
			singleDealing.setPrivateCosts(rs.getString(5));
			singleDealing.setFuel(rs.getString(6));
			singleDealing.setFuelLiters(rs.getString(7));
		} else if(singleDealing.getDealingType()==DealingType.INCOME){
			singleDealing.setIncomeClientId(rs.getString(8));
		}
		singleDealing.setAmount(rs.getString(9));
		singleDealing.setComments1(rs.getString(10));
		singleDealing.setComments2(rs.getString(11));
		singleDealing.setComments3(rs.getString(12));
		singleDealing.setMachine(rs.getString(13));
		singleDealing.setCity(rs.getString(14));
		singleDealing.setUserName(rs.getString(15));
		singleDealing.setIncomeClientName(rs.getString(16));
		singleDealing.setDocumentId(rs.getInt(17));
		final String documentTypeStr = rs.getString(18);
		if(StringUtils.isNotEmpty(documentTypeStr)){
			singleDealing.setDocumentType(DocumentType.valueOf(documentTypeStr));
			if(singleDealing.getDocumentType() == DocumentType.INVOICE){
				singleDealing.setDocumentNumber(rs.getString(19));
			} else {
				singleDealing.setDocumentNumber(rs.getString(20));
			}
		}

		return singleDealing;
	}

	public DealingVO getDealingById(final String dealingId) throws DaoException {
		DealingVO result = null;
		if (dealingId != null && !"".equals(dealingId)) {
			final String query = DEALING_QUERY.concat(String.format(
					"AND d.dealingId = %s", dealingId));
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					result = extractDealing(rs);
				}
			} catch (final SQLException e) {
				throw new DaoException("DealingDAO exception", e);
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	public List<DealingVO> getDealingList(final DealingFilterVO dealingFilterVO) throws DaoException {
		final List<DealingVO> resultList = new ArrayList<DealingVO>();
		if(dealingFilterVO == null){
			return resultList;
		}

		final String query = prepareQuery(dealingFilterVO);

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				resultList.add(extractDealing(rs));
			}
		} catch (final SQLException e) {
			throw new DaoException("DealingDAO exception", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}

	private String getNextDealingId(final Statement stmt) throws SQLException {
		final String result;
		final ResultSet rs = stmt.executeQuery("select coalesce(max(dealingId) + 1, 1) as nextId from dealing");
		if(rs.next()){
			result = rs.getString("nextId");
		} else{
			result = null;
		}
		return result;
	}

	public ReportDataVO getReportData(final String worksheetName, final Date dateFrom, final Date dateTo, final String cityIdn) throws DaoException {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			final ReportDataVO result = new ReportDataVO();
			String dataQuery = "select d.dealingType, d.date, c.cityDesc, d.corporateCosts, d.privateCosts, d.fuel, d.amount, "
					+ " d.comments1, d.comments2, d.comments3 "
					+ " from `Dealing` d "
					+ " left join city c on d.city=c.cityIdn "
					+ " where 1 ";
			if(dateFrom != null){
				dataQuery = dataQuery.concat(" AND d.date >='" + dateFrom + "'");
			}
			if(dateTo != null){
				dataQuery = dataQuery.concat(" AND d.date <='" + dateTo + "'");
			}
			if(StringUtils.isNotEmpty(cityIdn)){
				dataQuery = dataQuery.concat(" AND d.city ='" + cityIdn + "'");
			}
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(dataQuery);

			int xlsRowNumber = 1;
			final DataSheet dataSheet = new DataSheet();
			dataSheet.setDataRowsOffset(3);
			while(rs.next()){

				final List<DataCell> row = extractDataRow(worksheetName, rs, xlsRowNumber++);
				dataSheet.getRows().add(row);

			}
			result.addDataSheet(dataSheet);

			return result;

		} catch (final SQLException e) {
			throw new DaoException("DealingDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
	}

	public List<DealingVO> getUserDealingList(final String userId) throws DaoException {

		final List<DealingVO> resultList = new ArrayList<DealingVO>();

		final String query = DEALING_QUERY.concat(String.format(
				"AND d.userId = '%s'", userId));
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				resultList.add(extractDealing(rs));
			}
		} catch (final SQLException e) {
			throw new DaoException("DealingDAO exception", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}

	private String insertDealing(final SqueezeVO squeezeVO, final String userName,final Statement stmt) throws SQLException {
		final String dealingId = getNextDealingId(stmt);
		if(dealingId != null){
			stmt.executeUpdate(" insert into dealing(dealingId,dealingType,income,amount,userId,date,comments1,city)"
					+ "values("
					+ dealingId + ", "
					+ "'income', "
					+ "'" + squeezeVO.getClientId() + "', "
					+ squeezeVO.getAmount() + ", "
					+ "'" + userName + "',"
					+ (squeezeVO.getSqueezeDate()!=null ? "'"+squeezeVO.getSqueezeDate()+"'," : "null,")
					+ "'" + squeezeVO.getComments() + "',"
					+ "'" + squeezeVO.getCity() + "')");
			squeezeVO.setDealingId(Translator.parseInteger(dealingId));
			return dealingId;
		} else {
			return null;
		}
	}

	private String prepareQuery(final DealingFilterVO dealingFilterVO) {

		final StringBuilder query = new StringBuilder(DEALING_QUERY);

		if(dealingFilterVO.getPhrase() != null){
			query.append(" and  ")
			.append("(")
			.append(" d.corporateCosts like '%" + dealingFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append(" d.privateCosts like '%" + dealingFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append(" d.income like '%" + dealingFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append(" d.fuel like '%" + dealingFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append(" d.comments1 like '%" + dealingFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append( "d.comments2 like '%" + dealingFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append( "d.comments3 like '%" + dealingFilterVO.getPhrase() + "%'")
			.append(")");
		} else {
			query.append(dealingFilterVO.getCompanyCosts() != null   ?  " and  d.corporateCosts='" + dealingFilterVO.getCompanyCosts() + "'" : "")
			.append(dealingFilterVO.getDateFrom() != null ?  " and d.date >= '" + dealingFilterVO.getDateFrom() + "'" : "" )
			.append(dealingFilterVO.getDateTo() != null ?  " and d.date <= '" + dealingFilterVO.getDateTo() + "'" : "" );

		}
		query.append(" order by d.date desc, d.dealingId desc ");
		return query.toString();
	}

	public void removeBySqueezeId(final String squeezeId) throws DaoException {
		if (squeezeId != null && !"".equals(squeezeId)) {
			final String query = String.format(
					"DELETE FROM d USING squeeze s JOIN dealing d" +
							" ON (s.dealingId = d.dealingID) WHERE s.squeezeId = %s", squeezeId);
			try {
				getConnection().createStatement().executeUpdate(query);
			} catch (final SQLException e) {
				throw new DaoException("SqueezeDAO exception", e);
			} finally {
				disconnect(null, null);
			}
		}
	}

	public String save(final DealingVO dealingVO, final String userName) throws DaoException {
		if(dealingVO.getDealingId() != null && !"".equals(dealingVO.getDealingId())){
			return update(dealingVO, userName);
		} else {
			return create(dealingVO, userName);
		}
	}

	private String update(  final DealingVO dealingVO, final String userName ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			final int rowsInserted = stmt
					.executeUpdate("UPDATE `dealing` SET " +
							"dealingType = '" + dealingVO.getDealingType().getIdn() + "', " +
							"date = " + (dealingVO.getDate() != null ? "'" +dealingVO.getDate()+"'" : "null") + ", " +
							"corporateCosts = '" + dealingVO.getCorporateCosts() + "', " +
							"privateCosts = '" + dealingVO.getPrivateCosts() + "', " +
							"income = '" + dealingVO.getIncomeClientId() + "', " +
							"fuel = '" + dealingVO.getFuel() + "', " +
							"fuelLiters = " + dealingVO.getFuelLiters() + ", " +
							"amount = " + dealingVO.getAmount() + ", " +
							"comments1 = '" + dealingVO.getComments1() + "', " +
							"comments2 = '" + dealingVO.getComments2() + "', " +
							"comments3 = '" + dealingVO.getComments3() + "', " +
							"machine = '" + dealingVO.getMachine() + "', " +
							"city = '" + dealingVO.getCity() + "', " +
							"userId = '" + userName + "', " +
							"documentId = " + dealingVO.getDocumentId() + ", " +
							"documentType = " + (dealingVO.getDocumentType() != null ? "'" + dealingVO.getDocumentType().getIdn() + "'" : "null") + " " +
							"WHERE dealingId = " + dealingVO.getDealingId() );

		} catch (final SQLException e) {
			throw new DaoException("DealingDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}
}
