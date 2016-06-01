<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<html>
	<body>	
		<table cellspacing='0' style='font-size: 11px; font-family: arial;' width='100%'>
			<tr style='text-align:center; vertical-align: middle; font-size: 15px; font-weight: bold;'>
				<td rowspan='3' width='230px'></td>
				<td height='40px'>FAKTURA VAT</td>
				<td width='220px'>${number}</td>
			</tr>
			<tr>
				<td height='22px'>Data wystawienia: ${createDate!}</td>
				<td>ORYGINA£/KOPIA</td>
			</tr>
			<tr>
				<td height='22px'>Data sprzeda¿y: ${signDate!}</td>
				<td/>
			</tr>
			<tr>
				<td height='22px'>pieczêæ sprzedawcy</td>
				<td colspan='2'/>
			</tr>
		</table>
		<table cellspacing='0' style='font-size: 11px; font-family: arial;'>
			<tr>
				<td height='60px'>
					Sprzedawca:<br/>
					${seller.name!}<br/>
					${seller.addressCity!}<br/>
					${seller.addressStreet!}
				</td>
				<td height='60px'>
					Nabywca:<br/>
					${client.name!}<br/>
					${client.addressStreet!}<br/>
					${client.addressCity!}
				</td>
			</tr>
			<tr>
				<td height='22px'>NIP: ${seller.nip!}</td>
				<td>NIP: ${client.nip!}</td>
			</tr>
			<tr>
				<td height='44px'>
					Nr rachunku: ${bankAccountName!}<br/>
					${bankAccountNumber!}
				</td>
				<td>
					Sposób zap³aty: <br/>
					${paymentType.desc}
					<#if paymentDate??>
						. Termin p³atnoœci: ${paymentDate}
					</#if>
				</td>
			</tr>
			<tr>
				<td colspan='2' height='22'/>
			</tr>
		</table>
		<table>
			<tr style='text-align:center;'>
				<td rowspan='2' height='22' style='border: 1px solid;'>
					Nazwa us³ugi
				</td>
				<td rowspan='2' width='70' style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid;'>
					Iloœæ
				</td>
				<td rowspan='2' colspan="2" style='border: 1px solid;'>
					Okres
				</td>
				<td rowspan='2' colspan="2" style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid;'>
					Wartoœæ netto
				</td>
				<td colspan='3' width='90' style='border: 1px solid;'>
					VAT
				</td>
				<td rowspan='2' colspan='2' width='90' style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					Wartoœæ brutto
				</td>
			</tr>
			<tr style='text-align:center;'>
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-left-width: 1px; border-left-style: solid; border-right-width: 1px; border-right-style: solid;'>
					%
				</td>
				<td colspan='2' style='border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					kwota
				</td>
			</tr>
			
			<#list documentProducts as productRecord>
			<tr>
				<td height='22'  style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${productRecord.productDefinition.productDescription}
				</td>
				<td style='border-bottom-width: 1px; border-bottom-style: solid;'>
					${productRecord.quantity}
				</td>
				<td colspan="2" width='160' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					
				</td>
				<td colspan="2" style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid;'>
					${productRecord.netSumAmount?number?string["0.00"]}
				</td>
				<td style='text-align:right; border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${productRecord.productDefinition.vatRate.desc!}
				</td>
				<td colspan='2' style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${productRecord.vatAmount?number?string["0.00"]}
				</td>
				<td colspan='2' style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${productRecord.grossSumAmount?number?string["0.00"]}
				</td>
			</tr>
			</#list>
			
			<tr>
				<td colspan='2' height='22'/>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					RAZEM
				</td>
				<td colspan='2' style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid;'>
					${netAmount?number?string["0.00"]}
				</td>
				<td style='text-align:center; border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					x
				</td>
				<td colspan='2' style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${vatAmount?number?string["0.00"]}
				</td>
				<td colspan='2' style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${grossAmount?number?string["0.00"]}
				</td>
			</tr>
			<!-- <tr>
				<td colspan='5'/>
				<td colspan='2' rowspan='5'>W TYM</td>
				<td colspan='2'/>
				<td>zw</td>
				<td colspan='2'/>
				<td colspan='2'/>
			</tr>
			<tr>
				<td colspan='5'/>
				<td colspan='2'/>
				<td>23</td>
				<td colspan='2'/>
				<td colspan='2'/>
			</tr> -->
			<tr>
				<td colspan='2' height='22'/>
				<td colspan='9' rowspan="2">S³ownie: ${paidWords}</td>
			</tr>
			<tr>
				<td colspan='2' height='22'/>
			</tr>
			<tr>
				<td colspan='11' height='22'/>
			</tr>
			
			<tr>
				<td colspan='11' height='50'/>
			</tr>
			
			<tr style='font-size: 12px; font-style: italic; font-weight: bold;'>
				<td rowspan='2' height='50' style='border: 1px solid;'>
					Razem do zap³aty:
				</td>
				<td rowspan='2' height='50' style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${grossAmount?number?string["0.00"]}
				</td>
				<td colspan='9' rowspan='2'></td>
			</tr>
			<tr/>
			
			<tr>
				<td colspan='11' height='22'/>
			</tr>
			<tr>
				<td colspan='11' height='22'/>
			</tr>
			<tr>
				<td colspan='11' height='22'/>
			</tr>
			
			<tr style='font-size: 12px; font-style: italic; font-weight: bold;'>
				<td rowspan='2' height='50' style='border: 1px solid;'>
					Saldo na dzieñ<br/>wystawienia faktury:
				</td>
				<td rowspan='2' height='50' style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					0,00
				</td>
				<td colspan='9' rowspan='2'></td>
			</tr>
			<tr/>
			<tr>
				<td colspan='11' height='22'>Je¿eli wartoœæ salda konta jest wy¿sza ni¿ kwota do zap³aty, pilnie skontaktuj siê z operatorem !</td>
			</tr>
			
		</table>
		
	</body>
</html>