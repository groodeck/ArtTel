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
import org.arttel.service.InvoiceService;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.arttel.ui.TableHeader;
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

	public enum Event {
		MAIN, SAVE, EDIT, ADD_PRODUCT_ROW, DEL_PRODUCT_ROW, NEW, DELETE, SEARCH, BACK, CHANGE_PRODUCT, PAID_ENTERED, PRINT, CHANGE_PAYMENT_TYPE,
		SETTLE_INVOICE, CORRECT, COPY, SORT, CHANGEPAGE
	}

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

	@Autowired
	private InvoiceNumberGenerator invoiceNumberGenerator;

	@Autowired
	private InvoiceService invoiceService;

	private final Logger log = Logger.getLogger(InvoicesController.class);

	private static final String SELECTED_INVOICE = "selectedInvoice";
	private static final String INVOICES_LIST = "invoiceList";
	private static final String CORRECTION_LIST = "correctionList";
	private static final String INVOICES_FILTER = "nvoiceFilter";

	private void applyPermissions(final List<InvoiceVO> invoiceList,
			final List<CorrectionVO> correctionList, final String loggedUser) {

		for (final InvoiceVO invoice : invoiceList) {
			invoice.applyPermissions(loggedUser);
		}
		for (final CorrectionVO correction:correctionList){
			correction.applyPermissions(loggedUser);
		}
	}

	private void clearInvoiceRecordsIds(final List<InvoceProductVO> invoiceProducts) {
		for(final InvoceProductVO product : invoiceProducts){
			product.setInvoiceProductId(null);
		}
	}

	private CorrectionVO getCorrection(final InvoiceVO invoice) {
		try {
			final CorrectionVO correction =
					correctionDao.getCorrectionForInvoice(invoice.getInvoiceId());
			if(correction != null){
				correction.setInvoiceNumber(invoice.getNumber());
			}
			return correction;
		} catch (final DaoException e) {
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

	private Date getCurrentDate() {
		return new Date(new java.util.Date().getTime());
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

	@Override
	protected TableHeader getModelDefaultHeader() {
		return InvoiceVO.resultTableHeader;
	}

	private String getNextInvoiceNumber(final String userName) {
		return invoiceNumberGenerator.getNextNumber(userName);
	}

	private Date getPaymentDate(final Date createDate, final PaymentType cash) {
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

	private ProductVO getProductDefinition(final HttpServletRequest request,
			final int selectedProduct) throws DaoException {
		final String changedParamName = "product[" + selectedProduct
				+ "].productDefinition.productId";
		final String productId = request.getParameter(changedParamName);
		final ProductVO productDefinition = productDao.getProductById(productId);
		return productDefinition;
	}

	private boolean hasChanged(final InvoiceVO invoiceVO) {
		// TODO zaimplementowac metodê equals i uzyc
		return true;
	}

	private void performActionAddProductRow(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		invoiceVO.addNewInvoiceProduct();
		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionBackToSearchresults(
			final UserContext userContext, final HttpServletRequest request) {
		final InvoiceFilterVO invoiceFilterVO = (InvoiceFilterVO) request
				.getSession().getAttribute(INVOICES_FILTER);
		final PageInfo pageInfo = getCurrentPageInfo(request);
		try {
			final ResultPage<InvoiceVO> invoiceList = invoiceService.getInvoiceList(invoiceFilterVO, pageInfo,
					userContext.getUserName());
			final List<CorrectionVO> correctionList = getCorrections(invoiceList.getRecords());
			applyPermissions(invoiceList.getRecords(), correctionList, userContext.getUserName());
			request.setAttribute(INVOICES_LIST, invoiceList);
			request.setAttribute(CORRECTION_LIST, correctionList);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionChangePage(final UserContext userContext, final HttpServletRequest request) {
		final String newPageNo = request.getParameter(NEW_PAGE_NO);
		updatePage(newPageNo, request);
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionChangePaymentType(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {

		final Date createDate = invoiceVO.getCreateDate();
		final PaymentType paymentType = invoiceVO.getPaymentType();
		final Date paymentDate = getPaymentDate(createDate, paymentType);
		invoiceVO.setPaymentDate(paymentDate);

		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionChangeProduct(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		final int selectedProduct = Translator.parseInt(request
				.getParameter(EVENT_PARAM));
		try {
			final ProductVO productDefinition = getProductDefinition(request,
					selectedProduct);

			final InvoceProductVO invoiceProduct = invoiceVO
					.getProduct(selectedProduct);
			invoiceProduct.setProductDefinition(productDefinition);
			recalculateInvoiceProduct(invoiceProduct);
			recalculateInvoice(invoiceVO);
			request.setAttribute(SELECTED_INVOICE, invoiceVO);
			request.setAttribute(EVENT, Event.EDIT);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void performActionCopy(final UserContext userContext,
			final HttpServletRequest request) {
		final String invoiceId = request.getParameter(EVENT_PARAM);

		if (invoiceId != null) {
			final InvoiceVO invoiceDetails = invoiceService.getInvoice(invoiceId);
			invoiceDetails.setInvoiceId(null);
			clearInvoiceRecordsIds(invoiceDetails.getInvoiceProducts());
			invoiceDetails.setNumber(getNextInvoiceNumber(userContext.getUserName()));
			final Date currentDate = getCurrentDate();
			invoiceDetails.setCreateDate(currentDate);
			invoiceDetails.setPaymentDate(getPaymentDate(currentDate,
					PaymentType.CASH));
			invoiceDetails.setStatus(InvoiceStatus.DRAFT);
			invoiceDetails.applyPermissions(userContext.getUserName());
			request.getSession().setAttribute(FORM, invoiceDetails);
			request.setAttribute(SELECTED_INVOICE, invoiceDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionCorrectInvoice(final HttpServletRequest request,
			final HttpServletResponse response)  {
		try {
			request.getRequestDispatcher("correction.app").forward(request, response);
		} catch (final ServletException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void performActionDeleteInvoice(final UserContext userContext,
			final HttpServletRequest request) {

		final String invoiceId = request.getParameter(EVENT_PARAM);
		if (invoiceId != null) {
			invoiceService.deleteInvoice(invoiceId);
		}
		performActionBackToSearchresults(userContext, request);
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

	private void performActionEdit(final UserContext userContext, final HttpServletRequest request) {

		final String invoiceId = request.getParameter(EVENT_PARAM);
		if (invoiceId != null) {
			final InvoiceVO invoiceDetails = invoiceService.getInvoice(invoiceId);
			invoiceDetails.applyPermissions(userContext.getUserName());
			getCorrections(Lists.newArrayList(invoiceDetails));
			request.getSession().setAttribute(FORM, invoiceDetails);
			request.setAttribute(SELECTED_INVOICE, invoiceDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionMain(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		invoiceVO.clearProductList();
		request.setAttribute(EVENT, Event.MAIN);
		request.setAttribute(SELECTED_INVOICE, invoiceVO);
	}

	private void performActionNew(final UserContext userContext,
			final HttpServletRequest request) {

		final InvoiceVO invoiceDetails = new InvoiceVO();
		invoiceDetails.setNumber(getNextInvoiceNumber(userContext.getUserName()));
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

	private void performActionPaymentEntered(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {

		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionPrint(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		try {
			final String sessionId = request.getSession().getId();
			final String invoiceLink = invoiceGenerator.generateInvoice(invoiceVO, sessionId);
			if(StringUtils.isNotEmpty(invoiceLink)){
				final String invoiceId = invoiceVO.getInvoiceId();
				invoiceDao.setInvoiceStatus(invoiceId, InvoiceStatus.PENDING);
				invoiceVO.setStatus(InvoiceStatus.PENDING);
			}
			request.setAttribute(RESULT_FILE_LINK, invoiceLink);
			request.setAttribute(SELECTED_INVOICE, invoiceVO);
			request.setAttribute(EVENT, Event.EDIT);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void performActionSave(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		if(hasChanged(invoiceVO)){
			invoiceVO.setStatus(InvoiceStatus.DRAFT);
		}
		invoiceService.save(invoiceVO, userContext.getUserName());
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionSearch(final UserContext userContext,
			final InvoiceFilterVO invoiceFilterVO, final HttpServletRequest request) {
		final PageInfo pageInfo = getCurrentPageInfo(request);
		invoiceFilterVO.populate(request);
		invoiceFilterVO.setUser(userContext.getUserName());
		request.getSession().setAttribute(INVOICES_FILTER, invoiceFilterVO);
		try {
			final ResultPage<InvoiceVO> invoiceList = invoiceService.getInvoiceList(invoiceFilterVO, pageInfo, userContext.getUserName());
			final List<CorrectionVO> correctionList = getCorrections(invoiceList.getRecords());
			request.setAttribute(INVOICES_LIST, invoiceList);
			request.setAttribute(CORRECTION_LIST, correctionList);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionSettleInvoice(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {

		final String invoiceId = request.getParameter(EVENT_PARAM);
		invoiceDao.setInvoiceStatus(invoiceId, InvoiceStatus.SETTLED);
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionSort(final UserContext userContext, final HttpServletRequest request) {
		final String sortColumn = request.getParameter(SORT_COLUMN);
		updateSortColumn(sortColumn, request);
		performActionBackToSearchresults(userContext, request);
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
			selectsMap.put("bankAccountDictionary", sellerDao.getSellerBankAccounts(userName));

		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	@RequestMapping("/invoices.app")
	public String process(final HttpServletRequest request, final HttpServletResponse response) {

		final UserContext userContext = getUserContext(request);

		final InvoiceVO invoiceVO = (InvoiceVO) getForm(InvoiceVO.class, request);
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
		case COPY:
			performActionCopy(userContext, request);
			break;
		case SORT:
			performActionSort(userContext, request);
			break;
		case CHANGEPAGE:
			performActionChangePage(userContext, request);
			break;

		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap(userContext));
		return "invoices";
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

}
