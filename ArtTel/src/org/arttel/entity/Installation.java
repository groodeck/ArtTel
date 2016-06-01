package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Instalation")
public class Installation implements UserSet {

	@Id
	@GeneratedValue
	@Column(name = "InstalationId")
	private Integer instalationId;

	@Column(name = "Status")
	private String status;

	@Column(name = "Phone")
	private String phone;

	@Column(name = "Address")
	private String address;

	@Column(name = "City")
	private String city;

	@Column(name = "InstallationDate")
	private Date installationDate;

	@Column(name = "InstallationType")
	private String installationType;

	@Column(name = "User")
	private String user;

	@Column(name = "TvSocketCount")
	private Integer tvSocketCount;

	@Column(name = "NetSocketCount")
	private Integer netSocketCount;

	@Column(name = "CableQuantity")
	private BigDecimal cableQuantity;

	@Column(name = "Comments")
	private String comments;

	@Column(name = "AdditionalComments")
	private String additionalComments;

	@Column(name = "SocketOrderSequence")
	private Integer socketOrderSequence;

	@Column(name = "InstalationClearingSequence")
	private Integer instalationClearingSequence;

	@Column(name = "DtvCount")
	private Integer dtvCount;

	@Column(name = "AtvCount")
	private Integer atvCount;

	@Column(name = "MultiroomCount")
	private Integer multiroomCount;

	@Column(name = "NetCount")
	private Integer netCount;

	@Column(name = "TelCount")
	private Integer telCount;

	@OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true, fetch=FetchType.LAZY)
	@JoinColumn(name="instalationId")
	private List<InstallationDevice> devices;

	public String getAdditionalComments() {
		return additionalComments;
	}

	public String getAddress() {
		return address;
	}

	public Integer getAtvCount() {
		return atvCount;
	}

	public BigDecimal getCableQuantity() {
		return cableQuantity;
	}

	public String getCity() {
		return city;
	}

	public String getComments() {
		return comments;
	}

	public List<InstallationDevice> getDevices() {
		return devices;
	}

	public Integer getDtvCount() {
		return dtvCount;
	}

	public Integer getInstalationClearingSequence() {
		return instalationClearingSequence;
	}

	public Integer getInstalationId() {
		return instalationId;
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public String getInstallationType() {
		return installationType;
	}

	public Integer getMultiroomCount() {
		return multiroomCount;
	}

	public Integer getNetCount() {
		return netCount;
	}

	public Integer getNetSocketCount() {
		return netSocketCount;
	}

	public String getPhone() {
		return phone;
	}

	public Integer getSocketOrderSequence() {
		return socketOrderSequence;
	}

	public String getStatus() {
		return status;
	}

	public Integer getTelCount() {
		return telCount;
	}

	public Integer getTvSocketCount() {
		return tvSocketCount;
	}

	public String getUser() {
		return user;
	}

	public void setAdditionalComments(final String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setAtvCount(final Integer atvCount) {
		this.atvCount = atvCount;
	}

	public void setCableQuantity(final BigDecimal cableQuantity) {
		this.cableQuantity = cableQuantity;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public void setDevices(final List<InstallationDevice> devices) {
		this.devices = devices;
	}

	public void setDtvCount(final Integer dtvCount) {
		this.dtvCount = dtvCount;
	}

	public void setInstalationClearingSequence(final Integer instalationClearingSequence) {
		this.instalationClearingSequence = instalationClearingSequence;
	}

	public void setInstalationId(final Integer instalationId) {
		this.instalationId = instalationId;
	}

	public void setInstallationDate(final Date installationDate) {
		this.installationDate = installationDate;
	}

	public void setInstallationType(final String installationType) {
		this.installationType = installationType;
	}

	public void setMultiroomCount(final Integer multiroomCount) {
		this.multiroomCount = multiroomCount;
	}

	public void setNetCount(final Integer netCount) {
		this.netCount = netCount;
	}

	public void setNetSocketCount(final Integer netSocketCount) {
		this.netSocketCount = netSocketCount;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setSocketOrderSequence(final Integer socketOrderSequence) {
		this.socketOrderSequence = socketOrderSequence;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setTelCount(final Integer telCount) {
		this.telCount = telCount;
	}

	public void setTvSocketCount(final Integer tvSocketCount) {
		this.tvSocketCount = tvSocketCount;
	}

	@Override
	public void setUser(final String user) {
		this.user = user;
	}
}
