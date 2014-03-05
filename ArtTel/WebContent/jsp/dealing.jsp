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
     
    <form method="post" action="dealing.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<c:import url="menu.jsp" />
	
	<c:if test="${event=='MAIN' || event=='SEARCH'}">
		
		<br/>
		<table>
			<tr>
				<td><b>Saldo użytkownika:</b></td><td style="width: 50px;"/><td><b>Saldo firmy:</b>
					<custom:select name="dealingYear" values="${selectsMap.dealingYears}" selectedValue="${companyBalanceList[0].dealingYear}"
						onChange="submitForm('reloadBalance')" />
				</td>
			</tr>
			<tr>
				<td>
					<c:if test="${not empty userBalanceList}"> 
						<custom:dealingBalance isIndividual="true" userBalanceList="${userBalanceList}"/>
					</c:if>
				</td>
				<td/>
				<td>
					<c:if test="${not empty companyBalanceList}"> 
						<custom:dealingBalance userBalanceList="${companyBalanceList}"/>
					</c:if>
				</td>
			</tr>
		</table>
		<br/>
		
		<hr size="2px"/>
		
		<b><c:out value="OBRÓT - FILTR"/></b>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Szybkie wyszukiwanie</td>
				<td class="field" colspan="1">
					<input type="text" style="width: 100%" name="dealingFilter.phrase" value="${dealingFilter.phrase}" />
				</td>
			</tr>
			<tr>
				<td class="label">Koszty firmowe</td>
				<td class="field">
					<custom:select name="dealingFilter.companyCosts" values="${selectsMap.companyCosts}" selectedValue="${dealingFilter.companyCosts}" />
				</td>
				<td class="label">Data od</td>
				<td class="field">
					<custom:date name="dealingFilter.dateFrom" identifier="filterDateFrom" value="${dealingFilter.dateFrom}"  />
				</td>
				<td class="label">Data do</td>
				<td class="field">
					<custom:date name="dealingFilter.dateTo" identifier="filterDateTo" value="${dealingFilter.dateTo}"  />
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
				<td>Rodzaj obrotu</td>
				<td>Data</td>	
				<td>Obrót</td>
				<td>Kwota</td>
				<td>Uwagi 1</td>		
				<td>Uwagi 2</td>
				<td>Monter</td>
			</tr>
			
			<c:forEach items="${dealingList}" var="dealing" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}">
					<td  onclick="submitFormWithParam('edit',${dealing.dealingId})" ><c:out value="${dealing.dealingId}"/></td>
					<td  onclick="submitFormWithParam('edit',${dealing.dealingId})" ><c:out value="${dealing.dealingType.desc}"/></td>
					<td  onclick="submitFormWithParam('edit',${dealing.dealingId})" ><c:out value="${dealing.date}"/></td>
					<td  onclick="submitFormWithParam('edit',${dealing.dealingId})" style="background-color: ${dealing.dealingType.color}" >
						<c:out value="${dealing.dealingTitle}"/>
					</td>
					<td  onclick="submitFormWithParam('edit',${dealing.dealingId})" ><c:out value="${dealing.amount}"/></td>
					<td  onclick="submitFormWithParam('edit',${dealing.dealingId})" ><c:out value="${dealing.comments1}"/></td>
					<td  onclick="submitFormWithParam('edit',${dealing.dealingId})" ><c:out value="${dealing.comments2}"/></td>
					<td  onclick="submitFormWithParam('edit',${dealing.dealingId})" ><c:out value="${dealing.user}"/></td>
					<td >
						<c:choose>
							<c:when test="${dealing.editable}">
								<input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${dealing.dealingId})"/>
							</c:when>
							<c:otherwise>
								<input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${dealing.dealingId})" disabled="disabled"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td ><input type="button" value="Kopiuj" onclick="submitFormWithParam('copy',${dealing.dealingId})"/></td>
				</tr>
			</c:forEach>
		</table>	
	</c:if>
	
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="dealingId" value="${selectedDealing.dealingId}" />
		
		<br/>
		<b><c:out value="OBRÓT - EDYCJA"/></b>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label" style="width: 120px;">Rodzaj obrotu</td>
				<td class="field" style="width: 230px;">
					<custom:select name="dealingType" values="${selectsMap.dealingType}" selectedValue="${selectedDealing.dealingType.idn}" 
						onChange="setElementsVisibility()"/>
				</td>
			</tr><tr>
				<td class="label">Data</td>
				<td class="field"><custom:date name="date" identifier="dealingEditDate" value="${selectedDealing.date}"/></td>
			</tr><tr>
				<td class="label">Miejscowość</td>
				<td class="field"><custom:select name="city" values="${selectsMap.cityDictionary}" selectedValue="${selectedDealing.city}"/></td>
			</tr><tr id="companyCostRow">
				<td class="label">Koszty firmowe</td>
				<td class="field">
					<custom:select name="corporateCosts" values="${selectsMap.companyCosts}" selectedValue="${selectedDealing.corporateCosts}" />
				</td>
			</tr><tr id="privateCostRow">
				<td class="label">Koszty prywatne</td>
				<td class="field"><input type="text" name="privateCosts" value="${selectedDealing.privateCosts}"/></td>
			</tr><tr id="incomeRow">
				<td class="label">Przychody</td>
				<td class="field">
					<custom:select name="income" values="${selectsMap.clients}" selectedValue="${selectedDealing.incomeClientId}" />
				</td>
			</tr><tr id="fuelRow">
				<td class="label">Paliwo</td>
				<td class="field">
					<custom:select name="fuel" values="${selectsMap.fuel}" selectedValue="${selectedDealing.fuel}" />
					Litrów: <input type="text" name="fuelLiters" value="${selectedDealing.fuelLiters}" style="width: 50px;"/>
					<BR /> Pojazd: <custom:select name="machine" values="${selectsMap.machines}" selectedValue="${selectedDealing.machine}" />
				</td>
			</tr><tr>
				<td class="label">Kwota</td>
				<td class="field"><input type="text" name="amount" value="${selectedDealing.amount}"/></td>
			</tr><tr>
				<td class="label">Uwagi 1</td>
				<td class="field"><input type="text" name="comments1" value="${selectedDealing.comments1}"/></td>
			</tr><tr>
				<td class="label">Uwagi 2</td>
				<td class="field"><input type="text" name="comments2" value="${selectedDealing.comments2}"/></td>
			</tr><tr>
				<td class="label">Uwagi 3</td>
				<td class="field"><input type="text" name="comments3" value="${selectedDealing.comments3}"/></td>
			</tr><tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<c:choose>
					<c:when test="${selectedDealing.editable}">
						<input type="button" value="Zapisz" onclick="submitForm('save')" />
					</c:when>
					<c:otherwise>
						<input type="button" value="Zapisz" onclick="submitForm('save')" disabled="disabled"/>
					</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	
	</c:if>
	
	</form>

</body>	
</html>

	<![CDATA[
	 <script type="text/javascript" language="javascript" defer="defer">
	 	    
	 function setElementsVisibility(){
	 	var dealingTypeCtrl = document.getElementById('dealingType');
	 	if(dealingTypeCtrl != null && dealingTypeCtrl.value=='costs'){
	 		setCostControlsVisible();
	 	} else if(dealingTypeCtrl != null && dealingTypeCtrl.value=='income'){
	 		setIncomeControlsVisible();
	 	}
	 };
	 
	 function setCostControlsVisible(){
		 document.getElementById('incomeRow').style.display='none';
		 document.getElementById('companyCostRow').style.display='block';
		 document.getElementById('privateCostRow').style.display='block';
		 document.getElementById('fuelRow').style.display='block';
	 };
	 
	 function setIncomeControlsVisible(){
		 document.getElementById('incomeRow').style.display='block';
		 document.getElementById('companyCostRow').style.display='none';
		 document.getElementById('privateCostRow').style.display='none';
		 document.getElementById('fuelRow').style.display='none';
		 
	 };
	 
	 setElementsVisibility()
	 
	 </script>
     ]]>
</jsp:root>