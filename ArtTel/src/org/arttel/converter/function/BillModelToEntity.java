package org.arttel.converter.function;

import static org.arttel.util.Translator.getDecimal;
import static org.arttel.util.Translator.parseInteger;

import java.util.List;

import org.arttel.controller.vo.BillProductVO;
import org.arttel.controller.vo.BillVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.InvoiceProductCorrectionDAO;
import org.arttel.dao.ProductDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.entity.Bill;
import org.arttel.entity.BillProducts;
import org.arttel.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

@Component
public final class BillModelToEntity implements Function<BillVO, Bill>{

	@Autowired
	private ClientDAO clientDao;

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private InvoiceProductCorrectionDAO invoiceProductCorrectionDao;

	@Override
	public Bill apply(final BillVO model) {
		final Bill entity = new Bill();
		final Integer invoiceId = parseInteger(model.getDocumentId());
		entity.setDocumentId(invoiceId);
		entity.setDocumentNumber(model.getNumber());
		entity.setCreateDate(model.getCreateDate());
		entity.setPaymentDate(model.getPaymentDate());
		entity.setNetAmount(getDecimal(model.getAmount()));
		entity.setComments(model.getComments());
		entity.setClient(getClient(model.getClientId()));
		entity.setUser(model.getUser());
		final PaymentType paymentType = model.getPaymentType();
		if(paymentType != null){
			entity.setPaymentType(paymentType.getIdn());
		}
		final InvoiceStatus invoiceStatus = model.getStatus();
		if(invoiceStatus != null){
			entity.setDocumentStatus(invoiceStatus.getIdn());
		}
		entity.setSellerId(parseInteger(model.getSellerId()));
		entity.setAdditionalComments(model.getAdditionalComments());
		entity.setSellerBankAccountId(model.getSellerBankAccountId());
		entity.setDocumentProducts(convertProductsToEntity(model.getDocumentProducts(), invoiceId));
		entity.setRealizationDate(model.getRealizationDate());
		entity.setCity(model.getCity());
		return entity;
	}

	private List<BillProducts> convertProductsToEntity(final List<BillProductVO> invoiceProducts, final Integer invoiceId) {
		return Lists.newArrayList(FluentIterable.from(invoiceProducts).transform(new Function<BillProductVO, BillProducts>(){

			@Override
			public BillProducts apply(final BillProductVO model) {
				final BillProducts entity = new BillProducts();
				entity.setDocumentId(invoiceId);
				final String invoiceProductId = model.getInvoiceProductId();
				entity.setDocumentProductId(parseInteger(invoiceProductId));
				final ProductVO productDefinition = model.getProductDefinition();
				if(productDefinition != null){
					entity.setProductId(Integer.parseInt(productDefinition.getProductId()));
				}
				entity.setQuantity(getDecimal(model.getQuantity()));
				entity.setSumAmount(getDecimal(model.getSumAmount()));
				entity.setTaxReleaseBasis(model.getTaxReleaseBasis());
				entity.setProductClassification(model.getProductClassification());
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
};
