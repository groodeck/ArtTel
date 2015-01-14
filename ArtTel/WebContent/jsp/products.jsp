<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:custom="urn:jsptagdir:/WEB-INF/tags"
	version="2.0" >
    
<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
     
    <style type="text/css" >
    	 @IMPORT url("data/css/instalations.css");
	</style> 
    

 <html xmlns="http://www.w3.org/1999/xhtml">
 <body style="background-color: #F0FFF0">
     
    <form method="post" action="products.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
	<c:if test="${event=='MAIN' || event=='SEARCH'}">
		
		<br/>
		<b><c:out value="PRODUKTY - FILTR"/></b>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Nazwa produktu</td>
				<td class="field" colspan="1">
					<input type="text" name="productFilter.name" value="${productFilter.name}" />
				</td>
				<td width="10px"/>
				<td align="right" ><input type="button" value="Szukaj" onclick="submitForm('search')"/></td>
			</tr>
		</table>
		
		<br/>
		<input type="button" value="Dodaj" onclick="submitForm('new')"/>
		<br/><br/>

		<c:if test="${not empty productList}">
			<table class="borderedTable" cellpadding="2" cellspacing="1" >
				
				<tr class="tableHeader">
					<td>Numer</td>
					<td>Nazwa</td>
					<td>Cena netto</td>
					<td>VAT</td>
					<td>Jednostka</td>
				</tr>
				
				<c:forEach items="${productList}" var="product" varStatus="rowStatus">
					<tr class="row${rowStatus.index%2}">
						<td  onclick="submitFormWithParam('edit',${product.productId})" ><c:out value="${product.productId}"/></td>
						<td  onclick="submitFormWithParam('edit',${product.productId})" ><c:out value="${product.productDescription}"/></td>
						<td  onclick="submitFormWithParam('edit',${product.productId})" ><c:out value="${product.netPrice}"/></td>
						<td  onclick="submitFormWithParam('edit',${product.productId})" ><c:out value="${product.vatRate.desc}"/></td>
						<td  onclick="submitFormWithParam('edit',${product.productId})" ><c:out value="${product.unitType.desc}"/></td>
						<td><input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${product.productId})"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>	
	</c:if>
	
		
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="productId" value="${selectedProduct.productId}" />
		
		<br/>
		<b><c:out value="PRODUKT - EDYCJA"/></b>
		<br/><br/>
		
		<table>
			<tr>
				<td class="label">Nazwa produktu / usługi</td>
				<td class="field">
					<input type="text" name="productDescription" value="${selectedProduct.productDescription}"/>
				</td>
			</tr>
			<tr>
				<td class="label">Cena netto</td>
				<td class="field">
					<input type="text" name="netPrice" value="${selectedProduct.netPrice}"/>
				</td>
			</tr>
			<tr>
				<td class="label">Stawka VAT</td>
				<td class="field">
					<custom:select name="vatRate" values="${selectsMap.vatRateDictionary}" selectedValue="${selectedProduct.vatRate.idn}" />
				</td>
			</tr>
			<tr>
				<td class="label">Jednostka miary</td>
				<td class="field">
					<custom:select name="unitType" values="${selectsMap.unitTypesDictionary}" selectedValue="${selectedProduct.unitType.idn}" />
				</td>
			</tr>
			<tr>
				<td class="label">Uwagi</td>
				<td class="field">
					<input type="text" name="comments" value="${selectedProduct.comments}"/>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<input type="button" value="Zapisz" onclick="submitForm('save')" />
				</td>
			</tr>
		</table>
	
	</c:if>
	
	</form>

</body>	
</html>

 	<jsp:text>
 	<![CDATA[
 		<script type="text/javascript" src="data/js/common.js" ></script>
 		 <script type="text/javascript" language="javascript">
     
     	function submitForm(event){
     		document.getElementById('event').value = event;
     		document.forms[0].submit();
     	}
     </script>
 	]]>
 
 	</jsp:text>

</jsp:root>