package org.arttel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.PayoutsVO;
import org.arttel.exception.DaoException;
import org.springframework.stereotype.Component;

@Component
public class PayoutDAO extends BaseDao {

	private final Logger log = Logger.getLogger(PayoutDAO.class);
	
	public String create( final PayoutsVO payoutsVO ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("insert into Payout(nazwaTowaru, dokument, kwota) " +
							"values("
							+ "'" + payoutsVO.getNazwaTowaru() + "', " 
							+ "'" + payoutsVO.getDokument() + "', "
							+ payoutsVO.getKwota() + ")");
			
		} catch (SQLException e) {
			throw new DaoException("PayoutsDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}
	
	public List<PayoutsVO> getPayoutList() {
		
		final String query = " select payoutId,nazwaTowaru,dokument,kwota " +
				" from Payout " ;

		final List<PayoutsVO> resultList = new ArrayList<PayoutsVO>();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				final PayoutsVO singlePayout = new PayoutsVO();
				singlePayout.setPayoutId(rs.getString(1));
				singlePayout.setNazwaTowaru(rs.getString(2));
				singlePayout.setDokument(rs.getString(3));
				singlePayout.setKwota(rs.getString(4));
				
				resultList.add(singlePayout);

			} 
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}

	public PayoutsVO getPayoutById(final String payoutId) {
		
		final String query = " select payoutId, nazwaTowaru, dokument, kwota " +
								" from Payout" +
								" where payoutId = " + payoutId;

		PayoutsVO result = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				result = new PayoutsVO();
				result.setPayoutId(rs.getString(1));
				result.setNazwaTowaru(rs.getString(2));
				result.setDokument(rs.getString(3));
				result.setKwota(rs.getString(5));
			}
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return result;
	}

}
