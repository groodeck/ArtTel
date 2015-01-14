<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:custom="urn:jsptagdir:/WEB-INF/tags"
	version="2.0" >
    
<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
     
    <style type="text/css" >
    	 @IMPORT url("data/css/instalations.css");
	</style> 
    

 <html xmlns="http://www.w3.org/1999/xhtml">
 <body style="background-color: #F0FFF0">
     
    <form method="post" action="clients.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
	<c:if test="${event=='MAIN' || event=='SEARCH'}">
		
		<br/>
		<b><c:out value="KLIENCI - FILTR"/></b>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Nazwa klienta</td>
				<td class="field" colspan="1">
					<input type="text" name="clientFilter.name" value="${clientFilter.name}" />
				</td>
				<td width="10px"/>
				<td align="right" ><input type="button" value="Szukaj" onclick="submitForm('search')"/></td>
			</tr>
		</table>
		
		<br/>
		<input type="button" value="Dodaj" onclick="submitForm('new')"/>
		<br/><br/>

		<c:if test="${not empty clientList}">
			<table class="borderedTable" cellpadding="2" cellspacing="1" >
				
				<tr class="tableHeader">
					<td>Numer</td>
					<td>Nazwa</td>
					<td>w Instalacjach</td>
					<td>w Zleceniach</td>
					<td>w Raportach</td>
					<td>w Przeciskach</td>
					<td>w Obrocie</td>
					<td>w Fakturach</td>
					
				</tr>
				
				<c:forEach items="${clientList}" var="client" varStatus="rowStatus">
					<tr class="row${rowStatus.index%2}">
						<td onclick="submitFormWithParam('edit',${client.clientId})"><c:out value="${client.clientId}"/></td>
						<td onclick="submitFormWithParam('edit',${client.clientId})"><c:out value="${client.clientDesc}"/></td>
						<td onclick="submitFormWithParam('edit',${client.clientId})"><custom:checkbox name="forInstalation" value="${client.forInstalation}" disabled="true"/></td>
						<td onclick="submitFormWithParam('edit',${client.clientId})"><custom:checkbox name="forOrder" value="${client.forOrder}" disabled="true"/></td>
						<td onclick="submitFormWithParam('edit',${client.clientId})"><custom:checkbox name="forReport" value="${client.forReport}" disabled="true"/></td>
						<td onclick="submitFormWithParam('edit',${client.clientId})"><custom:checkbox name="forSqueeze" value="${client.forSqueeze}" disabled="true"/></td>
						<td onclick="submitFormWithParam('edit',${client.clientId})"><custom:checkbox name="forDealing" value="${client.forDealing}" disabled="true"/></td>
						<td onclick="submitFormWithParam('edit',${client.clientId})"><custom:checkbox name="forInvoice" value="${client.forInvoice}" disabled="true"/></td>
						<td><input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${client.clientId})"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>	
	</c:if>
	
		
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="clientId" value="${selectedClient.clientId}" />
		
		<br/>
		<b><c:out value="KLIENT - EDYCJA"/></b>
		<br/><br/>
		
		<table>
			<tr>
				<td class="label">Nazwa klienta</td>
				<td class="field">
					<input type="text" name="clientDesc" value="${selectedClient.clientDesc}"/>
				</td>
			</tr>
			<tr>
				<td class="label">NIP</td>
				<td class="field">
					<input type="text" name="nip" value="${selectedClient.nip}"/>
				</td>
			</tr>
			
			<tr>
				<td class="label">Miejscowość</td>
				<td class="field">
					<input type="text" name="city" value="${selectedClient.city}"/>
				</td>
			</tr>
			<tr>
				<td class="label">Kod pocztowy</td>
				<td class="field">
					<input type="text" name="zip" value="${selectedClient.zip}"/>
				</td>
			</tr>
			<tr>
				<td class="label">Ulica</td>
				<td class="field">
					<input type="text" name="street" value="${selectedClient.street}"/>
				</td>
			</tr>
			<tr>
				<td class="label">Nr domu</td>
				<td class="field">
					<input type="text" name="house" value="${selectedClient.house}"/>
				</td>
			</tr>
			<tr>
				<td class="label">Nr mieszkania</td>
				<td class="field">
					<input type="text" name="appartment" value="${selectedClient.appartment}"/>
				</td>
			</tr>
			<tr>
				<td class="label">w Instalacjach</td>
				<td class="field"><custom:checkbox name="forInstalation" value="${selectedClient.forInstalation}"/></td>
			</tr>
			<tr>
				<td class="label">w Zleceniach</td>
				<td class="field"><custom:checkbox name="forOrder" value="${selectedClient.forOrder}"/></td>
			</tr>
			<tr>
				<td class="label">w Raportach</td>
				<td class="field"><custom:checkbox name="forReport" value="${selectedClient.forReport}"/></td>
			</tr>
			<tr>
				<td class="label">w Przeciskach</td>
				<td class="field"><custom:checkbox name="forSqueeze" value="${selectedClient.forSqueeze}"/></td>
			</tr>
			<tr>
				<td class="label">w Obrocie</td>
				<td class="field"><custom:checkbox name="forDealing" value="${selectedClient.forDealing}"/></td>
			</tr>
			<tr>
				<td class="label">w Fakturach</td>
				<td class="field"><custom:checkbox name="forInvoice" value="${selectedClient.forInvoice}"/></td>
			</tr>
		</table>
		<table>
			<tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<input type="button" value="Zapisz" onclick="submitForm('save')" />
				</td>
			</tr>
		</table>
	
	</c:if>
	
	</form>

</body>	
</html>

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

</jsp:root>