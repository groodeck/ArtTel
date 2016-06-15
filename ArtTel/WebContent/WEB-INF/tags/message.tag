<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	 xmlns:c="http://java.sun.com/jsp/jstl/core" >

	<jsp:directive.tag language="java" pageEncoding="ISO-8859-2"/>
		
	<jsp:directive.attribute name="uiMessage" type="String" required="false"/>
		
	<style>
		.messageDiv {
			border: 1px solid; 
			background-color: green; 
			padding-left: 10px; 
			padding-right: 10px;
		}
	</style>
	
	<c:if test="${not empty uiMessage}">
   		<br/>
	   	<div class="messageDiv">
	   		<h3><c:out value="${uiMessage}"/></h3>
	   	</div>
   </c:if>

</jsp:root>