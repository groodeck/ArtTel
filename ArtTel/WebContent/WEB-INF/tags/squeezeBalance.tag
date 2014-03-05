<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	 xmlns:c="http://java.sun.com/jsp/jstl/core" >

	<jsp:directive.tag language="java" pageEncoding="ISO-8859-2"/>
	
	<style type="text/css" >
    	 @IMPORT url("data/css/arttel.css");
	</style> 
	
	<jsp:directive.attribute name="squeezeBalance" type="org.arttel.controller.vo.SqueezeBalanceVO" required="true"/>

	<table class="borderedTable" cellpadding="2" cellspacing="1" >
				
		<tr class="tableHeader"> 
			<td colspan="3">Podsumowanie</td>
		</tr>
		<tr class="tableHeader">
			<td>Przeciski w sztukach</td>
			<td>Metry</td>
			<td>Kwota</td>
		</tr>
		<tr class="row0">
			<td >
				<c:out value="${squeezeBalance.squeezeCount}"/>
			</td>
			<td >
				<c:out value="${squeezeBalance.meters}"/>
			</td>
			<td  >
				<c:out value="${squeezeBalance.amount}"/>
			</td>
		</tr>
	</table>	
	   	
</jsp:root>