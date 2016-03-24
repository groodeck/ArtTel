package org.arttel.converter.function;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.BillProductVO;
import org.arttel.controller.vo.BillVO;
import org.arttel.controller.vo.InvoceProductCorrectionVO;
import org.arttel.dao.ProductDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.entity.Bill;
import org.arttel.entity.BillProducts;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

@Component
public final class BillEntityToModel implements Function<Bill, BillVO> {

	@Autowired
	private ProductDAO productDao;

	@Override
	public BillVO apply(final Bill entity) {
		final BillVO model = new BillVO();
		model.setDocumentId(entity.getDocumentId().toString());
		model.setNumber(entity.getDocumentNumber());
		model.setCreateDate(entity.getCreateDate());
		model.setPaymentDate(entity.getPaymentDate());
		model.setComments(entity.getComments());
		model.setClientId(entity.getClient().getClientId().toString());
		model.setClientDesc(entity.getClient().getClientDesc());
		model.setUser(entity.getUser());
		final String paymentTypeIdn = entity.getPaymentType();
		if(StringUtils.isNotEmpty(paymentTypeIdn)){
			model.setPaymentType(PaymentType.getValueByIdn(paymentTypeIdn));
		}
		final String invoiceStatusIdn = entity.getDocumentStatus();
		if(StringUtils.isNotEmpty(invoiceStatusIdn)){
			model.setStatus(InvoiceStatus.getValueByIdn(invoiceStatusIdn));
		}
		model.setSellerId(entity.getSellerId().toString());
		model.setAdditionalComments(entity.getAdditionalComments());
		model.setSellerBankAccountId(entity.getSellerBankAccountId());
		model.setDocumentProducts(convertProductsToModel(entity.getDocumentProducts()));
		model.setAmount(Translator.toString(entity.getAmount()));
		return model;
	}

	private List<BillProductVO> convertProductsToModel(final List<BillProducts> billProducts) {
		return Lists.newArrayList(FluentIterable.from(billProducts).transform(new Function<BillProducts, BillProductVO>(){

			@Override
			public BillProductVO apply(final BillProducts entity) {
				final BillProductVO model = new BillProductVO();
				final String invoiceProductId = entity.getDocumentProductId().toString();
				model.setInvoiceProductId(invoiceProductId);
				final Integer productId = entity.getProductId();
				model.setProductDefinition(productDao.getProductById(productId.toString()));
				model.setQuantity(Translator.toString(entity.getQuantity()));
				model.setSumAmount(Translator.toString(entity.getSumAmount()));
				model.setTaxReleaseBasis(entity.getTaxReleaseBasis());
				model.setProductClassification(entity.getProductClassification());
				//				model.setCorrection(getProductCorrection(invoiceProductId));
				return model;
			}

			private InvoceProductCorrectionVO getProductCorrection(final String invoiceProductId) {
				//return invoiceProductCorrectionDao.getInvoiceProductCorrection(invoiceProductId);
				return null; //TODO
			}
		}));
	}

};
