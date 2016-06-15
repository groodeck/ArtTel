<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	 xmlns:c="http://java.sun.com/jsp/jstl/core" 
	 xmlns:fn="http://java.sun.com/jsp/jstl/functions" >

	<jsp:directive.tag language="java" pageEncoding="ISO-8859-2"/>
		
	<jsp:directive.attribute name="device" type="org.arttel.controller.vo.InstallationDeviceModel" required="true"/>
	<jsp:directive.attribute name="prefix" type="String" required="true"/>
	 
	<table class="borderedTable" cellpadding="2" cellspacing="1" >
		<input type="hidden" name="${prefix}.installationId" value="${device.installationId}" />
		<input type="hidden" name="${prefix}.installationDeviceId" value="${device.installationDeviceId}" />
		<input type="hidden" name="${prefix}.deviceType" value="${device.deviceType.idn}" />
		<tr>
			<td class="label" colspan="2">${device.deviceType.desc}</td>
		</tr><tr>
			<td class="label" width='100px' >Nr seryjny</td>
			<td class="field"><input type="text" name="${prefix}.serialNumber" value="${device.serialNumber}"/></td>
		</tr>
		<c:if test="${device.deviceType != 'ONE_WAY_1' and device.deviceType != 'ONE_WAY_2'}">
			<tr>
				<td class="label">MAC Adres</td>
				<td class="field"><input type="text" name="${prefix}.macAddress" value="${device.macAddress}"/></td>
			</tr><tr id="${prefix}_downstream_row">
				<td class="label">Downstream</td>
				<td class="field"><input type="text" name="${prefix}.downstream" value="${device.downstream}"/></td>
			</tr><tr id="${prefix}_upstream_row">
				<td class="label">Upstream</td>
				<td class="field"><input type="text" name="${prefix}.upstream" value="${device.upstream}"/></td>
			</tr>
		</c:if>
		<tr>
			<td class="label">Uwagi</td>
			<td class="field">
				<textarea rows="3" cols="22" name="${prefix}.comments">${device.comments}</textarea>
			</td>
		</tr>
	</table>
	
</jsp:root>