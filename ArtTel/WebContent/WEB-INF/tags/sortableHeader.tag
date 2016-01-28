<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	 xmlns:c="http://java.sun.com/jsp/jstl/core" 
	 xmlns:fn="http://java.sun.com/jsp/jstl/functions" >

	<jsp:directive.tag language="java" pageEncoding="ISO-8859-2"/>
		
	<jsp:directive.attribute name="column" type="org.arttel.ui.SortableColumn" required="true"/>
	<jsp:directive.attribute name="sortUrl" type="String" required="true"/>
	 
	<th>
		<a href="${sortUrl}${column.columnKey}">
			<c:out value="${column.columnDescription}" />
		</a>
		<c:if test="${column.sortOrder != null}">
			${column.sortOrder.symbol}
		</c:if>
	</th>
	
</jsp:root>