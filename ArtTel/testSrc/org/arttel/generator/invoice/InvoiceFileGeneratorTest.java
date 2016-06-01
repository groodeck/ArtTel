package org.arttel.generator.invoice;

import static org.assertj.core.api.Assertions.assertThat;

import org.arttel.controller.vo.ClientVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.SellerVO;
import org.arttel.dictionary.PaymentType;
import org.arttel.dictionary.UnitType;
import org.arttel.dictionary.VatRate;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceFileGeneratorTest {

	@Mock
	private InvoiceContentGenerator contentGenerator;

	@InjectMocks
	private final InvoiceFileGenerator generator = new InvoiceFileGenerator();

	@Test
	public void shouldGenerateInvoiceFile() {
		// given
		final InvoiceVO invoice = new InvoiceVO();
		invoice.setNumber("1/03/2016");
		final SellerVO seller = new SellerVO();
		seller.setSellerDesc("Art-Tel");
		seller.setZip("00-940");
		seller.setCity("£owicz");
		seller.setStreet("Bratkowice");
		seller.setHouse("8");
		seller.setAppartment("22");

		final ClientVO client = new ClientVO();
		client.setClientDesc("test klient");
		client.setZip("00-970");
		client.setCity("£owicz");
		client.setStreet("kostka");
		client.setHouse("1");
		client.setAppartment("13");

		invoice.setSeller(seller);
		invoice.setClient(client);

		invoice.setAdditionalComments("dodatkowe uwagi");
		invoice.setComments("uwagi");
		invoice.setCreateDate(new java.sql.Date(LocalDate.parse("2015-01-01").toDate().getTime()));
		invoice.setPaymentDate(new java.sql.Date(LocalDate.parse("2016-02-02").toDate().getTime()));
		invoice.setSignDate(new java.sql.Date(LocalDate.parse("2016-02-05").toDate().getTime()));
		invoice.setPaymentType(PaymentType.TRANSFER_21);
		final InvoceProductVO product = new InvoceProductVO();
		final ProductVO productDefinition = new ProductVO();
		productDefinition.setProductDescription("us³uga instalacji");
		productDefinition.setUnitType(UnitType.SZT);
		productDefinition.setNetPrice("1.22");
		productDefinition.setVatRate(VatRate.VAT_23);
		product.setQuantity("3");
		product.setNetSumAmount("45.0");
		product.setGrossSumAmount("60");
		product.setVatAmount("15");
		product.setProductDefinition(productDefinition);
		invoice.setInvoiceProducts(Lists.newArrayList(product));
		invoice.setNetAmount("45");
		invoice.setVatAmount("15");
		invoice.setPaidWords("zap³acono tyle i tyle");

		final String html = new InvoiceContentGenerator().generateHtml(invoice);
		Mockito.when(contentGenerator.generateHtml(invoice)).thenReturn(html);

		//when
		final Optional<String> generatePdf = generator.generatePdf(invoice, "757658765856757587585");

		//then
		assertThat(generatePdf.isPresent()).isTrue();

	}

}
