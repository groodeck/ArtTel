package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.FundsEntryVO;
import org.arttel.exception.DaoException;
import org.springframework.stereotype.Component;

@Component
public class FundsEntryDAO extends BaseDao {

	private final Logger log = Logger.getLogger(FundsEntryDAO.class);
	
	public String create( final FundsEntryVO fundEntry ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("insert into fundsEntry(entryAmount,entryDate,userId,comments) " +
							"values("
							+ fundEntry.getEntryAmount() + ", "
							+ "'" + fundEntry.getEntryDate() + "', "
							+ "'" + fundEntry.getUserName() + "', "
							+ "'" + fundEntry.getComments() + "')");
			
		} catch (SQLException e) {
			throw new DaoException("FundsEntryDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	public String save(FundsEntryVO fundsEntryVO) throws DaoException {
		if(fundsEntryVO.getFundsEntryId() != null && !"".equals(fundsEntryVO.getFundsEntryId())){
			return update(fundsEntryVO);
		} else {
			return create(fundsEntryVO);
		}
	}

	private String update(  final FundsEntryVO fundsEntryVO) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("UPDATE `fundsEntry` SET " +
							"entryAmount = " + fundsEntryVO.getEntryAmount() + ", " +
							"entryDate = '" +fundsEntryVO.getEntryDate()+"', " +
							"userId = '" + fundsEntryVO.getUserName() + "', " + 
							"comments = '" + fundsEntryVO.getComments() + "' " + 
							"WHERE fundsEntryId = " + fundsEntryVO.getFundsEntryId() );
							
		} catch (SQLException e) {
			throw new DaoException("FundsEntryDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	private FundsEntryVO extractFundsEntry(ResultSet rs) throws SQLException {

		final FundsEntryVO singleEntry = new FundsEntryVO();
		singleEntry.setFundsEntryId(rs.getString(1));
		singleEntry.setEntryAmount(rs.getString(2));
		singleEntry.setEntryDate(rs.getDate(3));
		singleEntry.setUserName(rs.getString(4));
		singleEntry.setComments(rs.getString(5));
		return singleEntry;
	}
	
	public List<FundsEntryVO> getUserFundsEntryList(String userId) {

		final List<FundsEntryVO> resultList = new ArrayList<FundsEntryVO>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery("select fundsEntryId, entryAmount, entryDate, userId, comments " + 
									" from fundsEntry " +
									" where userId='" + userId + "'");
			while(rs.next()){
				resultList.add(extractFundsEntry(rs));
			} 
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}
	
}
