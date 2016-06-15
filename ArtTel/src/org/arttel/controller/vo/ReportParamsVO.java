package org.arttel.controller.vo;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.dictionary.ReportType;
import org.arttel.util.Translator;

public class ReportParamsVO {

	private Date dataOd;
	private Date dataDo;
	private ReportType reportType;
	private String city;

	public String getCity() {
		return city;
	}

	public Date getDataDo() {
		return dataDo;
	}

	public Date getDataOd() {
		return dataOd;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public void populate( final HttpServletRequest request ) {

		final String dataOdStr = request.getParameter("dataOd");
		dataOd = Translator.parseDate(dataOdStr, null);
		final String dataDoStr = request.getParameter("dataDo");
		dataDo = Translator.parseDate(dataDoStr, null);
		final String reportTypeStr = request.getParameter("reportType");
		if(reportTypeStr != null){
			reportType = ReportType.getValueByIdn(reportTypeStr);
		}
		city = request.getParameter("city");
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public void setDataDo(final Date dataDo) {
		this.dataDo = dataDo;
	}

	public void setDataOd(final Date dataOd) {
		this.dataOd = dataOd;
	}

	public void setReportType(final ReportType reportType) {
		this.reportType = reportType;
	}
}
