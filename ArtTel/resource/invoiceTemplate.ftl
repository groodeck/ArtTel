<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<html>
	<body>	
		<table cellspacing='0' style='font-size: 12px; font-family: arial;' width='100%'>
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
		<table cellspacing='0'>
			<tr style='text-align:center;'>
				<td rowspan='3' height='22' style='border: 1px solid;'>
					Lp.
				</td>
				<td rowspan='3' height='22' style='border: 1px solid;'>
					Nazwa towaru lub us³ugi
				</td>
				<td rowspan='3' height='22' style='border: 1px solid;'>
					PKWiU
				</td>
				<td rowspan='3' height='22' style='border: 1px solid;'>
					J.m.
				</td>
				<td rowspan='3' width='70' style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid;'>
					Iloœæ
				</td>
				<td rowspan='2' colspan="2" style='border: 1px solid;'>
					Cena jednostkowa bez podatku
				</td>
				<td rowspan='2' colspan="2" style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid;'>
					Wartoœæ netto
				</td>
				<td colspan='3' width='90' style='border: 1px solid;'>
					Podatek
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
			<tr style='text-align:center;'>
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-left-width: 1px; border-left-style: solid; border-right-width: 1px; border-right-style: solid;'>
					z³
				</td>
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					gr
				</td>
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-left-width: 1px; border-left-style: solid; border-right-width: 1px; border-right-style: solid;'>
					z³
				</td>
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					gr
				</td>
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;' />
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-left-width: 1px; border-left-style: solid; border-right-width: 1px; border-right-style: solid;'>
					z³
				</td>
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					gr
				</td>
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-left-width: 1px; border-left-style: solid; border-right-width: 1px; border-right-style: solid;'>
					z³
				</td>
				<td style='border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					gr
				</td>
			</tr>
			
			<#list documentProducts as productRecord>
			<tr>
				<td height='22'  style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					
				</td>
				<td style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${productRecord.productDefinition.productDescription}
				</td>
				<td style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;' />
				<td style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;' >
					${productRecord.productDefinition.unitType.desc}
				</td>
				<td style='border-bottom-width: 1px; border-bottom-style: solid;'>
					${productRecord.quantity}
				</td>
				<td colspan="2" style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${productRecord.productDefinition.netPrice?number?string["0.00"]}
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
				<td colspan='5' height='22'/>
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
			 <tr>
				<td rowspan='2' colspan='5' height='22'/>
				<td rowspan='5' colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;' >
					W TYM
				</td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					${invoiceDetailValueMap[VAT_ZW].netAmount?number?string["0.00"]}
				</td>
				<td style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
					zw
				</td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
			</tr>
			<tr>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
				<td style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>23</td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
			</tr>
			<tr>
				<td colspan='5' height='22' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>S³ownie:</td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
				<td style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>8</td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;' > </td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
			</tr>
			<tr>
				<td rowspan='2' colspan='5' height='22' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>${paidWords}</td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
				<td style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>5</td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
			</tr>
			<tr>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
				<td style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>0</td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
				<td colspan='2' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'> </td>
			</tr>
			<tr>
				<td colspan='14' height='10'/>
			</tr>
			<tr>
				<td colspan='2' height='40' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>Razem do zap³aty:</td>
				<td colspan='3' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>${grossAmount?number?string["0.00"]}</td>
			</tr>
			<tr>
				<td colspan='14' height='10'/>
			</tr>
			<tr>
				<td colspan='14' height='40' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>Uwagi: ${comments}</td>
			</tr>
			<tr>
				<td colspan='14' height='40'/>
			</tr>
			<tr>
				<td colspan='11' height='22'/>
			</tr>
		</table>
		
		<table>
			<tr>
				<td height='60' width='10'/>
				<td width='200' style='border-width: 1px; border-style: solid;'/>
				<td/>
				<td width='200' style='border-width: 1px; border-style: solid;'/>
				<td width='10'/>
			</tr>
			<tr>
				<td width='10'/>
				<td width='100' style='text-align: center'>Podpis imienny osoby upowa¿nionej do odbioru faktur VAT</td>	
				<td/>
				<td width='100' style='text-align: center'>Podpis imienny osoby upowa¿nionej do wystawienia faktury VAT</td>
				<td width='10'/>
			</tr>
		</table>
		
	</body>
</html>