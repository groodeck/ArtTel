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
     
    <form method="post" action="squeezes.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
	<c:if test="${event=='MAIN' || event=='SEARCH'}">
		
		<br/>
		<c:if test="${userContext.userPrivileges['squeezeSummary.form'] and not empty squeezeBalance}"> 
			<custom:squeezeBalance squeezeBalance="${squeezeBalance}" />
		</c:if>
		
		<br/>
		<b><c:out value="PRZECISKI - FILTR"/></b>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Szybkie wyszukiwanie</td>
				<td class="field" colspan="1">
					<input type="text" style="width: 100%" name="squeezeFilter.phrase" value="${squeezeFilter.phrase}" />
				</td>
			</tr>
			<tr>
				<td class="label">Miejscowość</td>
				<td class="field">
					<custom:select name="squeezeFilter.city" values="${selectsMap.cityDictionary}" selectedValue="${squeezeFilter.city}" />
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
				<td>Zleceniodawca</td>
				<td>Przeciski w sztukach</td>
				<td>Cena</td>
				<td>Metry</td>	
				<td>Kwota</td>
				<td>Data</td>
				<td>Status</td>
				<td>Monter</td>
			</tr>
			
			<c:forEach items="${squeezeList}" var="squeeze" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}">
					<td  onclick="submitFormWithParam('edit',${squeeze.squeezeId})" ><c:out value="${squeeze.squeezeId}"/></td>
					<td  onclick="submitFormWithParam('edit',${squeeze.squeezeId})" ><c:out value="${squeeze.clientDesc}"/></td>
					<td  onclick="submitFormWithParam('edit',${squeeze.squeezeId})" ><c:out value="${squeeze.quantity}"/></td>
					<td  onclick="submitFormWithParam('edit',${squeeze.squeezeId})" ><c:out value="${squeeze.price}"/></td>
					<td  onclick="submitFormWithParam('edit',${squeeze.squeezeId})" ><c:out value="${squeeze.meters}"/></td>
					<td  onclick="submitFormWithParam('edit',${squeeze.squeezeId})" ><c:out value="${squeeze.amount}"/></td>
					<td  onclick="submitFormWithParam('edit',${squeeze.squeezeId})" ><c:out value="${squeeze.squeezeDate}"/></td>
					<td  onclick="submitFormWithParam('edit',${squeeze.squeezeId})" style="background-color: ${squeeze.status.color}" >
						<c:out value="${squeeze.status.desc}"/>
					</td>
					<td  onclick="submitFormWithParam('edit',${squeeze.squeezeId})" ><c:out value="${squeeze.user}"/></td>
					<td >
						<c:choose>
							<c:when test="${squeeze.editable}">
								<input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${squeeze.squeezeId})"/>
							</c:when>
							<c:otherwise>
								<input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${squeeze.squeezeId})" disabled="disabled"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td ><input type="button" value="Kopiuj" onclick="submitFormWithParam('copy',${squeeze.squeezeId})"/></td>
				</tr>
			</c:forEach>
		</table>	
	</c:if>
	
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="squeezeId" value="${selectedSqueeze.squeezeId}" />
		<input type="hidden" name="dealingId" value="${selectedSqueeze.dealingId}" />
		
		<br/>
		<b><c:out value="PRZECISK - EDYCJA"/></b>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label" style="width: 120px;">Miejscowość</td>
				<td class="field" style="width: 230px;">
					<custom:select name="city" values="${selectsMap.cityDictionary}" selectedValue="${selectedSqueeze.city}" />
				</td>
			</tr><tr>
				<td class="label" style="width: 120px;">Zleceniodawca</td>
				<td class="field" style="width: 230px;">
					<custom:select name="clientId" values="${selectsMap.clientDictionary}" selectedValue="${selectedSqueeze.clientId}" />
				</td>
			</tr><tr>
				<td class="label">Ilość przecisków</td>
				<td class="field"><input type="text" name="quantity" value="${selectedSqueeze.quantity}"/></td>
			</tr><tr>
				<td class="label">Metry</td>
				<td class="field">
					<input type="text" id="meters" name="meters" value="${selectedSqueeze.meters}" onchange="calculateIncomeAmount()"/>
				</td>
			</tr><tr>
				<td class="label">Cena</td>
				<td class="field">
					<input type="text" id="price" name="price" value="${selectedSqueeze.price}" onchange="calculateIncomeAmount()"/>
				</td>
			</tr><tr id="privateCostRow">
				<td class="label">Kwota przychodu</td>
				<td class="field"><input type="text" id="amount" name="amount" value="${selectedSqueeze.amount}" readonly="readonly"/></td>
			</tr><tr>
				<td class="label">Data</td>
				<td class="field"><custom:date name="squeezeDate" identifier="squeezeDate" value="${selectedSqueeze.squeezeDate}" /></td>
			</tr><tr>
				<td class="label">Status</td>
				<td class="field"><custom:select name="status" values="${selectsMap.status}" selectedValue="${selectedSqueeze.status.idn}" /></td>
			</tr><tr>
				<td class="label">Uwagi</td>
				<td class="field"><input type="text" name="comments" value="${selectedSqueeze.comments}"/></td>
			</tr><tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<c:choose>
					<c:when test="${selectedSqueeze.editable}">
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

 	<jsp:text>
 	<![CDATA[
 		<script type="text/javascript" src="data/js/common.js" ></script>
 		 <script type="text/javascript" language="javascript">
     
     	function submitForm(event){
     		document.getElementById('event').value = event;
     		document.forms[0].submit();
     	}
     	
     	function calculateIncomeAmount(){
	     	var metersCtrl = document.getElementById('meters');
	     	var priveCtrl = document.getElementById('price');
	     	if(metersCtrl && priveCtrl){
		     	var meters = metersCtrl.value;
	   		  	var price = priveCtrl.value;
	   		  	if(!isNaN(meters) && !isNaN(price)){
	   		  		var costs = meters * price;
	   		  		document.getElementById('amount').value = costs;
	   		  	}
	     	}
     	}
     	
     	calculateIncomeAmount();
     	
     </script>
 	]]>
 
 	</jsp:text>

</jsp:root>