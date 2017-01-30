<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root version="2.0"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core" 
	xmlns:custom="urn:jsptagdir:/WEB-INF/tags" >
	
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
        import="org.arttel.util.Translator"/>

<jsp:text>
 	<![CDATA[
 		<script type="text/javascript" src="data/js/common.js" ></script>
		<script type="text/javascript" src="data/js/jquery.min.js" ></script>
		<script type="text/javascript" src="data/js/jquery-ui.min.js" ></script>
 		 		
 		<script type="text/javascript" defer='defer'>
 		
 			var changeInstallationType = function(){
 				var installationType = $("#installationType");
 				if(installationType != null && installationType.val() != null){
	 				if(installationType.val() == 'zwrot'){
		 				$("#twoWay1_upstream_row").hide();
		 				$("#twoWay1_downstream_row").hide();
		 				
		 				$("#twoWay2_upstream_row").hide();
		 				$("#twoWay2_downstream_row").hide();
		 				
		 				$("#modem_upstream_row").hide();
		 				$("#modem_downstream_row").hide();
	 				} else {
	 					$("#twoWay1_upstream_row").show();
		 				$("#twoWay1_downstream_row").show();
		 				
		 				$("#twoWay2_upstream_row").show();
		 				$("#twoWay2_downstream_row").show();
		 				
		 				$("#modem_upstream_row").show();
		 				$("#modem_downstream_row").show();
	 				}
 				}
 			};
 			
 		</script>
 	]]>
 	</jsp:text>
 	
    <style type="text/css" >
    	 @IMPORT url("data/css/instalations.css");
	</style> 
     
    <form method="post" action="instalations.app"  enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
	<custom:message uiMessage="${uiMessage}"/>
	
	<c:if test="${event=='MAIN' || event=='SEARCH'}">
	
		<br/>
		<b><c:out value="INSTALACJE - FILTR"/></b>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Szybkie wyszukiwanie</td>
				<td class="field" colspan="1">
					<input type="text" style="width: 100%" name="instalationFilter.phrase" value="${instalationFilter.phrase}" />
				</td>
			</tr>
			<tr>
				<td class="label">Miejscowość</td>
				<td class="field">
					<custom:select name="instalationFilter.city" values="${selectsMap.cityDictionaryWithEmpty}" selectedValue="${instalationFilter.city}" />
				</td>
				<td class="label">Status</td>
				<td class="field">
					<custom:select name="instalationFilter.status" values="${selectsMap.statusWithEmpty}" selectedValue="${instalationFilter.status}" />
				</td>
			</tr>
			<tr>
				<td class="label">Data od</td>
				<td class="field">
					<custom:date name="instalationFilter.dateFrom" identifier="filterDateFrom" value="${instalationFilter.dateFrom}"  />
				</td>
				<td class="label">Data do</td>
				<td class="field">
					<custom:date name="instalationFilter.dateTo" identifier="filterDateTo" value="${instalationFilter.dateTo}"  />
				</td>
				<td width="10px"/>
				<td align="right" ><input type="button" value="Szukaj" onclick="submitForm('search')"/></td>
				
				<td width="30px"/>
			</tr>
			<tr>
				<td class="label">Rodzaj instalacji</td>
				<td class="field">
					<custom:select name="instalationFilter.instalationType" values="${selectsMap.instalationTypeWithEmpty}" 
						selectedValue="${instalationFilter.firstInstalationType}" />
				</td>
				<td class="label">Monter</td>
				<td class="field">
					<custom:select name="instalationFilter.user" values="${selectsMap.users}" selectedValue="${instalationFilter.user}" />
				</td>
			</tr>
			<tr>
				<td class="label">Nr seryjny</td>
				<td class="field">
					<input type="text" style="width: 100%" name="instalationFilter.serial" value="${instalationFilter.serial}" />
				</td>
				<td class="label">MAC adres</td>
				<td class="field">
					<input type="text" style="width: 100%" name="instalationFilter.mac" value="${instalationFilter.mac}" />
				</td>
			</tr>
		</table>

<!-- TODO: wrzucic do TAGa -->
		<c:if test="${unclearedSocketCount != null}">
			<br/>
			<table class="borderedTable" cellpadding="2" cellspacing="1">
				<tr>
					<td class="label" nowrap="nowrap" colspan="4">Do rozliczenia</td>
				</tr>
				<tr>
					<td class="field">
						Miejscowość
					</td><td class="field" colspan="3">
						<custom:select name="selectedCity" values="${selectsMap.cityDictionary}" selectedValue="${selectedCity}" 
								onChange="submitForm('change_selected_city')"/>
					</td>
				</tr>
				<tr>
					<td class="field">
						Nierozliczone gniazda
					</td><td style="width: 100px" class="field" >
						<table style="border-style: solid;border-width:1px; border-color: black;">
							<tr style="width: 100%; height: 10px">
								<c:set var="socketPercentage" value="${unclearedSocketCount / 200 * 100}"/>
								<c:if test="${socketPercentage > 0}">
									<td style="background-color: red; width: ${socketPercentage}"/>
								</c:if> 
								<td style="background-color: white; width: ${100-socketPercentage}"/>
							</tr>
						</table>
					</td><td class="field">
						<c:out value="${unclearedSocketCount}"></c:out>
					</td>
					<td align="right" class="field">
						<input type="button" value="Resetuj" onclick="submitForm('reset_socket_order')"/>
					</td>
				</tr>
				
				<tr>
					<td class="field">
						Nierozliczone instalacje
					</td><td style="width: 100px" class="field" >
						<table style="border-style: solid;border-width:1px; border-color: black;">
							<tr style="width: 100%; height: 10px">
								<c:set var="unclearedInstalationsPercentage" value="${unclearedInstalationCount / 200 * 100}"/>
								<c:if test="${socketPercentage > 0}">
									<td style="background-color: red; width: ${unclearedInstalationsPercentage}"/>
								</c:if> 
								<td style="background-color: white; width: ${100-unclearedInstalationsPercentage}"/>
							</tr>
						</table>
					</td><td class="field">
						<c:out value="${unclearedInstalationCount}"></c:out>
					</td>
					<td align="right" class="field">
						<input type="button" value="Resetuj" onclick="submitForm('clear_instalations')"/>
					</td>
				</tr>
				
			</table>
		</c:if>
	
		
		<br/>
		<input type="button" value="Dodaj" onclick="submitForm('new')"/>
		<br/><br/>
		<c:if test="${not empty instalationList.records}">
			<table class="borderedTable" cellpadding="2" cellspacing="1" >
				
				<tr class="tableHeader">
					<td/>
					<custom:sortableHeader column="${installationsTableHeader.columns.installationId}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.installationType}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.status}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.installationDate}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.address}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.twoWay1_downstream}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.twoWay1_upstream}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.twoWay2_downstream}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.twoWay2_upstream}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.modem_downstream}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.modem_upstream}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.comments}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
					<custom:sortableHeader column="${installationsTableHeader.columns.user}" sortUrl="instalations.app?event=sort&amp;sortColumn=" />
				</tr>
				
				<c:forEach items="${instalationList.records}" var="instalation" varStatus="rowStatus">
					<tr class="row${rowStatus.count%2}">
						<td>
							<custom:checkbox name="resultRecordSelected_${rowStatus.index}" value="" />				
						</td>
						<td  onclick="submitFormWithParam('edit',${instalation.installationId})" ><c:out value="${instalation.installationId}"/></td>
						<td  onclick="submitFormWithParam('edit',${instalation.installationId})" ><c:out value="${instalation.installationType.desc}"/></td>
						<td  onclick="submitFormWithParam('edit',${instalation.installationId})" style="background-color: ${instalation.status.color}"><c:out value="${instalation.status.desc}"/></td>
						<td  onclick="submitFormWithParam('edit',${instalation.installationId})" ><c:out value="${instalation.installationDate}"/></td>
						<td  onclick="submitFormWithParam('edit',${instalation.installationId})" ><c:out value="${instalation.address}"/></td>
						<c:choose>
							<c:when test="${not empty instalation.twoWay1}">
								<td  onclick="submitFormWithParam('edit',${instalation.installationId})" style="background-color: ${instalation.twoWay1.downstreamColor}"><c:out value="${instalation.twoWay1.downstream}"/></td>
								<td  onclick="submitFormWithParam('edit',${instalation.installationId})" style="background-color: ${instalation.twoWay1.upstreamColor}"><c:out value="${instalation.twoWay1.upstream}"/></td>
							</c:when>
							<c:otherwise>
								<td/>
								<td/>			
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${not empty instalation.twoWay2}">
								<td  onclick="submitFormWithParam('edit',${instalation.installationId})" style="background-color: ${instalation.twoWay2.downstreamColor}" ><c:out value="${instalation.twoWay2.downstream}"/></td>
								<td  onclick="submitFormWithParam('edit',${instalation.installationId})" style="background-color: ${instalation.twoWay2.upstreamColor}" ><c:out value="${instalation.twoWay2.upstream}"/></td>
							</c:when>
							<c:otherwise>
								<td/>
								<td/>			
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${not empty instalation.modem}">
								<td  onclick="submitFormWithParam('edit',${instalation.installationId})" style="background-color: ${instalation.modem.downstreamColor}" ><c:out value="${instalation.modem.downstream}"/></td>
								<td  onclick="submitFormWithParam('edit',${instalation.installationId})" style="background-color: ${instalation.modem.upstreamColor}" ><c:out value="${instalation.modem.upstream}"/></td>
							</c:when>
							<c:otherwise>
								<td/>
								<td/>			
							</c:otherwise>
						</c:choose>
						<td  onclick="submitFormWithParam('edit',${instalation.installationId})" ><c:out value="${instalation.comments}"/></td>
						<td  onclick="submitFormWithParam('edit',${instalation.installationId})" ><c:out value="${instalation.user}"/></td>
					</tr>
				</c:forEach>
			</table>
			<br/>
			<table>
				<tr>
					<td>
						<input type="button" value="Usuń" onclick="submitFormWithConfirmation('delete')"/>
					</td><td>
						<input type="button" value="Kopiuj" onclick="submitForm('copy')"/>
					</td><td>
						<input type="button" value="Zamknij" onclick="submitFormWithConfirmation('close')"/>
					</td>
					<td width="20px"/>
					<td>
						Strona: <custom:pageNav page="${instalationList}" pageChangeUrl="instalations.app?event=changePage&amp;newPageNo="/>
					</td>
				</tr>
			</table>
			
		</c:if>
	</c:if>
	
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="installationId" value="${selectedInstalation.installationId}" />
		
		<br/>
		<b><c:out value="INSTALACJE - EDYCJA"/></b>
		<br/><br/>
		
		<div style="float:left; width: 40%;">
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Rodzaj instalacji</td>
				<td class="field">
					<custom:select name="installationType" values="${selectsMap.instalationType}" selectedValue="${selectedInstalation.installationType.idn}" 
						onChange="changeInstallationType()"/>
				</td>
			</tr><tr>
				<td class="label">Status</td>
				<td class="field">
					<custom:select name="status" values="${selectsMap.status}" selectedValue="${selectedInstalation.status.idn}" />
				</td>
			</tr><tr>
				<td class="label">Data instalacji(yyyy-MM-dd)</td>
				<td class="field"><custom:date name="installationDate" value="${selectedInstalation.installationDate}" identifier="editDataInstalacji"/></td>
			</tr><tr>
				<td class="label">Miejscowość</td>
				<td class="field">
					<custom:select name="city" values="${selectsMap.cityDictionary}" selectedValue="${selectedInstalation.city}" />
				</td>
			</tr><tr>	
				<td class="label">Adres</td>
				<td class="field"><input type="text" name="address" value="${selectedInstalation.address}"/></td>
			</tr><tr>
				<td class="label">Nr telefonu</td>
				<td class="field"><input type="text" name="phone" value="${selectedInstalation.phone}"/></td>
			</tr><tr>
				<td class="label">DTV</td>
				<td class="field"><input type="text" name="dtvCount" value="${selectedInstalation.dtvCount}"/></td>
			</tr><tr>
				<td class="label">Multiroom ATV/DTV: przebudowy</td>
				<td class="field"><input type="text" name="multiroomCount" value="${selectedInstalation.multiroomCount}"/></td>
			</tr><tr>
				<td class="label">NET</td>
				<td class="field"><input type="text" name="netCount" value="${selectedInstalation.netCount}"/></td>
			</tr><tr>
				<td class="label">TEL</td>
				<td class="field"><input type="text" name="telCount" value="${selectedInstalation.telCount}"/></td>
			</tr><tr>
				<td class="label">ATV (bez usług DTV)</td>
				<td class="field"><input type="text" name="atvCount" value="${selectedInstalation.atvCount}"/></td>
			</tr><tr>
				<td class="label">Gniazda TV</td>
				<td class="field"><input type="text" name="tvSocketCount" value="${selectedInstalation.tvSocketCount}"/></td>
			</tr><tr>
				<td class="label">Gniazda NET</td>
				<td class="field"><input type="text" name="netSocketCount" value="${selectedInstalation.netSocketCount}"/></td>
			</tr><tr>
				<td class="label">Ilość kabla</td>
				<td class="field"><input type="text" name="cableQuantity" value="${selectedInstalation.cableQuantity}"/></td>
			</tr><tr>
				<td class="label">Uwagi</td>
				<td class="field"><input type="text" name="comments" value="${selectedInstalation.comments}"/></td>
			</tr><tr>
				<td class="label">Dodatkowe uwagi</td>
				<td class="field"><input type="text" name="additionalComments" value="${selectedInstalation.additionalComments}"/></td>
			</tr>
		</table>
		</div>

		<div style="float:left; width: 60%;">
			<table>
				<tr>
					<td>
						<custom:installationDevice device="${selectedInstalation.twoWay1}" prefix="twoWay1"/>
					</td>
					<td width="20px;"/>
					<td>
						<custom:installationDevice device="${selectedInstalation.twoWay2}" prefix="twoWay2"/>
					</td>
				</tr>
				<tr><td height="20px;"/></tr>
				<tr>
					<td>
						<custom:installationDevice device="${selectedInstalation.oneWay1}" prefix="oneWay1"/>
					</td>
					<td width="20px;"/>
					<td>
						<custom:installationDevice device="${selectedInstalation.oneWay2}" prefix="oneWay2"/>
					</td>
				</tr>
				<tr><td height="20px;"/></tr>
				<tr>
					<td>
						<custom:installationDevice device="${selectedInstalation.modem}" prefix="modem"/>
					</td>
					<td width="20px;"/>
					<td />
				</tr>
			</table>
		</div>

		<div style="clear:both" align="center">
			<br/>
			<table>
				<tr>
					<td>
						<input type="button" value="Anuluj" onclick="submitForm('back')"/>
					</td>
					<td width="20px"/>
					<td>
						<c:choose>
							<c:when test="${selectedInstalation.editable}">
								<input type="button" value="Zapisz" onclick="submitForm('save')" />
							</c:when>
							<c:otherwise><input type="button" value="Zapisz" onclick="submitForm('save')" disabled="disabled"/></c:otherwise>
						</c:choose>
					</td>
				
				</tr>
			</table>
		</div>

		<jsp:text>
	 	<![CDATA[
	 		<script type="text/javascript" defer='defer'>
	 			changeInstallationType();
	 		</script>
	 	]]>
	 	</jsp:text>
	 	
	</c:if>
	</form>
	
</jsp:root>