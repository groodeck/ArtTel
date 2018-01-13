package org.arttel.service;

import org.apache.log4j.Logger;
import org.arttel.dao.SellerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SellerService {

	private final static Logger log = Logger.getLogger(SellerService.class);

	@Autowired
	private SellerDAO sellerDao;

	public boolean checkGenerateDealingOnInvoiceSettle(final Integer sellerId) {
		return sellerDao.getSellerById(sellerId.toString()).isGenerateDealingOnInvoiceSettle();
	}


}
