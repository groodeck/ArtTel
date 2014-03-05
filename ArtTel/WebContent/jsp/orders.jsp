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
    	 @IMPORT url("data/css/orders.css");
	</style> 
 
 
 <body>

    <form method="post" action="orders.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<c:import url="menu.jsp" />
	
	<c:if test="${event=='MAIN' || event=='SEARCH'}">
	
		 <br/>
		<b><c:out value="ZLECENIA - FILTR"/></b>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Szybkie wyszukiwanie</td>
				<td class="field" colspan="1">
					<input type="text" style="width: 100%" name="orderFilter.phrase" value="${orderFilter.phrase}" />
				</td>
			</tr>
			<tr>
				<td class="label">Miejscowość</td>
				<td class="field">
					<custom:select name="orderFilter.city" values="${selectsMap.cityDictionaryWithEmpty}" selectedValue="${orderFilter.city}" />
				</td>
				<td class="label">Status</td>
				<td class="field">
					<custom:select name="orderFilter.status" values="${selectsMap.statusWithEmpty}" selectedValue="${orderFilter.status}" />
				</td>
				<td class="label">Data od</td>
				<td class="field">
					<custom:date name="orderFilter.dateFrom" identifier="filterDateFrom" value="${orderFilter.dateFrom}"  />
				</td>
				<td class="label">Data do</td>
				<td class="field">
					<custom:date name="orderFilter.dateTo" identifier="filterDateTo" value="${orderFilter.dateTo}"  />
				</td>
				<td width="10px"/>
				<td align="right" ><input type="button" value="Szukaj" onclick="submitForm('search')"/></td>
			</tr>
		</table> 
		
		<br/>
		<br/>
		<input type="button" value="Dodaj" onclick="submitForm('new')"/>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			
			<tr class="tableHeader">
				<td>L.p.</td>
				<td>Status</td>
				<td>Data wystawienia zlecenia</td>	
				<td>Data wykonania zlecenia</td>
				<td>Rodzaj zlecenia</td>
				<td>Uwagi</td>	
				<td>Adres</td>
				<td>Monter</td>
			</tr>
			
			<c:forEach items="${orderList}" var="order" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}">
					<td  onclick="submitFormWithParam('edit',${order.orderId})" ><c:out value="${order.orderId}"/></td>
					<td  onclick="submitFormWithParam('edit',${order.orderId})" style="background-color: ${order.status.color}"><c:out value="${order.status.desc}"/></td>
					<td  onclick="submitFormWithParam('edit',${order.orderId})" ><c:out value="${order.issueDate}"/></td>
					<td  onclick="submitFormWithParam('edit',${order.orderId})" ><c:out value="${order.realizationDate}"/></td>
					<td  onclick="submitFormWithParam('edit',${order.orderId})" ><c:out value="${order.orderType.desc}"/></td>
					<td  onclick="submitFormWithParam('edit',${order.orderId})" 
						style="width: 150px;" ><c:out value="${order.comments}"/></td>
					<td  onclick="submitFormWithParam('edit',${order.orderId})" ><c:out value="${order.address}"/></td>
					<td  onclick="submitFormWithParam('edit',${order.orderId})" ><c:out value="${order.user}"/></td>
					<td >
						<c:choose>
							<c:when test="${order.editable}">
								<input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${order.orderId})"/>
							</c:when>
							<c:otherwise>
								<input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${order.orderId})" disabled="disabled"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td ><input type="button" value="Kopiuj" onclick="submitFormWithParam('copy',${order.orderId})"/></td>
					<td>
						<c:choose>
							<c:when test="${order.editable and order.closable}">
								<input type="button" value="Zamknij" onclick="submitFormWithParam('close',${order.orderId})"/>
							</c:when>
							<c:otherwise>
								<input type="button" value="Zamknij" onclick="submitFormWithParam('close',${order.orderId})" disabled="disabled"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="orderId" value="${selectedOrder.orderId}" />
		
		<br/>
		<b><c:out value="ZLECENIE - EDYCJA"/></b>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Status</td>
				<td class="field">
					<custom:select name="status" values="${selectsMap.status}" selectedValue="${selectedOrder.status.idn}" />
				</td>
			</tr><tr>
				<td class="label">Rodzaj zlecenia</td>
				<td class="field">
					<custom:select name="orderType" values="${selectsMap.orderType}" selectedValue="${selectedOrder.orderType.idn}" />
				</td>
			</tr><tr>
				<td class="label">Data wystawienia zlecenia(yyyy-MM-dd)</td>
				<td class="field"><custom:date name="issueDate" value="${selectedOrder.issueDate}" identifier="editIssueDate"/></td>
			</tr><tr>
				<td class="label">Nr telefonu</td>
				<td class="field"><input type="text" name="phone" value="${selectedOrder.phone}"/></td>
			</tr><tr>
				<td class="label">Imię</td>
				<td class="field"><input type="text" name="name" value="${selectedOrder.name}"/></td>
			</tr><tr>
				<td class="label">Nazwisko</td>
				<td class="field"><input type="text" name="surname" value="${selectedOrder.surname}"/></td>
			</tr><tr>	
				<td class="label">Adres</td>
				<td class="field"><input type="text" name="address" value="${selectedOrder.address}"/></td>
			</tr><tr>
				<td class="label">Miejscowość</td>
				<td class="field">
					<custom:select name="city" values="${selectsMap.cityDictionary}" selectedValue="${selectedOrder.city}" />
				</td>
			</tr><tr>
				<td class="label">Pakiet</td>
				<td class="field"><input type="text" name="bundle" value="${selectedOrder.bundle}"/></td>
			</tr><tr>
				<td class="label">Nr seryjny</td>
				<td class="field"><input type="text" name="serialNumber" value="${selectedOrder.serialNumber}"/></td>
			</tr><tr>
				<td class="label">Data wykonania zlecenia(yyyy-MM-dd)</td>
				<td class="field"><custom:date name="realizationDate" value="${selectedOrder.realizationDate}" identifier="editRealizationDate"/></td>
			</tr><tr>
				<td class="label">Sposób rozwiązania</td>
				<td class="field"><input type="text" name="solution" value="${selectedOrder.solution}"/></td>
			</tr><tr>
				<td class="label">Uwagi</td>
				<td class="field"><input type="text" name="comments" value="${selectedOrder.comments}"/></td>
			</tr><tr>
				<td class="label">Dodatkowe uwagi</td>
				<td class="field"><input type="text" name="additionalComments" value="${selectedOrder.additionalComments}"/></td>
			</tr><tr>
				<td class="label">Opis problemu</td>
				<td class="field"><input type="text" name="problemDescription" value="${selectedOrder.problemDescription}"/></td>
			</tr><tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<c:choose>
					<c:when test="${selectedOrder.editable}">
						<input type="button" value="Zapisz" onclick="submitForm('save')" />
					</c:when>
					<c:otherwise><input type="button" value="Zapisz" onclick="submitForm('save')" disabled="disabled"/></c:otherwise>
					</c:choose>
				</td>
			</tr>

		</table>
	</c:if>
	
	</form>
	
	<form method="post" action="orders.app?event=import_file"  enctype="multipart/form-data" accept-charset="UTF-8">
	<HR/>	
	<br/>
	<b><c:out value="ZLECENIA - IMPORT"/></b>
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
				<td colspan="2"><B>Bledy importu</B></td>
			</tr>
			<c:forEach items="${importErrors}" var="error" varStatus="rowStatus">
				<tr>
					<td colspan="2" style="color: red;"><c:out value="${error}"/></td>
				</tr>
			</c:forEach>
		</c:if>
		
	</table>
	</form>
	
</body>
</jsp:root>