package org.arttel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.converter.InvoiceConverter;
import org.arttel.dao.CorrectionDAO;
import org.arttel.dao.InvoiceDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.entity.Invoice;
import org.arttel.exception.DaoException;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

@Component
@Transactional
public class InvoiceService {

	private final static Logger log = Logger.getLogger(InvoiceService.class);

	@Autowired
	private InvoiceDAO invoiceDao;

	@Autowired
	private InvoiceConverter invoiceConverter;

	@Autowired
	private CorrectionDAO correctionDao;

	@Autowired
	private DealingService dealingService;

	@Autowired
	private SellerService sellerService;

	public void deleteInvoice(final List<Integer> invoiceIds) {
		for(final Integer invoiceId : invoiceIds){
			correctionDao.removeCorrectionForInvoice(invoiceId.toString());
			invoiceDao.deleteInvoice(invoiceId.toString());
		}
	}

	public Optional<InvoiceVO> findByDocumentNumber(final String documentNumber, final String userName) {
		final List<Invoice> invoiceList = invoiceDao.findByDocumentNumber(documentNumber, userName);
		if(invoiceList.isEmpty()){
			return Optional.absent();
		} else {
			final Invoice singleInvoice = Iterables.getOnlyElement(invoiceList);
			return Optional.of(invoiceConverter.convert(singleInvoice));
		}
	}

	public InvoiceVO getInvoice(final String invoiceId) {
		final Invoice entity = invoiceDao.getInvoiceById(invoiceId);
		return invoiceConverter.convert(entity);
	}

	public ResultPage<InvoiceVO> getInvoiceList(final InvoiceFilterVO invoiceFilterVO, final PageInfo pageInfo,
			final String user) throws DaoException {

		final ResultPage<Invoice> entityList = invoiceDao.getInvoiceList(invoiceFilterVO, pageInfo);

		final ResultPage<InvoiceVO> resultList = invoiceConverter.convert(entityList);
		for(final InvoiceVO invoiceVO : resultList.getRecords()){
			invoiceVO.applyPermissions(user);
		}
		return resultList;
	}

	public void save(final InvoiceVO invoiceVO, final String userName) {
		final Invoice entity = invoiceConverter.convert(invoiceVO, userName);
		invoiceDao.save(entity);
	}

	public void setInvoicePending(final String invoiceId) {
		final Invoice invoice = invoiceDao.getInvoiceById(invoiceId);
		invoice.setDocumentStatus(InvoiceStatus.PENDING.getIdn());
	}

	public void settleInvoice(final String invoiceId, final String userName) {
		final Invoice invoice = invoiceDao.getInvoiceById(invoiceId);
		if(sellerService.checkGenerateDealingOnInvoiceSettle(invoice.getSellerId())){
			dealingService.generateDealing(invoice);
		}
		invoice.setDocumentStatus(InvoiceStatus.SETTLED.getIdn());

	}
}
