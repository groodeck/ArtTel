<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
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
     
    <form method="post" action="labor.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8">
	<input type="hidden" id="event" name="event" />
	
	<c:import url="menu.jsp" />
	
	<br/>
	
	<table style="font-family:sans-serif;">
		<tr>
			<td colspan="2" style="font-weight: bold;"><c:out value="ROBOCIZNA"/></td>
		</tr>
		<tr>
			<td>Numer zlecenia</td>
			<td><input type="text" name="numerZlecenia" /></td>
		</tr><tr>
			<td>Miasto</td>
			<td><input type="text" name="miasto"/></td>
		</tr><tr>
			<td>Adres</td>
			<td><input type="text" name="adres"/></td>
		</tr><tr>
			<td>Nazwa pionu</td>
			<td><input type="text" name="nazwaPionu"/></td>
		</tr><tr>
			<td>Rodzaj robocizny</td>
			<td><input type="text" name="rodzaj"/></td>
		</tr><tr>
			<td>Ilosc</td>
			<td><input type="text" name="ilosc"/></td>
		</tr><tr>
			<td>j.m.</td>
			<td><input type="text" name="jednostkaMiary"/></td>
		</tr><tr>
			<td/><td><input type="button" name="save" value="zapisz" onclick="submitForm('save')"/></td>
		</tr>
	</table>
	
	</form>

</body>	
</html>
</jsp:root>