package org.arttel.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.InstalationVO;
import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.controller.vo.filter.InstalationFilterVO;
import org.arttel.dictionary.InstalationType;
import org.arttel.dictionary.Status;
import org.arttel.exception.DaoException;
import org.arttel.generator.CellType;
import org.arttel.generator.DataCell;
import org.arttel.generator.DataSheet;
import org.arttel.generator.report.ReportDataVO;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstalationDAO extends BaseDao {

	@Autowired
	private CityDAO cityDao;
	
	private final Logger log = Logger.getLogger(InstalationDAO.class);
	
	public String save( final InstalationVO instalationVO, final String user ) throws DaoException {
		
		if(instalationVO.getInstalationId() != null && !"".equals(instalationVO.getInstalationId())){
			return update(instalationVO, user);
		} else {
			return create(instalationVO, user);
		}
	}
	
	private String create( final InstalationVO instalationVO, final String user ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("insert into instalation(status,nrTelefonu,imie,nazwisko,adres,miejscowosc,nrSeryjny,dataInstalacji," +
							"rodzajInstalacji,dataPodpisaniaUmowy,pakiet,iloscGniazd,wykonaneInstalacje,iloscKabla,uwagi,dodatkoweUwagi," +
							"macAdres,downstream,upstream,userId) " +
							"values("
							+ "'" + instalationVO.getStatus().getIdn() + "', " 
							+ "'" + instalationVO.getNrTelefonu() + "', "
							+ "'" + instalationVO.getImie() + "', " 
							+ "'" + instalationVO.getNazwisko() + "', "
							+ "'" + instalationVO.getAdres() + "', "
							+ "'" + instalationVO.getCity() + "', "
							+ "'" + instalationVO.getNrSeryjny() + "', "
							+ (instalationVO.getDataInstalacji()!=null ? "'"+instalationVO.getDataInstalacji()+"'" : "null") + ", "
							+ "'" + instalationVO.getRodzajInstalacji().getIdn() + "', "
							+ (instalationVO.getDataPodpisaniaUmowy()!=null ? "'"+instalationVO.getDataPodpisaniaUmowy()+"'" : "null") + ", "
							+ "'" + instalationVO.getPakiet() + "', "
							+ instalationVO.getIloscGniazd() + ", "
							+ (instalationVO.getWykonaneInstalacje()!=null ?  instalationVO.getWykonaneInstalacje() : "null") + ", "
							+ "'" + instalationVO.getIloscKabla() + "', "
							+ "'" + instalationVO.getUwagi() + "', "
							+ "'" + instalationVO.getDodatkoweUwagi() + "', "
							+ "'" + instalationVO.getMacAdres() + "', "
							+ (instalationVO.getDownstream()!=null ? "'" + instalationVO.getDownstream() + "'" : "null") + ", "
							+ (instalationVO.getUpstream()!=null ? "'" + instalationVO.getUpstream() + "'" : "null") + ", "
							+ "'" + user + "')");
			
		} catch (SQLException e) {
			throw new DaoException("InstalationDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}
	
	private String update( final InstalationVO instalationVO, final String user ) throws DaoException {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("UPDATE instalation SET " +
							"status = '" + instalationVO.getStatus().getIdn() + "', " +
							"nrTelefonu = '" + instalationVO.getNrTelefonu() + "', " +
							"imie = '" + instalationVO.getImie() + "', " +
							"nazwisko = '" + instalationVO.getNazwisko() + "', " +
							"adres = '" + instalationVO.getAdres() + "', " +
							"miejscowosc = '" + instalationVO.getCity() + "', " +
							"nrSeryjny = '" + instalationVO.getNrSeryjny() + "', " +
							"dataInstalacji = " + (instalationVO.getDataInstalacji()!=null ? "'"+instalationVO.getDataInstalacji()+"'" : "null") + ", " + 
							"rodzajInstalacji = '" + instalationVO.getRodzajInstalacji().getIdn() + "', " +
							"dataPodpisaniaUmowy = " + (instalationVO.getDataPodpisaniaUmowy()!=null ? "'"+instalationVO.getDataPodpisaniaUmowy()+"'" : "null") + ", " +
							"pakiet = '" + instalationVO.getPakiet() + "', " +
							"iloscGniazd = " + instalationVO.getIloscGniazd() + ", " + 
							"wykonaneInstalacje = " + (instalationVO.getWykonaneInstalacje()!=null ?  instalationVO.getWykonaneInstalacje() : "null" ) + ", " +
							"iloscKabla = '" + instalationVO.getIloscKabla() + "', " + 
							"uwagi = '" + instalationVO.getUwagi() + "', " + 
							"dodatkoweUwagi = '" + instalationVO.getDodatkoweUwagi() + "', " +
							"macAdres = '" + instalationVO.getMacAdres() + "', " +
							"downstream = "+ (instalationVO.getDownstream()!=null ? "'" + instalationVO.getDownstream() + "'" : "null") + ", " +
							"upstream = " + (instalationVO.getUpstream()!=null ? "'" + instalationVO.getUpstream() + "'" : "null") + " " +
							"WHERE instalationId = " + instalationVO.getInstalationId() );
							
		} catch (SQLException e) {
			throw new DaoException("DecoderDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}

	private static Map<String,String> QUERY_MAP = new HashMap<String,String>(){
		{
			put("SD", 		"select dataInstalacji,adres,nrSeryjny from Instalation where rodzajInstalacji in ('sd')");
			put("HD", 		"select dataInstalacji,adres,nrSeryjny,macAdres,upstream,downstream from Instalation where rodzajInstalacji in ('hd_bez_modemu')");
			put("HD PVR", 	"select dataInstalacji,adres,nrSeryjny,macAdres,upstream,downstream from Instalation where rodzajInstalacji in ('hd', 'pvr')");
			put("NET", 		"select dataInstalacji,adres,macAdres,upstream,downstream from Instalation where rodzajInstalacji in ('modem')");
			put("NET+TEL", 	"select dataInstalacji,adres,macAdres,upstream,downstream from Instalation where rodzajInstalacji in ('tel_net')");
			put("TEL", 		"select dataInstalacji,adres,macAdres,upstream,downstream from Instalation where rodzajInstalacji in ('terminal')");
			
		}
	};
	
	public ReportDataVO getReportData(final String worksheetName, final Date dateFrom, final Date dateTo, final String cityIdn, int dataRowsOffset) throws DaoException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			final ReportDataVO result = new ReportDataVO();
			String dataQuery = QUERY_MAP.get(worksheetName);
			if(dateFrom != null){
				dataQuery = dataQuery.concat(" AND dataInstalacji >='" + dateFrom + "'");
			}
			if(dateTo != null){
				dataQuery = dataQuery.concat(" AND dataInstalacji <='" + dateTo + "'");
			}
			if(cityIdn != null){
				dataQuery = dataQuery.concat(" AND miejscowosc ='" + cityIdn + "'");
			}
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(dataQuery);
			
			//TODO: dodaæ dynamiczny zapis metadanych instalacji, komorek niestandardowych do ReportDataVO.reportDetailsList
			
			int xlsRowNumber = 1, sheetRowsDelimiter = 39;
			DataSheet dataSheet = new DataSheet();
			dataSheet.setDataRowsOffset(dataRowsOffset);
			dataSheet.addDetailsCell(dataRowsOffset - 4, 2, getCityInfo(cityIdn));
			dataSheet.addDetailsCell(dataRowsOffset - 4, 3, getDateInfo(dateFrom, dateTo));
			
			while(rs.next()){
				
				final List<DataCell> row = extractDataRow(worksheetName, rs, xlsRowNumber++);
				dataSheet.getRows().add(row);
				
				if(sheetRowsDelimiter-- == 0){
					sheetRowsDelimiter = 39;
					result.addDataSheet(dataSheet);
					dataSheet = new DataSheet();
					dataSheet.setDataRowsOffset(dataRowsOffset);
					dataSheet.addDetailsCell(dataRowsOffset - 4, 2, getCityInfo(cityIdn));
					dataSheet.addDetailsCell(dataRowsOffset - 4, 3, getDateInfo(dateFrom, dateTo));
				}
			}
			result.addDataSheet(dataSheet);
			
			return result;
			
		} catch (SQLException e) {
			throw new DaoException("DecoderDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
	}


	private DataCell getDateInfo(Date dateFrom, Date dateTo) {
		final String dateFromStr = dateFrom != null ? dateFrom.toString() : null;
		final String dateToStr = dateTo != null ? dateTo.toString() : null;
		final StringBuilder sb = new StringBuilder("w okresie");
		sb.append(" od ").append(Translator.complement(dateFromStr, '.', 48));
		sb.append(" do ").append(Translator.complement(dateToStr, '.', 48));
		return new DataCell( sb.toString(), CellType.TEXT);
	}

	private DataCell getCityInfo(final String cityIdn) throws DaoException {
		final String cityDesc = getCityDesc(cityIdn);
		final String cellText = "w " + Translator.complement(cityDesc, '.', 65);
		return new DataCell( cellText, CellType.TEXT);
	}

	private String getCityDesc(final String cityIdn) throws DaoException {
		
		final SimpleComboElement city = cityDao.getCityByIdn(cityIdn);
		return city != null ? city.getCityDesc() : "";
	}

	private List<DataCell> extractDataRow(final String worksheetName, final ResultSet rs, int xlsRowNumber) throws SQLException {
		
		final List<DataCell> row = new ArrayList<DataCell>();
		row.add(new DataCell(xlsRowNumber, CellType.INT));
		row.add(new DataCell(rs.getDate(1),CellType.DATE));
		row.add(new DataCell(rs.getString(2),CellType.TEXT));
		row.add(new DataCell(rs.getString(3),CellType.TEXT));
		if ("HD".equals(worksheetName) || "HD PVR".equals(worksheetName)){
			row.add(new DataCell(rs.getString(4),CellType.TEXT));
			row.add(new DataCell(rs.getDouble(5),CellType.DOUBLE));
			row.add(new DataCell(rs.getDouble(6),CellType.DOUBLE));
		} else if("NET".equals(worksheetName) || "NET+TEL".equals(worksheetName)||"TEL".equals(worksheetName)) {
			row.add(new DataCell(rs.getDouble(4),CellType.DOUBLE));
			row.add(new DataCell(rs.getDouble(5),CellType.DOUBLE));
		}
		return row;
	}
	
	public InstalationVO getInstalationById(final String instalationId) {
		
		final String query = " select instalationId,status,nrTelefonu,imie,nazwisko,adres,miejscowosc,nrSeryjny,dataInstalacji," +
						" rodzajInstalacji,dataPodpisaniaUmowy,pakiet,iloscGniazd,wykonaneInstalacje,iloscKabla,uwagi,dodatkoweUwagi," +
						" macAdres,downstream,upstream, userId" +
						" from Instalation" +
						" where instalationId = " + instalationId;
		
		InstalationVO result = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				result = new InstalationVO();
				result.setInstalationId(rs.getString(1));
				final String statusIdn = rs.getString(2);
				if(statusIdn != null){
					result.setStatus(Status.getValueByIdn(statusIdn));
				}
				result.setNrTelefonu(rs.getString(3));
				result.setImie(rs.getString(4));
				result.setNazwisko(rs.getString(5));
				result.setAdres(rs.getString(6));
				result.setCity(rs.getString(7));
				result.setNrSeryjny(rs.getString(8));
				result.setDataInstalacji(rs.getDate(9));
				final String instalationTypeIdn = rs.getString(10);
				if(instalationTypeIdn != null){
					result.setRodzajInstalacji(InstalationType.getValueByIdn(instalationTypeIdn));
				}
				result.setDataPodpisaniaUmowy(rs.getDate(11));
				result.setPakiet(rs.getString(12));
				result.setIloscGniazd(rs.getString(13));
				result.setWykonaneInstalacje(rs.getString(14));
				result.setIloscKabla(rs.getString(15));
				result.setUwagi(rs.getString(16));
				result.setDodatkoweUwagi(rs.getString(17));
				result.setMacAdres(rs.getString(18));
				result.setDownstream(rs.getString(19));
				result.setUpstream(rs.getString(20));
				result.setUser(rs.getString(21));
			}
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return result;
	}

	public List<InstalationVO> getInstalationList( final InstalationFilterVO instalationFilterVO ) {
		
		final List<InstalationVO> resultList = new ArrayList<InstalationVO>();
		if(instalationFilterVO == null){
			return resultList;
		}
		
		final String query = prepareQuery(instalationFilterVO);

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				final InstalationVO singleInstalation = new InstalationVO();
				singleInstalation.setInstalationId(rs.getString(1));
				final String statusIdn = rs.getString(2);
				if(statusIdn != null){
					singleInstalation.setStatus(Status.getValueByIdn(statusIdn));
				}
				singleInstalation.setNrTelefonu(rs.getString(3));
				singleInstalation.setImie(rs.getString(4));
				singleInstalation.setNazwisko(rs.getString(5));
				singleInstalation.setAdres(rs.getString(6));
				singleInstalation.setCity(rs.getString(7));
				singleInstalation.setNrSeryjny(rs.getString(8));
				singleInstalation.setDataInstalacji(rs.getDate(9));
				final String instalationTypeIdn = rs.getString(10);
				if(instalationTypeIdn != null){
					singleInstalation.setRodzajInstalacji(InstalationType.getValueByIdn(instalationTypeIdn));
				}
				singleInstalation.setDataPodpisaniaUmowy(rs.getDate(11));
				singleInstalation.setPakiet(rs.getString(12));
				singleInstalation.setIloscGniazd(rs.getString(13));
				singleInstalation.setWykonaneInstalacje(rs.getString(14));
				singleInstalation.setIloscKabla(rs.getString(15));
				singleInstalation.setUwagi(rs.getString(16));
				singleInstalation.setDodatkoweUwagi(rs.getString(17));
				singleInstalation.setMacAdres(rs.getString(18));
				singleInstalation.setDownstream(rs.getString(19));
				singleInstalation.setUpstream(rs.getString(20));
				singleInstalation.setUser(rs.getString(21));
				
				resultList.add(singleInstalation);

			} 
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return resultList;
	}

	private String prepareQuery(final InstalationFilterVO instalationFilterVO) {
		
		final StringBuilder query = new StringBuilder(" select instalationId,status,nrTelefonu,imie,nazwisko,adres,miejscowosc,nrSeryjny,dataInstalacji," +
		" rodzajInstalacji,dataPodpisaniaUmowy,pakiet,iloscGniazd,wykonaneInstalacje,iloscKabla,uwagi,dodatkoweUwagi," +
		" macAdres,downstream,upstream, userId" +
		" from Instalation " +
		" where true ");
		
		if(instalationFilterVO.getPhrase() != null){
			query.append(" and  ")
				.append("(")
					.append("miejscowosc like '%" + instalationFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append("adres like '%" + instalationFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append("imie like '%" + instalationFilterVO.getPhrase() + "%'")
					.append(" OR ")
					.append("nazwisko like '%" + instalationFilterVO.getPhrase() + "%'")
				.append(")");
		} else {
			query.append(instalationFilterVO.getCity() != null   ?  " and  miejscowosc='" + instalationFilterVO.getCity() + "'" : "")
				.append(instalationFilterVO.getStatus() != null ?  " and status='" + instalationFilterVO.getStatus() + "'" : "" )
				.append(instalationFilterVO.getDateFrom() != null ?  " and dataInstalacji >= '" + instalationFilterVO.getDateFrom() + "'" : "" )
				.append(instalationFilterVO.getDateTo() != null ?  " and dataInstalacji <= '" + instalationFilterVO.getDateTo() + "'" : "" )
				.append(instalationFilterVO.getInstalationType() != null ?  " and rodzajInstalacji = '" + instalationFilterVO.getInstalationType() + "'" : "" )
				.append(instalationFilterVO.getUser() != null ?  " and userId = '" + instalationFilterVO.getUser() + "'" : "" );
		}
		query.append(" order by dataInstalacji desc, instalationId desc ");
		return query.toString();
	}

	public int deleteInstalationById(final String instalationId) {
		
		if(instalationId == null || "".equals(instalationId)){
			return 0;
		}
		
		final String query = " DELETE FROM Instalation WHERE instalationId = " + instalationId;
		int rowsRemoved = -1;
		
		try {
			final Statement stmt = getConnection().createStatement();
			rowsRemoved = stmt.executeUpdate(query);
		} catch (SQLException e) {
			log.error("SQLException", e);
		}
		return rowsRemoved;
	}

	public int getSocketCount(final String city) {
		final String query = " SELECT sum(iloscGniazd) " +
				" FROM Instalation " +
				" WHERE miejscowosc ='%s' " +
				" AND iloscGniazd IS NOT NULL" +
				" AND socketOrderSequence IS NULL ";
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(String.format(query, city));
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return 0;
	}

	public boolean resetSocketOrder(final String city) {
		
		final String nextSequenceQuery = "SELECT IFNULL(MAX(socketOrderSequence)+1,1) FROM instalation";
		
		final String updateQuery = 
				"UPDATE instalation " +
				"SET socketOrderSequence=%s " +
				"WHERE miejscowosc ='%s' " +
				"	AND socketOrderSequence IS NULL " +
				"	AND iloscGniazd > 0";

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(nextSequenceQuery);
			if(rs.next()){
				final int sequence = rs.getInt(1);
				stmt.executeUpdate(String.format(updateQuery, sequence, city));
				return true;
			}
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return false;
	}

	public void closeInstalation(String instalationId) {

		final String query = " UPDATE Instalation SET status='" + Status.DONE.getIdn() + "' WHERE instalationId = " + instalationId;
		
		try {
			final Statement stmt = getConnection().createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			log.error("SQLException", e);
		}
	}

	public int getUnclearedInstalationCount(final String city) {
		final String query = " SELECT sum(wykonaneInstalacje) " +
				" FROM Instalation " +
				" WHERE miejscowosc ='%s' " +
				" AND instalationClearingSequence IS NULL ";
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(String.format(query, city));
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return 0;
	}
	
public boolean clearInstalations(final String city) {
		
		final String nextSequenceQuery = "SELECT IFNULL(MAX(instalationClearingSequence)+1,1) FROM instalation";
		
		final String updateQuery = 
				"UPDATE instalation " +
				"SET instalationClearingSequence=%s " +
				"WHERE miejscowosc ='%s' " +
				"	AND instalationClearingSequence IS NULL ";

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(nextSequenceQuery);
			if(rs.next()){
				final int sequence = rs.getInt(1);
				stmt.executeUpdate(String.format(updateQuery, sequence, city));
				return true;
			}
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
		return false;
	}
}
