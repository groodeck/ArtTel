package org.arttel.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.controller.vo.BillVO;
import org.arttel.controller.vo.DealingVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.filter.DealingFilterVO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.DealingDAO;
import org.arttel.dictionary.DealingType;
import org.arttel.dictionary.DocumentType;
import org.arttel.entity.Bill;
import org.arttel.entity.Client;
import org.arttel.entity.Invoice;
import org.arttel.exception.DaoException;
import org.arttel.util.Translator;
import org.assertj.core.util.Lists;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;

@Component
@Transactional
public class DealingService {

	private final static Logger log = Logger.getLogger(DealingService.class);

	@Autowired
	private DealingDAO dealingDao;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private BillService billService;

	@Autowired
	private ClientDAO clientDao;

	public void deleteDealingById(final String dealingId) {
		try {
			dealingDao.deleteDealingById(dealingId);
		} catch (final DaoException e) {
			log.error("DaoException",e);
			e.printStackTrace();
		}
	}

	private String determineDealingUser(final DealingVO dealingVO) throws DaoException {
		final String dealingId = dealingVO.getDealingId();
		if (StringUtils.isEmpty(dealingId)){
			return null;
		} else {
			final DealingVO dealing = dealingDao.getDealingById(dealingId);
			return dealing.getUser();
		}
	}

	public void generateDealing(final Bill bill) {
		final DealingVO dealingVO = new DealingVO();
		dealingVO.setDealingType(DealingType.INCOME);
		dealingVO.setDate(LocalDate.now().toDate());
		dealingVO.setIncomeClientId(bill.getClient().getClientId().toString());
		dealingVO.setIncomeClientName(bill.getClient().getClientDesc());
		dealingVO.setAmount(bill.getAmount().toString());
		dealingVO.setComments1(bill.getComments());
		dealingVO.setComments2(bill.getAdditionalComments());
		dealingVO.setComments3(StringUtils.EMPTY);
		dealingVO.setUserName(bill.getUser());
		dealingVO.setDocumentId(bill.getDocumentId());
		dealingVO.setCity(bill.getCity());
		dealingVO.setDocumentType(DocumentType.BILL);
		try {
			dealingDao.create(dealingVO, bill.getUser());
		} catch (final DaoException e) {
			log.error("DealingDAO create error", e);
		}
	}

	public void generateDealing(final Invoice invoice) {
		final DealingVO dealingVO = new DealingVO();
		dealingVO.setDealingType(DealingType.INCOME);
		dealingVO.setDate(LocalDate.now().toDate());
		dealingVO.setIncomeClientId(invoice.getClient().getClientId().toString());
		dealingVO.setIncomeClientName(invoice.getClient().getClientDesc());
		dealingVO.setAmount(invoice.getNetAmount().add(invoice.getVatAmount()).toString());
		dealingVO.setComments1(invoice.getComments());
		dealingVO.setComments2(invoice.getAdditionalComments());
		dealingVO.setComments3(StringUtils.EMPTY);
		dealingVO.setUserName(invoice.getUser());
		dealingVO.setDocumentId(invoice.getDocumentId());
		dealingVO.setCity(invoice.getCity());
		dealingVO.setDocumentType(DocumentType.INVOICE);
		try {
			dealingDao.create(dealingVO, invoice.getUser());
		} catch (final DaoException e) {
			log.error("DealingDAO create error", e);
		}
	}

	public DealingVO getDealingById(final String dealingId) {
		try {
			return dealingDao.getDealingById(dealingId);
		} catch (final DaoException e) {
			log.error("DaoException",e);
			return null;
		}
	}

	public List<DealingVO> getDealingList(final DealingFilterVO dealingFilterVO) {
		try{
			return dealingDao.getDealingList(dealingFilterVO);
		} catch (final DaoException e) {
			log.error("DaoException", e);
			return Lists.newArrayList();
		}
	}

	private String getDealingUserOrClientUser(final String dealingUser, final String clientId) {
		if(dealingUser != null){
			return dealingUser;
		} else if (clientId != null){
			final Client client = clientDao.getClientById(clientId);
			return client.getUser();
		} else {
			return StringUtils.EMPTY;
		}
	}

	private Optional<Integer> getDocumentId(final String documentNumber, final DocumentType documentType, final String userName) {
		Integer documentId = null;
		if(documentType == DocumentType.INVOICE){
			final Optional<InvoiceVO> invoice = invoiceService.findByDocumentNumber(documentNumber, userName);
			if(invoice.isPresent()){
				documentId = Translator.parseInteger(invoice.get().getDocumentId());
			}
		} else {
			final Optional<BillVO> bill = billService.findByDocumentNumber(documentNumber, userName);
			if(bill.isPresent()){
				documentId = Translator.parseInteger(bill.get().getDocumentId());
			}
		}
		return Optional.fromNullable(documentId);
	}

	public String save(final DealingVO dealingVO, final String loggedUser) {
		try {
			final String dealingUser = determineDealingUser(dealingVO);
			updateInvoiceId(dealingVO, dealingUser);
			return dealingDao.save(dealingVO, dealingUser == null ? loggedUser : dealingUser);
		} catch (final DaoException e) {
			log.error("DaoException", e);
			return null;
		}
	}

	private void updateInvoiceId(final DealingVO dealingVO, final String dealingUser) {
		final String documentNumber = dealingVO.getDocumentNumber();
		final DocumentType documentType = dealingVO.getDocumentType();
		if(StringUtils.isNotBlank(documentNumber) && documentType != null){
			final String user = getDealingUserOrClientUser(dealingUser, dealingVO.getIncomeClientId());
			final Optional<Integer> documentId = getDocumentId(documentNumber, documentType, user);
			if(documentId.isPresent()){
				dealingVO.setDocumentId(documentId.get());
				dealingVO.setDocumentNumber(documentNumber);
			}
		} else {
			dealingVO.setDocumentId(null);
			dealingVO.setDocumentNumber(null);
		}
	}
}
