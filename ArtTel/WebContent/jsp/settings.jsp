<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:custom="urn:jsptagdir:/WEB-INF/tags"
	version="2.0" >
<jsp:directive.page import="org.arttel.dictionary.DealingType"/>
    
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
     
    <form method="post" action="settings.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
	<br/>
	<b><c:out value="USTAWIENIA"/></b>
	<br/><br/>
	
	<c:if test="${not empty settings.userBalanceList}"> 
		<custom:dealingBalance userBalanceList="${settings.userBalanceList}" isIndividual="true"/>
	</c:if>

	<br/>
	
	<!-- FUNDUSZE MONTERÓW -->
	
	<HR/>	
	<table class="borderedTable" cellpadding="2" cellspacing="1">
		<tr class="tableHeader">
			<td>Monter</td>
			<td>Kwota do dyspozycji</td>
			<td>Uwagi</td>
		</tr>
		<tr>
			<td class="field">
				<custom:select name="newFunds.user" values="${selectsMap.users}" selectedValue="${settings.newFunds.userName}" 
					onChange="setElementsVisibility()"/>
			</td>
			<td class="field">
				<input type="text" name="newFunds.amount" value="${settings.newFunds.entryAmount}"/>
			</td>
			<td class="field">
				<input type="text" name="newFunds.comments" value="${settings.newFunds.comments}"/>
			</td>
			<td class="field"><input type="button" value="Dodaj" onclick="submitForm('addFunds')"/></td>
		</tr>
	</table>
		
	<br/>
	<input type="button" value="Pokaż fundusze" onclick="submitForm('fundsDetails')"/>
	<br/>
	<br/>
	
	<c:if test="${not empty settings.fundsEntryList}">
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
				
			<tr class="tableHeader">
				<td>Monter</td>
				<td>Kwota dodana</td>
				<td>Data</td>
				<td>Uwagi</td>
			</tr>
				
			<c:forEach items="${settings.fundsEntryList}" var="fundsEntry" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}">
					<td><c:out value="${fundsEntry.userName}"/></td>
					<td><c:out value="${fundsEntry.entryAmount}"/></td>
					<td><c:out value="${fundsEntry.entryDate}"/></td>
					<td><c:out value="${fundsEntry.comments}"/></td>
					<!-- <td><input type="button" value="Usuń" onclick="submitFormWithParam('delFunds,${fundsEntry.fundsEntryId}')"/></td> -->
				</tr>
			</c:forEach>
			<tr class="tableHeader">
				<td>Suma</td>
				<td colspan="2"><c:out value="${settings.fundsEntrySum}"/></td>
			</tr>
		</table>	
	</c:if>
	
	<!-- MIASTA -->
	
	<HR/>	
	<table class="borderedTable" cellpadding="2" cellspacing="1">
		<tr class="tableHeader">
			<td>Nowe miasto</td>
			<td>w Instalacjach</td>
			<td>w Zleceniach</td>
			<td>w Raportach</td>
			<td>w Przeciskach</td>
			<td>w Obrocie</td>
		</tr>
		<tr>
			<td class="field"><input type="text" name="newCity.cityDesc" value="${settings.newCity.cityDesc}"/></td>
			<td class="field"><custom:checkbox name="newCity.forInstalation" value="${settings.newCity.forInstalation}"/></td>
			<td class="field"><custom:checkbox name="newCity.forOrder" value="${settings.newCity.forOrder}"/></td>
			<td class="field"><custom:checkbox name="newCity.forReport" value="${settings.newCity.forReport}"/></td>
			<td class="field"><custom:checkbox name="newCity.forSqueeze" value="${settings.newCity.forSqueeze}"/></td>
			<td class="field"><custom:checkbox name="newCity.forDealing" value="${settings.newCity.forDealing}"/></td>
			<td class="field"><input type="button" value="Dodaj" onclick="submitForm('addCity')"/></td>
		</tr>
	</table>
		
	<br/>
	<input type="button" value="Pokaż miasta" onclick="submitForm('citiesDetails')"/>
	<br/>
	<br/>
	
	<c:if test="${not empty settings.cityList}">
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
				
			<tr class="tableHeader">
				<td>Nazwa miejscowości</td>
				<td>w Instalacjach</td>
				<td>w Zleceniach</td>
				<td>w Raportach</td>
				<td>w Przeciskach</td>
				<td>w Obrocie</td>
			</tr>
				
			<c:forEach items="${settings.cityList}" var="city" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}">
					<td><c:out value="${city.cityDesc}"/></td>
					<td><custom:checkbox name="city.forInstalation.${rowStatus.count}" value="${city.forInstalation}" disabled="true"/></td>
					<td><custom:checkbox name="city.forOrder.${rowStatus.count}" value="${city.forOrder}" disabled="true"/></td>
					<td><custom:checkbox name="city.forReport.${rowStatus.count}" value="${city.forReport}" disabled="true"/></td>
					<td><custom:checkbox name="city.forSqueeze.${rowStatus.count}" value="${city.forSqueeze}" disabled="true"/></td>
					<td><custom:checkbox name="city.forDealing.${rowStatus.count}" value="${city.forDealing}" disabled="true"/></td>
					<!-- <td><input type="button" value="Usuń" onclick="submitFormWithParam('delCity,${city.cityId}')"/></td> -->
				</tr>
			</c:forEach>
		</table>	
	</c:if>
	
	<!-- KOSZTY FIRMOWE -->
	
	<HR/>	
	<table class="borderedTable" cellpadding="2" cellspacing="1">
		<tr class="tableHeader">
			<td>Nowy rodzaj kosztu</td>
			<td>w Instalacjach</td>
			<td>w Zleceniach</td>
			<td>w Raportach</td>
			<td>w Przeciskach</td>
			<td>w Obrocie</td>
		</tr>
		<tr>
			<td class="field"><input type="text" name="newCompanyCost.companyCostDesc" value="${settings.newCompanyCost.companyCostDesc}"/></td>
			<td class="field"><custom:checkbox name="newCompanyCost.forInstalation" value="${settings.newCompanyCost.forInstalation}"/></td>
			<td class="field"><custom:checkbox name="newCompanyCost.forOrder" value="${settings.newCompanyCost.forOrder}"/></td>
			<td class="field"><custom:checkbox name="newCompanyCost.forReport" value="${settings.newCompanyCost.forReport}"/></td>
			<td class="field"><custom:checkbox name="newCompanyCost.forSqueeze" value="${settings.newCompanyCost.forSqueeze}"/></td>
			<td class="field"><custom:checkbox name="newCompanyCost.forDealing" value="${settings.newCompanyCost.forDealing}"/></td>
			<td class="field"><input type="button" value="Dodaj" onclick="submitForm('addCompanyCost')"/></td>
		</tr>
	</table>
		
	<br/>
	<input type="button" value="Pokaż rodzaje Kosztów firmowych" onclick="submitForm('companyCostsDetails')"/>
	<br/>
	<br/>
	
	<c:if test="${not empty settings.companyCostsList}">
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
				
			<tr class="tableHeader">
				<td>Rodzaj kosztu firmowego</td>
				<td>w Instalacjach</td>
				<td>w Zleceniach</td>
				<td>w Raportach</td>
				<td>w Przeciskach</td>
				<td>w Obrocie</td>
			</tr>
				
			<c:forEach items="${settings.companyCostsList}" var="cost" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}">
					<td><c:out value="${cost.companyCostDesc}"/></td>
					<td><custom:checkbox name="cost.forInstalation.${rowStatus.count}" value="${cost.forInstalation}" disabled="true"/></td>
					<td><custom:checkbox name="cost.forOrder.${rowStatus.count}" value="${cost.forOrder}" disabled="true"/></td>
					<td><custom:checkbox name="cost.forReport.${rowStatus.count}" value="${cost.forReport}" disabled="true"/></td>
					<td><custom:checkbox name="cost.forSqueeze.${rowStatus.count}" value="${cost.forSqueeze}" disabled="true"/></td>
					<td><custom:checkbox name="cost.forDealing.${rowStatus.count}" value="${cost.forDealing}" disabled="true"/></td>
				</tr>
			</c:forEach>
		</table>	
	</c:if>
	
	</form>

</body>	
</html>

</jsp:root>