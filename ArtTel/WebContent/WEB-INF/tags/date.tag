<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	 xmlns:c="http://java.sun.com/jsp/jstl/core" >

	<jsp:directive.tag language="java" pageEncoding="ISO-8859-2"/>
	<style type="text/css"  >
    	 @IMPORT url("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css");
    	 @IMPORT url("data/css/arttel.css");
	</style> 
	
	<jsp:directive.attribute name="identifier" type="String" required="true"/>
	<jsp:directive.attribute name="name" type="String" required="true"/>
	<jsp:directive.attribute name="value" type="String" required="false"/>
	<jsp:directive.attribute name="editable" type="Boolean" required="false"/>
	
	<c:set var="disabledStr" value=""/>
	<c:choose>
		<c:when test="${editable==false}">
			<c:set var="disabledStr" value="disabled='disabled'" />
		</c:when>
		<c:otherwise>
		<![CDATA[
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
			<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script> 
			<script>
				$(document).ready(function() {
				   	$("#datepicker${identifier}").datepicker({ 
				   		dateFormat: 'yy-mm-dd', 
				   		firstDay: 1,
				   		showOn: "button",
						buttonImage: "data/img/cal2.jpg",
						buttonImageOnly: true
				   		});
				});
			</script>
		]]>
		</c:otherwise>
	</c:choose>

<![CDATA[	
	 <input type="text" name="${name}" id="datepicker${identifier}" value="${value}" ${disabledStr} /> 	
]]>

</jsp:root>