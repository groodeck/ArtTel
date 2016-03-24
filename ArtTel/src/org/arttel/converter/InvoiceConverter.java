package org.arttel.converter;

import org.arttel.controller.vo.InvoiceVO;
import org.arttel.converter.function.InvoiceEntityToModel;
import org.arttel.converter.function.InvoiceModelToEntity;
import org.arttel.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class InvoiceConverter extends BaseConverter<InvoiceVO, Invoice> {

	@Autowired
	private InvoiceEntityToModel toModel;

	@Autowired
	private InvoiceModelToEntity toEntity;

	@Override
	protected Function<InvoiceVO, Invoice> toEntity() {
		return toEntity;
	}

	@Override
	protected Function<Invoice, InvoiceVO> toModel() {
		return toModel;
	}


}
