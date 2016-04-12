package org.arttel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.arttel.config.AppConfig;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.entity.Invoice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(classes={AppConfig.class})//TODO: add test AppConfig and add persistence-context
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class InvoiceDAOTest {

	@Autowired
	private InvoiceDAO dao;
	@PersistenceContext
	private EntityManager em;

	private Invoice createInvoice(final String number) {
		final Invoice invoice = new Invoice();
		invoice.setDocumentNumber(number);
		invoice.setDocumentStatus(InvoiceStatus.DRAFT.getIdn());
		invoice.setPaymentType(PaymentType.CASH.getIdn());
		invoice.setSellerId(1);
		dao.save(invoice);
		return invoice;
	}

	@Test
	public void test() throws Exception {

		final Integer in1 = createInvoice("13/05/2017").getDocumentId();
		final Integer in2 = createInvoice("3/05/2017").getDocumentId();
		final Integer in3 = createInvoice("10/05/2017").getDocumentId();
		final Integer in4 = createInvoice("9/05/2017").getDocumentId();
		final Integer in5 = createInvoice("13/06/2017").getDocumentId();
		final Integer in6 = createInvoice("3/06/2017").getDocumentId();
		final Integer in7 = createInvoice("10/06/2017").getDocumentId();
		final Integer in8 = createInvoice("9/06/2017").getDocumentId();

		final List<String> resultList = em.createQuery(
				"select concat("
						+ "substring(substring(concat('00000', i.documentNumber),length(concat('00000', i.documentNumber))-12, 13), 10, 4), "
						+ "substring(substring(concat('00000', i.documentNumber),length(concat('00000', i.documentNumber))-12, 13), 7, 2), "
						+ "substring(substring(concat('00000', i.documentNumber),length(concat('00000', i.documentNumber))-12, 13), 1, 5)) as ordercolumn "
						+ "FROM Invoice i order by ordercolumn ASC").getResultList();

		for(final String invoice : resultList){

			System.out.println(invoice);
		}

		dao.deleteInvoice(in1.toString());
		dao.deleteInvoice(in2.toString());
		dao.deleteInvoice(in3.toString());
		dao.deleteInvoice(in4.toString());
		dao.deleteInvoice(in5.toString());
		dao.deleteInvoice(in6.toString());
		dao.deleteInvoice(in7.toString());
		dao.deleteInvoice(in8.toString());
	}

}
