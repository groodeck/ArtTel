package org.arttel.converter.function;

import java.util.List;

import org.arttel.controller.vo.InstallationDeviceModel;
import org.arttel.controller.vo.InstallationVO;
import org.arttel.dictionary.InstallationType;
import org.arttel.dictionary.Status;
import org.arttel.entity.Installation;
import org.arttel.entity.InstallationDevice;
import org.arttel.util.Translator;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

@Component
public class InstallationModelToEntity implements Function<InstallationVO, Installation> {

	@Override
	public Installation apply(final InstallationVO model) {
		final Installation entity = new Installation();
		entity.setInstalationId(model.getInstallationId());
		final InstallationType installationType = model.getInstallationType();
		if(installationType != null){
			entity.setInstallationType(installationType.getIdn());
		}
		final Status installationStatus = model.getStatus();
		if(installationStatus != null){
			entity.setStatus(installationStatus.getIdn());
		}
		entity.setInstallationDate(Translator.toSqlDate(model.getInstallationDate()));
		entity.setAddress(model.getAddress());
		entity.setCity(model.getCity());
		entity.setPhone(model.getPhone());
		entity.setComments(model.getComments());
		entity.setAdditionalComments(model.getAdditionalComments());
		entity.setTvSocketCount(model.getTvSocketCount());
		entity.setNetSocketCount(model.getNetSocketCount());
		entity.setDtvCount(model.getDtvCount());
		entity.setAtvCount(model.getAtvCount());
		entity.setMultiroomCount(model.getMultiroomCount());
		entity.setNetCount(model.getNetCount());
		entity.setTelCount(model.getTelCount());
		entity.setCableQuantity(model.getCableQuantity());
		entity.setDevices(convertInstallationDevices(model));
		entity.setUser(model.getUser());
		return entity;
	}

	private InstallationDevice convertDevice(final InstallationDeviceModel model, final Integer installationId,
			final InstallationType installationType) {
		final InstallationDevice entity = new InstallationDevice();
		entity.setInstalationDeviceId(model.getInstallationDeviceId());
		entity.setInstalationId(installationId);
		entity.setDeviceType(model.getDeviceType());
		entity.setSerialNumber(model.getSerialNumber());
		entity.setMacAddress(model.getMacAddress());
		if(installationType != installationType.ZWROT){
			entity.setDownstream(model.getDownstream());
			entity.setUpstream(model.getUpstream());
		}
		entity.setComments(model.getComments());
		return entity;
	}

	private List<InstallationDevice> convertInstallationDevices(final InstallationVO model) {
		final List<InstallationDevice> deviceList = Lists.newArrayList();
		final InstallationDeviceModel twoWay1 = model.getTwoWay1();
		if(twoWay1 != null){
			deviceList.add(convertDevice(twoWay1, model.getInstallationId(), model.getInstallationType()));
		}
		final InstallationDeviceModel twoWay2 = model.getTwoWay2();
		if(twoWay2 != null){
			deviceList.add(convertDevice(twoWay2, model.getInstallationId(), model.getInstallationType()));
		}
		final InstallationDeviceModel oneWay1 = model.getOneWay1();
		if(oneWay1 != null){
			deviceList.add(convertDevice(oneWay1, model.getInstallationId(), model.getInstallationType()));
		}
		final InstallationDeviceModel oneWay2 = model.getOneWay2();
		if(oneWay2 != null){
			deviceList.add(convertDevice(oneWay2, model.getInstallationId(), model.getInstallationType()));
		}
		final InstallationDeviceModel modem = model.getModem();
		if(modem != null) {
			deviceList.add(convertDevice(modem, model.getInstallationId(), model.getInstallationType()));
		}
		return deviceList;
	}

}
