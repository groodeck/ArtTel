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
import org.arttel.controller.vo.InvoceProductCorrectionVO;
import org.arttel.controller.vo.InvoceProductCorrectionVOFactory;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.dao.CityDAO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.CorrectionDAO;
import org.arttel.dao.InvoiceDAO;
import org.arttel.dao.InvoiceProductCorrectionDAO;
import org.arttel.dao.ProductDAO;
import org.arttel.dao.SellerDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.dictionary.UnitType;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.exception.DaoException;
import org.arttel.generator.correction.CorrectionGenerator;
import org.arttel.util.CorrectionNumberGenerator;
import org.arttel.util.DecimalWriter;
import org.arttel.util.Translator;
import org.arttel.view.ComboElement;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Controller
public class CorrectionController extends BaseController {

	@Autowired
	private CityDAO cityDao;

	@Autowired
	private ClientDAO clientDao;

	@Autowired
	private InvoiceDAO invoiceDao;

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private CorrectionGenerator correctionGenerator;

	@Autowired
	private CorrectionDAO correctionDao;

	@Autowired
	private InvoiceProductCorrectionDAO invoiceProductCorrectionDao;
	
	@Autowired
	private SellerDAO sellerDao;

	private String targetPage = jspContextPrefix + "correction.jsp";
	
	private final Logger log = Logger.getLogger(CorrectionController.class);

	public enum Event {
		CORRECT, SAVE, EDIT, ADD_PRODUCT_ROW, DEL_PRODUCT_ROW,CHANGE_PRODUCT, BACK, DELETE, PRINT, CHANGE_PAYMENT_TYPE;
	}

	private static final String SELECTED_INVOICE = "selectedInvoice";

	@RequestMapping("/correction.app")
	public String process(HttpServletRequest request, HttpServletResponse response) {

		final UserContext userContext = getUserContext(request);

		InvoiceVO invoiceVO = (InvoiceVO) getForm(InvoiceVO.class, request);
		populateForm(invoiceVO, request);

		final Event event = getEvent(request, Event.CORRECT);

		switch (event) {
		case CORRECT:
			performActionCorrect(userContext, request);
			break;
		case SAVE:
			performActionSave(userContext, invoiceVO, request, response);
			break;
		case EDIT:
			performActionEdit(userContext, request);
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
		case BACK:
			performActionBackToInvoice(userContext, request, response);
			break;
		case DELETE:
			performActionDeleteCorrection(userContext, request, response);
			break;
		case PRINT:
			performActionPrint(userContext, invoiceVO, request);
			break;
		case CHANGE_PAYMENT_TYPE:
			performActionChangePaymentType(userContext, invoiceVO, request);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap(userContext));
		return "correction";
	}

	private void performActionPrint(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		try {
			final String sessionId = request.getSession().getId();
			final String correctionLink = correctionGenerator.generateCorrection(invoiceVO, sessionId);
			if(StringUtils.isNotEmpty(correctionLink)){
				final int invoiceId = Translator.parseInt(invoiceVO.getInvoiceId());
				invoiceDao.setInvoiceStatus(invoiceId, InvoiceStatus.PENDING);
				invoiceVO.setStatus(InvoiceStatus.PENDING);
			}
			request.setAttribute(RESULT_FILE_LINK, correctionLink);
			request.setAttribute(SELECTED_INVOICE, invoiceVO);
			request.setAttribute(EVENT, Event.EDIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void performActionDeleteCorrection(final UserContext userContext,
			final HttpServletRequest request, final HttpServletResponse response) {

		final String correctionId = request.getParameter(EVENT_PARAM);
		try {
			if (correctionId != null) {
				correctionDao.deleteCorrectionById(correctionId);
			}
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		performActionBackToInvoice(userContext, request, response);
	}
	
	private void populateForm(InvoiceVO invoiceVO, HttpServletRequest request) {
		if(invoiceVO.hasCorrection()){
			invoiceVO.getCorrection().populate(request);
			populateProductsCorrection(invoiceVO.getInvoiceProducts(), request);
		}
	}

	private void populateProductsCorrection(final List<InvoceProductVO> invoiceProducts, 
			final HttpServletRequest request) {

		final String prefix = "product";
		for(int i =0 ; i<invoiceProducts.size(); i++){
			final InvoceProductCorrectionVO product = invoiceProducts.get(i).getCorrection();
			product.populate(request, prefix+"["+i+"].correction.");
		}
	}
	
	private void prepareCorrection(final InvoiceVO invoice, final UserContext userContext) {
		final CorrectionVO correction = new CorrectionVO();
		final Date currentDate = getCurrentDate();
		correction.setCreateDate(currentDate);
		correction.setCorrectionNumber(getNextCorrectionNumber());
		correction.setInvoiceId(invoice.getInvoiceId());
		correction.setNetAmount(invoice.getNetAmount());
		correction.setVatAmount(invoice.getVatAmount());
		correction.setPaid(invoice.getPaid());
		final PaymentType paymentType = invoice.getPaymentType();
		correction.setPaymentType(paymentType);
		correction.setPaymentDate(getPaymentDate(currentDate, paymentType));
		correction.applyPermissions(userContext.getUserName());
		invoice.setCorrection(correction);
		invoice.prepareProductsCorrection();
	}
	
	private String getNextCorrectionNumber() {
		return new CorrectionNumberGenerator().getNextNumber();
	}
	
	private void performActionChangePaymentType(UserContext userContext,
			InvoiceVO invoiceVO, HttpServletRequest request) {

		final CorrectionVO correction = invoiceVO.getCorrection();
		final Date createDate = correction.getCreateDate();
		final PaymentType paymentType = correction.getPaymentType();
		final Date paymentDate = getPaymentDate(createDate, paymentType);
		correction.setPaymentDate(paymentDate);

		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionDelProductRow(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request) {
		final int selectedProduct = Translator.parseInt(request
				.getParameter(EVENT_PARAM));
		invoiceVO.getInvoiceProducts().remove(selectedProduct);
		recalculateCorrection(invoiceVO);
		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionChangeProduct(UserContext userContext,
			InvoiceVO invoiceVO, HttpServletRequest request) {
		final int selectedProduct = Translator.parseInt(request.getParameter(EVENT_PARAM));
		try {
			final ProductVO productDefinition = getProductDefinition(request, selectedProduct);
			final InvoceProductVO invoiceProduct = invoiceVO.getProduct(selectedProduct);
			final InvoceProductCorrectionVO invoiceProductCorrection = invoiceProduct.getCorrection();
			invoiceProductCorrection.setProductDefinition(productDefinition);
			recalculateProductCorrection(invoiceProduct);
			recalculateCorrection(invoiceVO);
			request.setAttribute(SELECTED_INVOICE, invoiceVO);
			request.setAttribute(EVENT, Event.EDIT);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void recalculateCorrection(final InvoiceVO invoiceVO) {
		BigDecimal vatAmount = new BigDecimal(0), netAmount = new BigDecimal(0);
		for (final InvoceProductVO product : invoiceVO.getInvoiceProducts()) {
			final InvoceProductCorrectionVO productCorrection = product.getCorrection();
			vatAmount = vatAmount.add(Translator.getDecimal(productCorrection.getVatAmount()));
			netAmount = netAmount.add(Translator.getDecimal(productCorrection.getNetSumAmount()));
		}
		final CorrectionVO correction = invoiceVO.getCorrection();
		correction.setNetAmount(netAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
		correction.setVatAmount(vatAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
		
		final BigDecimal netSumDiff = netAmount.subtract(Translator.getDecimal(invoiceVO.getNetAmount()));
		final BigDecimal vatSumDiff = vatAmount.subtract(Translator.getDecimal(invoiceVO.getVatAmount()));
		final BigDecimal grossAmount = Translator.getDecimal(correction.getGrossAmount());
		final BigDecimal grossSumDiff = grossAmount.subtract(Translator.getDecimal(invoiceVO.getGrossAmount()));
		correction.setNetAmountDiff(netSumDiff.toPlainString());
		correction.setVatAmountDiff(vatSumDiff.toPlainString());
		correction.setGrossAmountDiff(grossSumDiff.toPlainString());

		correction.setPaidWords(DecimalWriter.getDecimalSpoken(correction.getGrossAmountDiff()));
	}

	private void recalculateProductCorrection(final InvoceProductVO invoiceProduct) {
		final InvoceProductCorrectionVO correction = invoiceProduct.getCorrection();
		if (StringUtils.isNotEmpty(correction.getQuantity())) {
			final ProductVO productDefinition = correction.getProductDefinition();
			final BigDecimal netSinglePrice = new BigDecimal(productDefinition.getNetPrice());
			final BigDecimal quantity = new BigDecimal(correction.getQuantity());
			final BigDecimal netSumAmount = netSinglePrice.multiply(quantity);
			final BigDecimal vatRate = new BigDecimal(productDefinition.getVatRate().getIdn());
			final BigDecimal vatSumAmount = netSumAmount.multiply(vatRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			final BigDecimal grossSumAmount = netSumAmount.add(vatSumAmount);
			correction.setNetSumAmount(netSumAmount.toPlainString());
			correction.setVatAmount(vatSumAmount.toPlainString());
			correction.setGrossSumAmount(grossSumAmount.toPlainString());
			
			final BigDecimal quantityDiff = quantity.subtract(Translator.getDecimal(invoiceProduct.getQuantity()));
			final BigDecimal netSumDiff = netSumAmount.subtract(Translator.getDecimal(invoiceProduct.getNetSumAmount()));
			final BigDecimal vatSumDiff = vatSumAmount.subtract(Translator.getDecimal(invoiceProduct.getVatAmount()));
			final BigDecimal grossSumDiff = grossSumAmount.subtract(Translator.getDecimal(invoiceProduct.getGrossSumAmount()));
			correction.setQuantityDiff(quantityDiff.toPlainString());
			correction.setNetSumAmountDiff(netSumDiff.toPlainString());
			correction.setVatAmountDiff(vatSumDiff.toPlainString());
			correction.setGrossSumAmountDiff(grossSumDiff.toPlainString());
		}
	}

	private ProductVO getProductDefinition(HttpServletRequest request,
			final int selectedProduct) throws DaoException {
		final String changedParamName = 
				"product[" + selectedProduct+ "].correction.productDefinition.productId";
		final String productId = request.getParameter(changedParamName);
		final ProductVO productDefinition = 
				productDao.getProductById(productId);
		return productDefinition;
	}

	private void performActionAddProductRow(UserContext userContext,
			InvoiceVO invoiceVO, HttpServletRequest request) {
		
		invoiceVO.addNewInvoiceProduct();
		final InvoceProductVO invoiceProduct = 
				Iterables.getLast(invoiceVO.getInvoiceProducts(), null);
		final InvoceProductCorrectionVO productCorrection = 
				new InvoceProductCorrectionVOFactory().correctInvoiceProduct(invoiceProduct);
		invoiceProduct.setCorrection(productCorrection);
		
		request.setAttribute(SELECTED_INVOICE, invoiceVO);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionEdit(final UserContext userContext,
			final HttpServletRequest request) {

		final String correctionId = request.getParameter(EVENT_PARAM);
		try {
			if (correctionId != null) {
				final CorrectionVO correction = correctionDao.getCorrectionById(correctionId);
				final InvoiceVO invoiceDetails = getInvoiceDetails(correction.getInvoiceId());
				invoiceDetails.setCorrection(correction);
				correction.applyPermissions(userContext.getUserName());
				request.getSession().setAttribute(FORM, invoiceDetails);
				request.setAttribute(SELECTED_INVOICE, invoiceDetails);
			}
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private InvoiceVO getInvoiceDetails(final String invoiceId)
			throws DaoException {
		final InvoiceVO invoice = invoiceDao.getInvoiceById(invoiceId);
		deleteProductsWithoutCorrection(invoice);
		addCorrectionAddedProducts(invoice);
		return invoice;
	}

	private void deleteProductsWithoutCorrection(InvoiceVO invoice) {
		final List<InvoceProductVO> invoiceProducts = invoice.getInvoiceProducts();
		final List<InvoceProductVO> productsWithoutCorrection = Lists.newArrayList();
		for(final InvoceProductVO product : invoiceProducts){
			if(product.getCorrection() == null){
				productsWithoutCorrection.add(product);
			}
		}
		invoiceProducts.removeAll(productsWithoutCorrection);
	}

	private List<InvoceProductVO> addCorrectionAddedProducts(final InvoiceVO invoice){
		
		final List<InvoceProductVO> resultList = Lists.newArrayList();
		try {
			final List<InvoceProductCorrectionVO>correctionAddedProducts = 
					invoiceProductCorrectionDao.getCorrectionAddedProducts(invoice.getInvoiceId());
			for(final InvoceProductCorrectionVO correctionAddedProduct : correctionAddedProducts){
				invoice.addNewInvoiceProduct();
				final InvoceProductVO invoiceProduct = Iterables.getLast(invoice.getInvoiceProducts());
				invoiceProduct.setCorrection(correctionAddedProduct);
				resultList.add(invoiceProduct);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	private void performActionCorrect(final UserContext userContext,
			final HttpServletRequest request) {

		try {
			final String invoiceId = request.getParameter(EVENT_PARAM);
			final InvoiceVO invoiceVO = invoiceDao.getInvoiceById(invoiceId);
			prepareCorrection(invoiceVO, userContext);
			request.getSession().setAttribute(FORM, invoiceVO);
			request.setAttribute(SELECTED_INVOICE, invoiceVO);
			request.setAttribute(EVENT, Event.EDIT);
		} catch (DaoException e) {
			log.error("Dao Exception", e);
		}
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

	private void performActionSave(final UserContext userContext,
			final InvoiceVO invoiceVO, final HttpServletRequest request, 
			final HttpServletResponse response) {
		try {
			if(hasChanged(invoiceVO)){
			//TODO:
			}
			correctionDao.save(invoiceVO, userContext.getUserName());
			performActionBackToInvoice(userContext, request, response);
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
	}

	private void performActionBackToInvoice(final UserContext userContext,
			final HttpServletRequest request, final HttpServletResponse response) {
		
		try {
			request.setAttribute(EVENT, org.arttel.controller.InvoicesController.Event.SEARCH);
			request.getRequestDispatcher("invoices.app").forward(request, response);
		} catch (ServletException e) {
			log.error("ServletException", e);
		} catch (IOException e) {
			log.error("IOException", e);
		}
		
	}

	private boolean hasChanged(InvoiceVO invoiceVO) {
		// TODO zaimplementowac metod� equals i uzyc
		return false;
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
			selectsMap.put("cityDictionary", cityDao
					.getCityDictionary(true, DictionaryPurpose.forInvoice));
			final String userName = userContext.getUserName();
			final ClientFilterVO clientFilter = 
					new ClientFilterVO(userName, DictionaryPurpose.forInvoice);
			selectsMap.put("clientDictionary", clientDao.getClientDictionary(false, clientFilter));
			selectsMap.put("unitTypesDictionary",
					UnitType.getComboElementList(false));
			selectsMap.put("productDictionary", productDao.getProductDictionary(true, userName));
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
