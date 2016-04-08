package org.arttel.converter;

import org.arttel.controller.vo.ClientVO;
import org.arttel.converter.function.ClientEntityToModel;
import org.arttel.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class ClientConverter extends BaseConverter<ClientVO, Client>{

	@Autowired
	private ClientEntityToModel toModel;

	@Override
	protected Function<ClientVO, Client> toEntity() {
		// TODO implement
		return null;
	}

	@Override
	protected Function<Client, ClientVO> toModel() {
		return toModel;
	}

}
