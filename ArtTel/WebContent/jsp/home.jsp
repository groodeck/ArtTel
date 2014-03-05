<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
	xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0" 
 	xmlns:c="http://java.sun.com/jsp/jstl/core">
   
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import="org.arttel.controller.BaseController"/>
     
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
 <body style="background-color: #F0FFF0" onload="document.forms.loginForm.login.focus()">   
    
    <form method="post" action="home.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8"
    	name="loginForm">
	<input type="hidden" id="event" name="event" />
	
	<c:choose>
	<c:when test="${userContext.userLogged}">
		<c:import url="menu.jsp" />
	</c:when>
	<c:otherwise>
		<table style="font-family:sans-serif;" >
			<tr >
				<td height="10px"/>
			</tr><tr>
	  			<td width="100%" colspan="2">
					LOGOWANIE DO SYSTEMU
				</td>
			</tr><tr >
				<td height="5px"/>
			</tr><tr>
	  			<td style="width: 80px" >
					Login:
				</td><td >
					<input type="text" name="login" value="" style="width: 120px;" />
				</td>
			</tr><tr>
	  			<td>
					Hasło:
				</td><td >
					<input type="password" name="password" value="" style="width: 120px;" />
				</td>
			</tr><tr>
				<td/>
				<td width="80px" >
					<input type="submit" value="Login" onclick="submitForm('login')" />
				</td>
			</tr>
		</table>
	</c:otherwise>
	</c:choose>
	
	</form>

</body>
</html>
	
</jsp:root>