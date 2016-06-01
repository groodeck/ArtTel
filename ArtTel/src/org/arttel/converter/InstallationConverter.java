package org.arttel.converter;

import org.arttel.controller.vo.InstallationVO;
import org.arttel.converter.function.InstallationEntityToModel;
import org.arttel.converter.function.InstallationModelToEntity;
import org.arttel.entity.Installation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class InstallationConverter extends BaseConverter<InstallationVO, Installation>  {

	@Autowired
	private InstallationEntityToModel toModel;

	@Autowired
	private InstallationModelToEntity toEntity;

	@Override
	protected Function<InstallationVO, Installation> toEntity() {
		return toEntity;
	}

	@Override
	protected Function<Installation, InstallationVO> toModel() {
		return toModel;
	}


}
