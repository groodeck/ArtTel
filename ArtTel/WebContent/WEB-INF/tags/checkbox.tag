<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	 xmlns:c="http://java.sun.com/jsp/jstl/core" >

	<jsp:directive.tag language="java" pageEncoding="ISO-8859-2"/>
	
	<style type="text/css" >
    	 @IMPORT url("data/css/arttel.css");
	</style> 
	
	<jsp:directive.attribute name="name" type="String" required="true"/>
	<jsp:directive.attribute name="value" type="Boolean" required="true"/>
	<jsp:directive.attribute name="onChange" type="String" required="false"/>
	<jsp:directive.attribute name="disabled" type="Boolean" required="false"/>

	<c:set var="checked" value="" />
	<c:set var="disabledParam" value="" /> 
	
	<c:if test="${value}">
			<c:set var="checked" value="checked='checked' " />
	</c:if>
	<c:if test="${disabled}">
			<c:set var="disabledParam" value="disabled='disabled'" />
	</c:if>  
		
	<![CDATA[
		<input type="checkbox" name="${name}" id="${name}" onchange="${onChange}" value="true" ${checked}  ${disabledParam} />
	]]>

</jsp:root>