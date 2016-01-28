<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	 xmlns:c="http://java.sun.com/jsp/jstl/core" 
	 xmlns:fn="http://java.sun.com/jsp/jstl/functions" >

	<jsp:directive.tag language="java" pageEncoding="ISO-8859-2"/>
		
	<jsp:directive.attribute name="page" type="org.arttel.ui.ResultPage" required="true"/>
	<jsp:directive.attribute name="pageChangeUrl" type="String" required="true"/>
	<jsp:text>
		<![CDATA[ 
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
			<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
		]]>
	</jsp:text>
	
	<c:if test="${page.pageNo gt 0}">
		<a href="${pageChangeUrl}${(page.pageNo - 1)}">Poprzednia</a>
	</c:if>
	  	<input id="pageNumber" value="${page.pageNo + 1}" style="width: 25px;" onkeydown=""/>/<c:out value="${page.pageCount}"/>
	<c:if test="${page.pageNo lt page.pageCount - 1}">
		<a href="${pageChangeUrl}${(page.pageNo + 1)}">Nastepna</a>
	</c:if>
	
	<jsp:text>
		<![CDATA[ 
			<script type="text/javascript">
				$(document).on('keypress', 'input', function(e) {
					if(e.keyCode == 13) {
					    e.preventDefault();
					    var pageNumber = $("#pageNumber").val();
					    if(!isNaN(pageNumber) && pageNumber > 0 && pageNumber <= ${page.pageCount}){
					    	window.location.href = '${pageChangeUrl}' + (pageNumber-1);
					    }
					  }
				});

			</script>
		]]>
	</jsp:text>
	
</jsp:root>