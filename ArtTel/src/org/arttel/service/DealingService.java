package org.arttel.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.controller.vo.DealingVO;
import org.arttel.dao.DealingDAO;
import org.arttel.dictionary.DealingType;
import org.arttel.entity.Invoice;
import org.arttel.exception.DaoException;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DealingService {

	private final static Logger log = Logger.getLogger(DealingService.class);

	@Autowired
	private DealingDAO dealingDao;

	public void generateDealing(final Invoice invoice) {
		final String user = invoice.getUser();
		final DealingVO dealingVO = new DealingVO();
		dealingVO.setDealingType(DealingType.INCOME);
		dealingVO.setDate(LocalDate.now().toDate());
		dealingVO.setIncomeClientId(invoice.getClient().getClientId().toString());
		dealingVO.setIncomeClientName(invoice.getClient().getClientDesc());
		dealingVO.setAmount(invoice.getNetAmount().add(invoice.getVatAmount()).toString());
		dealingVO.setComments1(invoice.getComments());
		dealingVO.setComments2(invoice.getAdditionalComments());
		dealingVO.setComments3(StringUtils.EMPTY);
		dealingVO.setUserName(user);
		dealingVO.setDocumentId(invoice.getDocumentId());
		dealingVO.setCity(invoice.getCity());
		try {
			dealingDao.create(dealingVO, invoice.getUser());
		} catch (final DaoException e) {
			log.error("DealingDAO create error", e);
		}
	}

}
