package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.arttel.controller.vo.SqueezeVO;
import org.arttel.controller.vo.filter.SqueezeFilterVO;
import org.arttel.dictionary.SqueezeStatus;
import org.arttel.exception.DaoException;
import org.springframework.stereotype.Component;

@Component
public class SqueezeDAO extends BaseDao {

	@PersistenceContext
	private EntityManager em;

	private static final String SQUEEZE_QUERY =
			" SELECT s.squeezeId ,s.quantity, s.meters, s.price, s.city, s.amount, s.userId, s.comments, s.date, s.clientId, c.clientDesc, s.squeezeStatus" +
					" FROM `Squeeze` s LEFT JOIN Client c ON s.clientId = c.clientId " +
					" LEFT JOIN City ct ON s.city = ct.cityIdn " +
					" WHERE true ";

	public String create( final SqueezeVO squeezeVO, final String userName ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			insertSqueeze(squeezeVO, userName, stmt);
		} catch (final SQLException e) {
			throw new DaoException("SqueezeDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	public void deleteSqueezeById(final String squeezeId) throws DaoException {
		if (squeezeId != null && !"".equals(squeezeId)) {
			final String query = String.format("DELETE FROM squeeze WHERE squeezeId = %s", squeezeId);
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
		singleSqueeze.setStatus(SqueezeStatus.getValueByIdn(rs.getString(12)));
		return singleSqueeze;
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

	private void insertSqueeze(final SqueezeVO squeezeVO, final String userName, final Statement stmt) throws SQLException {
		stmt.executeUpdate("insert into squeeze(quantity,meters,price,squeezeStatus,city, amount, userId, comments, date, clientId) " +
				"values("
				+ squeezeVO.getQuantity() + ", "
				+ squeezeVO.getMeters() + ", "
				+ squeezeVO.getPrice() + ","
				+ "'" + squeezeVO.getStatus().getIdn() + "',"
				+ "'" + squeezeVO.getCity() + "',"
				+ squeezeVO.getAmount() + ","
				+ "'" + userName + "',"
				+ "'" + squeezeVO.getComments() + "',"
				+ squeezeVO.getSqueezeDate() + ","
				+ squeezeVO.getClientId()
				+ ")");
	}

	private String prepareQuery(final SqueezeFilterVO squeezeFilterVO) {

		final StringBuilder query = new StringBuilder(SQUEEZE_QUERY);

		if(squeezeFilterVO.getPhrase() != null){
			query.append(" and  ")
			.append("(")
			.append(" ct.cityDesc like '%" + squeezeFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append(" s.amount like '%" + squeezeFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append(" s.comments like '%" + squeezeFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append(" c.clientDesc like '%" + squeezeFilterVO.getPhrase() + "%'")
			.append(" OR ")
			.append(" s.date like '%" + squeezeFilterVO.getPhrase() + "%'")
			.append(")");
		} else {
			query.append(squeezeFilterVO.getCity() != null   ?  " and  s.city='" + squeezeFilterVO.getCity() + "'" : "");
		}

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
			final String query = "UPDATE `squeeze` s " +
					"SET " +
					"s.quantity = " + squeezeVO.getQuantity() + ", " +
					"s.meters = " + squeezeVO.getMeters() + ", " +
					"s.price = " + squeezeVO.getPrice() + ", " +
					"s.squeezeStatus = '" + squeezeVO.getStatus().getIdn() + "', " +
					"s.city = '" + squeezeVO.getCity() + "', " +
					"s.amount = " + squeezeVO.getAmount() + ", " +
					"s.userId = '" + userName + "', " +
					"s.comments = '" + squeezeVO.getComments() + "', " +
					"s.date = " + (squeezeVO.getSqueezeDate()!=null ? "'" + squeezeVO.getSqueezeDate() + "'" : "null") + ", " +
					"s.clientId = " + squeezeVO.getClientId() + " " +
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
