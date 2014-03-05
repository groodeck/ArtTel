package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.filter.ProductFilterVO;
import org.arttel.dictionary.UnitType;
import org.arttel.dictionary.VatRate;
import org.arttel.exception.DaoException;
import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class ProductDAO extends BaseDao {

	private static final String PRODUCT_QUERY = 
			"SELECT productId, productDescription, netPrice, vat, unitType, comments " +
			"FROM Product " +
			"WHERE true ";
	
	public String save(final ProductVO productVO, final String userName) {
		if(StringUtils.isNotEmpty(productVO.getProductId())){
			return update(productVO, userName);
		} else {
			return create(productVO, userName);
		}
	}
	
	private String update(final ProductVO productVO, final String userName) {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			final String query = 
					"UPDATE Product SET "
					+ "productDescription = '" + productVO.getProductDescription()
					+ "', netPrice = " + productVO.getNetPrice()
					+ ", vat = " + productVO.getVatRate().getIdn()
					+ ", unitType = '" + productVO.getUnitType().getIdn()
					+ "', comments = '" + productVO.getComments()
					+ "' WHERE productId = " + productVO.getProductId();
			System.out.println(query);
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			log.error("ProductDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	public String create(final ProductVO productVO, final String user) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate("insert into Product (productDescription, netPrice, vat, unitType, comments, user) values(" +
					"'" + productVO.getProductDescription() + "',"+
					productVO.getNetPrice() + "," +
					productVO.getVatRate().getIdn() + ",'" +
					productVO.getUnitType().getIdn() + "','" +
					productVO.getComments() + "','" +
					user +
					"')");
		} catch (SQLException e) {
			log.error("ProductDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return null;
	}

	public List<ProductVO> getProductList(final ProductFilterVO productFilterVO) { 
		final List<ProductVO> resultList = new ArrayList<ProductVO>();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			final String query = prepareQuery(productFilterVO);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				resultList.add(extractProduct(rs));
			}
		} catch (SQLException e) {
			log.error("ProductDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}
	
	private String prepareQuery(final ProductFilterVO productFilterVO) {

		final StringBuilder query = new StringBuilder(PRODUCT_QUERY);

		if(productFilterVO != null){
			final String productName = productFilterVO.getName();
			if(StringUtils.isNotEmpty(productName)){
				query.append(" AND productDescription LIKE '%" + productName + "%'");
			}
			query.append(" AND user = '" + productFilterVO.getUser() + "'");
		}
		query.append(" ORDER BY productId DESC ");

		return query.toString();
	}
	
	private ProductVO extractProduct(ResultSet rs) throws SQLException {
		final ProductVO productVO = new ProductVO();
		productVO.setProductId(rs.getString(1));
		productVO.setProductDescription(rs.getString(2));
		productVO.setNetPrice(rs.getString(3));
		productVO.setVatRate(VatRate.getValueByIdn(rs.getString(4)));
		productVO.setUnitType(UnitType.getValueByIdn(rs.getString(5)));
		productVO.setComments(rs.getString(6));
		return productVO;
	}

	public List<ComboElement> getProductDictionary(final boolean withEmptyOption, final String user) {
		final List<ComboElement> productList = Lists.newArrayList();
		if(withEmptyOption){
			productList.add(0, new EmptyComboElement());
		}
		final ProductFilterVO filter = new ProductFilterVO();
		filter.setUser(user);
		productList.addAll(getProductList(filter ));
		return productList;
	}

	public ProductVO getProductById(String productId) {
		ProductVO result = null;
		if (StringUtils.isNotEmpty(productId)) {
			final String query = PRODUCT_QUERY.concat(String.format(
					" AND productId = %s", productId));
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					result = extractProduct(rs);
				}
			} catch (SQLException e) {
				log.error("ProductDAO SQLException", e);
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	public void deleteProductById(String productId) throws DaoException {
		
		if (productId != null && !"".equals(productId)) {
			final String query = String.format("DELETE FROM Product WHERE productId = %s", productId);
			Statement stmt = null;
			try {
				if(!checkProductAlreadyUsed(productId)){
					stmt = getConnection().createStatement();
					stmt.executeUpdate(query);
				}
			} catch (SQLException e) {
				throw new DaoException("ProductDAO exception", e);
			} finally {
				disconnect(stmt, null);
			}
		}
	}

	private boolean checkProductAlreadyUsed(final String productId) throws SQLException {
		final Statement stmt = getConnection().createStatement();
		final ResultSet rs = stmt.executeQuery(
				"SELECT count(*) FROM InvoceProducts WHERE productId=" + productId);
		if(rs.next()){
			long productAssignmentRows = rs.getLong(1);
			if(productAssignmentRows > 0){
				return true;
			}
		}
		return false;
	}
}
