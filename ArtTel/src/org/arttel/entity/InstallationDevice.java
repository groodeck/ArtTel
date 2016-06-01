package org.arttel.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.arttel.dictionary.DeviceType;

@Entity
@Table(name = "InstalationDevice")
public class InstallationDevice{

	@Id
	@GeneratedValue
	@Column(name = "InstalationDeviceId")
	private Integer instalationDeviceId;

	@Column(name = "InstalationId")
	private Integer instalationId;

	@Enumerated(EnumType.STRING)
	@Column(name = "DeviceType")
	private DeviceType deviceType;

	@Column(name = "SerialNumber")
	private String serialNumber;

	@Column(name = "MacAddress")
	private String macAddress;

	@Column(name = "Downstream")
	private BigDecimal downstream;

	@Column(name = "Upstream")
	private BigDecimal upstream;

	@Column(name = "Comments")
	private String comments;

	public String getComments() {
		return comments;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public BigDecimal getDownstream() {
		return downstream;
	}

	public Integer getInstalationDeviceId() {
		return instalationDeviceId;
	}

	public Integer getInstalationId() {
		return instalationId;
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

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setDeviceType(final DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public void setDownstream(final BigDecimal downstream) {
		this.downstream = downstream;
	}

	public void setInstalationDeviceId(final Integer instalationDeviceId) {
		this.instalationDeviceId = instalationDeviceId;
	}

	public void setInstalationId(final Integer instalationId) {
		this.instalationId = instalationId;
	}

	public void setMacAddress(final String macAddress) {
		this.macAddress = macAddress;
	}

	public void setSerialNumber(final String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setUpstream(final BigDecimal upstream) {
		this.upstream = upstream;
	}

}
