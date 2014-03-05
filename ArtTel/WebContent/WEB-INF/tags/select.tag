<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	 xmlns:c="http://java.sun.com/jsp/jstl/core" >

	<jsp:directive.tag language="java" pageEncoding="ISO-8859-2"/>
	
	<style type="text/css" >
    	 @IMPORT url("data/css/arttel.css");
	</style> 
	
	<jsp:directive.attribute name="name" type="String" required="true"/>
	<jsp:directive.attribute name="values" type="java.util.List" required="true"/>
	<jsp:directive.attribute name="selectedValue" type="String" required="false"/>
	<jsp:directive.attribute name="onChange" type="String" required="false"/>
	<jsp:directive.attribute name="style" type="String" required="false"/>
	<jsp:directive.attribute name="editable" type="Boolean" required="false"/>

	<c:set var="disabledStr" value=""/>
	<c:if test="${editable==false}">
		<c:set var="disabledStr" value="disabled='disabled'" />
	</c:if>

<![CDATA[
	<select name='${name}' id='${name}' onchange="${onChange}" style="${style}" ${disabledStr} >
]]>
		<c:forEach var="optValue" items="${values}">
			<c:choose>
				<c:when test="${optValue.idn==selectedValue}">
					<option value="${optValue.idn}" selected="selected" >${optValue.desc}</option>	
				</c:when>
				<c:otherwise>
					<option value="${optValue.idn}" >${optValue.desc}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
<![CDATA[
	</select>
]]>
</jsp:root>