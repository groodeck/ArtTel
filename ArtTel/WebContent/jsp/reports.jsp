<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:custom="urn:jsptagdir:/WEB-INF/tags"
	version="2.0" >
    
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
     
    <style type="text/css" >
    	 @IMPORT url("data/css/instalations.css");
	</style> 
	
     <script type="text/javascript" language="javascript">
     
     	function submitForm(event){
     		document.getElementById('event').value = event;
     		document.forms[0].submit();
     	}
     	
     </script>
     
 <html xmlns="http://www.w3.org/1999/xhtml">
 <body style="background-color: #F0FFF0">   
   
    <form method="post" action="reports.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8">
	<input type="hidden" id="event" name="event" />
	
	<c:import url="menu.jsp" />
	
	<br/>
	
	<table style="font-family:sans-serif;">
		<tr>
			<td colspan="2" style="font-weight: bold;"><c:out value="RAPORTY"/></td>
		</tr>
		<tr>
			<td>Typ raportu</td>
			<td>
				<custom:select name="reportType" values="${selectsMap.reportType}" selectedValue="${reportParams.reportType.idn}" />
			</td>
		</tr><tr>
			<td>Miejscowość</td>
			<td>
				<custom:select name="city" values="${selectsMap.cityDictionary}" selectedValue="${reportParams.city}" />
			</td>
		</tr><tr>
			<td>Data od (YYYY-MM-DD)</td>
			<td><custom:date name="dataOd" identifier="dataOd"/></td>
		</tr><tr>
			<td>Data do (YYYY-MM-DD)</td>
			<td><custom:date name="dataDo" identifier="dataDo"/></td>
		</tr><tr>
			<td/><td><input type="button" name="generate" value="Generuj" onclick="submitForm('generate')"/></td>
		</tr>
		<c:if test="${not empty reportLink}">
			<tr><td height="10px"/></tr>
			<tr>
				<td colspan="2"><a href="${reportLink}">Pobierz plik raportu</a></td>
			</tr>
		</c:if>
	</table>
	
	</form>

</body>
</html>
</jsp:root>