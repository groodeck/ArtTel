package org.arttel.converter.function;

import org.arttel.controller.vo.ClientVO;
import org.arttel.entity.Client;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public final class ClientEntityToModel implements Function<Client, ClientVO> {

	@Override
	public ClientVO apply(final Client entity) {
		final ClientVO model = new ClientVO(entity.getClientId().toString(), entity.getClientDesc());
		model.setForInstalation(entity.getForInstalation());
		model.setForOrder(entity.getForOrder());
		model.setForReport(entity.getForReport());
		model.setForSqueeze(entity.getForSqueeze());
		model.setForDealing(entity.getForDealing());
		model.setForInvoice(entity.getForInvoice());
		model.setNip(entity.getNip());
		model.setCity(entity.getCity());
		model.setStreet(entity.getStreet());
		model.setHouse(entity.getHouse());
		model.setAppartment(entity.getAppartment());
		model.setZip(entity.getZip());
		return model;
	}


};
