package org.arttel.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.CorrectionVO;
import org.arttel.controller.vo.FinancialDocumentProductVO;
import org.arttel.controller.vo.FinancialDocumentVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.SellerDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.dictionary.UnitType;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.service.ProductService;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.arttel.util.Translator;
import org.arttel.view.ComboElement;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

public abstract class FinancialDocumentController<VO extends FinancialDocumentVO<Product>, Product extends FinancialDocumentProductVO>
extends BaseController<VO>  {

	protected enum Event {
		MAIN, SAVE, EDIT, ADD_PRODUCT_ROW, DEL_PRODUCT_ROW, NEW, DELETE_SELECTED, SEARCH, BACK, CHANGE_PRODUCT, PAID_ENTERED, PRINT, CHANGE_PAYMENT_TYPE,
		SETTLE_INVOICE, CORRECT, COPY, SORT, CHANGEPAGE, PRINT_SELECTED
	}

	@Autowired
	protected ProductService productService;

	@Autowired
	private CityDAO cityDao;

	@Autowired
	private ClientDAO clientDao;

	@Autowired
	private SellerDAO sellerDao;

	@Autowired
	private FileResponseHandler fileResponse;

	private final Logger log = Logger.getLogger(FinancialDocumentController.class);

	protected static final String CORRECTION_LIST = "correctionList";

	private void applyPermissions(final List<VO> documentList,
			final List<CorrectionVO> correctionList, final String loggedUser) {

		for (final VO document : documentList) {
			document.applyPermissions(loggedUser);
		}
		for (final CorrectionVO correction:correctionList){
			correction.applyPermissions(loggedUser);
		}
	}

	protected abstract void clearInvoiceRecordsIds(VO documentVO);

	protected abstract VO createNewDocument();

	protected abstract void deleteInvoice(final List<Integer> documentIds);

	protected abstract List<CorrectionVO> getCorrections(List<VO> records);

	private Date getCurrentDate() {
		return new Date(new java.util.Date().getTime());
	}

	protected abstract Class<VO> getDocumentClass();

	protected abstract String getDocumentFilterAttrName();

	protected Event getEvent(final HttpServletRequest request, final Event defaultValue) {

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

	protected abstract VO getFinancialDocument(final String documentId);

	protected abstract ResultPage<VO> getFinancialDocumentList(final UserContext userContext,
			final InvoiceFilterVO invoiceFilterVO, final PageInfo pageInfo) throws DaoException;

	protected abstract String getNextDocumentNumber(final String userName);

	private Date getPaymentDate(final Date createDate, final PaymentType cash) {
		final Date paymentDate;
		if (createDate == null) {
			paymentDate = null;
		} else {
			final DateTime paymentDay = new DateTime(createDate).plusDays(cash.getPaymentPeriod());
			paymentDate = new Date(paymentDay.getMillis());
		}
		return paymentDate;
	}

	private ProductVO getProductDefinition(final HttpServletRequest request,
			final int selectedProduct) throws DaoException {
		final String changedParamName = "product[" + selectedProduct
				+ "].productDefinition.productId";
		final String productId = request.getParameter(changedParamName);
		final ProductVO productDefinition = productService.getProductById(productId);
		return productDefinition;
	}

	@Override
	protected abstract String getResultRecordsListAttrName();

	protected abstract String getSelectedDocumentAttrName();

	protected abstract String getTargetPage();

	private void performActionAddProductRow(final UserContext userContext, final VO documentVO, final HttpServletRequest request) {
		documentVO.addNewProduct();
		request.setAttribute(getSelectedDocumentAttrName(), documentVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionChangePage(final UserContext userContext, final HttpServletRequest request) {
		final String newPageNo = request.getParameter(NEW_PAGE_NO);
		updatePage(newPageNo, request);
		searchDocumentsByFilter(userContext, request);
	}

	private void performActionChangePaymentType(final UserContext userContext, final VO documentVO, final HttpServletRequest request) {

		final Date createDate = documentVO.getCreateDate();
		final PaymentType paymentType = documentVO.getPaymentType();
		final Date paymentDate = getPaymentDate(createDate, paymentType);
		documentVO.setPaymentDate(paymentDate);

		request.setAttribute(getSelectedDocumentAttrName(), documentVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionChangeProduct(final UserContext userContext,
			final VO documentVO, final HttpServletRequest request) {
		final int selectedProduct = Translator.parseInt(request
				.getParameter(EVENT_PARAM));
		try {
			final ProductVO productDefinition = getProductDefinition(request,
					selectedProduct);

			final Product product = documentVO.getProduct(selectedProduct);
			product.setProductDefinition(productDefinition);
			recalculateProduct(product);
			recalculateDocument(documentVO);
			request.setAttribute(getSelectedDocumentAttrName(), documentVO);
			request.setAttribute(EVENT, Event.EDIT);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void performActionCopy(final UserContext userContext, final HttpServletRequest request) {
		final String documentId = request.getParameter(EVENT_PARAM);

		if (documentId != null) {
			final VO financialDocument = getFinancialDocument(documentId);
			financialDocument.setDocumentId(null);
			clearInvoiceRecordsIds(financialDocument);
			financialDocument.setNumber(getNextDocumentNumber(userContext.getUserName()));
			final Date currentDate = getCurrentDate();
			financialDocument.setCreateDate(currentDate);
			financialDocument.setPaymentDate(getPaymentDate(currentDate,
					PaymentType.CASH));
			financialDocument.setStatus(InvoiceStatus.DRAFT);
			financialDocument.applyPermissions(userContext.getUserName());
			request.getSession().setAttribute(FORM, financialDocument);
			request.setAttribute(getSelectedDocumentAttrName(), financialDocument);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionCorrectInvoice(final HttpServletRequest request,
			final HttpServletResponse response)  {
		try {
			request.getRequestDispatcher("correction.app").forward(request, response);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void performActionDeleteDocuments(final UserContext userContext,
			final HttpServletRequest request) {
		final List<Integer> documentIds = getSelectedRecordsIds(request);
		deleteInvoice(documentIds);
		searchDocumentsByFilter(userContext, request);
	}

	private void performActionDelProductRow(final UserContext userContext,
			final VO documentVO, final HttpServletRequest request) {
		final int selectedProduct = Translator.parseInt(request
				.getParameter(EVENT_PARAM));
		documentVO.getDocumentProducts().remove(selectedProduct);
		recalculateDocument(documentVO);
		request.setAttribute(getSelectedDocumentAttrName(), documentVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionEdit(final UserContext userContext, final HttpServletRequest request) {

		final String documentId = request.getParameter(EVENT_PARAM);
		if (documentId != null) {
			final VO document = getFinancialDocument(documentId);
			document.applyPermissions(userContext.getUserName());
			getCorrections(Lists.newArrayList(document));
			request.getSession().setAttribute(FORM, document);
			request.setAttribute(getSelectedDocumentAttrName(), document);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionMain(final UserContext userContext, final VO documentVO, final HttpServletRequest request) {
		documentVO.clearProductList();
		request.setAttribute(EVENT, Event.MAIN);
		request.setAttribute(getSelectedDocumentAttrName(), documentVO);
	}

	private void performActionNew(final UserContext userContext, final HttpServletRequest request) {

		final VO document = createNewDocument();
		document.setNumber(getNextDocumentNumber(userContext.getUserName()));
		final Date currentDate = getCurrentDate();
		document.setCreateDate(currentDate);
		document.setPaymentDate(getPaymentDate(currentDate,
				PaymentType.CASH));
		document.setStatus(InvoiceStatus.DRAFT);
		document.applyPermissions(userContext.getUserName());
		request.getSession().setAttribute(FORM, document);
		request.setAttribute(getSelectedDocumentAttrName(), document);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionPaymentEntered(final UserContext userContext, final VO documentVO, final HttpServletRequest request) {
		request.setAttribute(getSelectedDocumentAttrName(), documentVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionPrint(final UserContext userContext, final VO documentVO, final HttpServletRequest request,
			final HttpServletResponse response) {
		try {
			final String sessionId = request.getSession().getId();
			final Optional<String> filePath = printDocument(Lists.newArrayList(documentVO), sessionId);
			request.setAttribute(getSelectedDocumentAttrName(), documentVO);
			request.setAttribute(EVENT, Event.EDIT);
			sendFileBack(filePath, response);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void performActionPrintSelected(final UserContext userContext, final HttpServletRequest request, final HttpServletResponse response) {
		try {
			final List<VO> selectedDocuments = getSelectedRecords(request);
			final String sessionId = request.getSession().getId();
			final Optional<String> filePath = printDocument(selectedDocuments, sessionId);
			searchDocumentsByFilter(userContext, request);
			sendFileBack(filePath, response);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void performActionSave(final UserContext userContext, final VO documentVO, final HttpServletRequest request) {
		documentVO.setStatus(InvoiceStatus.DRAFT);
		saveFinancialDocument(userContext, documentVO);
		searchDocumentsByFilter(userContext, request);
	}

	private void performActionSearch(final UserContext userContext,
			final InvoiceFilterVO documentFilterVO, final HttpServletRequest request) {
		documentFilterVO.populate(getDocumentFilterAttrName(), request);
		documentFilterVO.setUser(userContext.getUserName());
		request.getSession().setAttribute(getDocumentFilterAttrName(), documentFilterVO);
		searchDocumentsByFilter(userContext, request);
	}

	private void performActionSettleDocument(final UserContext userContext, final VO documentVO, final HttpServletRequest request) {
		final String documentId = request.getParameter(EVENT_PARAM);
		settleFinancialDocument(documentId, userContext);
		searchDocumentsByFilter(userContext, request);
	}

	private void performActionSort(final UserContext userContext, final HttpServletRequest request) {
		final String sortColumn = request.getParameter(SORT_COLUMN);
		updateSortColumn(sortColumn, request);
		searchDocumentsByFilter(userContext, request);
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
			selectsMap.put("productDictionary", productService.getProductDictionary(false, userName));
			selectsMap.put("paymentTypeDictionary",
					PaymentType.getComboElementList(false));
			selectsMap.put("sellerDictionary", sellerDao.getSellerDictionary(false, userName));
			selectsMap.put("bankAccountDictionary", sellerDao.getSellerBankAccounts(userName));

		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		return selectsMap;
	}

	protected abstract Optional<String> printDocument(final List<VO> documentVO, final String sessionId) throws Exception;

	protected String process(final HttpServletRequest request, final HttpServletResponse response) {

		final UserContext userContext = getUserContext(request);

		@SuppressWarnings("unchecked")
		final VO documentVO = (VO) getForm(getDocumentClass(), request);
		documentVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);

		switch (event) {
		case MAIN:
			performActionMain(userContext, documentVO, request);
			break;
		case SAVE:
			performActionSave(userContext, documentVO, request);
			break;
		case EDIT:
			performActionEdit(userContext, request);
			break;
		case NEW:
			performActionNew(userContext, request);
			break;
		case ADD_PRODUCT_ROW:
			performActionAddProductRow(userContext, documentVO, request);
			break;
		case DEL_PRODUCT_ROW:
			performActionDelProductRow(userContext, documentVO, request);
			break;
		case CHANGE_PRODUCT:
			performActionChangeProduct(userContext, documentVO, request);
			break;
		case PAID_ENTERED:
			performActionPaymentEntered(userContext, documentVO, request);
			break;
		case DELETE_SELECTED:
			performActionDeleteDocuments(userContext, request);
			break;
		case SEARCH:
			performActionSearch(userContext, documentVO.getDocumentFilter(), request);
			break;
		case BACK:
			searchDocumentsByFilter(userContext, request);
			break;
		case PRINT:
			performActionPrint(userContext, documentVO, request, response);
			break;
		case CHANGE_PAYMENT_TYPE:
			performActionChangePaymentType(userContext, documentVO, request);
			break;
		case SETTLE_INVOICE:
			performActionSettleDocument(userContext, documentVO, request);
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
		case PRINT_SELECTED:
			performActionPrintSelected(userContext, request, response);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap(userContext));
		return getTargetPage();
	}

	protected abstract void recalculateDocument(final VO documentVO);

	protected abstract void recalculateProduct(final Product product);

	protected abstract void saveFinancialDocument(final UserContext userContext, final VO documentVO);

	private void searchDocumentsByFilter(final UserContext userContext, final HttpServletRequest request) {
		final InvoiceFilterVO documentFilterVO = (InvoiceFilterVO) request.getSession().getAttribute(getDocumentFilterAttrName());
		final PageInfo pageInfo = getCurrentPageInfo(request);
		try {
			final ResultPage<VO> documentList = getFinancialDocumentList(userContext, documentFilterVO, pageInfo);
			final List<CorrectionVO> correctionList = getCorrections(documentList.getRecords());
			applyPermissions(documentList.getRecords(), correctionList, userContext.getUserName());
			request.getSession().setAttribute(getResultRecordsListAttrName(), documentList);
			request.getSession().setAttribute(CORRECTION_LIST, correctionList);
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void sendFileBack(final Optional<String> filePath, final HttpServletResponse response) {
		if(filePath.isPresent()){
			fileResponse.sendToClient(filePath.get(), response);
		}
	}

	protected abstract void settleFinancialDocument(final String documentId, final UserContext userContext);

}
