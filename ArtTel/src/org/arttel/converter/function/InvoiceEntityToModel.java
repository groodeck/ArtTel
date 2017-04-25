package org.arttel.converter.function;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.InvoceProductCorrectionVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.SellerVO;
import org.arttel.converter.ClientConverter;
import org.arttel.dao.InvoiceProductCorrectionDAO;
import org.arttel.dao.SellerDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.entity.Invoice;
import org.arttel.entity.InvoiceProducts;
import org.arttel.service.ProductService;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

@Component
public final class InvoiceEntityToModel implements Function<Invoice, InvoiceVO> {

	@Autowired
	private ProductService productService;

	@Autowired
	private ClientConverter clientConverter;

	@Autowired
	private SellerDAO sellerDao;

	@Autowired
	private InvoiceProductCorrectionDAO invoiceProductCorrectionDao;

	@Override
	public InvoiceVO apply(final Invoice entity) {
		final InvoiceVO model = new InvoiceVO();
		model.setDocumentId(entity.getDocumentId().toString());
		model.setNumber(entity.getDocumentNumber());
		model.setCreateDate(entity.getCreateDate());
		model.setSignDate(entity.getSignatureDate());
		model.setPaymentDate(entity.getPaymentDate());
		model.setNetAmount(Translator.toString(entity.getNetAmount()));
		model.setVatAmount(Translator.toString(entity.getVatAmount()));
		model.setComments(entity.getComments());
		model.setClientId(entity.getClient().getClientId().toString());
		model.setClientDesc(entity.getClient().getClientDesc());
		model.setUser(entity.getUser());
		model.setPaid(Translator.toString(entity.getPaid()));
		final String paymentTypeIdn = entity.getPaymentType();
		if(StringUtils.isNotEmpty(paymentTypeIdn)){
			model.setPaymentType(PaymentType.getValueByIdn(paymentTypeIdn));
		}
		model.setPaidWords(entity.getPaidWords());
		final String invoiceStatusIdn = entity.getDocumentStatus();
		if(StringUtils.isNotEmpty(invoiceStatusIdn)){
			model.setStatus(InvoiceStatus.getValueByIdn(invoiceStatusIdn));
		}
		model.setSellerId(entity.getSellerId().toString());
		model.setAdditionalComments(entity.getAdditionalComments());
		model.setSellerBankAccountId(entity.getSellerBankAccountId());
		model.setSeller(getSeller(entity.getSellerId()));
		model.setClient(clientConverter.convert(entity.getClient()));
		model.setInvoiceProducts(convertProductsToModel(entity.getDocumentProducts()));
		return model;
	}

	private List<InvoceProductVO> convertProductsToModel(final List<InvoiceProducts> invoiceProducts) {
		return Lists.newArrayList(FluentIterable.from(invoiceProducts).transform(new Function<InvoiceProducts, InvoceProductVO>(){

			@Override
			public InvoceProductVO apply(final InvoiceProducts entity) {
				final InvoceProductVO model = new InvoceProductVO();
				final String invoiceProductId = entity.getDocumentProductId().toString();
				model.setInvoiceProductId(invoiceProductId);
				final Integer productId = entity.getProductId();
				model.setProductDefinition(productService.getProductById(productId.toString()));
				model.setQuantity(Translator.toString(entity.getQuantity()));
				model.setNetSumAmount(Translator.toString(entity.getNetSumAmount()));
				model.setVatAmount(Translator.toString(entity.getVatAmount()));
				model.setGrossSumAmount(Translator.toString(entity.getGrossSumAmount()));
				model.setCorrection(getProductCorrection(invoiceProductId));
				return model;
			}

			private InvoceProductCorrectionVO getProductCorrection(final String invoiceProductId) {
				return invoiceProductCorrectionDao.getInvoiceProductCorrection(invoiceProductId);
			}
		}));
	}

	private SellerVO getSeller(final Integer sellerId) {
		return sellerDao.getSellerById(sellerId.toString());
	}
};
