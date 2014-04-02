package org.arttel.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.OrderVO;
import org.arttel.controller.vo.filter.OrderFilterVO;
import org.arttel.dictionary.OrderType;
import org.arttel.dictionary.Status;
import org.arttel.entity.Order;
import org.arttel.exception.DaoException;
import org.arttel.generator.CellType;
import org.arttel.generator.DataCell;
import org.arttel.generator.DataSheet;
import org.arttel.generator.report.ReportDataVO;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderDAO extends BaseDao {

	@PersistenceContext
	private EntityManager em;
	
	private final Logger log = Logger.getLogger(OrderDAO.class);
	
	private static final String ORDER_QUERY = " select orderId,status,orderType,issueDate,name,surname,address,city,bundle,serialNumber," +
			" realizationDate,solution,comments,additionalComments,userId, phone, problemDescription" +
			" from Orders ";

	public OrderVO getOrderById(String orderId) {
		OrderVO result = null;
		if (orderId != null && !"".equals(orderId)) {
			final String query = ORDER_QUERY.concat(String.format(
					"WHERE orderId = %s", orderId));
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					result = extractOrder(rs);
				}
			} catch (SQLException e) {
				log.error("SQLException", e);
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	//TODO: extract builder method in instalation dao
	private OrderVO extractOrder(ResultSet rs) throws SQLException {
		final OrderVO singleOrder = new OrderVO();
		singleOrder.setOrderId(rs.getString(1));
		final String statusIdn = rs.getString(2);
		if(statusIdn != null){
			singleOrder.setStatus(Status.getValueByIdn(statusIdn));
		}
		final String orderTypeIdn = rs.getString(3);
		if(orderTypeIdn != null){
			singleOrder.setOrderType(OrderType.getValueByIdn(orderTypeIdn));
		}
		singleOrder.setIssueDate(rs.getDate(4));
		singleOrder.setName(rs.getString(5));
		singleOrder.setSurname(rs.getString(6));
		singleOrder.setAddress(rs.getString(7));
		singleOrder.setCity(rs.getString(8));
		singleOrder.setBundle(rs.getString(9));
		singleOrder.setSerialNumber(rs.getString(10));
		singleOrder.setRealizationDate(rs.getDate(11));
		singleOrder.setSolution(rs.getString(12));
		singleOrder.setComments(rs.getString(13));
		singleOrder.setAdditionalComments(rs.getString(14));
		singleOrder.setUser(rs.getString(15));
		singleOrder.setPhone(rs.getString(16));
		singleOrder.setProblemDescription(rs.getString(17));
		
		return singleOrder;
	}

	public void deleteOrderById(final String orderId) {
		if (orderId != null && !"".equals(orderId)) {
			final String query = String.format("DELETE FROM Orders WHERE orderId = %s", orderId);
			try {
				int rowsDeleted = getConnection().createStatement().executeUpdate(query);
			} catch (SQLException e) {
				log.error("SQLException", e);
			} finally {
				disconnect(null, null);
			}
		}
	}

	@Autowired
	private DataSource dataSource;
	
	
	
	public List<OrderVO> getOrderList(OrderFilterVO orderFilterVO) {
		
		final List<OrderVO> resultList = new ArrayList<OrderVO>();
		if(orderFilterVO == null){
			return resultList;
		}
		
		final String query = prepareQuery(orderFilterVO);

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				resultList.add(extractOrder(rs));
			} 
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}

	private String prepareQuery(final OrderFilterVO orderFilterVO) {
		
		final StringBuilder query = new StringBuilder(
				ORDER_QUERY + 
				" where " +
				"	true "
		);
		
		if(orderFilterVO.getPhrase() != null){
			query.append(" and  ")
				.append("(")
					.append(" city like '%" + orderFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append(" address like '%" + orderFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append(" name like '%" + orderFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append( "surname like '%" + orderFilterVO.getPhrase() + "%'")
				.append(")");
		} else {
			query.append(orderFilterVO.getCity() != null   ?  " and  city='" + orderFilterVO.getCity() + "'" : "")
				.append(orderFilterVO.getStatus() != null ?  " and status='" + orderFilterVO.getStatus() + "'" : "" )
				.append(orderFilterVO.getDateFrom() != null ?  " and realizationDate >= '" + orderFilterVO.getDateFrom() + "'" : "" )
				.append(orderFilterVO.getDateTo() != null ?  " and realizationDate <= '" + orderFilterVO.getDateTo() + "'" : "" );
		
		}
		query.append(" order by realizationDate desc, orderId desc ");
		return query.toString();
	}


	public String save(OrderVO orderVO, String userName) throws DaoException {
		if(orderVO.getOrderId() != null && !"".equals(orderVO.getOrderId())){
			return update(orderVO, userName);
		} else {
			return create(orderVO, userName);
		}
	}

	private String create( final OrderVO orderVO, final String userName ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("insert into Orders(status,orderType,issueDate,name,surname,address,city,bundle,serialNumber," +
							" realizationDate,solution,comments,additionalComments,userId, phone, problemDescription) " +
							"values("
							+ "'" + orderVO.getStatus().getIdn() + "', " 
							+ "'" + orderVO.getOrderType().getIdn() + "', "
							+ (orderVO.getIssueDate()!=null ? "'"+orderVO.getIssueDate()+"'" : "null") + ", "
							+ "'" + orderVO.getName() + "', " 
							+ "'" + orderVO.getSurname() + "', "
							+ "'" + orderVO.getAddress() + "', "
							+ "'" + orderVO.getCity() + "', "
							+ "'" + orderVO.getBundle() + "', "
							+ "'" + orderVO.getSerialNumber() + "', "
							+ (orderVO.getRealizationDate()!=null ? "'"+orderVO.getRealizationDate()+"'" : "null") + ", "
							+ "'" + orderVO.getSolution() + "', "
							+ "'" + orderVO.getComments() + "', "
							+ "'" + orderVO.getAdditionalComments() + "', "
							+ "'" + userName + "', "
							+ "'" + orderVO.getPhone() + "', "
							+ "'" + orderVO.getProblemDescription() + "')");
			
		} catch (SQLException e) {
			throw new DaoException("OrderDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}
	
	private String update(  final OrderVO orderVO, final String userName ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("UPDATE Orders SET " +
							"status = '" + orderVO.getStatus().getIdn() + "', " +
							"orderType = '" + orderVO.getOrderType().getIdn() + "', " +
							"issueDate = " + (orderVO.getIssueDate()!=null ? "'"+orderVO.getIssueDate()+"'" : "null") + ", " + 
							"name = '" + orderVO.getName() + "', " +
							"surname = '" + orderVO.getSurname() + "', " +
							"address = '" + orderVO.getAddress() + "', " +
							"city = '" + orderVO.getCity() + "', " +
							"bundle = '" + orderVO.getBundle() + "', " +
							"serialNumber = '" + orderVO.getSerialNumber() + "', " +
							"realizationDate = " + (orderVO.getRealizationDate()!=null ? "'"+orderVO.getRealizationDate()+"'" : "null") + ", " +
							"solution = '" + orderVO.getSolution() + "', " +
							"comments = '" + orderVO.getComments() + "', " + 
							"additionalComments = '" + orderVO.getAdditionalComments() + "', " +
							"phone = '" + orderVO.getPhone() + "', " +
							"problemDescription = '" + orderVO.getProblemDescription() + "' " +
							"WHERE orderId = " + orderVO.getOrderId() );
							
		} catch (SQLException e) {
			throw new DaoException("OrderDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	public ReportDataVO getReportData(String worksheetName, Date dateFrom, Date dateTo, String cityIdn) throws DaoException {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			final ReportDataVO result = new ReportDataVO();
			String dataQuery = "select o.orderId,o.status,o.orderType,o.issueDate,o.name,o.surname,o.address, "
				+ " c.cityDesc,o.bundle,o.serialNumber,o.realizationDate,o.solution,o.comments,o.additionalComments,o.userId, "
				+ " o.phone, o.problemDescription, o.comments "
				+ " from Orders o "
				+ " left join city c on o.city=c.cityIdn "
				+ " where 1 ";
			if(dateFrom != null){
				dataQuery = dataQuery.concat(" AND o.realizationDate >='" + dateFrom + "'");
			}
			if(dateTo != null){
				dataQuery = dataQuery.concat(" AND o.realizationDate <='" + dateTo + "'");
			}
			if(cityIdn != null){
				dataQuery = dataQuery.concat(" AND o.city ='" + cityIdn + "'");
			}
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(dataQuery);
			
			//TODO: dodaæ dynamiczny zapis metadanych instalacji, komorek niestandardowych do ReportDataVO.reportDetailsList
			
			int xlsRowNumber = 1, sheetRowsDelimiter = 39;
			DataSheet dataSheet = new DataSheet();
			dataSheet.setDataRowsOffset(3);
			while(rs.next()){
				
				final List<DataCell> row = extractDataRow(worksheetName, rs, xlsRowNumber++);
				dataSheet.getRows().add(row);
				
				if(sheetRowsDelimiter-- == 0){
					sheetRowsDelimiter = 39;
//					dataSheet.addDetailsCell(1,2, getCityInfo(cityIdn));
//					dataSheet.addDetailsCell(1,3, getDateInfo(dateFrom, dateTo));
					result.addDataSheet(dataSheet);
					dataSheet = new DataSheet();
					dataSheet.setDataRowsOffset(3);
				}
			}
			result.addDataSheet(dataSheet);
			
			return result;
			
		} catch (SQLException e) {
			throw new DaoException("OrderDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
	}
	
	private List<DataCell> extractDataRow(final String worksheetName, final ResultSet rs, int xlsRowNumber) throws SQLException {
		
		//orderId,status,orderType,issueDate,name,surname,address,city,bundle,serialNumber,realizationDate,solution,comments,additionalComments,userId, phone, problemDescription
		
		final List<DataCell> row = new ArrayList<DataCell>();
		row.add(new DataCell(xlsRowNumber, CellType.INT));
		final Status status = Status.getValueByIdn(rs.getString(2));
		if(status != null){
			row.add(new DataCell(status.getDesc(),CellType.TEXT)); //status
		}
		final OrderType orderType = OrderType.getValueByIdn(rs.getString(3));
		if(orderType != null) {
			row.add(new DataCell(orderType.getDesc(),CellType.TEXT)); //orderType
		}
		row.add(new DataCell(rs.getDate(4),CellType.DATE)); // data wystawienie zlecenia
		row.add(new DataCell(rs.getString(16),CellType.TEXT)); // phone
		row.add(new DataCell(Translator.join(rs.getString(6),rs.getString(5)),CellType.TEXT)); // nazwisko imie
		row.add(new DataCell(rs.getString(7),CellType.TEXT));
		row.add(new DataCell(rs.getString(8),CellType.TEXT)); //miejscowosc
		row.add(new DataCell(rs.getString(9),CellType.TEXT)); //pakiet
		row.add(new DataCell(rs.getDate(11),CellType.DATE)); //data realizacji
		row.add(new DataCell(rs.getString(15),CellType.TEXT)); //monter
		row.add(new DataCell(rs.getString(17),CellType.TEXT)); //problemDescription
		row.add(new DataCell(rs.getString(18),CellType.TEXT)); //comments
		
		return row;
	}
	
	public void closeOrder(String orderId) {

		final String query = " UPDATE Orders SET status='" + Status.DONE.getIdn() + "' WHERE orderId = " + orderId;
		
		try {
			final Statement stmt = getConnection().createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			log.error("SQLException", e);
		}
	}
}
