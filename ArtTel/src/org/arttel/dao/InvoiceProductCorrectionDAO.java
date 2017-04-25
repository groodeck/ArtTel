package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.arttel.controller.vo.InvoceProductCorrectionVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.exception.DaoException;
import org.arttel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class InvoiceProductCorrectionDAO extends BaseDao {

	@Autowired
	private ProductService productService;

	private static final String DELETE_PRODUCT_CORRECTION_QUERY =
			"DELETE FROM InvoceProductCorrection WHERE invoiceId = %s";
	private static final String INVOICE_PRODUCT_CORRECTION_QUERY =
			"SELECT "
					+ "invoceProductCorrectionId, invoceProductId, productId, quantity, quantityDiff, netSumAmount, "
					+ "vatAmount, grossSumAmount, netSumAmountDiff, vatAmountDiff, grossSumAmountDiff, invoiceId "
					+ "FROM InvoceProductCorrection WHERE true ";

	public void deleteInvoiceProductsCorrection(final List<InvoceProductVO> invoiceProducts,
			final String invoiceId, final Statement stmt) throws SQLException {

		final String deleteQuery = String.format(DELETE_PRODUCT_CORRECTION_QUERY, invoiceId);
		stmt.executeUpdate(deleteQuery);
	}

	private InvoceProductCorrectionVO extractInvoiceProductCorrection(final ResultSet rs)
			throws SQLException {
		final InvoceProductCorrectionVO result = new InvoceProductCorrectionVO();
		result.setInvoceProductCorrectionId(rs.getString(1));
		result.setInvoiceProductId(rs.getString(2));
		final String productId = rs.getString(3);
		final ProductVO productDefinition = productService.getProductById(productId);
		result.setProductDefinition(productDefinition);
		result.setQuantity(rs.getString(4));
		result.setQuantityDiff(rs.getString(5));
		result.setNetSumAmount(rs.getString(6));
		result.setVatAmount(rs.getString(7));
		result.setGrossSumAmount(rs.getString(8));
		result.setNetSumAmountDiff(rs.getString(9));
		result.setVatAmountDiff(rs.getString(10));
		result.setGrossSumAmountDiff(rs.getString(11));
		result.setInvoiceId(rs.getString(12));
		return result;
	}

	public List<InvoceProductCorrectionVO> getCorrectionAddedProducts(final String invoiceId)
			throws DaoException {
		final List<InvoceProductCorrectionVO> result = Lists.newArrayList();
		if (invoiceId != null && !"".equals(invoiceId)) {
			final String query = getCorrectionByInvoiceIdQuery(invoiceId);
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next()){
					result.add(extractInvoiceProductCorrection(rs));
				}
			} catch (final SQLException e) {
				throw new DaoException("InvoiceProductCorrectionDAO exception", e);
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	private String getCorrectionByInvoiceIdQuery(final String invoiceId) {
		return String.format(INVOICE_PRODUCT_CORRECTION_QUERY
				.concat(" AND invoceProductId IS NULL " +
						" AND invoiceId = %s"), invoiceId);
	}

	private String getCorrectionByInvoiceProductIdQuery(final String invoiceProductId) {
		return String.format(INVOICE_PRODUCT_CORRECTION_QUERY
				.concat(" AND invoceProductId = %s"), invoiceProductId);
	}

	public InvoceProductCorrectionVO getInvoiceProductCorrection(final String invoiceProductId) {
		InvoceProductCorrectionVO result = null;
		if (invoiceProductId != null && !"".equals(invoiceProductId)) {
			final String query = getCorrectionByInvoiceProductIdQuery(invoiceProductId);
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if(rs.next()){
					result = extractInvoiceProductCorrection(rs);
				}
			} catch (final SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	public void insertInvoiceProductsCorrection(final List<InvoceProductVO> invoiceProducts,
			final String invoiceId, final Statement stmt) throws SQLException {

		for (final InvoceProductVO productVO : invoiceProducts) {
			final InvoceProductCorrectionVO productCorrection = productVO.getCorrection();
			stmt.executeUpdate("insert into invoceProductCorrection(invoceProductId, productId, invoiceId, quantity, quantityDiff, "
					+ "netSumAmount, netSumAmountDiff, vatAmount, vatAmountDiff, grossSumAmount, grossSumAmountDiff) "
					+ "values ("
					+ productCorrection.getInvoiceProductId()
					+ ", "
					+ productCorrection.getProductDefinition().getProductId()
					+ ", "
					+ invoiceId
					+ ", "
					+ productCorrection.getQuantity()
					+ ", "
					+ productCorrection.getQuantityDiff()
					+ ", "
					+ productCorrection.getNetSumAmount()
					+ ", "
					+ productCorrection.getNetSumAmountDiff()
					+ ","
					+ productCorrection.getVatAmount()
					+ ","
					+ productCorrection.getVatAmountDiff()
					+ ","
					+ productCorrection.getGrossSumAmount()
					+ ","
					+ productCorrection.getGrossSumAmountDiff()
					+ ")");
		}
	}

	public void updateInvoiceProductsCorrection(final List<InvoceProductVO> invoiceProducts,
			final String invoiceId, final Statement stmt) throws SQLException {

		deleteInvoiceProductsCorrection(invoiceProducts, invoiceId, stmt);
		insertInvoiceProductsCorrection(invoiceProducts, invoiceId, stmt);
	}

}
