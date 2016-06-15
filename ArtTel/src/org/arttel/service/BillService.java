package org.arttel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.BillVO;
import org.arttel.controller.vo.filter.InvoiceFilterVO;
import org.arttel.converter.BillConverter;
import org.arttel.dao.BillDAO;
import org.arttel.dao.CorrectionDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.entity.Bill;
import org.arttel.exception.DaoException;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BillService {

	private final static Logger log = Logger.getLogger(BillService.class);

	@Autowired
	private BillDAO billDao;

	@Autowired
	private BillConverter billConverter;

	@Autowired
	private CorrectionDAO correctionDao;

	public void deleteBill(final List<Integer> billIds) {
		for(final Integer billId : billIds){
			//			correctionDao.removeCorrectionForInvoice(billId);
			billDao.deleteBill(billId);
		}
	}

	public BillVO getBill(final String billId) {
		final Bill entity = billDao.getBillById(billId);
		return billConverter.convert(entity);
	}

	public ResultPage<BillVO> getBillList(final InvoiceFilterVO documentFilterVO, final PageInfo pageInfo, final String user) throws DaoException {

		final ResultPage<Bill> entityList = billDao.getBillList(documentFilterVO, pageInfo);

		final ResultPage<BillVO> resultList = billConverter.convert(entityList);
		for(final BillVO billVO : resultList.getRecords()){
			billVO.applyPermissions(user);
		}
		return resultList;
	}

	public void save(final BillVO billVO, final String userName) {
		final Bill entity = billConverter.convert(billVO, userName);
		billDao.save(entity);
	}

	public void setBillStatus(final String billId, final InvoiceStatus status) {
		billDao.setBillStatus(billId, status);
	}
}
