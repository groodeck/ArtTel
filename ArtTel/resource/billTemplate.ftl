<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<html>
	<body style="allign: center">	
		<table>
			<tr>
				<td>
					<table cellspacing="0" style="font-size: 10px; font-family: arial; text-align:center; vertical-align: middle;">
						<tr>
							<td rowspan="2" width="196" style="border-width: 1px; border-style: solid; font-size: 15px; font-weight: bold; background-color: #7D7878;">
								FAKTURA NR
							</td>
							<td rowspan="2" width="197" style="border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid; font-size: 15px; font-weight: bold;">
								${number}
							</td>
							<td rowspan="2" width="18"/>
							<td width="396" height="20" style="border-width: 1px; border-style: solid;">
								${seller.city!}, ${createDate!}
							</td>
						</tr>
						<tr>
							<td height="20" style="background-color: #B4B2B2; border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;">
								miejscowoœæ, data
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table  cellspacing="0" style="font-size: 10px; font-family: arial; text-align:center; vertical-align: middle;">
						<tr>
							<td height="20"/>
						</tr>
						<tr>
							<td width="166" height="20" style="background-color: #B4B2B2; border-width: 1px; border-style: solid; font-weight: bold;">
								Termin p³atnoœci
							</td>
							<td width="28"></td>
							<td width="196" style="background-color: #B4B2B2; border-width: 1px; border-style: solid; font-weight: bold;">
								Forma p³atnoœci
							</td>
							<td width="18"/>
							<td width="396" style="background-color: #B4B2B2; border-width: 1px; border-style: solid; font-weight: bold;">
								Data dokonania/zakoñczenia dostawy, wykonania us³ugi
							</td>
						</tr>
						<tr>
							<td height="20" style="border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;">
								${paymentDate!}
							</td>
							<td/>
							<td style="border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;">
								${paymentType.desc!}
							</td>
							<td/>
							<td style="border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;">
								${realizationDate!}
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td>
					<table  cellspacing="0" style="font-size: 10px; font-family: arial; text-align:center; vertical-align: middle;">
						<tr>
							<td height="20"/>
						</tr>
						<tr>
							<td width="396" height="20" style="background-color: #B4B2B2; border-width: 1px; border-style: solid; font-weight: bold;">
								SPRZEDAWCA
							</td>
							<td width="18"/>
							<td width="396" style="background-color: #B4B2B2; border-width: 1px; border-style: solid; font-weight: bold;">
								NABYWCA
							</td>
						</tr>
						<tr>
							<td height="100" style="border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;">
								${seller.name!}<br/>
								${seller.addressCity!}<br/>
								${seller.addressStreet!}<br/>
								NIP: ${seller.nip!}
							</td>
							<td></td>
							<td style="border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;">
								${client.name!}<br/>
								${client.addressCity!}<br/>
								${client.addressStreet!}<br/>
								NIP: ${client.nip!}
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td>
					<table  cellspacing="0" style="font-size: 10px; font-family: arial; text-align:center; vertical-align: middle;">
						<tr>
							<td height="20"/>
						</tr>
						<tr>
							<td width="166" height="20" style="border-width: 1px; border-style: solid; font-weight: bold; background-color: #B4B2B2;">
								Bank, nr konta
							</td>
							<td width="647" style="border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid; font-weight: bold;">
								${bankAccountName!}, ${bankAccountNumber!}
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="20"/>
			</tr>
			
			<tr>
				<td>
					<table cellspacing='0' style='font-size: 11px; font-family: arial;'>
			
						<tr style='background-color: #B4B2B2; text-align:center;'>
							<td rowspan='2' height='22px' width="40px" style='border: 1px solid;'>
								L.p.
							</td>
							<td rowspan='2' width="250px" style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid;'>
								Nazwa towaru/us³ugi
							</td>
							<td rowspan='2' width='90px' style='border: 1px solid;'>
								Podstawa prawna zwolnienia*
							</td>
							<td rowspan='2' width="80px" style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid;'>
								PKWIU**
							</td>
							<td rowspan='2' width='40px' style='border: 1px solid;'>
								Jm
							</td>
							<td rowspan='2' width="70px" style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid;'>
								Iloœæ
							</td>
							<td  colspan='2' width="125px" style='border: 1px solid;'>
								Wartoœæ jednostkowa
							</td>
							<td  colspan='2' width="125px" style='border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								Wartoœæ towaru/us³ugi
							</td>
						</tr>
						<tr style='background-color: #B4B2B2; text-align:center;'>
							<td width="100px" style='border-bottom-width: 1px; border-bottom-style: solid; border-left-width: 1px; border-left-style: solid; border-right-width: 1px; border-right-style: solid;'>
								z³
							</td>
							<td width="20px" style='border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								gr
							</td>
							<td width="100px" style='border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								z³
							</td>
							<td width="20px" style='border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								gr
							</td>
						</tr>
						
						<#list documentProducts as documentProduct>
						<tr>
							<td height='20' style='text-align:center; border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${documentProduct?counter}
							</td>
							<td style='border-bottom-width: 1px; border-bottom-style: solid;'>
								${documentProduct.productDefinition.productDescription}
							</td>
							<td style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${documentProduct.taxReleaseBasis!}
							</td>
							<td style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid;'>
								${documentProduct.productClassification!}
							</td>
							<td style='text-align:right; border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${documentProduct.productDefinition.unitType.desc}
							</td>
							<td style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid;'>
								${documentProduct.quantity}
							</td>
							<td style='text-align:right; border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${documentProduct.productDefinition.priceWholePart}
							</td>
							<td style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${documentProduct.productDefinition.priceDecimalPart}
							</td>
							<td style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${documentProduct.amountWholePart}
							</td>
							<td style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${documentProduct.amountDecimalPart}
							</td>
						</tr>
						</#list>
						
						<tr>
							<td colspan='5' height='20'>* Ustawa o VAT (Dz. U. z 2004 r. nr 54, poz. 535 z póŸn. zm.) </td>
							<td style="text-align: right;">
								RAZEM
							</td>
							<td colspan='2' style='background-color: #7D7878; border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'/>
							<td style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${amountWholePart}
							</td>
							<td style='text-align:right; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${amountDecimalPart}
							</td>
						</tr>
						
						<tr>
							<td colspan='10' height='20'>** nie jest obowi¹zkowe</td>
						</tr>
						
						<tr>
							<td  colspan='5' height='40px' style='font-weight: bold; text-align:center; font-size:11px; border: 1px solid; background-color: #B4B2B2;'>
								UWAGI 
							</td>
							<td/>
							<td colspan='2' height='40px' style='background-color: #B4B2B2; border-width: 1px; border-style: solid; font-weight: bold; text-align: center; font-size:11px'>
								DO ZAP£ATY:
							</td>
							<td colspan='2' style='text-align: center; font-weight: bold; border-top-width: 1px; border-top-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'>
								${amount}
							</td>
						</tr>
						<tr>
							<td colspan='5' rowspan='3' style="font-size: 11px; font-weight: normal; text-align:left; vertical-align: top; border-left-width: 1px; border-left-style: solid; border-right-width: 1px; border-right-style: solid; border-bottom-width: 1px; border-bottom-style: solid;"> 
								${comments!}
							</td>
							<td/>
							<td colspan='4' height='20px'/>
						</tr>
						<tr>
							<td/>
							<td colspan='4' height='20px' style='background-color: #B4B2B2; border-width: 1px; border-style: solid; text-align: center;'>
								Fakturê wystawi³
							</td>
						</tr>
						<tr>
							<td/>
							<td colspan='4' height='40px' style='border-left-width: 1px; border-left-style: solid; border-bottom-width: 1px; border-bottom-style: solid; border-right-width: 1px; border-right-style: solid;'/>
						</tr>
						
					</table>
				</td>
			</tr>
		</table> 		
		
		 
	</body>
</html>