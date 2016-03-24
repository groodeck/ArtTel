package org.arttel.converter.function;

import static org.arttel.util.Translator.getDecimal;
import static org.arttel.util.Translator.parseInteger;

import java.util.List;

import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.dao.ClientDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.entity.Client;
import org.arttel.entity.Invoice;
import org.arttel.entity.InvoiceProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

@Component
public class InvoiceModelToEntity implements Function<InvoiceVO, Invoice> {

	@Autowired
	private ClientDAO clientDao;

	@Override
	public Invoice apply(final InvoiceVO model) {
		final Invoice entity = new Invoice();
		final Integer invoiceId = parseInteger(model.getDocumentId());
		entity.setDocumentId(invoiceId);
		entity.setDocumentNumber(model.getNumber());
		entity.setCreateDate(model.getCreateDate());
		entity.setSignatureDate(model.getSignDate());
		entity.setPaymentDate(model.getPaymentDate());
		entity.setNetAmount(getDecimal(model.getNetAmount()));
		entity.setVatAmount(getDecimal(model.getVatAmount()));
		entity.setComments(model.getComments());
		entity.setClient(getClient(model.getClientId()));
		entity.setUser(model.getUser());
		entity.setPaid(getDecimal(model.getPaid()));
		final PaymentType paymentType = model.getPaymentType();
		if(paymentType != null){
			entity.setPaymentType(paymentType.getIdn());
		}
		entity.setPaidWords(model.getPaidWords());
		final InvoiceStatus invoiceStatus = model.getStatus();
		if(invoiceStatus != null){
			entity.setDocumentStatus(invoiceStatus.getIdn());
		}
		entity.setSellerId(parseInteger(model.getSellerId()));
		entity.setAdditionalComments(model.getAdditionalComments());
		entity.setSellerBankAccountId(model.getSellerBankAccountId());
		entity.setDocumentProducts(convertProductsToEntity(model.getDocumentProducts(), invoiceId));
		return entity;
	}

	private List<InvoiceProducts> convertProductsToEntity(final List<InvoceProductVO> invoiceProducts, final Integer invoiceId) {
		return Lists.newArrayList(FluentIterable.from(invoiceProducts).transform(new Function<InvoceProductVO, InvoiceProducts>(){

			@Override
			public InvoiceProducts apply(final InvoceProductVO model) {
				final InvoiceProducts entity = new InvoiceProducts();
				entity.setDocumentId(invoiceId);
				final String invoiceProductId = model.getInvoiceProductId();
				entity.setDocumentProductId(parseInteger(invoiceProductId));
				final ProductVO productDefinition = model.getProductDefinition();
				if(productDefinition != null){
					entity.setProductId(Integer.parseInt(productDefinition.getProductId()));
				}
				entity.setQuantity(getDecimal(model.getQuantity()));
				entity.setNetSumAmount(getDecimal(model.getNetSumAmount()));
				entity.setVatAmount(getDecimal(model.getVatAmount()));
				entity.setGrossSumAmount(getDecimal(model.getGrossSumAmount()));
				return entity;
			}
		}));
	}



	protected Client getClient(final String clientId) {
		if(clientId == null){
			return null;
		} else {
			return clientDao.getClientById(clientId);
		}
	}
}
