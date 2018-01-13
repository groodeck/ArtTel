package org.arttel.converter;

import org.arttel.controller.vo.SqueezeVO;
import org.arttel.converter.function.SqueezeEntityToModel;
import org.arttel.converter.function.SqueezeModelToEntity;
import org.arttel.entity.Squeeze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class SqueezeConverter extends BaseConverter<SqueezeVO, Squeeze>  {

	@Autowired
	private SqueezeEntityToModel toModel;

	@Autowired
	private SqueezeModelToEntity toEntity;

	@Override
	protected Function<SqueezeVO, Squeeze> toEntity() {
		return toEntity;
	}

	@Override
	protected Function<Squeeze, SqueezeVO> toModel() {
		return toModel;
	}


}
