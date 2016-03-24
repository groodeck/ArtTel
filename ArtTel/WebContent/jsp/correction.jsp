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
     
    <form method="post" action="correction.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
	
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="documentId" value="${selectedInvoice.documentId}" />
		
		<br/>
		<b><c:out value="KOREKTA FAKTURY - EDYCJA"/></b>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label" style="width: 120px;">Numer kortekty</td>
				<td class="field" style="width: 230px;">
					<input type="text" name="correctionNumber" value="${selectedInvoice.correction.correctionNumber}"/>
				</td>
			</tr>
			<tr>
				<td class="label" style="width: 120px;">Numer korygowanej faktury</td>
				<td class="field" style="width: 230px;">
					<input type="text" name="number" value="${selectedInvoice.number}" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="label">Sprzedający</td>
				<td class="field">
					<custom:select name="sellerId" values="${selectsMap.sellerDictionary}" 
						selectedValue="${selectedInvoice.sellerId}" editable="false"/>
				</td>
			</tr>
			<tr>
				<td class="label">Klient</td>
				<td class="field">
					<custom:select name="clientId" values="${selectsMap.clientDictionary}" 
						selectedValue="${selectedInvoice.clientId}" editable="false"/>
				</td>
			</tr>
			<tr>
				<td class="label">Data wystawienia korekty</td>
				<td class="field">
					<custom:date name="correctionCreateDate" identifier="correctionCreateDate" value="${selectedInvoice.correction.createDate}" />
				</td>
			</tr>
			<tr>
				<td class="label">Data wystawienia faktury</td>
				<td class="field">
					<custom:date name="createDate" identifier="createDate" value="${selectedInvoice.createDate}" 
						editable="false"/>
				</td>
			</tr>
			<tr>
				<td class="label">Data sprzedaży</td>
				<td class="field">
					<custom:date name="signDate" identifier="signDate" value="${selectedInvoice.signDate}" 
						editable="false"/>
				</td>
			</tr>
			<tr>
				<td class="label">Data płatności</td>
				<td class="field">
					<custom:date name="paymentDate" identifier="paymentDate" value="${selectedInvoice.correction.paymentDate}" 
						editable="false"/>
				</td>
			</tr>
		</table>
		<br/>
		<table class="borderedTable">
			<tr>
				<td class="label" colspan="8">Produkty/Usługi</td>
			</tr><tr>
				<td class="label">Produkt/usługi</td>
				<td class="label">j.m.</td>
				<td class="label"/>
				<td class="label">Ilość</td>
				<td class="label">Cena netto</td>
				<td class="label">Wartość netto</td>
				<td class="label">Stawka VAT</td>
				<td class="label">Kwota VAT</td>
				<td class="label">Wartość brutto</td>
			</tr>
			<c:forEach items="${selectedInvoice.documentProducts}" var="product" varStatus="rowStatus">
				<tr>
				<td class="field">
					<custom:select name="product[${rowStatus.index}].productDefinition.productId" values="${selectsMap.productDictionary}" 
						selectedValue="${product.productDefinition.productId}" style="width: 400" 
						editable="false" />
					
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].productDefinition.unitType" 
						value="${product.productDefinition.unitType.idn}" style="width: 30" disabled="disabled"/>
				</td>
				<td class="field">
					Przed korektą
				</td>
				<td class="field" style="width: 50">
					<input type="text" name="product[${rowStatus.index}].quantity" value="${product.quantity}" 
						style="width: 40" disabled="disabled"/>
				</td>
				<td class="field" style="width: 90">
					<input type="text" name="product[${rowStatus.index}].productDefinition.netPrice" value="${product.productDefinition.netPrice}"
						style="width: 70" disabled="disabled"/>
				</td>
				<td class="field" style="width: 90">
					<input type="text" name="product[${rowStatus.index}].netSumAmount" value="${product.netSumAmount}"
						style="width: 80" disabled="disabled"/>
				</td>
				<td class="field" style="width: 80">
					<input type="text" name="product[${rowStatus.index}].productDefinition.vatRate" value="${product.productDefinition.vatRate.desc}"
						style="width: 40" disabled="disabled"/><c:out value=" %"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].vatAmount" value="${product.vatAmount}"
						style="width: 80" disabled="disabled"/>
				</td>
				<td class="field" style="width: 95">
					<input type="text" name="product[${rowStatus.index}].grossSumAmount" value="${product.grossSumAmount}"
						style="width: 80" disabled="disabled"/>
				</td>
				<td class="field">
					<input type="button" value="  -  " onclick="submitFormWithParam('del_product_row',${rowStatus.index})"/>
				</td>
			</tr>
	
			<tr>
				<td class="field">
					<custom:select name="product[${rowStatus.index}].correction.productDefinition.productId" values="${selectsMap.productDictionary}" 
						selectedValue="${product.correction.productDefinition.productId}" style="width: 400" 
						onChange="submitFormWithParam('change_product', ${rowStatus.index})"/>
				</td>
				<td class="field" >
					<input type="text" name="product[${rowStatus.index}].correction.productDefinition.unitType" 
						value="${product.correction.productDefinition.unitType.idn}" style="width: 30" disabled="disabled"/>
				</td>
				<td colspan="8">
					<table>
						<tr>
							<td class="field">Po korekcie</td>
							<td class="field" style="width: 50">
								<input type="text" name="product[${rowStatus.index}].correction.quantity" value="${product.correction.quantity}" 
									style="width: 40" onChange="submitFormWithParam('change_product',${rowStatus.index})"/></td>
							<td class="field" style="width: 90">
								<input type="text" name="product[${rowStatus.index}].correction.productDefinition.netPrice" 
									value="${product.correction.productDefinition.netPrice}" style="width: 70" disabled="disabled"/>
							</td>
							<td class="field" style="width: 90">
								<input type="text" name="product[${rowStatus.index}].correction.netSumAmount" value="${product.correction.netSumAmount}"
									style="width: 80" disabled="disabled"/>
							</td>
							<td class="field" style="width: 80">
								<input type="text" name="product[${rowStatus.index}].correction.productDefinition.vatRate" 
									value="${product.correction.productDefinition.vatRate.desc}" style="width: 40" disabled="disabled"/><c:out value=" %"/>
							</td>
							<td class="field">
								<input type="text" name="product[${rowStatus.index}].correction.vatAmount" value="${product.correction.vatAmount}"
									style="width: 80" disabled="disabled"/>
							</td>
							<td class="field" style="width: 95">
								<input type="text" name="product[${rowStatus.index}].correction.grossSumAmount" value="${product.correction.grossSumAmount}"
									style="width: 80" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td class="field">Korekta</td>
							<td class="field">
								<input type="text" name="product[${rowStatus.index}].correction.quantityDiff" value="${product.correction.quantityDiff}" 
									style="width: 40" disabled="disabled"/></td>
							<td class="field">
								<input type="text" name="product[${rowStatus.index}].correction.productDefinition.netPrice" 
									value="${product.correction.productDefinition.netPrice}" style="width: 70" disabled="disabled"/>
							</td>
							<td class="field">
								<input type="text" name="product[${rowStatus.index}].correction.netSumAmountDiff" value="${product.correction.netSumAmountDiff}"
									style="width: 80" disabled="disabled"/>
							</td>
							<td class="field">
								<input type="text" name="product[${rowStatus.index}].correction.productDefinition.vatRate" 
									value="${product.correction.productDefinition.vatRate.desc}" style="width: 40" disabled="disabled"/><c:out value=" %"/>
							</td>
							<td class="field">
								<input type="text" name="product[${rowStatus.index}].correction.vatAmountDiff" value="${product.correction.vatAmountDiff}"
									style="width: 80" disabled="disabled"/>
							</td>
							<td class="field">
								<input type="text" name="product[${rowStatus.index}].correction.grossSumAmountDiff" value="${product.correction.grossSumAmountDiff}"
									style="width: 80" disabled="disabled"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			</c:forEach>
			<tr>
				<td class="field" align="right" colspan="9">
					<!-- <input type="button" value="Definiuj produkt" onclick="document.location.href('products.app?event=new');" /> -->
					<input type="button" value="Dodaj wiersz" onclick="submitForm('add_product_row')" />
				</td>
			</tr>
			<tr>
				<td class="field" colspan="4"/>
				<td class="field" align="right">
					<c:out value="Przed korektą" />
				</td>
				<td class="field" align="right">
					<c:out value="${selectedInvoice.netAmount}" />
				</td>
				<td class="field" align="right">
					<c:out value="-" />
				</td>
				<td class="field" align="right">
					<c:out value="${selectedInvoice.vatAmount}" />
				</td>
				<td class="field" align="right">
					<c:out value="${selectedInvoice.grossAmount}" />
				</td>
			</tr>
			<tr>
				<td class="field" colspan="4"/>
				<td class="field" align="right">
					<c:out value="Po korekcie" />
				</td>
				<td class="field" align="right">
					<c:out value="${selectedInvoice.correction.netAmount}" />
				</td>
				<td class="field" align="right">
					<c:out value="-" />
				</td>
				<td class="field" align="right">
					<c:out value="${selectedInvoice.correction.vatAmount}" />
				</td>
				<td class="field" align="right">
					<c:out value="${selectedInvoice.correction.grossAmount}" />
				</td>
			</tr>
			<tr>
				<td class="field" colspan="4"/>
				<td class="field" align="right">
					<c:out value="RAZEM" />
				</td>
				<td class="field" align="right">
					<c:out value="${selectedInvoice.correction.netAmountDiff}" />
				</td>
				<td class="field" align="right">
					<c:out value="-" />
				</td>
				<td class="field" align="right">
					<c:out value="${selectedInvoice.correction.vatAmountDiff}" />
				</td>
				<td class="field" align="right">
					<c:out value="${selectedInvoice.correction.grossAmountDiff}" />
				</td>
			</tr>

			<c:forEach items="${selectedInvoice.invoiceDetailValues}" var="detailsValue" varStatus="status">
				<tr>
					<c:choose>
						<c:when test="${status.index==0}">
							<td class="field" colspan="4"/>
							<td class="field" align="right">
								<c:out value="w tym"/>
							</td>
						</c:when>
						<c:otherwise>
							<td class="field" colspan="5"/>
						</c:otherwise>
					</c:choose>
					<td class="field" align="right">
						<c:out value="${detailsValue.netAmount}" />
					</td>
					<td class="field" align="right">
						<c:out value="${detailsValue.vatRate.desc} %" />
					</td>
					<td class="field" align="right">
						<c:out value="${detailsValue.vatAmount}" />
					</td>
					<td class="field" align="right">
						<c:out value="${detailsValue.grossAmount}" />
					</td>
				</tr>
			</c:forEach>
		</table>
		<br/>
		<table class="borderedTable">
			<tr>
				<td class="label">Razem do zapłaty</td>
				<td class="field">
					<input type="text" name="paymentAmount" value="${selectedInvoice.correction.grossAmountDiff}" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="label">Zapłacono</td>
				<td class="field">
					<input type="text" name="paid" value="${selectedInvoice.correction.paid}" onChange="submitForm('paid_entered')"/>
				</td>
			</tr>
			<tr>
				<td class="label">Słownie</td>
				<td class="field">
					<input type="text" name="paidWords" value="${selectedInvoice.correction.paidWords}"  style="width: 300" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="label">Pozostało do zapłaty</td>
				<td class="field">
					<input type="text" name="paymentLeft" value="${selectedInvoice.correction.paymentLeft}" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="label">Sposób płatności</td>
				<td class="field">
					<custom:select name="paymentType" values="${selectsMap.paymentTypeDictionary}" 
						selectedValue="${selectedInvoice.correction.paymentType.idn}" style="width: 170" 
						onChange="submitForm('change_payment_type')"/>
				</td>
			</tr>
			<tr>
				<td class="label">Uwagi</td>
				<td class="field">
					<input type="text" name="comments" value="${selectedInvoice.correction.comments}"/>
				</td>
			</tr>
		</table>
		
		<table>
			<tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<c:choose>
					<c:when test="${selectedInvoice.correction.editable}">
						<input type="button" value="Zapisz" onclick="submitForm('save')" />
					</c:when>
					<c:otherwise>
						<input type="button" value="Zapisz" onclick="submitForm('save')" disabled="disabled"/>
					</c:otherwise>
					</c:choose>
				</td>
				<td width="10"/>
				<td><input type="button" value="Drukuj" onclick="submitForm('print')"/></td>
				<c:if test="${not empty reportLink}">
					<td><a href="${reportLink}">Faktura</a></td>
				</c:if>
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