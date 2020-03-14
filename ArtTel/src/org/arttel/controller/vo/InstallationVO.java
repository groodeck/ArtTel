package org.arttel.controller.vo;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.arttel.controller.vo.filter.InstallationFilterVO;
import org.arttel.dictionary.DeviceType;
import org.arttel.dictionary.InstallationType;
import org.arttel.dictionary.Status;
import org.arttel.ui.SortOrder;
import org.arttel.ui.SortableColumn;
import org.arttel.ui.TableHeader;
import org.arttel.util.Translator;
import org.joda.time.LocalDate;

public class InstallationVO extends BasePageVO {

	public static final TableHeader getResultTableHeader(){
		return new TableHeader(
				new SortableColumn("installationId", "i.instalationId", "L.p.", SortOrder.DESC),
				new SortableColumn("status", "i.status", "Status"),
				new SortableColumn("installationDate", "i.installationDate", "Data instalacji"),
				new SortableColumn("installationType", "i.installationType", "Rodzaj instalacji"),
				new SortableColumn("address", "i.address", "Adres"),
				new SortableColumn("twoWay1_downstream", "", "2way #1 Downstr"),
				new SortableColumn("twoWay1_upstream", "", "2way #1 Upstr"),
				new SortableColumn("twoWay2_downstream", "", "2way #2 Downstr"),
				new SortableColumn("twoWay2_upstream", "", "2way #2 Upstr"),
				new SortableColumn("modem_downstream", "", "Modem Downstr"),
				new SortableColumn("modem_upstream", "", "Modem Upstr"),
				new SortableColumn("comments", "i.comments", "Uwagi"),
				new SortableColumn("user", "i.user", "Monter")
				);
	}


	private InstallationFilterVO installationFilter = new InstallationFilterVO();

	private Integer installationId;

	private Status status;
	private InstallationType installationType;
	private String phone;
	private String address;
	private String city;
	private LocalDate installationDate;
	private Integer dtvCount;
	private Integer multiroomCount;
	private Integer netCount;
	private Integer telCount;
	private Integer atvCount;
	private Integer tvSocketCount;
	private Integer netSocketCount;
	private BigDecimal cableQuantity;
	private String comments;
	private String additionalComments;

	private boolean editable;
	private String user;

	private InstallationDeviceModel twoWay1 = new InstallationDeviceModel(DeviceType.TWO_WAY_1);
	private InstallationDeviceModel twoWay2 = new InstallationDeviceModel(DeviceType.TWO_WAY_2);
	private InstallationDeviceModel oneWay1 = new InstallationDeviceModel(DeviceType.ONE_WAY_1);
	private InstallationDeviceModel oneWay2 = new InstallationDeviceModel(DeviceType.ONE_WAY_2);
	private InstallationDeviceModel modem = new InstallationDeviceModel(DeviceType.MODEM);

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

	public Integer getDtvCount() {
		return dtvCount;
	}

	@Override
	public Integer getId() {
		return getInstallationId();
	}

	public LocalDate getInstallationDate() {
		return installationDate;
	}

	public InstallationFilterVO getInstallationFilter() {
		return installationFilter;
	}

	public Integer getInstallationId() {
		return installationId;
	}

	public InstallationType getInstallationType() {
		return installationType;
	}

	public InstallationDeviceModel getModem() {
		return modem;
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

	public InstallationDeviceModel getOneWay1() {
		return oneWay1;
	}

	public InstallationDeviceModel getOneWay2() {
		return oneWay2;
	}

	public String getPhone() {
		return phone;
	}

	public Status getStatus() {
		return status;
	}

	public Integer getTelCount() {
		return telCount;
	}

	public Integer getTvSocketCount() {
		return tvSocketCount;
	}

	public InstallationDeviceModel getTwoWay1() {
		return twoWay1;
	}

	public InstallationDeviceModel getTwoWay2() {
		return twoWay2;
	}

	@Override
	public String getUser() {
		return user;
	}

	public boolean isClosable(){
		return getStatus() != Status.DONE;
	}

	public boolean isEditable() {
		return editable;
	}

	public void populate( final HttpServletRequest request ) {

		installationId = Translator.parseInteger(request.getParameter("installationId"));

		final String statusStr = request.getParameter("status");
		if(statusStr != null){
			status = Status.getValueByIdn(statusStr);
		}
		final String installationTypeStr = request.getParameter("installationType");
		if(installationTypeStr != null){
			installationType = InstallationType.getValueByIdn(installationTypeStr);
		}
		phone = request.getParameter("phone");
		address = request.getParameter("address");
		city = request.getParameter("city");
		final String installationDateStr = request.getParameter("installationDate");
		installationDate = Translator.parseLocalDate(installationDateStr);
		tvSocketCount = Translator.parseInteger(request.getParameter("tvSocketCount"));
		netSocketCount = Translator.parseInteger(request.getParameter("netSocketCount"));
		cableQuantity = Translator.getDecimal(request.getParameter("cableQuantity"));
		dtvCount = Translator.parseInteger(request.getParameter("dtvCount"));
		multiroomCount = Translator.parseInteger(request.getParameter("multiroomCount"));
		netCount = Translator.parseInteger(request.getParameter("netCount"));
		telCount = Translator.parseInteger(request.getParameter("telCount"));
		atvCount = Translator.parseInteger(request.getParameter("atvCount"));
		comments = request.getParameter("comments");
		additionalComments = request.getParameter("additionalComments");

		twoWay1.populate(request, "twoWay1");
		twoWay2.populate(request, "twoWay2");
		oneWay1.populate(request, "oneWay1");
		oneWay2.populate(request, "oneWay2");
		modem.populate(request, "modem");
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

	public void setDtvCount(final Integer dtvCount) {
		this.dtvCount = dtvCount;
	}

	@Override
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	public void setInstallationDate(final LocalDate installationDate) {
		this.installationDate = installationDate;
	}

	public void setInstallationFilter(final InstallationFilterVO installationFilter) {
		this.installationFilter = installationFilter;
	}

	public void setInstallationId(final Integer installationId) {
		this.installationId = installationId;
	}

	public void setInstallationType(final InstallationType installationType) {
		this.installationType = installationType;
	}

	public void setModem(final InstallationDeviceModel modem) {
		this.modem = modem;
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

	public void setOneWay1(final InstallationDeviceModel oneWay) {
		oneWay1 = oneWay;
	}

	public void setOneWay2(final InstallationDeviceModel oneWay) {
		oneWay2 = oneWay;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setTelCount(final Integer telCount) {
		this.telCount = telCount;
	}

	public void setTvSocketCount(final Integer tvSocketCount) {
		this.tvSocketCount = tvSocketCount;
	}

	public void setTwoWay1(final InstallationDeviceModel twoWay1) {
		this.twoWay1 = twoWay1;
	}

	public void setTwoWay2(final InstallationDeviceModel twoWay2) {
		this.twoWay2 = twoWay2;
	}

	public void setUser(final String user) {
		this.user = user;
	}
}
