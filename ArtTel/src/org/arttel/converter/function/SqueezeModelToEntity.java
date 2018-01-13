package org.arttel.converter.function;

import org.arttel.controller.vo.SqueezeVO;
import org.arttel.entity.Squeeze;
import org.arttel.util.Translator;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class SqueezeModelToEntity implements Function<SqueezeVO, Squeeze> {

	@Override
	public Squeeze apply(final SqueezeVO model) {
		final Squeeze entity = new Squeeze();
		final String id = model.getSqueezeId();
		if(id != null){
			entity.setSqueezeId(Translator.parseInt(id));
		}
		//TODO rest of fields
		return entity;
	}
}
