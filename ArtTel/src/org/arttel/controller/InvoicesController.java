package org.arttel.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.controller.vo.CorrectionVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.dao.CorrectionDAO;
import org.arttel.dao.InvoiceDAO;
import org.arttel.exception.DaoException;
import org.arttel.generator.invoice.InvoiceGenerator;
import org.arttel.generator.invoice.InvoiceGeneratorOld;
import org.arttel.service.InvoiceService;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.arttel.ui.TableHeader;
import org.arttel.util.DecimalWriter;
import org.arttel.util.InvoiceNumberGenerator;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Controller
public class InvoicesController extends FinancialDocumentController<InvoiceVO, InvoceProductVO>{

	private static final String SELECTED_INVOICE = "selectedInvoice";
	private static final String INVOICE_LIST = "invoiceList";
	private static final String INVOICE_FILTER = "invoiceFilter";

	@Autowired
	private InvoiceDAO invoiceDao;

	//TODO remove after pdfPrinter on for invoice
	@Autowired
	private InvoiceGeneratorOld invoiceGeneratorOld;

	@Autowired
	private InvoiceGenerator invoiceGenerator;

	@Autowired
	private CorrectionDAO correctionDao;

	@Autowired
	private InvoiceNumberGenerator invoiceNumberGenerator;

	@Autowired
	private InvoiceService invoiceService;

	private final Logger log = Logger.getLogger(InvoicesController.class);

	@Override
	protected void clearInvoiceRecordsIds(final InvoiceVO invoice) {
		for(final InvoceProductVO product : invoice.getDocumentProducts()){
			product.setInvoiceProductId(null);
		}
	}

	@Override
	protected InvoiceVO createNewDocument() {
		return new InvoiceVO();
	}

	@Override
	protected void deleteInvoice(final List<Integer> invoiceIds) {
		invoiceService.deleteInvoice(invoiceIds);
	}

	private CorrectionVO getCorrection(final InvoiceVO invoice) {
		try {
			final CorrectionVO correction =
					correctionDao.getCorrectionForInvoice(invoice.getDocumentId());
			if(correction != null){
				correction.setInvoiceNumber(invoice.getNumber());
			}
			return correction;
		} catch (final DaoException e) {
			log.error("Dao Exception", e);
			return null;
		}
	}

	@Override
	protected List<CorrectionVO> getCorrections(final List<InvoiceVO> invoiceList) {
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

	@Override
	protected Class<InvoiceVO> getDocumentClass() {
		return InvoiceVO.class;
	}

	@Override
	protected String getDocumentFilterAttrName() {
		return INVOICE_FILTER;
	}

	@Override
	protected InvoiceVO getFinancialDocument(final String invoiceId) {
		return invoiceService.getInvoice(invoiceId);
	}

	@Override
	protected ResultPage<InvoiceVO> getFinancialDocumentList(final UserContext userContext, final InvoiceFilterVO invoiceFilterVO,
			final PageInfo pageInfo) throws DaoException {
		return invoiceService.getInvoiceList(invoiceFilterVO, pageInfo, userContext.getUserName());
	}

	@Override
	protected TableHeader getModelDefaultHeader() {
		return InvoiceVO.resultTableHeader;
	}

	@Override
	protected String getNextDocumentNumber(final String userName) {
		return invoiceNumberGenerator.getNextNumber(userName);
	}

	@Override
	protected String getResultRecordsListAttrName() {
		return INVOICE_LIST;
	}

	@Override
	protected String getSelectedDocumentAttrName() {
		return SELECTED_INVOICE;
	}

	@Override
	protected String getTableHeaderAttrName() {
		return "invoicesTableHeader";
	}

	@Override
	protected String getTargetPage() {
		return "invoices";
	}

	@Override
	protected Optional<String> printDocument(final List<InvoiceVO> invoices, final String sessionId) throws Exception {
		final Optional<String> generateInvoices = invoiceGeneratorOld.generateInvoices(invoices, sessionId);
		//TODO in progress
		//		final Optional<String> generateInvoices = invoiceGenerator.printDocuments(invoices, sessionId);
		return generateInvoices;
	}

	@Override
	@RequestMapping("/invoices.app")
	public String process(final HttpServletRequest request, final HttpServletResponse response) {
		return super.process(request, response);
	}

	@Override
	protected void recalculateDocument(final InvoiceVO invoiceVO) {
		BigDecimal vatAmount = new BigDecimal(0), netAmount = new BigDecimal(0);
		for (final InvoceProductVO product : invoiceVO.getDocumentProducts()) {
			vatAmount = vatAmount.add(Translator.getDecimal(product.getVatAmount()));
			netAmount = netAmount.add(Translator.getDecimal(product.getNetSumAmount()));
		}
		invoiceVO.setNetAmount(netAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
		invoiceVO.setVatAmount(vatAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
		invoiceVO.setPaidWords(DecimalWriter.getDecimalSpoken(invoiceVO.getGrossAmount()));
	}

	@Override
	protected void recalculateProduct(final InvoceProductVO invoiceProduct) {

		if (StringUtils.isNotEmpty(invoiceProduct.getQuantity())) {
			final ProductVO productDefinition = invoiceProduct.getProductDefinition();
			final BigDecimal netSinglePrice = new BigDecimal(productDefinition.getNetPrice());
			final BigDecimal quantity = new BigDecimal(invoiceProduct.getQuantity());
			final BigDecimal netAmount = netSinglePrice.multiply(quantity);
			final BigDecimal vatRate = new BigDecimal(productDefinition.getVatRate().getValue());
			final BigDecimal vatAmount = netAmount.multiply(vatRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			final BigDecimal grossSumAmount = netAmount.add(vatAmount);
			invoiceProduct.setNetSumAmount(netAmount.toPlainString());
			invoiceProduct.setVatAmount(vatAmount.toPlainString());
			invoiceProduct.setGrossSumAmount(grossSumAmount.toPlainString());
		}
	}

	@Override
	protected void saveFinancialDocument(final UserContext userContext, final InvoiceVO invoiceVO) {
		invoiceService.save(invoiceVO, userContext.getUserName());
	}

	@Override
	protected void settleFinancialDocument(final String invoiceId, final UserContext userContext) {
		invoiceService.settleInvoice(invoiceId, userContext.getUserName());
	}
}
