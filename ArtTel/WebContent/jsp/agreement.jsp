<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:custom="urn:jsptagdir:/WEB-INF/tags"
	version="2.0" >
    
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
     
    <jsp:text>
 	<![CDATA[
 		<script type="text/javascript" src="data/js/common.js" ></script>
 		 <script type="text/javascript" language="javascript">
     
     	function submitForm(event){
     		document.getElementById('event').value = event;
     		document.forms[0].submit();
     	}
     	
     </script>
 	]]>
 
 	</jsp:text>
 	
    <style type="text/css" >
    	 @IMPORT url("data/css/instalations.css");
	</style> 
    

     
 <html xmlns="http://www.w3.org/1999/xhtml">
 <body style="background-color: #F0FFF0">
     
    <form method="post" action="agreements.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
	<c:if test="${event=='MAIN' || event=='SEARCH'}">
		
		<br/>
		<b><c:out value="UMOWY - FILTR"/></b>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Szybkie wyszukiwanie</td>
				<td class="field" colspan="1">
					<input type="text" style="width: 100%" name="agreementFilter.phrase" value="${agreementFilter.phrase}" />
				</td>
			</tr>
			<tr>
				<td class="label">Nazwisko</td>
				<td class="field">
					<input type="text" name="agreementFilter.surname" value="${agreementFilter.surname}" />
				</td>
				<td class="label">Data od</td>
				<td class="field">
					<custom:date name="agreementFilter.dateFrom" identifier="filterDateFrom" value="${agreementFilter.dateFrom}"  />
				</td>
				<td class="label">Data do</td>
				<td class="field">
					<custom:date name="agreementFilter.dateTo" identifier="filterDateTo" value="${agreementFilter.dateTo}"  />
				</td>
				<td width="10px"/>
				<td align="right" ><input type="button" value="Szukaj" onclick="submitForm('search')"/></td>
			</tr>
		</table> 
		<br/>
		
		<input type="button" value="Dodaj" onclick="submitForm('new')"/>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			
			<tr class="tableHeader">
				<td>L.p.</td>
				<td>Data podpisania umowy</td>
				<td>Abonent <br/>(nazwisko i imię)</td>	
				<td>Adres</td>
				<td>Miejscowość</td>
				<td>Nr telefonu</td>		
				<td>Data zakończenia umowy</td>
				<td>Przedstawiciel handlowy</td>
			</tr>
			
			<c:forEach items="${agreementList}" var="agreement" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}">
					<td  onclick="submitFormWithParam('edit',${agreement.agreementId})" ><c:out value="${agreement.agreementId}"/></td>
					<td  onclick="submitFormWithParam('edit',${agreement.agreementId})" ><c:out value="${agreement.signDate}"/></td>
					<td  onclick="submitFormWithParam('edit',${agreement.agreementId})" ><c:out value="${agreement.surname} ${agreement.name}"/></td>
					<td  onclick="submitFormWithParam('edit',${agreement.agreementId})" ><c:out value="${agreement.address}"/></td>
					<td  onclick="submitFormWithParam('edit',${agreement.agreementId})" ><c:out value="${agreement.city}"/></td>
					<!-- <td  onclick="submitFormWithParam('edit',${agreement.agreementId})" ><c:out value="${agreement.bundle}"/></td> -->
					<td  onclick="submitFormWithParam('edit',${agreement.agreementId})" ><c:out value="${agreement.phone}"/></td>
					<td  onclick="submitFormWithParam('edit',${agreement.agreementId})" ><c:out value="${agreement.agreementEndDate}"/></td>
					<td  onclick="submitFormWithParam('edit',${agreement.agreementId})" ><c:out value="${agreement.user}"/></td>
					<td >
						<c:choose>
							<c:when test="${agreement.editable}">
								<input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${agreement.agreementId})"/>
							</c:when>
							<c:otherwise>
								<input type="button" value="  -  " disabled="disabled"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td ><input type="button" value="Kopiuj" onclick="submitFormWithParam('copy',${agreement.agreementId})"/></td>
				</tr>
			</c:forEach>
		</table>	
	</c:if>
	
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="agreementId" value="${selectedAgreement.agreementId}" />
		
		<br/>
		<b><c:out value="UMOWA - EDYCJA"/></b>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label" style="width: 120px;">Status</td>
				<td class="field" style="width: 230px;">
					<custom:select name="status" values="${selectsMap.status}" selectedValue="${selectedAgreement.status.idn}" />
				</td>
			</tr><tr>
				<td class="label">Data podpisania umowy</td>
				<td class="field"><custom:date name="signDate" identifier="signDate" value="${selectedAgreement.signDate}"/></td>
			</tr><tr>
				<td class="label">Przychody/Firma</td>
				<td class="field"><custom:select name="client" values="${selectsMap.clients}" selectedValue="${selectedAgreement.client}"/></td>
			</tr><tr id="companyCostRow">
				<td class="label">Nr umowy</td>
				<td class="field"><input type="text" name="agreementNumber" value="${selectedAgreement.agreementNumber}"/></td>
			</tr><tr id="privateCostRow">
				<td class="label">Imie</td>
				<td class="field"><input type="text" name="name" value="${selectedAgreement.name}"/></td>
			</tr><tr id="incomeRow">
				<td class="label">Nazwisko</td>
				<td class="field"><input type="text" name="surname" value="${selectedAgreement.surname}"/></td>
			</tr><tr id="fuelRow">
				<td class="label">Ulica</td>
				<td class="field"><input type="text" name="street" value="${selectedAgreement.street}"/></td>
			</tr><tr>
				<td class="label">Nr domu</td>
				<td class="field"><input type="text" name="house" value="${selectedAgreement.house}"/></td>
			</tr><tr>
				<td class="label">Nr mieszkania</td>
				<td class="field"><input type="text" name="apartment" value="${selectedAgreement.apartment}"/></td>
			</tr><tr>
				<td class="label">Nr telefonu</td>
				<td class="field"><input type="text" name="phone" value="${selectedAgreement.phone}"/></td>
			</tr><tr>
				<td class="label">Pakiet</td>
				<td class="field"><input type="text" name="bundle" value="${selectedAgreement.bundle}"/></td>
			</tr><tr>
				<td class="label">Miejscowość</td>
				<td class="field">
					<custom:select name="city" values="${selectsMap.cityDictionary}" selectedValue="${selectedAgreement.city}" />
				</td>
			</tr><tr>
				<td class="label">Miejsce podpisania umowy</td>
				<td class="field">
					<custom:select name="signPlace" values="${selectsMap.signPlace}" selectedValue="${selectedAgreement.signPlace}" />
				</td>
			</tr><tr>
				<td class="label">Przedstawiciel handlowy</td>
				<td class="field">
					<custom:select name="isAgentDeal" values="${selectsMap.yesNo}" selectedValue="${selectedAgreement.agentDeal}" />
				</td>
			</tr><tr>
				<td class="label">Data instalacji</td>
				<td class="field"><custom:date name="installationDate" identifier="installationDate" value="${selectedAgreement.installationDate}"/></td>
			</tr><tr>
				<td class="label">Data wpisu do systemu ewidencyjnego</td>
				<td class="field"><custom:date name="evidenceEntryDate" identifier="evidenceEntryDate" value="${selectedAgreement.evidenceEntryDate}"/></td>
			</tr><tr>
				<td class="label">Kod sprzedawcy</td>
				<td class="field"><input type="text" name="salesmanCode" value="${selectedAgreement.salesmanCode}"/></td>
			</tr><tr>
				<td class="label">Nr abonenta</td>
				<td class="field"><input type="text" name="subscriberNumber" value="${selectedAgreement.subscriberNumber}"/></td>
			</tr><tr>
				<td class="label">Data zakończenia umowy</td>
				<td class="field"><custom:date name="agreementEndDate" identifier="agreementEndDate" value="${selectedAgreement.agreementEndDate}"/></td>
			</tr><tr>
				<td class="label">Uwagi</td>
				<td class="field"><input type="text" name="comments" value="${selectedAgreement.comments}"/></td>
			</tr><tr>
				<td class="label">Dodatkowe uwagi</td>
				<td class="field"><input type="text" name="additionalComments" value="${selectedAgreement.additionalComments}"/></td>
			</tr><tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<c:choose>
					<c:when test="${selectedAgreement.editable}">
						<input type="button" value="Zapisz" onclick="submitForm('save')" />
					</c:when>
					<c:otherwise>
						<input type="button" value="Zapisz" disabled="disabled"/>
					</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	
	</c:if>
	
	</form>

</body>	
</html>

</jsp:root>