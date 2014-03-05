package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.InvoceProductCorrectionVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;

@Component
public class InvoiceProductDAO extends BaseDao {

	@Autowired
	private ProductDAO productDao;
	
	@Autowired
	private InvoiceProductCorrectionDAO invoiceProductCorrectionDao;
	
	public void insertInvoiceProducts(
			final List<InvoceProductVO> invoiceProducts,
			final String invoiceId, final Statement stmt) throws SQLException {

		for (final InvoceProductVO productVO : invoiceProducts) {
			insertInvoiceProduct(productVO, invoiceId, stmt);
		}
	}

	private void insertInvoiceProduct(final InvoceProductVO productVO,
			final String invoiceId, final Statement stmt)
			throws SQLException {
		stmt.executeUpdate("insert into InvoceProducts(invoiceId, productId, quantity, netSumAmount, vatAmount, grossSumAmount) "
				+ "values ("
				+ invoiceId
				+ ", "
				+ productVO.getProductDefinition().getProductId()
				+ ", "
				+ productVO.getQuantity()
				+ ", "
				+ productVO.getNetSumAmount()
				+ ","
				+ productVO.getVatAmount()
				+ ","
				+ productVO.getGrossSumAmount() + ")");
	}
	
	public void updateInvoiceProduct(
			final InvoceProductVO invoiceProduct,
			final String invoiceId, final Statement stmt) throws SQLException {
		
			stmt.executeUpdate("UPDATE InvoceProducts SET "
					+ " invoiceId=" + invoiceId 
					+ ", productId="+ invoiceProduct.getProductDefinition().getProductId()
					+ ", quantity="+ invoiceProduct.getQuantity()
					+ ", netSumAmount=" + invoiceProduct.getNetSumAmount()
					+ ", vatAmount=" + invoiceProduct.getVatAmount()
					+ ", grossSumAmount=" + invoiceProduct.getGrossSumAmount()
					+ " WHERE invoceProductId="+ invoiceProduct.getInvoiceProductId());
	}

	public void updateInvoiceProducts(final List<InvoceProductVO> invoiceProducts,
			final String invoiceId, final Statement stmt) throws SQLException {
		
		clearInvoiceProducts(invoiceProducts, invoiceId, stmt);
		for (final InvoceProductVO product : invoiceProducts) {
			if(product.getInvoiceProductId() != null){
				updateInvoiceProduct(product, invoiceId, stmt);
			} else {
				insertInvoiceProduct(product, invoiceId, stmt);
			}
		}
	}

	public void clearInvoiceProducts(final List<InvoceProductVO> invoiceProducts, 
			final String invoiceId, final Statement stmt)throws SQLException {
		String query = "DELETE FROM InvoceProducts WHERE invoiceId=%s ";
		final String invoiceProductsIds = Joiner.on(",").skipNulls().join(getInvoiceProductsId(invoiceProducts));
		if(!invoiceProducts.isEmpty()&& StringUtils.isNotEmpty(invoiceProductsIds)){
			query = query.concat(String.format(" AND invoceProductId NOT IN (%s)", invoiceProductsIds));
		}
		stmt.executeUpdate(String.format(query, invoiceId));
	}

	private Collection<String> getInvoiceProductsId(List<InvoceProductVO> invoiceProducts) {
		return Collections2.transform(invoiceProducts, new Function<InvoceProductVO, String>(){
			@Override
			public String apply(InvoceProductVO invoiceProduct) {
				return invoiceProduct.getInvoiceProductId();
			}
		});
	}

	public List<InvoceProductVO> getInvoiceProducts(final String invoiceId) throws SQLException {
		
		final List<InvoceProductVO> result = new ArrayList<InvoceProductVO>();
		if (invoiceId != null && !"".equals(invoiceId)) {
			final String query = String
					.format(" SELECT invoceProductId, quantity, netSumAmount, vatAmount, grossSumAmount, productId "
							+ " FROM InvoceProducts WHERE invoiceId = %s",
							invoiceId);
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					final InvoceProductVO invoiceProduct = extractInvoiceProduct(rs);
					invoiceProduct.setCorrection(
							getInvoiceProductCorrection(invoiceProduct.getInvoiceProductId()));
					result.add(invoiceProduct);
				}
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	private InvoceProductCorrectionVO getInvoiceProductCorrection(
			final String invoiceProductId) throws SQLException {
		return invoiceProductCorrectionDao.getInvoiceProductCorrection(invoiceProductId);
	}

	private InvoceProductVO extractInvoiceProduct(ResultSet rs) throws SQLException {
		final InvoceProductVO result = new InvoceProductVO();
		result.setInvoiceProductId(rs.getString(1));
		result.setQuantity(rs.getString(2));
		result.setNetSumAmount(rs.getString(3));
		result.setVatAmount(rs.getString(4));
		result.setGrossSumAmount(rs.getString(5));
		final String productId = rs.getString(6);
		result.setProductDefinition(productDao.getProductById(productId));
		return result;
	}
}
