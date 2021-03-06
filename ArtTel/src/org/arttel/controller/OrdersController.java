package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.controller.vo.OrderVO;
import org.arttel.controller.vo.filter.OrderFilterVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.OrderDAO;
import org.arttel.dictionary.OrderType;
import org.arttel.dictionary.Status;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.exception.UserNotLoggedException;
import org.arttel.importer.ImportResult;
import org.arttel.importer.OrderImporter;
import org.arttel.ui.TableHeader;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrdersController extends BaseController<OrderVO> {

	private enum Event {
		MAIN, SAVE, EDIT, NEW, DELETE, COPY, SEARCH, BACK, IMPORT_FILE, CLOSE
	}

	@Autowired
	OrderDAO orderDao;

	@Autowired
	private CityDAO cityDao;

	private final Logger log = Logger.getLogger(OrdersController.class);

	private static final String SELECTED_ORDER = "selectedOrder";
	private static final String ORDER_LIST = "orderList";
	private static final String ORDER_FILTER = "orderFilter";
	private static final String IMPORT_ERRORS = "importErrors";

	private void applyPermissions(final List<OrderVO> orderList,
			final String loggedUser) {

		for(final OrderVO order : orderList){
			order.applyPermissions(loggedUser);
		}
	}

	private void closeOrder(final String orderId) {
		orderDao.closeOrder(orderId);
	}

	@Override
	protected String getResultRecordsListAttrName() {
		return ORDER_LIST;
	}

	protected Event getEvent( final HttpServletRequest request, final Event defaultValue) {

		Event event = defaultValue;
		final String eventStr = request.getParameter("event");
		if( eventStr != null ){
			event = Event.valueOf( eventStr.toUpperCase() );
		}
		return event;
	}

	@Override
	protected TableHeader getModelDefaultHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTableHeaderAttrName() {
		return "ordersTableHeader";
	}

	private void performActionBackToSearchresults( final UserContext userContext, final HttpServletRequest request) {
		final OrderFilterVO orderFilterVO = (OrderFilterVO) request.getSession().getAttribute(ORDER_FILTER);
		final List<OrderVO> orderList = orderDao.getOrderList(orderFilterVO);
		applyPermissions(orderList, userContext.getUserName());
		request.setAttribute(getResultRecordsListAttrName(), orderList);
		request.setAttribute(EVENT, Event.SEARCH);
	}



	private void performActionClose(final UserContext userContext, final HttpServletRequest request) {

		final String orderId = request.getParameter(EVENT_PARAM);
		if(StringUtils.isNotEmpty(orderId)){
			closeOrder(orderId);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionCopy(final UserContext userContext,
			final HttpServletRequest request) {

		final String orderId = request.getParameter(EVENT_PARAM);

		if(orderId != null){
			final OrderVO orderDetails = orderDao.getOrderById(orderId);
			orderDetails.setOrderId(null);
			orderDetails.setUser(null);
			orderDetails.applyPermissions(userContext.getUserName());
			request.setAttribute(SELECTED_ORDER, orderDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);

	}

	private void performActionDelete( final UserContext userContext,
			final HttpServletRequest request ) {

		final String orderId = request.getParameter(EVENT_PARAM);

		if(orderId != null){
			orderDao.deleteOrderById(orderId);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionEdit( final UserContext userContext,
			final HttpServletRequest request ) {

		final String orderId = request.getParameter(EVENT_PARAM);

		if(orderId != null){
			final OrderVO orderDetails = orderDao.getOrderById(orderId);
			orderDetails.applyPermissions(userContext.getUserName());
			request.setAttribute(SELECTED_ORDER, orderDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionImportFile(final UserContext userContext, final HttpServletRequest request){

		final String baseDir = request.getServletContext().getInitParameter(BASE_DIR);
		final String uploadDir = request.getServletContext().getInitParameter(DATA_UPLOAD_DIR);
		final String sessionId = request.getSession().getId();
		final String fileDir = baseDir + "/" + uploadDir + "/" + sessionId;
		final String fileName = IMPORT_FILE_NAME;
		final String filePath = fileDir + "/" + fileName;

		final FileExtractor fileExtractor = new FileExtractor();
		fileExtractor.extractFile(request, fileDir, fileName);
		final ImportResult<OrderVO> importResults = new OrderImporter().importOrders(filePath);
		if(importResults.isDataOK()){
			final List<OrderVO> orderList = importResults.getDataList();
			applyPermissions(orderList, userContext.getUserName());
			request.setAttribute(getResultRecordsListAttrName(), orderList);
		} else {
			final List<String> errorList = importResults.getErrorList();
			request.setAttribute(IMPORT_ERRORS, errorList);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionMain( final UserContext userContext, final HttpServletRequest request) {
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionNew( final UserContext userContext,
			final HttpServletRequest request ) {

		final OrderVO orderDetails = new OrderVO();
		orderDetails.applyPermissions(userContext.getUserName());
		request.setAttribute(SELECTED_ORDER, orderDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionSave( final UserContext userContext,
			final OrderVO orderVO, final HttpServletRequest request ) {
		try {
			orderDao.save(orderVO, userContext.getUserName());
			performActionBackToSearchresults(userContext, request);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void performActionSearch( final UserContext userContext, final OrderFilterVO orderFilterVO, final HttpServletRequest request) {
		orderFilterVO.populate(request);
		request.getSession().setAttribute(ORDER_FILTER, orderFilterVO);
		final List<OrderVO> orderList = orderDao.getOrderList(orderFilterVO);
		applyPermissions(orderList, userContext.getUserName());
		request.setAttribute(getResultRecordsListAttrName(), orderList);
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private Map<String,List<? extends ComboElement>> prepareSelectsMap() {

		final Map<String,List<? extends ComboElement>> selectsMap = new HashMap<String,List<? extends ComboElement>>();
		selectsMap.put( "statusWithEmpty", Status.getComboElementList(true));
		selectsMap.put( "status", Status.getComboElementList(false));
		selectsMap.put( "orderType", OrderType.getComboElementList(false));
		try {
			selectsMap.put( "cityDictionaryWithEmpty", cityDao.getCityDictionary(true, DictionaryPurpose.forOrder));
			selectsMap.put( "cityDictionary", cityDao.getCityDictionary(false, DictionaryPurpose.forOrder));
		} catch (final DaoException e) {
			log.error("DaoException",e);
		}
		return selectsMap;
	}

	@RequestMapping("/orders.app")
	public String process(final HttpServletRequest request,
			final HttpServletResponse response) throws UserNotLoggedException {

		final UserContext userContext = getUserContext(request);
		if(!userContext.isUserLogged()){
			throw new UserNotLoggedException();
		}

		final OrderVO ordersVO = (OrderVO) getForm(OrderVO.class, request);
		ordersVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);

		switch(event){
		case MAIN:
			performActionMain(userContext, request);
			break;
		case SAVE:
			performActionSave(userContext, ordersVO, request);
			break;
		case EDIT:
			performActionEdit(userContext, request);
			break;
		case NEW:
			performActionNew(userContext, request);
			break;
		case DELETE:
			performActionDelete(userContext, request);
			break;
		case COPY:
			performActionCopy(userContext, request);
			break;
		case SEARCH:
			performActionSearch(userContext, ordersVO.getOrderFilter(), request);
			break;
		case BACK:
			performActionBackToSearchresults(userContext, request);
			break;
		case IMPORT_FILE:
			performActionImportFile(userContext, request);
			break;
		case CLOSE:
			performActionClose(userContext, request);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap());
		return "orders";
	}
}