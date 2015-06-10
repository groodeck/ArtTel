package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.arttel.controller.vo.SqueezeVO;
import org.arttel.controller.vo.filter.SqueezeFilterVO;
import org.arttel.dictionary.SqueezeStatus;
import org.arttel.exception.DaoException;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqueezeDAO extends BaseDao {

	@Autowired
	private DealingDAO dealingDao;

	private static final String SQUEEZE_QUERY =
			" SELECT s.squeezeId ,s.quantity, s.meters, s.price, d.city, d.amount, d.userId, d.comments1, d.date, d.income, c.clientDesc, s.squeezeStatus, d.dealingId" +
					" FROM `Squeeze` s JOIN dealing d " +
					" ON s.dealingId = d.dealingId " +
					" LEFT JOIN Client c ON d.income = c.clientId " +
					" WHERE true ";

	public String create( final SqueezeVO squeezeVO, final String userName ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			final String dealingId = insertDealing(squeezeVO, userName, stmt);
			insertSqueeze(squeezeVO, dealingId, userName, stmt);

		} catch (final SQLException e) {
			throw new DaoException("SqueezeDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	public void deleteSqueezeById(final String squeezeId) throws DaoException {
		if (squeezeId != null && !"".equals(squeezeId)) {
			final String query = String.format(
					"DELETE FROM s, d USING squeeze s JOIN dealing d" +
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

	private SqueezeVO extractSqueeze(final ResultSet rs) throws SQLException {
		final SqueezeVO singleSqueeze = new SqueezeVO();
		singleSqueeze.setSqueezeId(rs.getString(1));
		singleSqueeze.setQuantity(rs.getInt(2));
		singleSqueeze.setMeters(rs.getInt(3));
		singleSqueeze.setPrice(rs.getString(4));
		singleSqueeze.setCity(rs.getString(5));
		singleSqueeze.setAmount(rs.getString(6));
		singleSqueeze.setUser(rs.getString(7));
		singleSqueeze.setComments(rs.getString(8));
		singleSqueeze.setSqueezeDate(rs.getDate(9));
		singleSqueeze.setClientId(rs.getString(10));
		singleSqueeze.setClientDesc(rs.getString(11));
		singleSqueeze.setStatus(
				SqueezeStatus.getValueByIdn(rs.getString(12)));
		final String dealingIdStr = rs.getString(13);
		if(dealingIdStr != null){
			singleSqueeze.setDealingId(Translator.parseInt(dealingIdStr));
		}

		return singleSqueeze;
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

	public SqueezeVO getSqueezeById(final String squeezeId) throws DaoException {
		SqueezeVO result = null;
		if (squeezeId != null && !"".equals(squeezeId)) {
			final String query = SQUEEZE_QUERY.concat(String.format(
					" and s.squeezeId = %s", squeezeId));
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					result = extractSqueeze(rs);
				}
			} catch (final SQLException e) {
				throw new DaoException("SqueezeDAO exception", e);
			} finally {
				disconnect(stmt, rs);
			}
		}
		return result;
	}

	public List<SqueezeVO> getSqueezeList(){
		final List<SqueezeVO> resultList = new ArrayList<SqueezeVO>();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(SQUEEZE_QUERY);
			while(rs.next()){
				resultList.add(extractSqueeze(rs));
			}
		} catch (final SQLException e) {
			log.error("SqueezeDAO exception", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}

	public List<SqueezeVO> getSqueezeList(final SqueezeFilterVO squeezeFilterVO) throws DaoException {
		final List<SqueezeVO> resultList = new ArrayList<SqueezeVO>();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			final String query = prepareQuery(squeezeFilterVO);
			rs = stmt.executeQuery(query);
			while(rs.next()){
				resultList.add(extractSqueeze(rs));
			}
		} catch (final SQLException e) {
			throw new DaoException("SqueezeDAO exception", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}

	private void handleDealingOnSqueezeStatus(final SqueezeVO squeezeVO, final String userName, final Statement stmt)
			throws DaoException, SQLException {
		if(squeezeVO.getStatus() != SqueezeStatus.SETTLED && squeezeVO.getDealingId() != null){
			dealingDao.removeBySqueezeId(squeezeVO.getSqueezeId());
		}else if(squeezeVO.getDealingId() == null){
			insertDealing(squeezeVO, userName, stmt);
		}
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

	private void insertSqueeze(final SqueezeVO squeezeVO, final String dealingId, final String userName, final Statement stmt) throws SQLException {
		stmt.executeUpdate("insert into squeeze(quantity,meters,price,dealingId,squeezeStatus,city) " +
				"values("
				+ squeezeVO.getQuantity() + ", "
				+ squeezeVO.getMeters() + ", "
				+ squeezeVO.getPrice() + ","
				+ "" + dealingId + ","
				+ "'" + squeezeVO.getStatus().getIdn() + "',"
				+ "'" + squeezeVO.getCity() + "')");
	}

	private String prepareQuery(final SqueezeFilterVO squeezeFilterVO) {

		final StringBuilder query = new StringBuilder(SQUEEZE_QUERY);

		query.append(squeezeFilterVO.getCity() != null   ?  " and  d.city='" + squeezeFilterVO.getCity() + "'" : "");
		query.append(" ORDER BY s.squeezeId DESC ");

		return query.toString();
	}

	public String save(final SqueezeVO squeezeVO, final String userName) throws DaoException {
		if(squeezeVO.getSqueezeId() != null && !"".equals(squeezeVO.getSqueezeId())){
			return update(squeezeVO, userName);
		} else {
			return create(squeezeVO, userName);
		}
	}

	private String update(  final SqueezeVO squeezeVO, final String userName ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			//			handleDealingOnSqueezeStatus(squeezeVO, userName, stmt);
			final String query = "UPDATE `squeeze` s JOIN dealing d " +
					"ON s.dealingId = d.dealingID " +
					"SET " +
					"s.quantity = " + squeezeVO.getQuantity() + ", " +
					"s.meters = " + squeezeVO.getMeters() + ", " +
					"s.price = " + squeezeVO.getPrice() + ", " +
					"d.city = '" + squeezeVO.getCity() + "', " +
					"d.income = '" + squeezeVO.getClientId() + "', " +
					"d.amount = " + squeezeVO.getAmount() + ", " +
					"d.date = " + (squeezeVO.getSqueezeDate() != null ? "'"+squeezeVO.getSqueezeDate()+"'," : "null,") +
					"d.comments1 = '" + squeezeVO.getComments() + "', " +
					"d.userId = '" + userName + "', " +
					"s.squeezeStatus = '" + squeezeVO.getStatus().getIdn() + "', " +
					"s.dealingId = " + squeezeVO.getDealingId() + " " +
					"WHERE s.squeezeId = " + squeezeVO.getSqueezeId();
			System.out.println(query);
			stmt.executeUpdate(query);

		} catch (final SQLException e) {
			throw new DaoException("SqueezeDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}
}
