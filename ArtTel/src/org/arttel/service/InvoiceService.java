package org.arttel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.converter.InvoiceConverter;
import org.arttel.dao.CorrectionDAO;
import org.arttel.dao.InvoiceDAO;
import org.arttel.entity.Invoice;
import org.arttel.exception.DaoException;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

	public void deleteInvoice(final List<Integer> invoiceIds) {
		for(final Integer invoiceId : invoiceIds){
			correctionDao.removeCorrectionForInvoice(invoiceId.toString());
			invoiceDao.deleteInvoice(invoiceId.toString());
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
}
