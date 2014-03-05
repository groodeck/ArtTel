package org.arttel.importer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.arttel.controller.vo.OrderVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.OrderDAO;
import org.arttel.dictionary.OrderType;
import org.arttel.dictionary.Status;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class OrderImporter {
	
	@Autowired
	private CityDAO cityDao;

	@Autowired
	private OrderDAO orderDao;
	
	private static final String ORDERS_TAB_NAME = "zlecenia import";
	
	private static final int STATUS = 0;
	private static final int ADDRESS = 1;
	private static final int ORDER_TYPE = 2;
	private static final int ISSUE_DATE = 3;
	private static final int SURNAME_NAME = 4;
	private static final int PHONE = 5;
	private static final int REALIZATION_DATE = 6;
	private static final int SOLUTION = 7;
	private static final int COMMENTS = 8;
	private static final int ADDITIONAL_COMMENTS = 9;
	private static final int USER = 10;

	private Logger log = Logger.getLogger(OrderImporter.class);

	public ImportResult<OrderVO> importOrders(final String filePath) {

		final ImportResult<OrderVO> dataExtractResult = extractOrdersData(filePath);
		if(dataExtractResult.isDataOK()){
			final List<OrderVO> ordersList = dataExtractResult.getDataList();
			final ImportResult<OrderVO> saveResults = saveOrders(ordersList);
			return saveResults;
			
		} else {
			return dataExtractResult;
		}
	}

	private ImportResult<OrderVO> saveOrders(final List<OrderVO> ordersList) {
		final List<OrderVO> resultList = Lists.newArrayList();
		final List<String> errorList = Lists.newArrayList();
		int rowIndex = 1;
		for(final OrderVO order: ordersList){
			try{
				saveOrder(order);
				resultList.add(order);
			} catch (DaoException e) {
				String errorMsg = "Nieudany zapis zlecenia, rekord " + (rowIndex + 1)+ "). " + e.getMessage();
				errorList.add(errorMsg);
				log .error(errorMsg, e);
			}
			rowIndex++;
		}
		return new ImportResult<OrderVO>(resultList, errorList);
	}

	private ImportResult extractOrdersData(final String filePath) {

		final List<OrderVO> ordersList = Lists.newArrayList();
		final List<String> errorList = Lists.newArrayList();
		
		final Sheet sheet = getOrdersSheet(filePath);
		int rowIndex = 1;
		Row row = sheet.getRow(rowIndex++);
		while(row != null){
			try {
				final OrderVO order = getOrderData(row);
				ordersList.add(order);
			} catch (ImportException e) {
				errorList.add(e.getMessage() + " (rekord: " + (rowIndex-1) + ")");
			}
			
			row = sheet.getRow(rowIndex++);
		}
		return new ImportResult(ordersList, errorList);
	}

	private Sheet getOrdersSheet(final String filePath) {
		try {
			final InputStream inp = new FileInputStream(filePath);
			final Workbook wb = WorkbookFactory.create(inp);
//			final Sheet sheet = wb.getSheet(ORDERS_TAB_NAME);
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

	private void saveOrder(OrderVO order) throws DaoException {
		orderDao.save(order, order.getUser());
	}

	private OrderVO getOrderData(Row row) throws ImportException {
		
		final Status status = getStatus(row);
		
		String city = null;
		String address = null;
		final Cell addressCell = row.getCell(ADDRESS);
		if(addressCell!= null){
			final String[] addressParts = addressCell.getStringCellValue().split(" ", 2);
			city = getCityIdn(getFirstElementIfExists(addressParts)).toLowerCase();
			address = getSecondElementIfExists(addressParts);
		}
		
		final OrderType orderType = getOrderType(row);
		final Date issueDate = getIssueDate(row);
		
		String name = null;
		String surname = null;
		final Cell nameCell = row.getCell(SURNAME_NAME);
		if(nameCell!= null){
			final String[] nameParts = nameCell.getStringCellValue().split(" ", 2);
			name = getFirstElementIfExists(nameParts);
			surname = getSecondElementIfExists(nameParts);
		}
		
		final String phone = getPhone(row);
		final Date realizationDate = getRealizationDate(row);
		final String solution = getSolution(row);
		final String comments = getComments(row);
		final String additionalComments = getAdditionalComments(row);
		final String user = getUser(row);
		
		final OrderVO order = new OrderVO();
		order.setStatus(status);
		order.setCity(city);
		order.setAddress(address);
		order.setOrderType(orderType);
		order.setIssueDate(issueDate);
		order.setSurname(surname);
		order.setName(name);
		order.setPhone(phone);
		order.setRealizationDate(realizationDate);
		order.setSolution(solution);
		order.setComments(comments);
		order.setAdditionalComments(additionalComments);
		order.setUser(user);
		
		return order;
		
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

	private String getSolution(Row row) {
		String solution = null;
		final Cell solutionCell = row.getCell(SOLUTION);
		if(solutionCell!= null){
			solution = solutionCell.getStringCellValue();
		}
		return solution;
	}

	private Date getRealizationDate(Row row) {
		Date realizationDate = null;
		final Cell realizationDateCell = row.getCell(REALIZATION_DATE);
		if(realizationDateCell!= null){
			realizationDate = getSqlDate(realizationDateCell.getDateCellValue());
		}
		return realizationDate;
	}

	private String getPhone(Row row) {
		String phone = null;
		final Cell phoneCell = row.getCell(PHONE);
		if(phoneCell!= null){
			phone = phoneCell.getStringCellValue();
		}
		return phone;
	}

	private Date getIssueDate(Row row) {
		Date issueDate = null;
		final Cell issueDateCell = row.getCell(ISSUE_DATE);
		if(issueDateCell!= null){
			issueDate = getSqlDate(issueDateCell.getDateCellValue());
		}
		return issueDate;
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

	private OrderType getOrderType(Row row) throws ImportException {
		OrderType orderType = null;
		Cell orderTypeCell = row.getCell(ORDER_TYPE);
		if(orderTypeCell!= null){
			orderType = OrderType.getValueByDesc(orderTypeCell.getStringCellValue());
		}
		if(orderType == null){
			throw new ImportException("Nie mo¿na zidentyfikowaæ rodzaju zlecenia");
		}
		return orderType;
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

		return new Date(date.getTime());
	}

	private String getCityIdn(String cityDesc) throws ImportException {

		try {
			return cityDao.getCityIdnByDescription(cityDesc);
		} catch (DaoException e) {
			throw new ImportException("Nie mo¿na zidentyfikowaæ miasta");
		}
	}

}
