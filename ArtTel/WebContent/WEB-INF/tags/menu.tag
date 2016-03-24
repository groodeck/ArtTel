 <jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:c="http://java.sun.com/jsp/jstl/core">
    
    <jsp:directive.tag language="java" pageEncoding="ISO-8859-2 "/>
	
	<jsp:directive.attribute name="userContext" type="org.arttel.controller.UserContext" required="true"/>

	<table style="font-family:sans-serif;">
		<tr>
			<td>
				<c:out value="Zalogowany jako: ${userContext.userName}" />
			</td><td>
		 		<input type="button" value="Wyloguj" onclick="document.location = 'home.app?event=logout'" />
		 	</td>
	  	</tr>
	  </table>
	  
	  
	  <table style="font-family:sans-serif;">
	  	<tr>
			<c:if test="${userContext.userPrivileges['instalations.app']}">
	  			<td width="80px" align="center">
					<a href="instalations.app" >Instalacje</a>
	
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['orders.app']}">
				<td width="80px" align="center">
					<a href="orders.app" >Zlecenia</a>
	
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['payouts.app']}">
				<td width="80px" align="center">
					<a href="payouts.app" >Wypłaty</a>
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['materials.app']}">
				<td width="80px" align="center">
					<a href="materials.app" >Materiały</a>
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['labor.app']}">
				<td width="80px" align="center">
					<a href="labor.app" >Robocizna</a>
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['agreements.app']}">
				<td width="80px" align="center">
					<a href="agreements.app" >Umowy</a>
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['dealing.app']}">
				<td width="80px"  align="center">
					<a href="dealing.app" >Obrót</a>
				</td>
			</c:if>
				
			<c:if test="${userContext.userPrivileges['squeezes.app']}">
				<td width="80px"  align="center">
					<a href="squeezes.app" >Przeciski</a>
				</td>
			</c:if>
				
			<c:if test="${userContext.userPrivileges['buildings.app']}">
				<td width="80px" align="center">
					<a href="buildings.app" >Budynki</a>
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['reports.app']}">
				<td width="80px" align="center">
					<a href="reports.app" >Raporty</a>
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['settings.app']}">
				<td width="80px" align="center">
					<a href="settings.app" >Ustawienia</a>
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['invoices.app']}">
				<td width="80px" align="center">
					<a href="invoices.app" >Faktury</a>
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['bills.app']}">
				<td width="80px" align="center">
					<a href="bills.app" >Rachunki</a>
				</td>
			</c:if>
			<c:if test="${userContext.userPrivileges['products.app']}">
				<td width="80px" align="center">
					<a href="products.app" >Produkty</a>
				</td>
			</c:if>
			
			<c:if test="${userContext.userPrivileges['clients.app']}">
				<td width="80px" align="center">
					<a href="clients.app" >Klienci</a>
				</td>
			</c:if>
		</tr>
  	</table>
			<hr size="2px"/>
  	
</jsp:root>