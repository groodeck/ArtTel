package org.arttel.converter.function;

import org.arttel.controller.vo.SqueezeVO;
import org.arttel.entity.Squeeze;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public final class SqueezeEntityToModel implements Function<Squeeze, SqueezeVO> {

	@Override
	public SqueezeVO apply(final Squeeze entity) {
		final SqueezeVO model = new SqueezeVO();
		final Integer id = entity.getSqueezeId();
		if(id != null){
			model.setSqueezeId(entity.getSqueezeId().toString());
		}
		//TODO: rest of fields
		return model;
	}
};
