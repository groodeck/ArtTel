package org.arttel.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.CorrectionVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDAO extends BaseDao {

	@Autowired
	private InvoiceProductDAO invoiceProductDao;
	
	@Autowired
	private CorrectionDAO correctionDao;

	public String create(final InvoiceVO invoiceVO, final String userName)
			throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			final String invoiceId = insertInvoice(invoiceVO, userName, stmt);
			invoiceProductDao.insertInvoiceProducts(invoiceVO.getInvoiceProducts(), invoiceId, stmt);
		} catch (SQLException e) {
			throw new DaoException("InvoicesDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	private String insertInvoice(InvoiceVO invoiceVO, String userName, Statement stmt) throws SQLException {
		String updateQuery = "INSERT INTO Invoice(invoiceNumber, sellerId, clientId, createDate, signatureDate, paymentDate, netAmount, " +
				"vatAmount, comments, paid, paymentType, user, paidWords, invoiceStatus, additionalComments) VALUES ('"
				+ invoiceVO.getNumber()
				+ "', "
				+ invoiceVO.getSellerId()
				+ ", "
				+ invoiceVO.getClientId()
				+ ", "
				+ (invoiceVO.getCreateDate()!=null ? "'"+invoiceVO.getCreateDate()+"'" : "null")
				+ "," 
				+ (invoiceVO.getSignDate()!=null ? "'"+invoiceVO.getSignDate()+"'" : "null")
				+ ","
				+ (invoiceVO.getPaymentDate()!=null ? "'"+invoiceVO.getPaymentDate()+"'" : "null")
				+ ","
				+ invoiceVO.getNetAmount()
				+ ","
				+ invoiceVO.getVatAmount()
				+ ",'"
				+ invoiceVO.getComments()
				+ "',"
				+ invoiceVO.getPaid()
				+ ",'"
				+ invoiceVO.getPaymentType().getIdn()
				+ "','"
				+ userName
				+ "','"
				+ invoiceVO.getPaidWords()
				+ "','"
				+ invoiceVO.getStatus().getIdn()
				+ "','"
				+ invoiceVO.getAdditionalComments()
				+ "')";

		stmt.executeUpdate(updateQuery);

		return getInvoiceId(stmt);
	}

	private String getInvoiceId(Statement stmt) throws SQLException {
		final String result;
		final ResultSet rs = stmt
				.executeQuery("SELECT MAX(invoiceId) FROM Invoice");
		if (rs.next()) {
			result = rs.getString(1);
		} else {
			result = null;
		}
		return result;
	}

	public String save(final InvoiceVO invoiceVO, String userName)
			throws DaoException {
		if (invoiceVO.getInvoiceId() != null && !"".equals(invoiceVO.getInvoiceId())) {
			return update(invoiceVO, userName);
		} else {
			return create(invoiceVO, userName);
		}
	}

	private String update(final InvoiceVO invoiceVO, final String userName)
			throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			final String query = 
					"UPDATE invoice SET "
					+ "invoiceNumber = '" + invoiceVO.getNumber()
					+ "', clientId = " + invoiceVO.getClientId()
					+ ", sellerId = " + invoiceVO.getSellerId()
					+ ", createDate = " + (invoiceVO.getCreateDate()!=null ? "'"+invoiceVO.getCreateDate()+"'" : "null")
					+ ", signatureDate = " + (invoiceVO.getSignDate()!=null ? "'"+invoiceVO.getSignDate()+"'" : "null")
					+ ", paymentDate = " + (invoiceVO.getPaymentDate()!=null ? "'"+invoiceVO.getPaymentDate()+"'" : "null")
					+ ", netAmount = " + invoiceVO.getNetAmount()
					+ ", vatAmount = " + invoiceVO.getVatAmount()
					+ ", comments = '" + invoiceVO.getComments()
					+ "', additionalComments = '" + invoiceVO.getAdditionalComments()
					+ "', paid = " + invoiceVO.getPaid()
					+ ", paymentType = '" + invoiceVO.getPaymentType().getIdn()
					+ "', paidWords = '" + invoiceVO.getPaidWords()
					+ "', invoiceStatus = '" + invoiceVO.getStatus().getIdn()
					+ "', user = '" + userName
					+ "' WHERE invoiceId = " + invoiceVO.getInvoiceId();

			stmt.executeUpdate(query);
			invoiceProductDao.updateInvoiceProducts(invoiceVO.getInvoiceProducts(), 
					invoiceVO.getInvoiceId(), stmt);
			
		} catch (SQLException e) {
			throw new DaoException("InvoiceDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	public List<InvoiceVO> getInvoiceList(final InvoiceFilterVO invoiceFilterVO)
			throws DaoException {
		final List<InvoiceVO> resultList = new ArrayList<InvoiceVO>();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			final String query = prepareQuery(invoiceFilterVO);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				resultList.add(extractInvoice(rs));
			}
		} catch (SQLException e) {
			throw new DaoException("InvoiceDAO exception", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}

	private String prepareQuery(final InvoiceFilterVO invoiceFilterVO) {

		final StringBuilder query = new StringBuilder(INVOICE_QUERY);
		if(invoiceFilterVO != null){
			final String number = invoiceFilterVO.getNumber();
			if(StringUtils.isNotEmpty(number)){
				query.append(" and  i.invoiceNumber like '%"+ number +"%'");
			}
			final Date createDate = invoiceFilterVO.getCreateDate();
			if(createDate != null){
				query.append(" and  i.createDate = '"+ createDate +"'" );
			}
			query.append(" and  i.user = '"+ invoiceFilterVO.getUser() +"'" );
		}

		return query.append(" ORDER BY i.invoiceId DESC ").toString();
	}

	private static final String INVOICE_QUERY = 
			  " SELECT " 
			+ " i.invoiceId,"
			+ " i.invoiceNumber," 
			+ " i.createDate,"
			+ " i.signatureDate," 
			+ " i.paymentDate," 
			+ " i.netAmount," 
			+ " i.vatAmount," 
			+ " i.comments,"
			+ " c.clientId,"
			+ " c.clientDesc,"
			+ " i.user,"
			+ " i.paid,"
			+ " i.paymentType, "
			+ " i.paidWords, "
			+ " i.invoiceStatus, "
			+ " i.sellerId, "
			+ " i.additionalComments "
			+ " FROM Invoice i JOIN Client c on i.clientId=c.clientId"
			+ " WHERE true ";

	public InvoiceVO getInvoiceById(final String invoiceId) throws DaoException {
		InvoiceVO result = null;
		if (invoiceId != null && !"".equals(invoiceId)) {
			final String query = INVOICE_QUERY.concat(String.format(
					" and i.invoiceId = %s", invoiceId));
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					result = extractInvoice(rs);
					result.setInvoiceProducts(invoiceProductDao.getInvoiceProducts(invoiceId));
				}
			} catch (SQLException e) {
				throw new DaoException("InvoiceDAO exception", e);
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	private InvoiceVO extractInvoice(final ResultSet rs) throws SQLException {
		final InvoiceVO invoice = new InvoiceVO();
		invoice.setInvoiceId(rs.getString(1));
		invoice.setNumber(rs.getString(2));
		invoice.setCreateDate(rs.getDate(3));
		invoice.setSignDate(rs.getDate(4));
		invoice.setPaymentDate(rs.getDate(5));
		invoice.setNetAmount(rs.getString(6));
		invoice.setVatAmount(rs.getString(7));
		invoice.setComments(rs.getString(8));
		invoice.setClientId(rs.getString(9));
		invoice.setClientDesc(rs.getString(10));
		invoice.setUser(rs.getString(11));
		invoice.setPaid(rs.getString(12));
		final String paymentTypeIdn = rs.getString(13);
		if(StringUtils.isNotEmpty(paymentTypeIdn)){
			invoice.setPaymentType(PaymentType.getValueByIdn(paymentTypeIdn));
		}
		invoice.setPaidWords(rs.getString(14));
		final String invoiceStatusIdn = rs.getString(15);
		if(StringUtils.isNotEmpty(invoiceStatusIdn)){
			invoice.setStatus(InvoiceStatus.getValueByIdn(invoiceStatusIdn));
		}
		invoice.setSellerId(rs.getString(16));
		invoice.setAdditionalComments(rs.getString(17));
		return invoice;
	}

	private static final String DELETE_INVOICE_QUERY = "DELETE i.*, ip.* " +
			"	FROM Invoice i " +
			"		LEFT JOIN InvoceProducts ip ON i.invoiceId=ip.invoiceId" +
			"	WHERE i.invoiceId = %s";
	
	public void deleteInvoiceById(final String invoiceId) throws DaoException {
		if (invoiceId != null && !"".equals(invoiceId)) {
			Statement statement = null;
			try {
				removeCorrectionIfExists(invoiceId);
				statement = getConnection().createStatement();
				statement.executeUpdate(String.format(DELETE_INVOICE_QUERY, invoiceId));
			} catch (SQLException e) {
				throw new DaoException("CorrectionDAO exception", e);
			} finally {
				disconnect(statement, null);
			}
		}
	}

	private void removeCorrectionIfExists(final String invoiceId) throws DaoException {
		final CorrectionVO correction = correctionDao.getCorrectionForInvoice(invoiceId);
		if(correction != null){
			correctionDao.deleteCorrectionById(correction.getCorrectionId());;
		}
	}

	private static final String LAST_INVOICE_NUMBER_QUERY = 
			  " SELECT invoiceNumber FROM Invoice WHERE createDate > '%s' "
			  + "ORDER BY invoiceId DESC LIMIT 1";
	
	public String getLastInvoiceNumber(final java.util.Date date) throws DaoException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(String.format(LAST_INVOICE_NUMBER_QUERY, new Date(date.getTime())));
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			throw new DaoException("InvoiceDAO exception", e);
		} finally {
			disconnect(stmt, rs);
		}
		return null;
	}

	public void setInvoiceStatus(final int invoiceId, InvoiceStatus newStatus) throws DaoException {
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate(String.format(
					"UPDATE Invoice SET InvoiceStatus='%s' WHERE invoiceId=%s", newStatus.getIdn(), invoiceId));
		} catch (SQLException e) {
			throw new DaoException("InvoiceDAO exception", e);
		} finally {
			disconnect(stmt, null);
		}
	}
}
