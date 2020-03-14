package org.arttel.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.controller.vo.BillProductVO;
import org.arttel.controller.vo.BillVO;
import org.arttel.controller.vo.CorrectionVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.dao.CorrectionDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.exception.DaoException;
import org.arttel.generator.bill.BillGenerator;
import org.arttel.service.BillService;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.arttel.ui.TableHeader;
import org.arttel.util.BillNumberGenerator;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Controller
public class BillsController extends FinancialDocumentController<BillVO, BillProductVO>{

	private static final String SELECTED_BILL = "selectedBill";
	private static final String BILL_LIST = "billList";
	private static final String BILL_FILTER = "billFilter";

	@Autowired
	private BillGenerator billGenerator;

	@Autowired
	private CorrectionDAO correctionDao;

	@Autowired
	private BillNumberGenerator invoiceNumberGenerator;

	@Autowired
	private BillService billService;

	private final Logger log = Logger.getLogger(BillsController.class);

	@Override
	protected void clearInvoiceRecordsIds(final BillVO invoice) {
		for(final BillProductVO product : invoice.getDocumentProducts()){
			product.setInvoiceProductId(null);
		}
	}

	@Override
	protected BillVO createNewDocument() {
		return new BillVO();
	}

	@Override
	protected void deleteInvoice(final List<Integer> billIds) {
		billService.deleteBill(billIds);
	}

	private CorrectionVO getCorrection(final BillVO invoice) {
		//		try {
		//			final CorrectionVO correction = correctionDao.getCorrectionForInvoice(invoice.getInvoiceId());
		//			if(correction != null){
		//				correction.setInvoiceNumber(invoice.getNumber());
		//			}
		//			return correction;
		//		} catch (final DaoException e) {
		//			log.error("Dao Exception", e);
		//			return null;
		//		}
		return null;
	}

	@Override
	protected List<CorrectionVO> getCorrections(final List<BillVO> billList) {
		final List<CorrectionVO> resultList = Lists.newArrayList();
		//		for(final BillVO bill : billList){
		//			final CorrectionVO correction = getCorrection(bill);
		//			if(correction != null){
		//				bill.setCorrection(correction);
		//				resultList.add(correction);
		//			}
		//		}
		return resultList;
	}

	@Override
	protected Class<BillVO> getDocumentClass() {
		return BillVO.class;
	}

	@Override
	protected String getDocumentFilterAttrName() {
		return BILL_FILTER;
	}

	@Override
	protected BillVO getFinancialDocument(final String billId) {
		return billService.getBill(billId);
	}

	@Override
	protected ResultPage<BillVO> getFinancialDocumentList(final UserContext userContext, final InvoiceFilterVO documentFilterVO,
			final PageInfo pageInfo) throws DaoException {
		return billService.getBillList(documentFilterVO, pageInfo, userContext.getUserName());
	}

	@Override
	protected TableHeader getModelDefaultHeader() {
		return BillVO.getResultTableHeader();
	}

	@Override
	protected String getNextDocumentNumber(final String userName) {
		return invoiceNumberGenerator.getNextNumber(userName);
	}

	@Override
	protected String getResultRecordsListAttrName() {
		return BILL_LIST;
	}

	@Override
	protected String getSelectedDocumentAttrName() {
		return SELECTED_BILL;
	}

	@Override
	protected String getTableHeaderAttrName() {
		return "billsTableHeader";
	}

	@Override
	protected String getTargetPage() {
		return "bills";
	}

	@Override
	protected Optional<String> printDocument(final List<BillVO> bills, final String sessionId) throws Exception {
		return billGenerator.printDocuments(bills, sessionId);
	}

	@Override
	@RequestMapping("/bills.app")
	public String process(final HttpServletRequest request, final HttpServletResponse response) {
		return super.process(request, response);
	}

	@Override
	protected void recalculateDocument(final BillVO billVO) {
		BigDecimal amount = new BigDecimal(0);
		for (final BillProductVO product : billVO.getDocumentProducts()) {
			amount = amount.add(Translator.getDecimal(product.getSumAmount()));
		}
		billVO.setAmount(amount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
	}

	@Override
	protected void recalculateProduct(final BillProductVO billProduct) {

		if (StringUtils.isNotEmpty(billProduct.getQuantity())) {
			final ProductVO productDefinition = billProduct.getProductDefinition();
			final BigDecimal singlePrice = new BigDecimal(productDefinition.getNetPrice());
			final BigDecimal quantity = new BigDecimal(billProduct.getQuantity());
			final BigDecimal amount = singlePrice.multiply(quantity);
			billProduct.setSumAmount(amount.toPlainString());
		}
	}

	@Override
	protected void saveFinancialDocument(final UserContext userContext, final BillVO billVO) {
		billService.save(billVO, userContext.getUserName());
	}

	@Override
	protected void settleFinancialDocument(final String billId, final UserContext userContext) {
		billService.setBillStatus(billId, InvoiceStatus.SETTLED);
	}
}
