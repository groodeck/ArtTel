package org.arttel.converter;

import org.arttel.controller.vo.BillVO;
import org.arttel.converter.function.BillEntityToModel;
import org.arttel.converter.function.BillModelToEntity;
import org.arttel.entity.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class BillConverter extends BaseConverter<BillVO, Bill> {

	@Autowired
	private BillEntityToModel toModel;

	@Autowired
	private BillModelToEntity toEntity;

	@Override
	protected Function<BillVO, Bill> toEntity() {
		return toEntity;
	}

	@Override
	protected Function<Bill, BillVO> toModel() {
		return toModel;
	}
}
