package org.arttel.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.CorrectionVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.exception.DaoException;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class CorrectionDAO extends BaseDao {

	@Autowired
	private InvoiceProductCorrectionDAO invoiceProductCorrectionDao;

	private static final String DELETE_CORRECTION_QUERY = "DELETE c.*, ipc.* " +
			"	FROM Correction c " +
			"		LEFT JOIN InvoceProductCorrection ipc ON c.invoiceId=ipc.invoiceId" +
			"	WHERE c.correctionId = %s";

	private static final String CORRECTION_QUERY = " SELECT "
			+ "c.correctionId, " + "c.correctionNumber, " + "c.invoiceId, "
			+ "c.createDate, " + "c.netAmount, " + "c.vatAmount, "
			+ "c.netAmountDiff, " + "c.vatAmountDiff, " + "c.comments, "
			+ "c.user, " + "c.paid, " + "c.paymentType, " + "c.paidWords, " + "c.paymentDate "
			+ " FROM Correction c " + " WHERE true ";

	private static final String LAST_CORRECTION_NUMBER_QUERY =
			"SELECT correctionNumber FROM Correction c "
					+ "JOIN Invoice i on c.invoiceId = i.invoiceId "
					+ "JOIN Seller s on i.sellerId = s.sellerId "
					+ "JOIN User u on s.user = u.userName "
					+ "WHERE c.createDate > '%s' "
					+ "AND u.userName = '%s' "
					+ "ORDER BY c.correctionId ";

	public String create(final InvoiceVO invoiceVO, final String userName)
			throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			final String correctionId = insertCorrection(invoiceVO, userName, stmt);
			final InvoiceProductCorrectionDAO invoiceProductCorrectionDAO =
					invoiceProductCorrectionDao;
			invoiceProductCorrectionDAO.insertInvoiceProductsCorrection(
					invoiceVO.getInvoiceProducts(),invoiceVO.getInvoiceId(), stmt);
			return correctionId;
		} catch (final SQLException e) {
			throw new DaoException("InvoicesCorrectionDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
	}

	public void deleteCorrectionById(final String correctionId) throws DaoException {
		if (correctionId != null && !"".equals(correctionId)) {
			Statement statement = null;
			try {
				statement = getConnection().createStatement();
				statement.executeUpdate(String.format(DELETE_CORRECTION_QUERY, correctionId));
			} catch (final SQLException e) {
				throw new DaoException("CorrectionDAO exception", e);
			} finally {
				disconnect(statement, null);
			}
		}
	}

	private CorrectionVO extractCorrection(final ResultSet rs)
			throws SQLException {
		if (rs.next()) {
			final CorrectionVO correction = new CorrectionVO();
			correction.setCorrectionId(rs.getString(1));
			correction.setCorrectionNumber(rs.getString(2));
			correction.setInvoiceId(rs.getString(3));
			correction.setCreateDate(rs.getDate(4));
			correction.setNetAmount(rs.getString(5));
			correction.setVatAmount(rs.getString(6));
			final String netAmountDiff = rs.getString(7);
			correction.setNetAmountDiff(netAmountDiff);
			final String vatAmountDiff = rs.getString(8);
			correction.setVatAmountDiff(vatAmountDiff);
			correction.setComments(rs.getString(9));
			correction.setUser(rs.getString(10));
			correction.setPaid(rs.getString(11));
			final String paymentTypeIdn = rs.getString(12);
			if (StringUtils.isNotEmpty(paymentTypeIdn)) {
				correction.setPaymentType(PaymentType
						.getValueByIdn(paymentTypeIdn));
			}
			correction.setPaidWords(rs.getString(13));
			correction.setGrossAmountDiff(Translator.getDecimal(netAmountDiff)
					.add(Translator.getDecimal(vatAmountDiff)).toPlainString());
			correction.setPaymentDate(rs.getDate(14));
			return correction;
		} else {
			return null;
		}
	}

	public CorrectionVO getCorrectionById(final String correctionId) throws DaoException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			final String query = getCorrectionByIdQuery(correctionId);
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			return extractCorrection(rs);
		} catch (final SQLException e) {
			throw new DaoException("CorrectionDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
	}

	private String getCorrectionByIdQuery(final String correctionId) {
		return CORRECTION_QUERY.concat(String.format(" and c.correctionId = %s",
				correctionId));
	}

	private String getCorrectionByInvoiceQuery(final String invoiceId) {
		return CORRECTION_QUERY.concat(String.format(" and c.invoiceId = %s",
				invoiceId));
	}

	public CorrectionVO getCorrectionForInvoice(final String invoiceId)
			throws DaoException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			final String query = getCorrectionByInvoiceQuery(invoiceId);
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			return extractCorrection(rs);
		} catch (final SQLException e) {
			throw new DaoException("CorrectionDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
	}

	private String getCorrectionId(final Statement stmt) throws SQLException {
		final String result;
		final ResultSet rs = stmt
				.executeQuery("SELECT MAX(correctionId) FROM Correction");
		if (rs.next()) {
			result = rs.getString(1);
		} else {
			result = null;
		}
		return result;
	}

	public List<String> getCorrectionNumbers(final java.util.Date date, final String userName)
			throws DaoException {
		final List<String> results = Lists.newArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(String.format(LAST_CORRECTION_NUMBER_QUERY, new Date(date.getTime()), userName));
			while (rs.next()) {
				results.add(rs.getString(1));
			}
		} catch (final SQLException e) {
			throw new DaoException("CorrectionDAO exception", e);
		} finally {
			disconnect(stmt, rs);
		}
		return results;
	}

	private String insertCorrection(final InvoiceVO invoiceVO,
			final String userName, final Statement stmt) throws SQLException {
		final CorrectionVO correction = invoiceVO.getCorrection();
		final String insertQuery = "INSERT INTO Correction(correctionNumber, invoiceId, createDate, netAmount, "
				+ "vatAmount, netAmountDiff, vatAmountDiff, comments, paid, paymentType, user, paidWords, paymentDate) VALUES ('"
				+ correction.getCorrectionNumber()
				+ "', "
				+ correction.getInvoiceId()
				+ ", "
				+ (correction.getCreateDate() != null ? "'"
						+ correction.getCreateDate() + "'" : "null")
						+ ","
						+ correction.getNetAmount()
						+ ","
						+ correction.getVatAmount()
						+ ","
						+ correction.getNetAmountDiff()
						+ ","
						+ correction.getVatAmountDiff()
						+ ",'"
						+ correction.getComments()
						+ "',"
						+ correction.getPaid()
						+ ",'"
						+ correction.getPaymentType().getIdn()
						+ "','"
						+ userName
						+ "','" + correction.getPaidWords()
						+ "',"
						+ (correction.getPaymentDate() != null ? "'"
								+ correction.getPaymentDate() + "'" : "null")+ ")";

		stmt.executeUpdate(insertQuery);

		return getCorrectionId(stmt);
	}

	public String save(final InvoiceVO invoiceVO, final String userName)
			throws DaoException {
		final String correctionId = invoiceVO.getCorrection().getCorrectionId();
		if (correctionId != null && !"".equals(correctionId)) {
			return update(invoiceVO, userName);
		} else {
			return create(invoiceVO, userName);
		}
	}

	public void setInvoiceStatus(final int invoiceId, final InvoiceStatus newStatus)
			throws DaoException {
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate(String.format(
					"UPDATE Invoice SET InvoiceStatus='%s' WHERE invoiceId=%s",
					newStatus.getIdn(), invoiceId));
		} catch (final SQLException e) {
			throw new DaoException("InvoiceDAO exception", e);
		} finally {
			disconnect(stmt, null);
		}
	}

	private String update(final InvoiceVO invoice, final String userName)
			throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			final CorrectionVO correction = invoice.getCorrection();
			updateCorrection(correction, userName, stmt);

			final List<InvoceProductVO> invoiceProducts = invoice.getInvoiceProducts();
			invoiceProductCorrectionDao.updateInvoiceProductsCorrection(invoiceProducts,
					invoice.getInvoiceId(), stmt);

		} catch (final SQLException e) {
			throw new DaoException("InvoiceDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	private void updateCorrection(final CorrectionVO correction, final String userName,
			final Statement stmt) throws SQLException {
		final String query = "UPDATE correction SET "
				+ "correctionNumber = '" + correction.getCorrectionNumber()
				+ "', invoiceId = " + correction.getInvoiceId()
				+ ", createDate = "
				+ (correction.getCreateDate() != null ? "'" + correction.getCreateDate() + "'" : "null")
				+ ", netAmount = " + correction.getNetAmount()
				+ ", vatAmount = " + correction.getVatAmount()
				+ ", netAmountDiff = " + correction.getNetAmountDiff()
				+ ", vatAmountDiff = " + correction.getVatAmountDiff()
				+ ", comments = '" + correction.getComments()
				+ "', paid = " + correction.getPaid()
				+ ", paymentType = '" + correction.getPaymentType().getIdn()
				+ "', paidWords = '" + correction.getPaidWords()
				+ "', paymentDate = " + (correction.getPaymentDate() != null ? "'" + correction.getPaymentDate() + "'" : "null")
				+ ", user = '" + userName
				+ "' WHERE invoiceId = " + correction.getInvoiceId();

		stmt.executeUpdate(query);
	}
}
