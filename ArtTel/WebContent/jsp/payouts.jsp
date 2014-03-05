<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	version="2.0" >
    
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
     
   	<jsp:text>
 	<![CDATA[
 		<script type="text/javascript" src="data/js/common.js" ></script>
 	]]>
 	</jsp:text>
 	
    <style type="text/css" >
    	 @IMPORT url("data/css/instalations.css");
	</style> 
	
    <form method="post" action="payouts.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8">
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<c:import url="menu.jsp" />
	
	<c:if test="${event=='MAIN'}">
		
		<br/>
		<b><c:out value="WYPŁATY"/></b>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			
			<tr class="tableHeader">
				<td>Nazwa towaru/usługi</td>
				<td>Dokument</td>
				<td>Kwota</td>
				<td>Użytkownik</td>
			</tr>
			
			<c:forEach items="${payoutList}" var="payout" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}" onclick="submitFormWithParam('edit',${payout.payoutId})">
					<td><c:out value="${payout.nazwaTowaru}"/></td>
					<td><c:out value="${payout.dokument}"/></td>
					<td><c:out value="${payout.kwota}"/></td>
					<td><c:out value="${payout.user}"/></td>
					<td><input type="button" value=" - " onclick="submitFormWithParam('delete',${payout.payoutId})"/></td>
				</tr>
			</c:forEach>
		</table>
		<br/>
		<input type="button" value="Dodaj" onclick="submitForm('new')"/>
	</c:if>
	
	<c:if test="${event=='EDIT'}">
	
	<br/>
	<b><c:out value="WYPŁATY - EDYCJA"/></b>
	<br/><br/>
		
	<table class="borderedTable" cellpadding="2" cellspacing="1">
		<tr>
			<td class="label">Nazwa towaru/usługi</td>
			<td class="field"><input type="text" name="nazwaTowaru" /></td>
		</tr><tr>
			<td class="label">Dokument</td>
			<td class="field"><input type="text" name="dokument"/></td>
		</tr><tr>
			<td class="label">Kwota</td>
			<td class="field"><input type="text" name="kwota"/></td>
		</tr><tr>
			<td><input type="button" value="Anuluj" onclick="submitForm('main')"/></td>
			<td align="right"><input type="button" value="Zapisz" onclick="submitForm('save')"/></td>
		</tr>
	</table>
	
	</c:if>
	
	</form>

</jsp:root>