package org.arttel.controller.vo;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.arttel.dictionary.DeviceType;
import org.arttel.util.Translator;
import org.arttel.util.WebUtils;

public class InstallationDeviceModel {

	private DeviceType deviceType;
	private String serialNumber;
	private String macAddress;
	private BigDecimal downstream;
	private BigDecimal upstream;
	private String comments;
	private Integer installationDeviceId;
	private Integer installationId;

	public InstallationDeviceModel(final DeviceType deviceType){
		this.deviceType = deviceType;
	}

	public String getComments() {
		return comments;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public BigDecimal getDownstream() {
		return downstream;
	}

	public String getDownstreamColor(){
		return WebUtils.getDownstreamLevelColor(downstream);
	}

	public Integer getInstallationDeviceId() {
		return installationDeviceId;
	}

	public Integer getInstallationId() {
		return installationId;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public BigDecimal getUpstream() {
		return upstream;
	}

	public String getUpstreamColor(){
		return WebUtils.getUpstreamLevelColor(upstream);
	}

	public void populate(final HttpServletRequest request, final String prefix ) {
		installationDeviceId = Translator.parseInteger(request.getParameter(prefix + ".installationDeviceId"));
		installationId = Translator.parseInteger(request.getParameter(prefix + ".installationId"));
		final String deviceTypeIdn = request.getParameter(prefix + ".deviceType");
		if(deviceTypeIdn != null){
			deviceType = DeviceType.getValueByIdn(deviceTypeIdn);
		}
		serialNumber = request.getParameter(prefix + ".serialNumber");
		macAddress = request.getParameter(prefix + ".macAddress");
		final String downstreamStr = request.getParameter(prefix + ".downstream");
		downstream = Translator.toDecimal(downstreamStr);
		final String upstreamStr = request.getParameter(prefix + ".upstream");
		upstream = Translator.toDecimal(upstreamStr);
		comments = request.getParameter(prefix + ".comments");
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setDownstream(final BigDecimal downstream) {
		this.downstream = downstream;
	}

	public void setInstallationDeviceId(final Integer instalationDeviceId) {
		installationDeviceId = instalationDeviceId;
	}

	public void setInstallationId(final Integer instalationId) {
		installationId = instalationId;
	}

	public void setMacAddress(final String macAdres) {
		macAddress = macAdres;
	}

	public void setSerialNumber(final String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setUpstream(final BigDecimal upstream) {
		this.upstream = upstream;
	}
}
