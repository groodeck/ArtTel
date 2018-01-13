package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.SellerVO;
import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.entity.SellerBankAccount;
import org.arttel.view.ComboElement;
import org.arttel.view.EmptyComboElement;
import org.springframework.stereotype.Repository;

@Repository
@org.springframework.transaction.annotation.Transactional
public class SellerDAO extends BaseDao {

	@PersistenceContext
	private EntityManager em;

	private static final String SELLER_QUERY =
			"SELECT sellerId, sellerDesc, nip, city, street, house, appartment, zip, GenerateDealingOnInvoiceSettle "
					+ "FROM Seller "
					+ "WHERE true ";

	private SellerVO extractSeller(final ResultSet rs) throws SQLException {
		final SellerVO seller = new SellerVO();
		seller.setSellerId(rs.getString(1));
		seller.setSellerDesc(rs.getString(2));
		seller.setNip(rs.getString(3));
		seller.setCity(rs.getString(4));
		seller.setStreet(rs.getString(5));
		seller.setHouse(rs.getString(6));
		seller.setAppartment(rs.getString(7));
		seller.setZip(rs.getString(8));
		seller.setGenerateDealingOnInvoiceSettle(Boolean.TRUE.equals(rs.getBoolean(9)));
		return seller;
	}

	public List<? extends ComboElement> getSellerBankAccounts(final String userName) {

		final List<SellerBankAccount> bankAccounts = em.createQuery(
				"select s.bankAccounts from Seller s where s.user = :user")
				.setParameter("user", userName)
				.getResultList();

		final List<ComboElement> resultList = new ArrayList<ComboElement>();
		for(final SellerBankAccount bankAccount : bankAccounts){
			resultList.add(new SimpleComboElement(bankAccount.getSellerBankAccountId().toString(),
					String.format("%s (%s)", bankAccount.getAccountNumber(), bankAccount.getBankName())));
		}
		return resultList;
	}

	public SellerVO getSellerById(final String sellerId) {
		SellerVO result = null;
		if (StringUtils.isNotEmpty(sellerId)) {
			final String query = SELLER_QUERY.concat(
					String.format(" AND sellerId = %s", sellerId));
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					result = extractSeller(rs);
				}
			} catch (final SQLException e) {
				log.error("SellerDAO SQLException", e);
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	public List<ComboElement> getSellerDictionary(final boolean withEmptyOption, final String user) {
		final List<ComboElement> sellerList = new ArrayList<ComboElement>();
		if(withEmptyOption){
			sellerList.add(0, new EmptyComboElement());
		}
		sellerList.addAll(getSellerList(user));
		return sellerList;
	}

	public List<SellerVO> getSellerList(final String user) {
		final List<SellerVO> sellerList = new ArrayList<SellerVO>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			final String query = SELLER_QUERY.concat(
					String.format(" AND user = '%s'", user));
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				sellerList.add(extractSeller(rs));
			}
		} catch (final SQLException e) {
			log.error("SellerDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return sellerList;
	}
}
