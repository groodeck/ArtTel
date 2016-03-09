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
 
 		<jsp:text>
			<![CDATA[ 
				<?xml version="1.0" encoding="UTF8" ?> 
			 	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
	
				<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
				<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
			 ]]>
		</jsp:text>
		
 <body style="background-color: #F0FFF0">
     
    <form method="post" action="invoices.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
	<c:if test="${event=='MAIN' || event=='SEARCH'}">
		
		<br/>
		<b><c:out value="FAKTURY - FILTR"/></b>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Numer</td>
				<td class="field">
					<input type="text" name="invoiceFilter.number" value="${invoiceFilter.number}"/>
				</td>
				<td class="label">Data wystawienia</td>
				<td class="field">
					<custom:date name="invoiceFilter.createDate" identifier="filterCreateDate" value="${invoiceFilter.createDate}" />
				</td>
				<td width="10px"/>
				<td align="right" ><input type="button" value="Szukaj" onclick="submitForm('search')"/></td>
			</tr>
		</table> 
		<br/>
		
		<input type="button" value="Dodaj" onclick="submitForm('new')"/>
		<br/><br/>
		
		<c:if test="${not empty invoiceList.records}">
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			
			<tr class="tableHeader">
				<td/>
				<custom:sortableHeader column="${tableHeader.columns.invoiceNumber}" sortUrl="invoices.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${tableHeader.columns.clientName}" sortUrl="invoices.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${tableHeader.columns.grossAmount}" sortUrl="invoices.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${tableHeader.columns.netAmount}" sortUrl="invoices.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${tableHeader.columns.createDate}" sortUrl="invoices.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${tableHeader.columns.paymentDate}" sortUrl="invoices.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${tableHeader.columns.comments}" sortUrl="invoices.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${tableHeader.columns.invoiceStatus}" sortUrl="invoices.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${tableHeader.columns.user}" sortUrl="invoices.app?event=sort&amp;sortColumn=" />
				<td></td>
			</tr>
			
			<c:forEach items="${invoiceList.records}" var="invoice" varStatus="rowStatus">
				<tr class="row${rowStatus.index%2}">
				
					<td>
						<custom:checkbox name="invoiceSelect_${rowStatus.index}" value="" />				
					</td>
					<td  onclick="submitFormWithParam('edit',${invoice.invoiceId})" ><c:out value="${invoice.number}"/></td>
					<td  onclick="submitFormWithParam('edit',${invoice.invoiceId})" ><c:out value="${invoice.clientDesc}"/></td>
					<td  onclick="submitFormWithParam('edit',${invoice.invoiceId})" ><c:out value="${invoice.grossAmount}"/></td>
					<td  onclick="submitFormWithParam('edit',${invoice.invoiceId})" ><c:out value="${invoice.netAmount}"/></td>
					<td  onclick="submitFormWithParam('edit',${invoice.invoiceId})" ><c:out value="${invoice.createDate}"/></td>
					<td  onclick="submitFormWithParam('edit',${invoice.invoiceId})" ><c:out value="${invoice.paymentDate}"/></td>
					<td  onclick="submitFormWithParam('edit',${invoice.invoiceId})" style="max-width: 250px"><c:out value="${invoice.additionalComments}"/></td>
					<td  onclick="submitFormWithParam('edit',${invoice.invoiceId})" style="background-color: ${invoice.status.color}" >
						<c:out value="${invoice.status.desc}"/>
					</td>
					<td  onclick="submitFormWithParam('edit',${invoice.invoiceId})" ><c:out value="${invoice.user}"/></td>
					<td >
						<c:choose>
							<c:when test="${invoice.editable}">
								<!-- <input type="button" value="  -  " onclick="submitFormWithParamAndConfirmation('delete',${invoice.invoiceId})"/> -->
								<input type="button" value="Kopiuj" onclick="submitFormWithParam('copy',${invoice.invoiceId})"/>
								<c:if test="${invoice.status.idn=='PENDING'}">
									<input type="button" value="Rozliczona" onclick="submitFormWithParam('settle_invoice',${invoice.invoiceId})"/>
								</c:if>
							</c:when>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="11" style="text-align: center;">
					<custom:pageNav page="${invoiceList}" pageChangeUrl="invoices.app?event=changePage&amp;newPageNo="/>
				</td>
			</tr>
		</table>
		<br/>
			<input type="button" value="Usuń zaznaczone" onclick="submitForm('delete_selected')"/>
			<input type="button" value="Drukuj zaznaczone" onclick="submitForm('print_selected')"/>
		</c:if>
		
		<br/><br/>
		
		<c:if test="${not empty correctionList}">
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			
			<tr class="tableHeader">
				<td>Numer korekty</td>
				<td>Numer faktury korygowanej</td>
				<td>Kwota netto</td>
				<td>Kwota vat</td>
				<td>Kwota brutto</td>
				<td>Różnica</td>
				<td>Data wystawienia</td>
				<td>Uwagi</td>
				<td>Wystawił</td>
				<td></td>
			</tr>
			
			<c:forEach items="${correctionList}" var="correction" varStatus="rowStatus">
				<tr class="row${rowStatus.index%2}">
				
					<td  onclick="redirectToUrl('correction.app?event=edit&amp;eventParam=${correction.correctionId}'); " ><c:out value="${correction.correctionNumber}"/></td>
					<td  onclick="redirectToUrl('correction.app?event=edit&amp;eventParam=${correction.correctionId}'); " ><c:out value="${correction.invoiceNumber}"/></td>
					<td  onclick="redirectToUrl('correction.app?event=edit&amp;eventParam=${correction.correctionId}'); " ><c:out value="${correction.netAmount}"/></td>
					<td  onclick="redirectToUrl('correction.app?event=edit&amp;eventParam=${correction.correctionId}'); " ><c:out value="${correction.vatAmount}"/></td>
					<td  onclick="redirectToUrl('correction.app?event=edit&amp;eventParam=${correction.correctionId}'); " ><c:out value="${correction.grossAmount}"/></td>
					<td  onclick="redirectToUrl('correction.app?event=edit&amp;eventParam=${correction.correctionId}'); " ><c:out value="${correction.grossAmountDiff}"/></td>
					<td  onclick="redirectToUrl('correction.app?event=edit&amp;eventParam=${correction.correctionId}'); " ><c:out value="${correction.createDate}"/></td>
					<td  onclick="redirectToUrl('correction.app?event=edit&amp;eventParam=${correction.correctionId}'); " ><c:out value="${correction.comments}"/></td>
					<td  onclick="redirectToUrl('correction.app?event=edit&amp;eventParam=${correction.correctionId}'); " ><c:out value="${correction.user}"/></td>
					<td >
						<input type="button" value="  -  " onclick="redirectToUrl('correction.app?event=delete&amp;eventParam=${correction.correctionId}'); "/>
					</td>
				</tr>
			</c:forEach>
		</table>
		</c:if>
			
	</c:if>
	
	<c:if test="${event=='EDIT'}">
		<input type="hidden" name="invoiceId" value="${selectedInvoice.invoiceId}" />
		
		<br/>
		<b><c:out value="FAKTURA - EDYCJA"/></b>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label" style="width: 120px;">Numer</td>
				<td class="field" style="width: 230px;">
					<input type="text" name="number" value="${selectedInvoice.number}"/>
				</td>
			</tr>
			<tr>
				<td class="label">Sprzedający</td>
				<td class="field">
					<custom:select name="sellerId" values="${selectsMap.sellerDictionary}" selectedValue="${selectedInvoice.sellerId}" />
				</td>
			</tr>
			
			<tr>
				<td class="label">Konto sprzedającego</td>
				<td class="field">
					<custom:select name="sellerBankAccountId" values="${selectsMap.bankAccountDictionary}" selectedValue="${selectedInvoice.sellerBankAccountId}" />
				</td>
			</tr>
			<tr>
				<td class="label">Klient</td>
				<td class="field">
					<custom:select name="clientId" values="${selectsMap.clientDictionary}" selectedValue="${selectedInvoice.clientId}" />
					<input type="button" value="Definiuj klienta" onclick="redirectToUrl('clients.app?event=new'); " />
				</td>
			</tr>
			<tr>
				<td class="label">Data wystawienia</td>
				<td class="field">
					<custom:date name="createDate" identifier="createDate" value="${selectedInvoice.createDate}" />
				</td>
			</tr>
			<tr>
				<td class="label">Data sprzedaży</td>
				<td class="field">
					<custom:date name="signDate" identifier="signDate" value="${selectedInvoice.signDate}" />
				</td>
			</tr>
			<tr>
				<td class="label">Data płatności</td>
				<td class="field">
					<custom:date name="paymentDate" identifier="paymentDate" value="${selectedInvoice.paymentDate}" />
				</td>
			</tr>
			<tr>
				<td class="label">Status</td>
				<td class="field" style="background-color: ${selectedInvoice.status.color}">
					<c:out value="${selectedInvoice.status.desc}"/>
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
				<td class="label">Ilość</td>
				<td class="label">Cena netto</td>
				<td class="label">Wartość netto</td>
				<td class="label">Stawka VAT</td>
				<td class="label">Kwota VAT</td>
				<td class="label">Wartość brutto</td>
			</tr>
			<c:forEach items="${selectedInvoice.invoiceProducts}" var="product" varStatus="rowStatus">
				<tr>
				<td class="field">
					<custom:select name="product[${rowStatus.index}].productDefinition.productId" values="${selectsMap.productDictionary}" 
						selectedValue="${product.productDefinition.productId}" style="width: 400" 
						onChange="submitFormWithParam('change_product',${rowStatus.index})"/>
					
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].productDefinition.unitType" value="${product.productDefinition.unitType.idn}"
						style="width: 30" disabled="disabled"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].quantity" value="${product.quantity}" style="width: 40"
						onChange="submitFormWithParam('change_product',${rowStatus.index})"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].productDefinition.netPrice" value="${product.productDefinition.netPrice}"
						style="width: 70" disabled="disabled"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].netSumAmount" value="${product.netSumAmount}"
						style="width: 80" disabled="disabled"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].productDefinition.vatRate" value="${product.productDefinition.vatRate.desc}"
						style="width: 40" disabled="disabled"/><c:out value=" %"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].vatAmount" value="${product.vatAmount}"
						style="width: 80" disabled="disabled"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].grossSumAmount" value="${product.grossSumAmount}"
						style="width: 80" disabled="disabled"/>
				</td>
				<td class="field">
					<c:if test="${not selectedInvoice.hasCorrection()}">
						<input type="button" value="  -  " onclick="submitFormWithParam('del_product_row',${rowStatus.index})"/>
					</c:if>
				</td>
			</tr>
			
			</c:forEach>
			<c:if test="${not selectedInvoice.hasCorrection()}">
				<tr>
					<td class="field" align="right" colspan="8">
						<input type="button" value="Definiuj produkt" onclick="redirectToUrl('products.app?event=new');" />
						<input type="button" value="Dodaj wiersz" onclick="submitForm('add_product_row')" />
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="field" colspan="3"/>
				<td class="field" align="right">
					<c:out value="RAZEM" />
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

			<c:forEach items="${selectedInvoice.invoiceDetailValues}" var="detailsValue" varStatus="status">
				<tr>
					<c:choose>
						<c:when test="${status.index==0}">
							<td class="field" colspan="3"/>
							<td class="field" align="right">
								<c:out value="w tym"/>
							</td>
						</c:when>
						<c:otherwise>
							<td class="field" colspan="4"/>
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
					<input type="text" name="paymentAmount" value="${selectedInvoice.grossAmount}" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="label">Zapłacono</td>
				<td class="field">
					<input type="text" name="paid" value="${selectedInvoice.paid}" onChange="submitForm('paid_entered')"/>
				</td>
			</tr>
			<tr>
				<td class="label">Słownie</td>
				<td class="field">
					<input type="text" name="paidWords" value="${selectedInvoice.paidWords}"  style="width: 300"/>
				</td>
			</tr>
			<tr>
				<td class="label">Pozostało do zapłaty</td>
				<td class="field">
					<input type="text" name="paymentLeft" value="${selectedInvoice.paymentLeft}" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="label">Sposób płatności</td>
				<td class="field">
					<custom:select name="paymentType" values="${selectsMap.paymentTypeDictionary}" 
						selectedValue="${selectedInvoice.paymentType.idn}" style="width: 170" 
						onChange="submitForm('change_payment_type')"/>
				</td>
			</tr>
			<tr>
				<td class="label">Uwagi</td>
				<td class="field">
					<input type="text" name="comments" value="${selectedInvoice.comments}" style="width: 300"/>
				</td>
			</tr>
			<tr>
				<td class="label">Uwagi dodatkowe (niepublikowane)</td>
				<td class="field">
					<input type="text" name="additionalComments" value="${selectedInvoice.additionalComments}" style="width: 300"/>
				</td>
			</tr>
		</table>
		
		<table>
			<tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<c:choose>
					<c:when test="${selectedInvoice.editable}">
						<input type="button" value="Zapisz" onclick="submitForm('save')" />
					</c:when>
					<c:otherwise>
						<input type="button" value="Zapisz" onclick="submitForm('save')" disabled="disabled"/>
					</c:otherwise>
					</c:choose>
				</td>
				<td width="10"/>
				<c:if test="${not selectedInvoice.hasCorrection()}" >
					<td>
						<input type="button" value="Wystaw korektę" 
							onclick="submitFormWithParam('correct',${selectedInvoice.invoiceId})" />
					</td>
					<td width="10"/>
				</c:if>
				<c:if test="${selectedInvoice.hasCorrection()}" >
					Faktura korygowana
				</c:if >
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
     	
     	function redirectToUrl(url){
     		window.location.href = url;
     	}
     </script>
 	]]>
 
 	</jsp:text>

</jsp:root>