package org.arttel.controller.vo;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.arttel.dictionary.ReportType;
import org.arttel.util.Translator;

public class ReportParamsVO extends BasePageVO {
	
	private Date dataOd;
	private Date dataDo;
	private ReportType reportType;
	private String city;
	
	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	public Date getDataOd() {
		return dataOd;
	}

	public void setDataOd(Date dataOd) {
		this.dataOd = dataOd;
	}

	public Date getDataDo() {
		return dataDo;
	}

	public void setDataDo(Date dataDo) {
		this.dataDo = dataDo;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	protected String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setEditable(boolean editable) {
		// TODO Auto-generated method stub
		
	}
	
}
