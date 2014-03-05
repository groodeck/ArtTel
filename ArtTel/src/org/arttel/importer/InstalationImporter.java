package org.arttel.importer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.arttel.controller.vo.InstalationVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.InstalationDAO;
import org.arttel.dictionary.InstalationType;
import org.arttel.dictionary.Status;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class InstalationImporter {

	@Autowired
	private InstalationDAO instalationDao;
	
	@Autowired
	private CityDAO cityDao;
	
	private static final Logger log = Logger.getLogger(InstalationImporter.class);
	
	private static final String INSTALATIONS_TAB_NAME = "Instalacje import";
	
	private static final int STATUS = 0;
	private static final int INSTALATION_TYPE = 1;
	private static final int AGREEMENT_SIGN_DATE = 2;
	private static final int PHONE = 3;
	private static final int NAME_SURNAME = 4;
	private static final int ADDRESS = 5;
	private static final int CITY = 6;
	private static final int BUNDLE = 7;
	private static final int DECODER_SERIAL_NO = 8;
	private static final int INSTALATION_DATE = 9;
	private static final int MODEM_MAC = 10;
	private static final int DOWNSTREAM = 11;
	private static final int UPSTREAM = 12;
	private static final int COMMENTS = 13;
	private static final int ADDITIONAL_COMMENTS = 14;
	private static final int USER = 15;

	public ImportResult<InstalationVO> importInstalations(final String filePath) {

		final ImportResult<InstalationVO> dataExtractResults = extractInstalationsData(filePath);
		if(dataExtractResults.isDataOK()){
			final List<InstalationVO> instalationsList = dataExtractResults.getDataList();
			final ImportResult<InstalationVO> saveResults = saveInstalations(instalationsList);
			return saveResults;
		} else {
			return dataExtractResults;
		}
	}

	private ImportResult<InstalationVO> saveInstalations(final List<InstalationVO> instalationsList) {
		
		final List<InstalationVO> resultList = Lists.newArrayList();
		final List<String> errorList = Lists.newArrayList();
		int rowIndex = 1;
		for(final InstalationVO instalation: instalationsList){
			try {
				saveInstalation(instalation);
				resultList.add(instalation);
			} catch (DaoException e) {
				String errorMsg = "Nieudany zapis instalacji, rekord " + (rowIndex + 1)+ "). " + e.getMessage();
				errorList.add(errorMsg);
				log.error(errorMsg, e);
			}
			rowIndex++;
		}
		return new ImportResult<InstalationVO>(resultList, errorList);
	}

	private ImportResult<InstalationVO> extractInstalationsData(final String filePath) {

		final List<InstalationVO> instalationsList = Lists.newArrayList();
		final List<String> errorList = Lists.newArrayList();
		
		final Sheet sheet = getInstalationsSheet(filePath);
		int rowIndex = 1;
		Row row = sheet.getRow(rowIndex++);
		while(row != null){
			try {
				final InstalationVO instalation = getInstalationData(row);
				instalationsList.add(instalation);
			} catch (ImportException e) {
				errorList.add(e.getMessage() + " (rekord: " + (rowIndex) + ")");
			}
			
			row = sheet.getRow(rowIndex++);
		}
		return new ImportResult<InstalationVO>(instalationsList, errorList);
	}

	private Sheet getInstalationsSheet(final String filePath) {
		try {
			final InputStream inp = new FileInputStream(filePath);
			final Workbook wb = WorkbookFactory.create(inp);
			//final Sheet sheet = wb.getSheet(INSTALATIONS_TAB_NAME);
			final Sheet sheet = wb.getSheetAt(0);
			return sheet;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void saveInstalation(InstalationVO instalation) throws DaoException {
		instalationDao.save(instalation, instalation.getUser());
	}

	private InstalationVO getInstalationData(Row row) throws ImportException {
		
		final Status status = getStatus(row);
		final InstalationType instalationType = getInstalationType(row);
		final Date agreementSignDate = getAgreementSignDate(row);
		final String phone = getPhone(row);
		
		String name = null;
		String surname = null;
		final Cell nameCell = row.getCell(NAME_SURNAME);
		if(nameCell!= null){
			final String[] nameParts = nameCell.getStringCellValue().split(" ", 2);
			name = getFirstElementIfExists(nameParts);
			surname = getSecondElementIfExists(nameParts);
		}

		final String city = getCity(row);
		final String address = getAddress(row);
		final Date instalationDate = getInstalationDate(row);
		final String comments = getComments(row);
		final String additionalComments = getAdditionalComments(row);
		final String user = getUser(row);
		final String bundle = getBundle(row);
		final String decoderSerialNo = getDecoderSerialNo(row);
		final String modemMac = getModemMac(row);
		final String downstream = getDownstream(row);
		final String upstream = getUpstream(row);
		
		final InstalationVO instalation = new InstalationVO();
		instalation.setStatus(status);
		instalation.setRodzajInstalacji(instalationType);
		instalation.setDataPodpisaniaUmowy(agreementSignDate);
		instalation.setNrTelefonu(phone);
		instalation.setImie(name);
		instalation.setNazwisko(surname);
		instalation.setAdres(address);
		instalation.setCity(city);
		instalation.setPakiet(bundle);
		instalation.setNrSeryjny(decoderSerialNo);
		instalation.setDataInstalacji(instalationDate);
		instalation.setMacAdres(modemMac);
		instalation.setDownstream(downstream);
		instalation.setUpstream(upstream);
		instalation.setUwagi(comments);
		instalation.setDodatkoweUwagi(additionalComments);
		instalation.setUser(user);
		
		return instalation;
		
	}

	private String getFirstElementIfExists(final String[] elements) {
		final String firstElement;
		if(elements.length >= 1){
		firstElement = elements[0];
		} else {
			firstElement = null;
		}
		return firstElement;
	}

	private String getSecondElementIfExists(final String[] elements) {
		final String secondElement;
		if(elements.length >=2){
			secondElement = elements[1];
		} else {
			secondElement = null;
		}
		return secondElement;
	}

	private String getUpstream(Row row) {
		String upstream = null;
		final Cell upstreamCell = row.getCell(UPSTREAM);
		if(upstreamCell!= null){
			upstream = getCellStringValue(upstreamCell);
		}
		return upstream;
	}

	private String getDownstream(Row row) {
		String downstream = null;
		final Cell downstreamCell = row.getCell(DOWNSTREAM);
		if(downstreamCell!= null){
			downstream = getCellStringValue(downstreamCell);
		}
		return downstream;
	}

	private String getModemMac(Row row) {
		String modemMac = null;
		final Cell modemMacCell = row.getCell(MODEM_MAC);
		if(modemMacCell!= null){
			modemMac = getCellStringValue(modemMacCell);
		}
		return modemMac;
	}

	private String getDecoderSerialNo(Row row) {
		String decoderSerialNo = null;
		final Cell decoderSerialNoCell = row.getCell(DECODER_SERIAL_NO);
		if(decoderSerialNoCell!= null){
			decoderSerialNo = getCellStringValue(decoderSerialNoCell);
		}
		return decoderSerialNo;
	}

	private String getAddress(Row row) {
		String address = null;
		final Cell addressCell = row.getCell(ADDRESS);
		if(addressCell!= null){
			address = addressCell.getStringCellValue();
		}
		return address;
	}

	private String getCity(Row row) throws ImportException {
		String city = null;
		final Cell cityCell = row.getCell(CITY);
		if(cityCell!= null){
			city = getCityIdn(cityCell.getStringCellValue().toLowerCase());
		}
		return city;
	}

	private String getAdditionalComments(Row row) {
		String additionalComments = null;
		final Cell additionalCommentsCell = row.getCell(ADDITIONAL_COMMENTS);
		if(additionalCommentsCell!= null){
			additionalComments = additionalCommentsCell.getStringCellValue();
		}
		return additionalComments;
	}

	private String getComments(Row row) {
		String comments = null;
		final Cell commentsCell = row.getCell(COMMENTS);
		if(commentsCell!= null){
			comments = commentsCell.getStringCellValue();
		}
		return comments;
	}

	private String getBundle(Row row) {
		String bundle = null;
		final Cell bundleCell = row.getCell(BUNDLE);
		if(bundleCell!= null){
			bundle = bundleCell.getStringCellValue();
		}
		return bundle;
	}

	private Date getInstalationDate(Row row) {
		Date instalationDate = null;
		final Cell instalationDateCell = row.getCell(INSTALATION_DATE);
		if(instalationDateCell!= null){
			instalationDate = getSqlDate(instalationDateCell.getDateCellValue());
		}
		return instalationDate;
	}

	private String getPhone(Row row) {
		String phone = null;
		final Cell phoneCell = row.getCell(PHONE);
		if(phoneCell!= null){
			phone = getCellStringValue(phoneCell);
		}
		return phone;
	}

	private String getCellStringValue(Cell phoneCell) {
		
		switch (phoneCell.getCellType()){
		
			case Cell.CELL_TYPE_NUMERIC:
				return Double.toString(phoneCell.getNumericCellValue());
			
			case Cell.CELL_TYPE_STRING:
			case Cell.CELL_TYPE_BLANK:
				return emptyAsNull(phoneCell.getStringCellValue());
		
		}
		return null;
	}

	private String emptyAsNull(String stringCellValue) {
		if(StringUtils.isEmpty(stringCellValue)){
			return null;
		}
		return stringCellValue;
	}

	private Date getAgreementSignDate(Row row) {
		Date agreementDate = null;
		final Cell agreementDateCell = row.getCell(AGREEMENT_SIGN_DATE);
		if(agreementDateCell!= null){
			agreementDate = getSqlDate(agreementDateCell.getDateCellValue());
		}
		return agreementDate;
	}

	private String getUser(Row row) throws ImportException {
		String user = null;
		final Cell userCell = row.getCell(USER);
		if(userCell!= null){
			user = userCell.getStringCellValue().toLowerCase();
		}
		if(user == null){
			throw new ImportException("Nie mo¿na zidentyfikowaæ montera");
		}
		return user;
	}

	private InstalationType getInstalationType(Row row) throws ImportException {
		InstalationType instalationType = null;
		Cell instalationTypeCell = row.getCell(INSTALATION_TYPE);
		if(instalationTypeCell!= null){
			instalationType = InstalationType.getValueByDesc(instalationTypeCell.getStringCellValue());
		}
		if(instalationType == null){
			throw new ImportException("Nie mo¿na zidentyfikowaæ rodzaju instalacji");
		}
		return instalationType;
	}

	private Status getStatus(Row row) throws ImportException {
		Status status = null;
		final Cell statusCell = row.getCell(STATUS);
		if(statusCell!= null){
			status = Status.getValueByDesc(statusCell.getStringCellValue());
		}
		if(status == null){
			throw new ImportException("Nie mo¿na zidentyfikowaæ statusu");
		}
		return status;
	}

	private Date getSqlDate(java.util.Date date) {
		final Date result; 
		if(date == null){
			result = null; 
		} else {
			result = new Date(date.getTime());
		}
		return result;
	}

	private String getCityIdn(String cityDesc) throws ImportException {

		try {
			return cityDao.getCityIdnByDescription(cityDesc);
		} catch (DaoException e) {
			throw new ImportException("Nie mo¿na zidentyfikowaæ miasta");
		}
	}

}
