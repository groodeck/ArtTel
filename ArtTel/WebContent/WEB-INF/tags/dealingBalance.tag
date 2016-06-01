<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	 xmlns:c="http://java.sun.com/jsp/jstl/core" >

	<jsp:directive.tag language="java" pageEncoding="ISO-8859-2"/>
	
	<jsp:directive.attribute name="userBalanceList" type="java.util.List" required="true"/>
	<jsp:directive.attribute name="isIndividual" type="Boolean" required="false"/>

	<table class="borderedTable" cellpadding="2" cellspacing="1" >
				
			<tr class="tableHeader">
				<c:if test="${isIndividual}">
					<td>Monter</td>
				</c:if>
				<td>Koszty</td>	
				<td>Przychody</td>
				<td>Zysk</td>
				<c:if test="${isIndividual}">				
					<td>Kwota do dyspozycji</td>
				</c:if>
			</tr>
				
			<c:forEach items="${userBalanceList}" var="userBalance" varStatus="rowStatus">
				<tr class="row${rowStatus.count%2}">
					<c:if test="${isIndividual}">
						<td  onclick="submitFormWithParam('details',${userBalance.balanceId})" >
							<c:out value="${userBalance.userName}"/>
						</td>
					</c:if>
					<td  onclick="submitFormWithParam('details',${userBalance.balanceId})" style="background-color: red" >
						<c:out value="${userBalance.costSum}"/>
					</td>
					<td  onclick="submitFormWithParam('details',${userBalance.balanceId})" style="background-color: #FFCC00" >
						<c:out value="${userBalance.incomeSum}"/>
					</td>
					<td  onclick="submitFormWithParam('details',${userBalance.balanceId})" style="background-color: #009900" >
						<c:out value="${userBalance.gain}"/>
					</td>
					<c:if test="${isIndividual}">
						<td  onclick="submitFormWithParam('details',${userBalance.balanceId})" style="background-color: #00B0F0" >
							<c:out value="${userBalance.amountLeft}"/>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>	
	   	
</jsp:root>