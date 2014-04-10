package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Installation")
public class Installation {

	@Id
	@GeneratedValue
	@Column(name = "InstalationId")
	private Integer instalationId;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "NrTelefonu")
	private String phone;
	
	@Column(name = "Imie")
	private String name;
	
	@Column(name = "Nazwisko")
	private String surname;
	
	@Column(name = "Adres")
	private String address;
	
	@Column(name = "Miejscowosc")
	private String city;
	
	@Column(name = "NrSeryjny")
	private String serialNumber;
	
	@Column(name = "DataInstalacji")
	private Date installationDate;
	
	@Column(name = "RodzajInstalacji")
	private String installationType;
	
	@Column(name = "DataPodpisaniaUmowy")
	private Date agreementSignDate;
	
	@Column(name = "Pakiet")
	private String servicePackage;
	
	@Column(name = "UserId")
	private String userId;
	
	@Column(name = "WykonaneInstalacje")
	private Integer installationsCount;
	
	@Column(name = "IloscKabla")
	private String cableQuantity;
	
	@Column(name = "Uwagi")
	private String comments;
	
	@Column(name = "DodatkoweUwagi")
	private String additionalComments;
	
	@Column(name = "MacAdres")
	private String macAddress;
	
	@Column(name = "Downstream")
	private BigDecimal downstream;
	
	@Column(name = "Upstream")
	private BigDecimal upstream;
	
	@Column(name = "Iloscgniazd")
	private Integer socketQuantity;
	
	@Column(name = "SocketOrderSequence")
	private Integer socketOrderSequence;
	
	@Column(name = "InstalationClearingSequence")
	private Integer instalationClearingSequence;

	public Integer getInstalationId() {
		return instalationId;
	}

	public void setInstalationId(Integer instalationId) {
		this.instalationId = instalationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public String getInstallationType() {
		return installationType;
	}

	public void setInstallationType(String installationType) {
		this.installationType = installationType;
	}

	public Date getAgreementSignDate() {
		return agreementSignDate;
	}

	public void setAgreementSignDate(Date agreementSignDate) {
		this.agreementSignDate = agreementSignDate;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getInstallationsCount() {
		return installationsCount;
	}

	public void setInstallationsCount(Integer installationsCount) {
		this.installationsCount = installationsCount;
	}

	public String getCableQuantity() {
		return cableQuantity;
	}

	public void setCableQuantity(String cableQuantity) {
		this.cableQuantity = cableQuantity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAdditionalComments() {
		return additionalComments;
	}

	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public BigDecimal getDownstream() {
		return downstream;
	}

	public void setDownstream(BigDecimal downstream) {
		this.downstream = downstream;
	}

	public BigDecimal getUpstream() {
		return upstream;
	}

	public void setUpstream(BigDecimal upstream) {
		this.upstream = upstream;
	}

	public Integer getSocketQuantity() {
		return socketQuantity;
	}

	public void setSocketQuantity(Integer socketQuantity) {
		this.socketQuantity = socketQuantity;
	}

	public Integer getSocketOrderSequence() {
		return socketOrderSequence;
	}

	public void setSocketOrderSequence(Integer socketOrderSequence) {
		this.socketOrderSequence = socketOrderSequence;
	}

	public Integer getInstalationClearingSequence() {
		return instalationClearingSequence;
	}

	public void setInstalationClearingSequence(Integer instalationClearingSequence) {
		this.instalationClearingSequence = instalationClearingSequence;
	}
	
}
