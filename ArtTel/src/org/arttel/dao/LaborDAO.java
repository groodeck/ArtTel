package org.arttel.dao;

import java.sql.SQLException;
import java.sql.Statement;

import org.arttel.controller.vo.LaborVO;
import org.arttel.exception.DaoException;
import org.springframework.stereotype.Component;

@Component
public class LaborDAO extends BaseDao {

	public String create( final LaborVO laborVO ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("insert into labor(numerZlecenia,miasto,adres,nazwaPionu,rodzaj,ilosc,jednostkaMiary,uzytkownikId) " +
							"values("
							+ "'" + laborVO.getNumerZlecenia() + "', " 
							+ "'" + laborVO.getMiasto() + "', "
							+ "'" + laborVO.getAdres() + "', "
							+ "'" + laborVO.getNazwaPionu() + "', "
							+ "'" + laborVO.getRodzaj() + "', "
							+ "'" + laborVO.getIlosc() + "', "
							+ "'" + laborVO.getJednostkaMiary() + "', "
							+ "'" + laborVO.getUzytkownikId() + "')");
			
		} catch (SQLException e) {
			throw new DaoException("LaborDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}
	
}
