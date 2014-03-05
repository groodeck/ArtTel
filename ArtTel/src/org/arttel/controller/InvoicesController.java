package org.arttel.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.controller.vo.CorrectionVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.CorrectionDAO;
import org.arttel.dao.InvoiceDAO;
import org.arttel.dao.ProductDAO;
import org.arttel.dao.SellerDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.dictionary.UnitType;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.generator.invoice.InvoiceGenerator;
import org.arttel.util.DecimalWriter;
import org.arttel.util.InvoiceNumberGenerator;
import org.arttel.util.Translator;
import org.arttel.view.ComboElement;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

@Controller
public class InvoicesController extends BaseController {

	@Autowired
	private CityDAO cityDao;

	@Autowired
	private ClientDAO clientDao;
	
	@Autowired
	private InvoiceDAO invoiceDao;
	
	@Autowired
	private ProductDAO productDao;
	
	@Autowired
	private InvoiceGenerator invoiceGenerator;
	
	@Autowired
	private CorrectionDAO correctionDao;
	
	@Autowired
	private SellerDAO sellerDao;

	private String targetPage = jspContextPrefix + "invoices.jsp";

	private final Logger log = Logger.getLogger(InvoicesController.class);

	public enum Event {
		MAIN, SAVE, EDIT, ADD_PRODUCT_ROW, DEL_PRODUCT_ROW, NEW, DELETE, SEARCH, BACK, CHANGE_PRODUCT, PAID_ENTERED, PRINT, CHANGE_PAYMENT_TYPE,
		SETTLE_INVOICE, CORRECT, EDIT_CORRECTION, DELETE_CORRECTION, COPY
	}

	private static final String SELECTED_INVOICE = "selectedInvoice";
	private static final String INVOICES_LIST = "invoiceList";
	private static final String CORRECTION_LIST = "correctionList";
	private static final String INVOICES_FILTER = "nvoiceFilter";

	@RequestMapping("/invoices.app")
	public String process(HttpServletRequest request, HttpServletResponse response) {

		UserContext userContext = getUserContext(request);

		InvoiceVO invoiceVO = (InvoiceVO) getForm(InvoiceVO.class, request);
		invoiceVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);

		switch (event) {
		case MAIN:
			performActionMain(userContext, invoiceVO, request);
			break;
		case SAVE:
			performActionSave(userContext, invoiceVO, request);
			break;
		case EDIT:
			performActionEdit(userContext, request);
			break;
		case NEW:
			performActionNew(userContext, request);
			break;
		case ADD_PRODUCT_ROW:
			performActionAddProductRow(userContext, invoiceVO, request);
			break;
		case DEL_PRODUCT_ROW:
			performActionDelProductRow(userContext, invoiceVO, request);
			break;
		case CHANGE_PRODUCT:
			performActionChangeProduct(userContext, invoiceVO, request);
			break;
		case PAID_ENTERED:
			performActionPaymentEntered(userContext, invoiceVO, request);
			break;
		case DELETE:
			performActionDeleteInvoice(userContext, request);
			break;
		case SEARCH:
			performActionSearch(userContext, invoiceVO.getInvoiceFilter(),
					request);
			break;
		case BACK:
			performActionBackToSearchresults(userContext, request);
			break;
		case PRINT:
			performActionPrint(userContext, invoiceVO, request);
			break;
		case CHANGE_PAYMENT_TYPE:
			performActionChangePaymentType(userContext, invoiceVO, request);
			break;
		case SETTLE_INVOICE:
			performActionSettleInvoice(userContext, invoiceVO, request);
			break;
		case CORRECT:
			performActionCorrectInvoice(request, response);
			break;
		case EDIT_CORRECTION:
			performActionEditCorrection(request, response);
			break;
		case DELETE_CORRECTION:
			performActionDeleteCorrection(request, response);
			break;
		case COPY:
			performActionCopy(userContext, request);
			break;
			
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap(userContext));
		return "invoices";
	}

	private void performActionCopy(UserContext userContext,
			HttpServletRequest request) {
		final String invoiceId = request.getParameter(EVENT_PARAM);

		try {
			if (invoiceId != null) {
				InvoiceVO invoiceDetails = invoiceDao.getInvoiceById(invoiceId);
				invoiceDetails.setInvoiceId(null);
				invoiceDetails.setNumber(getNextInvoiceNumber());
				final Date currentDate = getCurrentDate();
				invoiceDetails.setCreateDate(currentDate);
				invoiceDetails.setPaymentDate(getPaymentDate(currentDate,
						PaymentType.CASH));
				invoiceDetails.setStatus(InvoiceStatus.DRAFT);
				invoiceDetails.applyPermissions(userContext.getUserName());
				request.getSession().setAttribute(FORM, invoiceDetails);
				request.setAttribute(SELECTED_INVOICE, invoiceDetails);
			}
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionCorrectInvoice(final HttpServletRequest request, 
			final HttpServletResponse response)  {
		try {
			request.getRequestDispatcher("correction.app").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void performActionEditCorrection(final HttpServletRequest request, 
			final HttpServletResponse response)  {
		try {
			request.setAttribute(EVENT,  org.arttel.controller.CorrectionController.Event.EDIT);
			request.getRequestDispatcher("correction.app").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void performActionDeleteCorrection(final HttpServletRequest request, 
			final HttpServletResponse response)  {
		try {
			request.setAttribute(EVENT,  org.arttel.controller.CorrectionController.Event.DELETE);
			request.getRequestDispatcher("correction.app").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void performActionSettleInvoice(UserContext userContext,
			InvoiceVO invoiceVO, HttpServletRequest request) {
		
		final int invoiceId = Translator.parseInt(request.getParameter(EVENT_PARAM));
		try {
			invoiceDao.setInvoiceStatus(invoiceId, InvoiceStatus.SETTLED);
		} catch (DaoException e) {
			log.error("Dao Exception", e);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionChangePaymentType(UserContext userContext,
			InvoiceVO invoiceVO, HttpServletRequest request) {

		final Date createDate = invoiceVO.getCreateDate();
		final PaymentType paymentType = invoiceVO.getPaymentType();
		final Date paymentDate = getPaymentDate(createDate, paymentType);
		invoiceVO.setPaymentDate(paymentDate);

		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionPrint(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		try {
			final String sessionId = request.getSession().getId();
			final String invoiceLink = invoiceGenerator.generateInvoice(invoiceVO, sessionId);
			if(StringUtils.isNotEmpty(invoiceLink)){
				final int invoiceId = Translator.parseInt(invoiceVO.getInvoiceId());
				invoiceDao.setInvoiceStatus(invoiceId, InvoiceStatus.PENDING);
				invoiceVO.setStatus(InvoiceStatus.PENDING);
			}
			request.setAttribute(RESULT_FILE_LINK, invoiceLink);
			request.setAttribute(SELECTED_INVOICE, invoiceVO);
			request.setAttribute(EVENT, Event.EDIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void performActionDelProductRow(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		final int selectedProduct = Translator.parseInt(request
				.getParameter(EVENT_PARAM));
		invoiceVO.getInvoiceProducts().remove(selectedProduct);
		recalculateInvoice(invoiceVO);
		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionChangeProduct(UserContext userContext,
			InvoiceVO invoiceVO, HttpServletRequest request) {
		final int selectedProduct = Translator.parseInt(request
				.getParameter(EVENT_PARAM));
		try {
			final ProductVO productDefinition = getProductDefinition(request,
					selectedProduct);

			final InvoceProductVO invoiceProduct = invoiceVO
					.getProduct((selectedProduct));
			invoiceProduct.setProductDefinition(productDefinition);
			recalculateInvoiceProduct(invoiceProduct);
			recalculateInvoice(invoiceVO);
			request.setAttribute(SELECTED_INVOICE, invoiceVO);
			request.setAttribute(EVENT, Event.EDIT);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void performActionPaymentEntered(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {

		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void recalculateInvoice(final InvoiceVO invoiceVO) {
		BigDecimal vatAmount = new BigDecimal(0), netAmount = new BigDecimal(0);
		for (final InvoceProductVO product : invoiceVO.getInvoiceProducts()) {
			vatAmount = vatAmount.add(Translator.getDecimal(product
					.getVatAmount()));
			netAmount = netAmount.add(Translator.getDecimal(product
					.getNetSumAmount()));
		}
		invoiceVO.setNetAmount(netAmount.setScale(2, BigDecimal.ROUND_HALF_UP)
				.toPlainString());
		invoiceVO.setVatAmount(vatAmount.setScale(2, BigDecimal.ROUND_HALF_UP)
				.toPlainString());
		invoiceVO.setPaidWords(DecimalWriter.getDecimalSpoken(invoiceVO
				.getGrossAmount()));
	}

	private void recalculateInvoiceProduct(final InvoceProductVO invoiceProduct) {

		if (StringUtils.isNotEmpty(invoiceProduct.getQuantity())) {
			final ProductVO productDefinition = invoiceProduct
					.getProductDefinition();
			final BigDecimal netSinglePrice = new BigDecimal(
					productDefinition.getNetPrice());
			final BigDecimal quantity = new BigDecimal(
					invoiceProduct.getQuantity());
			final BigDecimal netAmount = netSinglePrice.multiply(quantity);
			final BigDecimal vatRate = new BigDecimal(productDefinition
					.getVatRate().getIdn());
			final BigDecimal vatAmount = netAmount.multiply(vatRate).setScale(
					2, BigDecimal.ROUND_HALF_UP);
			final BigDecimal grossSumAmount = netAmount.add(vatAmount);
			invoiceProduct.setNetSumAmount(netAmount.toPlainString());
			invoiceProduct.setVatAmount(vatAmount.toPlainString());
			invoiceProduct.setGrossSumAmount(grossSumAmount.toPlainString());
		}
	}

	private ProductVO getProductDefinition(HttpServletRequest request,
			final int selectedProduct) throws DaoException {
		final String changedParamName = "product[" + selectedProduct
				+ "].productDefinition.productId";
		final String productId = request.getParameter(changedParamName);
		ProductVO productDefinition = productDao.getProductById(productId);
		return productDefinition;
	}

	private void performActionAddProductRow(UserContext userContext,
			InvoiceVO invoiceVO, HttpServletRequest request) {
		invoiceVO.addNewInvoiceProduct();
		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionEdit(final UserContext userContext,
			final HttpServletRequest request) {

		final String invoiceId = request.getParameter(EVENT_PARAM);

		try {
			if (invoiceId != null) {
				InvoiceVO invoiceDetails = invoiceDao.getInvoiceById(invoiceId);
				invoiceDetails.applyPermissions(userContext.getUserName());
				getCorrections(Lists.newArrayList(invoiceDetails));
				request.getSession().setAttribute(FORM, invoiceDetails);
				request.setAttribute(SELECTED_INVOICE, invoiceDetails);
			}
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionNew(final UserContext userContext,
			final HttpServletRequest request) {

		final InvoiceVO invoiceDetails = new InvoiceVO();
		invoiceDetails.setNumber(getNextInvoiceNumber());
		final Date currentDate = getCurrentDate();
		invoiceDetails.setCreateDate(currentDate);
		invoiceDetails.setPaymentDate(getPaymentDate(currentDate,
				PaymentType.CASH));
		invoiceDetails.setStatus(InvoiceStatus.DRAFT);
		invoiceDetails.applyPermissions(userContext.getUserName());
		request.getSession().setAttribute(FORM, invoiceDetails);
		request.setAttribute(SELECTED_INVOICE, invoiceDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private Date getPaymentDate(Date createDate, PaymentType cash) {
		final Date paymentDate;
		if (createDate == null) {
			paymentDate = null;
		} else {
			final DateTime paymentDay = new DateTime(createDate).plusDays(cash
					.getPaymentPeriod());
			paymentDate = new Date(paymentDay.getMillis());
		}
		return paymentDate;
	}

	private Date getCurrentDate() {
		return new Date(new java.util.Date().getTime());
	}

	private String getNextInvoiceNumber() {
		return new InvoiceNumberGenerator().getNextNumber();
	}

	private void performActionDeleteInvoice(final UserContext userContext,
			final HttpServletRequest request) {

		final String invoiceId = request.getParameter(EVENT_PARAM);
		try {
			if (invoiceId != null) {
				invoiceDao.deleteInvoiceById(invoiceId);
			}
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionSave(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		try {
			if(hasChanged(invoiceVO)){
				invoiceVO.setStatus(InvoiceStatus.DRAFT);
			}
			invoiceDao.save(invoiceVO, userContext.getUserName());
			performActionBackToSearchresults(userContext, request);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
	}

	private boolean hasChanged(InvoiceVO invoiceVO) {
		// TODO zaimplementowac metodê equals i uzyc
		return true;
	}

	private void performActionSearch(final UserContext userContext,
			final InvoiceFilterVO invoiceFilterVO,
			final HttpServletRequest request) {
		invoiceFilterVO.populate(request);
		invoiceFilterVO.setUser(userContext.getUserName());
		request.getSession().setAttribute(INVOICES_FILTER, invoiceFilterVO);
		try {
			final List<InvoiceVO> invoiceList = getInvoiceList(invoiceFilterVO, userContext.getUserName());
			final List<CorrectionVO> correctionList = getCorrections(invoiceList);
			request.setAttribute(INVOICES_LIST, invoiceList);
			request.setAttribute(CORRECTION_LIST, correctionList);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private List<InvoiceVO> getInvoiceList(final InvoiceFilterVO invoiceFilterVO, String user)
			throws DaoException {
		final List<InvoiceVO> invoiceList = invoiceDao.getInvoiceList(invoiceFilterVO);
		for(final InvoiceVO invoice : invoiceList){
			invoice.applyPermissions(user);
		}
		return invoiceList;
	}

	private CorrectionVO getCorrection(final InvoiceVO invoice) {
		try {
			final CorrectionVO correction = 
					correctionDao.getCorrectionForInvoice(invoice.getInvoiceId());
			if(correction != null){
				correction.setInvoiceNumber(invoice.getNumber());
			}
			return correction;
		} catch (DaoException e) {
			log.error("Dao Exception", e);
			return null;
		}
	}
	
	private List<CorrectionVO> getCorrections(final List<InvoiceVO> invoiceList) {
		final List<CorrectionVO> resultList = Lists.newArrayList();
		for(final InvoiceVO invoice : invoiceList){
			final CorrectionVO correction = getCorrection(invoice);
			if(correction != null){
				invoice.setCorrection(correction);
				resultList.add(correction);
			}
		}
		return resultList;
	}

	private void applyPermissions(final List<InvoiceVO> invoiceList,
			final List<CorrectionVO> correctionList, final String loggedUser) {

		for (final InvoiceVO invoice : invoiceList) {
			invoice.applyPermissions(loggedUser);
		}
		for (CorrectionVO correction:correctionList){
			correction.applyPermissions(loggedUser);
		}
	}

	private void performActionBackToSearchresults(
			final UserContext userContext, final HttpServletRequest request) {
		final InvoiceFilterVO invoiceFilterVO = (InvoiceFilterVO) request
				.getSession().getAttribute(INVOICES_FILTER);
		try {
			final List<InvoiceVO> invoiceList = getInvoiceList(invoiceFilterVO, 
					userContext.getUserName());
			final List<CorrectionVO> correctionList = getCorrections(invoiceList);
			applyPermissions(invoiceList, correctionList, userContext.getUserName());
			request.setAttribute(INVOICES_LIST, invoiceList);
			request.setAttribute(CORRECTION_LIST, correctionList);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionMain(final UserContext userContext,
			InvoiceVO invoiceVO, final HttpServletRequest request) {
		invoiceVO.clearProductList();
		request.setAttribute(EVENT, Event.MAIN);
		request.setAttribute(SELECTED_INVOICE, invoiceVO);
	}

	protected Event getEvent(final HttpServletRequest request,
			final Event defaultValue) {

		Event event = (Event) request.getAttribute(EVENT);
		if(event == null){
			final String eventStr = request.getParameter(EVENT);
			if (eventStr == null) {
				event = defaultValue;
			} else {
				event = Event.valueOf(eventStr.toUpperCase());
			}
		}
		return event;
	}

	private Map<String, List<? extends ComboElement>> prepareSelectsMap(final UserContext userContext) {
		final Map<String, List<? extends ComboElement>> selectsMap = new HashMap<String, List<? extends ComboElement>>();
		try {
			selectsMap.put("cityDictionary", cityDao.getCityDictionary(true, DictionaryPurpose.forInvoice));
			final String userName = userContext.getUserName();
			final ClientFilterVO clientFilter = 
					new ClientFilterVO(userName,DictionaryPurpose.forInvoice);
			selectsMap.put("clientDictionary", clientDao.getClientDictionary(false,clientFilter));
			selectsMap.put("unitTypesDictionary",
					UnitType.getComboElementList(false));
			selectsMap.put("productDictionary", productDao.getProductDictionary(false, userName));
			selectsMap.put("paymentTypeDictionary",
					PaymentType.getComboElementList(false));
			selectsMap.put("sellerDictionary", sellerDao.getSellerDictionary(false, userName));

		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}

}
