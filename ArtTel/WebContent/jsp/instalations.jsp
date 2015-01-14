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
 	]]>
 	</jsp:text>
 
    <style type="text/css" >
    	 @IMPORT url("data/css/instalations.css");
	</style> 
     
    <form method="post" action="instalations.app"  enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
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
						selectedValue="${instalationFilter.instalationType}" />
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
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			
			<tr class="tableHeader">
				<td>L.p.</td>
				<td>Status</td>
				<td>Data instalacji</td>
				<td>Rodzaj instalacji</td>
				<td>Adres</td>
				<td>Nr seryjny</td>
				<td>MAC adres</td>
				<td>Downstream</td>
				<td>Upstream</td>
				<td>Uwagi</td>
				<td>Monter</td>
			</tr>
			
			<c:forEach items="${instalationList}" var="instalation" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}">
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" ><c:out value="${instalation.instalationId}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" style="background-color: ${instalation.status.color}"><c:out value="${instalation.status.desc}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" ><c:out value="${instalation.dataInstalacji}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" ><c:out value="${instalation.rodzajInstalacji.desc}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" ><c:out value="${instalation.adres}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" ><c:out value="${instalation.nrSeryjny}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" ><c:out value="${instalation.macAdres}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" style="background-color: ${instalation.downstreamColor}" ><c:out value="${instalation.downstream}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" style="background-color: ${instalation.upstreamColor}" ><c:out value="${instalation.upstream}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" ><c:out value="${instalation.uwagi}"/></td>
					<td  onclick="submitFormWithParam('edit',${instalation.instalationId})" ><c:out value="${instalation.user}"/></td>
					<td >
						<c:choose>
							<c:when test="${instalation.editable}">
								<input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${instalation.instalationId})"/>
							</c:when>
							<c:otherwise>
								<input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${instalation.instalationId})" disabled="disabled"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td><input type="button" value="Kopiuj" onclick="submitFormWithParam('copy',${instalation.instalationId})"/></td>
					<td>
						<c:choose>
							<c:when test="${instalation.editable and instalation.closable}">
								<input type="button" value="Zamknij" onclick="submitFormWithParam('close',${instalation.instalationId})"/>
							</c:when>
							<c:otherwise>
								<input type="button" value="Zamknij" onclick="submitFormWithParam('close',${instalation.instalationId})" disabled="disabled"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="instalationId" value="${selectedInstalation.instalationId}" />
		
		<br/>
		<b><c:out value="INSTALACJE - EDYCJA"/></b>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Status</td>
				<td class="field">
					<custom:select name="status" values="${selectsMap.status}" selectedValue="${selectedInstalation.status.idn}" />
				</td>
			</tr><tr>
				<td class="label">Rodzaj instalacji</td>
				<td class="field">
					<custom:select name="rodzajInstalacji" values="${selectsMap.instalationType}" selectedValue="${selectedInstalation.rodzajInstalacji.idn}" />
				</td>
			</tr><tr>
				<td class="label">Data podpisania umowy(yyyy-MM-dd)</td>
				<td class="field"><custom:date name="dataPodpisaniaUmowy" identifier="editDataPodpisaniaUmowy" value="${selectedInstalation.dataPodpisaniaUmowy}"/></td>
			</tr><tr>
				<td class="label">Nr telefonu</td>
				<td class="field"><input type="text" name="nrTelefonu" value="${selectedInstalation.nrTelefonu}"/></td>
			</tr><tr>
				<td class="label">Imię</td>
				<td class="field"><input type="text" name="imie" value="${selectedInstalation.imie}"/></td>
			</tr><tr>
				<td class="label">Nazwisko</td>
				<td class="field"><input type="text" name="nazwisko" value="${selectedInstalation.nazwisko}"/></td>
			</tr><tr>	
				<td class="label">Adres</td>
				<td class="field"><input type="text" name="adres" value="${selectedInstalation.adres}"/></td>
			</tr><tr>
				<td class="label">Miejscowość</td>
				<td class="field">
					<custom:select name="city" values="${selectsMap.cityDictionary}" selectedValue="${selectedInstalation.city}" />
				</td>
			</tr><tr>
				<td class="label">Pakiet</td>
				<td class="field"><input type="text" name="pakiet" value="${selectedInstalation.pakiet}"/></td>
			</tr><tr>
				<td class="label">Nr seryjny</td>
				<td class="field"><input type="text" name="nrSeryjny" value="${selectedInstalation.nrSeryjny}"/></td>
			</tr><tr>
				<td class="label">Data instalacji(yyyy-MM-dd)</td>
				<td class="field"><custom:date name="dataInstalacji" value="${selectedInstalation.dataInstalacji}" identifier="editDataInstalacji"/></td>
			</tr><tr>
				<td class="label">Ilość gniazd</td>
				<td class="field"><input type="text" name="iloscGniazd" value="${selectedInstalation.iloscGniazd}"/></td>
			</tr><tr>
				<td class="label">Wykonane instalacje</td>
				<td class="field"><input type="text" name="wykonaneInstalacje" value="${selectedInstalation.wykonaneInstalacje}"/></td>
			</tr><tr>
				<td class="label">Ilość kabla</td>
				<td class="field"><input type="text" name="iloscKabla" value="${selectedInstalation.iloscKabla}"/></td>
			</tr><tr>
				<td class="label">MAC Adres</td>
				<td class="field"><input type="text" name="macAdres" value="${selectedInstalation.macAdres}"/></td>
			</tr><tr>
				<td class="label">Downstream</td>
				<td class="field"><input type="text" name="downstream" value="${selectedInstalation.downstream}"/></td>
			</tr><tr>
				<td class="label">Upstream</td>
				<td class="field"><input type="text" name="upstream" value="${selectedInstalation.upstream}"/></td>
			</tr><tr>
				<td class="label">Uwagi</td>
				<td class="field"><input type="text" name="uwagi" value="${selectedInstalation.uwagi}"/></td>
			</tr><tr>
				<td class="label">Dodatkowe uwagi</td>
				<td class="field"><input type="text" name="dodatkoweUwagi" value="${selectedInstalation.dodatkoweUwagi}"/></td>
			</tr><tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<c:choose>
					<c:when test="${selectedInstalation.editable}">
						<input type="button" value="Zapisz" onclick="submitForm('save')" />
					</c:when>
					<c:otherwise><input type="button" value="Zapisz" onclick="submitForm('save')" disabled="disabled"/></c:otherwise>
					</c:choose>
				</td>
			</tr>

		</table>
	</c:if>
	</form>
	
	<form method="post" action="instalations.app?event=import_file"  enctype="multipart/form-data" accept-charset="UTF-8">
	<HR/>	
	<br/>
	<b><c:out value="INSTALACJE - IMPORT"/></b>
	<br/><br/>
	<table class="borderedTable" cellpadding="2" cellspacing="1" >
		
		<tr>
			<td>Wskaż plik</td>
            <td><INPUT NAME="F1" TYPE="file"/></td>
            <td colspan="2">
				<input type="button" value="Importuj" onclick="submit();" />
			</td>
		</tr>
		<br/>
		
		<c:if test="${not empty importErrors}"> 
			<tr>
				<td colspan="2"><B>Błędy importu</B></td>
			</tr>
			<c:forEach items="${importErrors}" var="error" varStatus="rowStatus">
				<tr>
					<td colspan="2" style="color: red;"><c:out value="${error}"/></td>
				</tr>
			</c:forEach>
		</c:if>
			
	</table>
	</form>

</jsp:root>