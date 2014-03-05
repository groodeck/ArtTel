package org.arttel.dao;

import java.sql.SQLException;
import java.sql.Statement;

import org.arttel.controller.vo.MaterialsVO;
import org.arttel.exception.DaoException;
import org.springframework.stereotype.Component;

@Component
public class MaterialDAO extends BaseDao {

	public String create( final MaterialsVO materialsVO ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("insert into material(numerZlecenia,miasto,adres,nazwaPionu,rodzaj,ilosc,jednostkaMiary,uzytkownikId) " +
							"values("
							+ "'" + materialsVO.getNumerZlecenia() + "', " 
							+ "'" + materialsVO.getMiasto() + "', "
							+ "'" + materialsVO.getAdres() + "', "
							+ "'" + materialsVO.getNazwaPionu() + "', "
							+ "'" + materialsVO.getRodzaj() + "', "
							+ "'" + materialsVO.getIlosc() + "', "
							+ "'" + materialsVO.getJednostkaMiary() + "', "
							+ "'" + materialsVO.getUzytkownikId() + "')");
			
		} catch (SQLException e) {
			throw new DaoException("MaterialDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}
	
}
