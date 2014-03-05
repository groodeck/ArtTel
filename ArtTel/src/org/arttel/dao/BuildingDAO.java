package org.arttel.dao;

import java.sql.SQLException;
import java.sql.Statement;

import org.arttel.controller.vo.BuildingVO;
import org.arttel.exception.DaoException;
import org.springframework.stereotype.Component;

@Component
public class BuildingDAO extends BaseDao {

	public String create( final BuildingVO buildingVO ) throws DaoException {
		
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			int rowsInserted = stmt
					.executeUpdate("insert into building(osiedle,punktOptyczny,administrator,ulica,miejscowosc," +
							"iloscMieszkan,uzwrotniony,typSieci,zmodernizowany,nieZmodernizowany,potrzebaModernizacji," +
							"zgoda,iloscKondygnacji,iloscPionow,iloscHP,przylaczeElektryczne,wejsciaDoBudynku," +
							"typWzmacniacza,nrSeryjny,producent,poborMocy,czestotliwoscGorna,lokalizacjaWzmacniacza," +
							"sygnalWE,sygnalPierwszyWzm,dsWzmoc,dsKorek,dsWzmacniacz,kzKorekcja,kzWzmocnienie,gein," +
							"doprowadzenie,dojscieDobudynku,rgDoWzm,doSrodka,zakresModernizacjiPiwnica,zakresModernizacjiPiony," +
							"projekt,dataWystapienia,dataOtrzymania,wykonawca,nrZlecenia,dataZlecenia,planowanyOdbior," +
							"odbiorRzeczywisty,hpWZleceniu,dystrybucjaKanalBudynek,dystrybucjaBudynek,dystrybucjaRazem," +
							"magistrala,iloscGniazd,iloscAbVectra,iloscAbSocjal,iloscAbSocjalAdm,cenaAbSocjalAdm," +
							"zawartoscPakietu,ilecoAbKonkurent,cenaPakietuKonkurent,zawartoscPakietuKonkurent,uwagi) " +
							"values("
							+ "'" + buildingVO.getOsiedle ()                 + "', " 
							+ "'" + buildingVO.getPunktOptyczny()            + "', "
							+ "'" + buildingVO.getAdministrator()            + "', "
							+ "'" + buildingVO.getUlica()                    + "', "
							+ "'" + buildingVO.getMiejscowosc()              + "', "
							+ "'" + buildingVO.getIloscMieszkan()            + "', "
							+ "'" + buildingVO.getUzwrotniony()              + "', "
							+ "'" + buildingVO.getTypSieci()                 + "', " 
							+ "'" + buildingVO.getZmodernizowany()           + "', "
							+ "'" + buildingVO.getNieZmodernizowany()        + "', "
							+ "'" + buildingVO.getPotrzebaModernizacji()     + "', "
							+ "'" + buildingVO.getZgoda()                    + "', "
							+ "'" + buildingVO.getIloscKondygnacji()         + "', "
							+ "'" + buildingVO.getIloscPionow()              + "', "
							+ "'" + buildingVO.getIloscHP()                  + "', " 
							+ "'" + buildingVO.getPrzylaczeElektryczne()     + "', "
							+ "'" + buildingVO.getWejsciaDoBudynku()         + "', "
							+ "'" + buildingVO.getTypWzmacniacza()           + "', "
							+ "'" + buildingVO.getNrSeryjny()                + "', "
							+ "'" + buildingVO.getProducent()                + "', "
							+ "'" + buildingVO.getPoborMocy()                + "', "
							+ "'" + buildingVO.getCzestotliwoscGorna()       + "', " 
							+ "'" + buildingVO.getLokalizacjaWzmacniacza()   + "', "
							+ "'" + buildingVO.getSygnalWE()                 + "', "
							+ "'" + buildingVO.getSygnalPierwszyWzm()        + "', "
							+ "'" + buildingVO.getDsWzmoc()                  + "', "
							+ "'" + buildingVO.getDsKorek()                  + "', "
							+ "'" + buildingVO.getDsWzmacniacz()             + "', "
							+ "'" + buildingVO.getKzKorekcja()               + "', " 
							+ "'" + buildingVO.getKzWzmocnienie()            + "', "
							+ "'" + buildingVO.getGein()                     + "', "
							+ "'" + buildingVO.getDoprowadzenie()            + "', "
							+ "'" + buildingVO.getDojscieDobudynku()         + "', "
							+ "'" + buildingVO.getRgDoWzm()                  + "', "
							+ "'" + buildingVO.getDoSrodka()                 + "', "
							+ "'" + buildingVO.getZakresModernizacjiPiwnica()+ "', " 
							+ "'" + buildingVO.getZakresModernizacjiPiony()  + "', "
							+ "'" + buildingVO.getProjekt()                  + "', "
							+ "'" + buildingVO.getDataWystapienia()          + "', "
							+ "'" + buildingVO.getDataOtrzymania()           + "', "
							+ "'" + buildingVO.getWykonawca()                + "', "
							+ "'" + buildingVO.getNrZlecenia()               + "', "
							+ "'" + buildingVO.getDataZlecenia()             + "', "
							+ "'" + buildingVO.getPlanowanyOdbior()          + "', "
							+ "'" + buildingVO.getOdbiorRzeczywisty()        + "', " 
							+ "'" + buildingVO.getHpWZleceniu()              + "', "
							+ "'" + buildingVO.getDystrybucjaKanalBudynek()  + "', "
							+ "'" + buildingVO.getDystrybucjaBudynek()       + "', "
							+ "'" + buildingVO.getDystrybucjaRazem()         + "', "
							+ "'" + buildingVO.getMagistrala()               + "', "
							+ "'" + buildingVO.getIloscGniazd()              + "', "
							+ "'" + buildingVO.getIloscAbVectra()            + "', "
							+ "'" + buildingVO.getIloscAbSocjal()            + "', "
							+ "'" + buildingVO.getIloscAbSocjalAdm()         + "', " 
							+ "'" + buildingVO.getCenaAbSocjalAdm()          + "', "
							+ "'" + buildingVO.getZawartoscPakietu()         + "', "
							+ "'" + buildingVO.getIlecoAbKonkurent()         + "', "
							+ "'" + buildingVO.getCenaPakietuKonkurent()     + "', "
							+ "'" + buildingVO.getZawartoscPakietuKonkurent()+ "', "
							+ "'" + buildingVO.getUwagi()                    + "')");
			
		} catch (SQLException e) {
			throw new DaoException("BuildingDAO SQLException", e);
		} finally {
			disconnect(stmt, null);
		}
		return null;
	}
	
}
