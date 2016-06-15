package org.arttel.converter.function;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.arttel.controller.vo.InstallationDeviceModel;
import org.arttel.controller.vo.InstallationVO;
import org.arttel.dictionary.DeviceType;
import org.arttel.dictionary.InstallationType;
import org.arttel.dictionary.Status;
import org.arttel.entity.Installation;
import org.arttel.entity.InstallationDevice;
import org.arttel.util.Translator;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

@Component
public final class InstallationEntityToModel implements Function<Installation, InstallationVO> {

	@Override
	public InstallationVO apply(final Installation entity) {
		final InstallationVO model = new InstallationVO();
		model.setInstallationId(entity.getInstalationId());
		final String installationTypeStr = entity.getInstallationType();
		if(StringUtils.isNotBlank(installationTypeStr)){
			model.setInstallationType(InstallationType.getValueByIdn(installationTypeStr));
		}
		final String installationStatusStr = entity.getStatus();
		if(StringUtils.isNotBlank(installationStatusStr)){
			model.setStatus(Status.getValueByIdn(installationStatusStr));
		}
		model.setInstallationDate(Translator.toLocalDate(entity.getInstallationDate()));
		model.setAddress(entity.getAddress());
		model.setCity(entity.getCity());
		model.setPhone(entity.getPhone());
		model.setComments(entity.getComments());
		model.setAdditionalComments(entity.getAdditionalComments());
		model.setTvSocketCount(entity.getTvSocketCount());
		model.setNetSocketCount(entity.getNetSocketCount());
		model.setDtvCount(entity.getDtvCount());
		model.setAtvCount(entity.getAtvCount());
		model.setMultiroomCount(entity.getMultiroomCount());
		model.setNetCount(entity.getNetCount());
		model.setTelCount(entity.getTelCount());
		model.setCableQuantity(entity.getCableQuantity());
		model.setTwoWay1(convertDevice(DeviceType.TWO_WAY_1, entity.getDevices()));
		model.setTwoWay2(convertDevice(DeviceType.TWO_WAY_2, entity.getDevices()));
		model.setOneWay1(convertDevice(DeviceType.ONE_WAY_1, entity.getDevices()));
		model.setOneWay2(convertDevice(DeviceType.ONE_WAY_2, entity.getDevices()));
		model.setModem(convertDevice(DeviceType.MODEM, entity.getDevices()));
		model.setUser(entity.getUser());
		return model;
	}

	private InstallationDeviceModel convertDevice(final DeviceType requiredDeviceType, final List<InstallationDevice> devices) {
		final Optional<InstallationDevice> requiredDevice =
				FluentIterable.from(devices).firstMatch(new Predicate<InstallationDevice>(){
					@Override
					public boolean apply(final InstallationDevice input) {
						return input.getDeviceType() == requiredDeviceType;
					}
				});
		if(requiredDevice.isPresent()){
			return convertDevice(requiredDevice.get());
		} else {
			return new InstallationDeviceModel(requiredDeviceType);
		}
	}

	private InstallationDeviceModel convertDevice(final InstallationDevice entity) {
		final InstallationDeviceModel model = new InstallationDeviceModel(entity.getDeviceType());
		model.setInstallationDeviceId(entity.getInstalationDeviceId());
		model.setInstallationId(entity.getInstalationId());
		model.setSerialNumber(entity.getSerialNumber());
		model.setMacAddress(entity.getMacAddress());
		model.setDownstream(entity.getDownstream());
		model.setUpstream(entity.getUpstream());
		model.setComments(entity.getComments());
		return model;
	}
};
