package org.arttel.converter;

import static org.arttel.util.Translator.getDecimal;
import static org.arttel.util.Translator.parseInteger;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.InvoceProductCorrectionVO;
import org.arttel.controller.vo.InvoceProductVO;
import org.arttel.controller.vo.InvoiceVO;
import org.arttel.controller.vo.ProductVO;
import org.arttel.dao.ClientDAO;
import org.arttel.dao.InvoiceProductCorrectionDAO;
import org.arttel.dao.ProductDAO;
import org.arttel.dictionary.InvoiceStatus;
import org.arttel.dictionary.PaymentType;
import org.arttel.entity.Client;
import org.arttel.entity.Invoice;
import org.arttel.entity.InvoiceProducts;
import org.arttel.ui.ResultPage;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Component
public class InvoiceConverter {

	@Autowired
	private ClientDAO clientDao;

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private InvoiceProductCorrectionDAO invoiceProductCorrectionDao;

	private final Function<Invoice, InvoiceVO> toModel = new Function<Invoice, InvoiceVO>(){

		@Override
		public InvoiceVO apply(final Invoice entity) {
			final InvoiceVO model = new InvoiceVO();
			model.setInvoiceId(entity.getInvoiceId().toString());
			model.setNumber(entity.getInvoiceNumber());
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
			final String invoiceStatusIdn = entity.getInvoiceStatus();
			if(StringUtils.isNotEmpty(invoiceStatusIdn)){
				model.setStatus(InvoiceStatus.getValueByIdn(invoiceStatusIdn));
			}
			model.setSellerId(entity.getSellerId().toString());
			model.setAdditionalComments(entity.getAdditionalComments());
			model.setSellerBankAccountId(entity.getSellerBankAccountId());
			model.setInvoiceProducts(convertProductsToModel(entity.getInvoiceProducts()));
			return model;
		}
	};

	private final Function<InvoiceVO, Invoice> toEntity = new Function<InvoiceVO, Invoice>(){

		@Override
		public Invoice apply(final InvoiceVO model) {
			final Invoice entity = new Invoice();
			final Integer invoiceId = parseInteger(model.getInvoiceId());
			entity.setInvoiceId(invoiceId);
			entity.setInvoiceNumber(model.getNumber());
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
				entity.setInvoiceStatus(invoiceStatus.getIdn());
			}
			entity.setSellerId(parseInteger(model.getSellerId()));
			entity.setAdditionalComments(model.getAdditionalComments());
			entity.setSellerBankAccountId(model.getSellerBankAccountId());
			entity.setInvoiceProducts(convertProductsToEntity(model.getInvoiceProducts(), invoiceId));
			return entity;
		}

	};

	public InvoiceVO convert(final Invoice entity) {
		return toModel.apply(entity);
	}

	public Invoice convert(final InvoiceVO invoiceVO, final String userName) {
		final Invoice entity = toEntity.apply(invoiceVO);
		entity.setUser(userName);
		return entity;
	}

	public ResultPage<InvoiceVO> convert(final ResultPage<Invoice> entities) {
		final ImmutableList<InvoiceVO> results =
				FluentIterable.from(entities.getRecords()).transform(toModel).toList();
		return new ResultPage<InvoiceVO>(results, entities.getPageNo(), entities.getPageCount());
	}

	private List<InvoiceProducts> convertProductsToEntity(final List<InvoceProductVO> invoiceProducts, final Integer invoiceId) {
		return Lists.newArrayList(FluentIterable.from(invoiceProducts).transform(new Function<InvoceProductVO, InvoiceProducts>(){

			@Override
			public InvoiceProducts apply(final InvoceProductVO model) {
				final InvoiceProducts entity = new InvoiceProducts();
				entity.setInvoiceId(invoiceId);
				final String invoiceProductId = model.getInvoiceProductId();
				entity.setInvoceProductId(parseInteger(invoiceProductId));
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

	private List<InvoceProductVO> convertProductsToModel(final List<InvoiceProducts> invoiceProducts) {
		return Lists.newArrayList(FluentIterable.from(invoiceProducts).transform(new Function<InvoiceProducts, InvoceProductVO>(){

			@Override
			public InvoceProductVO apply(final InvoiceProducts entity) {
				final InvoceProductVO model = new InvoceProductVO();
				final String invoiceProductId = entity.getInvoceProductId().toString();
				model.setInvoiceProductId(invoiceProductId);
				final Integer productId = entity.getProductId();
				model.setProductDefinition(productDao.getProductById(productId.toString()));
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

	protected Client getClient(final String clientId) {
		if(clientId == null){
			return null;
		} else {
			return clientDao.getClientById(clientId);
		}
	}

}
