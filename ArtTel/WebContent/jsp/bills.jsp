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
     
    <form method="post" action="bills.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	<input type="hidden" id="eventParam" name="eventParam" />
	
	<custom:menu userContext="${userContext}" />
	
	<c:if test="${event=='MAIN' || event=='SEARCH'}">
		
		<br/>
		<b><c:out value="RACHUNKI - FILTR"/></b>
		<br/><br/>
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label">Numer</td>
				<td class="field">
					<input type="text" name="billFilter.number" value="${billFilter.number}"/>
				</td>
				<td class="label">Data wystawienia</td>
				<td class="field">
					<custom:date name="billFilter.createDate" identifier="filterCreateDate" value="${billFilter.createDate}" />
				</td>
				<td width="10px"/>
				<td align="right" ><input type="button" value="Szukaj" onclick="submitForm('search')"/></td>
			</tr>
		</table> 
		<br/>
		
		<input type="button" value="Dodaj" onclick="submitForm('new')"/>
		<br/><br/>
		
		<c:if test="${not empty billList.records}">
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			
			<tr class="tableHeader">
				<td/>
				<custom:sortableHeader column="${billsTableHeader.columns.documentNumber}" sortUrl="bills.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${billsTableHeader.columns.clientName}" sortUrl="bills.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${billsTableHeader.columns.amount}" sortUrl="bills.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${billsTableHeader.columns.createDate}" sortUrl="bills.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${billsTableHeader.columns.paymentDate}" sortUrl="bills.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${billsTableHeader.columns.comments}" sortUrl="bills.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${billsTableHeader.columns.documentStatus}" sortUrl="bills.app?event=sort&amp;sortColumn=" />
				<custom:sortableHeader column="${billsTableHeader.columns.user}" sortUrl="bills.app?event=sort&amp;sortColumn=" />
				<td></td>
			</tr>
			
			<c:forEach items="${billList.records}" var="bill" varStatus="rowStatus">
				<tr class="row${rowStatus.index%2}">
				
					<td>
						<custom:checkbox name="invoiceSelect_${rowStatus.index}" value="" />				
					</td>
					<td  onclick="submitFormWithParam('edit',${bill.documentId})" ><c:out value="${bill.number}"/></td>
					<td  onclick="submitFormWithParam('edit',${bill.documentId})" ><c:out value="${bill.clientDesc}"/></td>
					<td  onclick="submitFormWithParam('edit',${bill.documentId})" ><c:out value="${bill.amount}"/></td>
					<td  onclick="submitFormWithParam('edit',${bill.documentId})" ><c:out value="${bill.createDate}"/></td>
					<td  onclick="submitFormWithParam('edit',${bill.documentId})" ><c:out value="${bill.paymentDate}"/></td>
					<td  onclick="submitFormWithParam('edit',${bill.documentId})" style="max-width: 250px"><c:out value="${bill.additionalComments}"/></td>
					<td  onclick="submitFormWithParam('edit',${bill.documentId})" style="background-color: ${bill.status.color}" >
						<c:out value="${bill.status.desc}"/>
					</td>
					<td  onclick="submitFormWithParam('edit',${bill.documentId})" ><c:out value="${bill.user}"/></td>
					<td >
						<c:choose>
							<c:when test="${bill.editable}">
								<input type="button" value="Kopiuj" onclick="submitFormWithParam('copy',${bill.documentId})"/>
								<c:if test="${bill.status.idn=='PENDING'}">
									<input type="button" value="Rozliczony" onclick="submitFormWithParam('settle_invoice',${bill.documentId})"/>
								</c:if>
							</c:when>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="11" style="text-align: center;">
					<custom:pageNav page="${billList}" pageChangeUrl="bills.app?event=changePage&amp;newPageNo="/>
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
		<input type="hidden" name="documentId" value="${selectedBill.documentId}" />
		
		<br/>
		<b><c:out value="RACHUNEK - EDYCJA"/></b>
		<br/><br/>
		
		<table class="borderedTable" cellpadding="2" cellspacing="1" >
			<tr>
				<td class="label" style="width: 120px;">Numer</td>
				<td class="field" style="width: 230px;">
					<input type="text" name="number" value="${selectedBill.number}"/>
				</td>
			</tr>
			<tr>
				<td class="label">Sprzedający</td>
				<td class="field">
					<custom:select name="sellerId" values="${selectsMap.sellerDictionary}" selectedValue="${selectedBill.sellerId}" />
				</td>
			</tr>
			
			<tr>
				<td class="label">Konto sprzedającego</td>
				<td class="field">
					<custom:select name="sellerBankAccountId" values="${selectsMap.bankAccountDictionary}" selectedValue="${selectedBill.sellerBankAccountId}" />
				</td>
			</tr>
			<tr>
				<td class="label">Klient</td>
				<td class="field">
					<custom:select name="clientId" values="${selectsMap.clientDictionary}" selectedValue="${selectedBill.clientId}" />
					<input type="button" value="Definiuj klienta" onclick="redirectToUrl('clients.app?event=new'); " />
				</td>
			</tr>
			<tr>
				<td class="label">Data wystawienia</td>
				<td class="field">
					<custom:date name="createDate" identifier="createDate" value="${selectedBill.createDate}" />
				</td>
			</tr>
			<tr>
				<td class="label">Data płatności</td>
				<td class="field">
					<custom:date name="paymentDate" identifier="paymentDate" value="${selectedBill.paymentDate}" />
				</td>
			</tr>
			<tr>
				<td class="label">Data dokonania/zakończenia dostawy,<br/> wykonania usługi</td>
				<td class="field">
					<custom:date name="realizationDate" identifier="realizationDate" value="${selectedBill.realizationDate}" />
				</td>
			</tr>
			<tr>
				<td class="label">Status</td>
				<td class="field" style="background-color: ${selectedBill.status.color}">
					<c:out value="${selectedBill.status.desc}"/>
				</td>
			</tr>
		</table>
		<br/>
		<table class="borderedTable">
			<tr>
				<td class="label" colspan="8">Produkty/Usługi</td>
			</tr><tr>
				<td class="label">Nazwa towaru/usługi</td>
				<td class="label">Podstawa<br/>prawna<br/>zwolnienia</td>
				<td class="label">PKWIU</td>
				<td class="label">j.m.</td>
				<td class="label">Ilość</td>
				<td class="label">Wartość jednostkowa</td>
				<td class="label">Wartość towaru/usługi</td>
				<td class="label"/>
			</tr>
			<c:forEach items="${selectedBill.documentProducts}" var="product" varStatus="rowStatus">
				<tr>
				<td class="field">
					<custom:select name="product[${rowStatus.index}].productDefinition.productId" values="${selectsMap.productDictionary}" 
						selectedValue="${product.productDefinition.productId}" style="width: 400" 
						onChange="submitFormWithParam('change_product',${rowStatus.index})"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].taxReleaseBasis" value="${product.taxReleaseBasis}" style="width: 70"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].productClassification" value="${product.productClassification}" style="width: 70"/>
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
						style="width: 140" disabled="disabled"/>
				</td>
				<td class="field">
					<input type="text" name="product[${rowStatus.index}].sumAmount" value="${product.sumAmount}"
						style="width: 140" disabled="disabled"/>
				</td>
				<td class="field">
					<c:if test="${not selectedBill.hasCorrection()}">
						<input type="button" value="  -  " onclick="submitFormWithParam('del_product_row',${rowStatus.index})"/>
					</c:if>
				</td>
			</tr>
			
			</c:forEach>
			<c:if test="${not selectedBill.hasCorrection()}">
				<tr>
					<td class="field" align="right" colspan="8">
						<input type="button" value="Definiuj produkt" onclick="redirectToUrl('products.app?event=new');" />
						<input type="button" value="Dodaj wiersz" onclick="submitForm('add_product_row')" />
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="field" colspan="4"/>
				<td class="field" align="right">
					<b><c:out value="RAZEM" /></b>
				</td>
				<td class="field" align="right">
					<c:out value="-" />
				</td>
				<td class="field" align="right">
					<b><c:out value="${selectedBill.amount}"/></b>
				</td>
				<td class="field"/>
			</tr>

		</table>
		<br/>
		<table class="borderedTable">
			<tr>
				<td class="label">Razem do zapłaty</td>
				<td class="field">
					<input type="text" name="paymentAmount" value="${selectedBill.amount}" disabled="disabled"/>
				</td>
			</tr>
			<!-- <tr>
				<td class="label">Zapłacono</td>
				<td class="field">
					<input type="text" name="paid" value="${selectedBill.paid}" onChange="submitForm('paid_entered')"/>
				</td>
			</tr>
			<tr>
				<td class="label">Słownie</td>
				<td class="field">
					<input type="text" name="paidWords" value="${selectedBill.paidWords}"  style="width: 300"/>
				</td>
			</tr>
			<tr>
				<td class="label">Pozostało do zapłaty</td>
				<td class="field">
					<input type="text" name="paymentLeft" value="${selectedBill.paymentLeft}" disabled="disabled"/>
				</td>
			</tr> -->
			<tr>
				<td class="label">Sposób płatności</td>
				<td class="field">
					<custom:select name="paymentType" values="${selectsMap.paymentTypeDictionary}" 
						selectedValue="${selectedBill.paymentType.idn}" style="width: 170" 
						onChange="submitForm('change_payment_type')"/>
				</td>
			</tr>
			<tr>
				<td class="label">Uwagi</td>
				<td class="field">
					<input type="text" name="comments" value="${selectedBill.comments}" style="width: 300"/>
				</td>
			</tr>
			<tr>
				<td class="label">Uwagi dodatkowe (niepublikowane)</td>
				<td class="field">
					<input type="text" name="additionalComments" value="${selectedBill.additionalComments}" style="width: 300"/>
				</td>
			</tr>
		</table>
		
		<table>
			<tr>
				<td><input type="button" value="Anuluj" onclick="submitForm('back')"/></td>
				<td align="right">
					<c:choose>
					<c:when test="${selectedBill.editable}">
						<input type="button" value="Zapisz" onclick="submitForm('save')" />
					</c:when>
					<c:otherwise>
						<input type="button" value="Zapisz" onclick="submitForm('save')" disabled="disabled"/>
					</c:otherwise>
					</c:choose>
				</td>
				<td width="10"/>
				<c:if test="${not selectedBill.hasCorrection()}" >
					<td>
						<!-- <input type="button" value="Wystaw korektę" 
							onclick="submitFormWithParam('correct',${selectedBill.documentId})" />  -->
					</td>
					<td width="10"/>
				</c:if>
				<c:if test="${selectedBill.hasCorrection()}" >
					Rachunek korygowany
				</c:if >
				<td><input type="button" value="Drukuj" onclick="submitForm('print')"/></td>
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