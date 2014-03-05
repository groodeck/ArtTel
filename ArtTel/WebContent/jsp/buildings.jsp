<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root 
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	version="2.0" >
    
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
    
    <style type="text/css" >
    	 @IMPORT url("data/css/instalations.css");
	</style> 
	 
     <script type="text/javascript" language="javascript">
     
     	function submitForm(event){
     		document.getElementById('event').value = event;
     		document.forms[0].submit();
     	}
     	
     </script>
     
 <html xmlns="http://www.w3.org/1999/xhtml">
 <body style="background-color: #F0FFF0">
    
    <form method="post" action="buildings.app" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8" >
	<input type="hidden" id="event" name="event" />
	
	<c:import url="menu.jsp" />
	
	<br/>
	
	<table style="font-family:sans-serif;">
		<tr>
			<td colspan="2" style="font-weight: bold;"><c:out value="BUDYNKI"/></td>
		</tr><tr>
			<td width="200px">Osiedle</td>
			<td><input type="text" name="osiedle" /></td>
		</tr><tr>
			<td>Punkt optyczny</td>
			<td><input type="text" name="punktOptyczny"/></td>
		</tr><tr>
			<td>Administrator</td>
			<td><input type="text" name="administrator"/></td>
		</tr><tr>
			<td>Ulica</td>
			<td><input type="text" name="ulica"/></td>
		</tr><tr>
			<td>Miejscowośc</td>
			<td><input type="text" name="miejscowosc"/></td>
		</tr><tr>
			<td>Ilość mieszkań</td>
			<td><input type="text" name="iloscMieszkan"/></td>
		</tr><tr>
			<td>Czy uzwrotniony</td>
			<td><input type="text" name="uzwrotniony"/></td>
		</tr><tr>
			<td>Typ sieci</td>
			<td><input type="text" name="typSieci"/></td>
		</tr><tr>
			<td>Zmodernizowany</td>
			<td><input type="text" name="zmodernizowany"/></td>
		</tr><tr>
			<td>Nie zmodernizowany</td>
			<td><input type="text" name="nieZmodernizowany"/></td>
		</tr><tr>
			<td>Potrzeba modernizacji</td>
			<td><input type="text" name="potrzebaModernizacji"/></td>
		</tr><tr>
			<td>Zgoda</td>
			<td><input type="text" name="zgoda"/></td>
		</tr><tr>
			<td>Ilość kondygnacji</td>
			<td><input type="text" name="iloscKondygnacji"/></td>
		</tr><tr>
			<td>Ilość pionów</td>
			<td><input type="text" name="iloscPionow"/></td>
		</tr><tr>
			<td>Ilość HP</td>
			<td><input type="text" name="iloscHP"/></td>
		</tr><tr>
			<td>Przyłącze elektryczne</td>
			<td><input type="text" name="przylaczeElektryczne"/></td>
		</tr><tr>
			<td>Wejścia do budynku</td>
			<td><input type="text" name="wejsciaDoBudynku"/></td>
		</tr><tr>
			<td>Typ wzmacniacza</td>
			<td><input type="text" name="typWzmacniacza"/></td>
		</tr><tr>
			<td>Numer seryjny</td>
			<td><input type="text" name="nrSeryjny"/></td>
		</tr><tr>
			<td>Producent</td>
			<td><input type="text" name="producent"/></td>
		</tr><tr>
			<td>Pobór mocy</td>
			<td><input type="text" name="poborMocy"/></td>
		</tr><tr>
			<td>f - górne</td>
			<td><input type="text" name="czestotliwoscGorna"/></td>
		</tr><tr>
			<td>Lokalizacja wzmacniacza</td>
			<td><input type="text" name="lokalizacjaWzmacniacza"/></td>
		</tr><tr>
			<td>Sygnał na wejściu</td>
			<td><input type="text" name="sygnalWE"/></td>
		</tr><tr>
			<td>Sygnał na pierwszym WZM</td>
			<td><input type="text" name="sygnalPierwszyWzm"/></td>
		</tr><tr>
			<td>DS wzmoc</td>
			<td><input type="text" name="dsWzmoc"/></td>
		</tr><tr>
			<td>DS korek</td>
			<td><input type="text" name="dsKorek"/></td>
		</tr><tr>
			<td>DS wzmacniacz</td>
			<td><input type="text" name="dsWzmacniacz"/></td>
		</tr><tr>
			<td>KZ korekcja</td>
			<td><input type="text" name="kzKorekcja"/></td>
		</tr><tr>
			<td>KZ wzmocnienie</td>
			<td><input type="text" name="kzWzmocnienie"/></td>
		</tr><tr>
			<td>Gein</td>
			<td><input type="text" name="gein"/></td>
		</tr><tr>
			<td>Doprowadzenie z kanalizacji</td>
			<td><input type="text" name="doprowadzenie"/></td>
		</tr><tr>
			<td>Dojście do budynku</td>
			<td><input type="text" name="dojscieDobudynku"/></td>
		</tr><tr>
			<td>RG do WZM</td>
			<td><input type="text" name="rgDoWzm"/></td>
		</tr><tr>
			<td>Do środka budynku</td>
			<td><input type="text" name="doSrodka"/></td>
		</tr><tr>
			<td>Zakres modernizacji piwnica</td>
			<td><input type="text" name="zakresModernizacjiPiwnica"/></td>
		</tr><tr>
			<td>Zakres modernizacji piony</td>
			<td><input type="text" name="zakresModernizacjiPiony"/></td>
		</tr><tr>
			<td>Projekt</td>
			<td><input type="text" name="projekt"/></td>
		</tr><tr>
			<td>Data wystąpienia</td>
			<td><input type="text" name="dataWystapienia"/></td>
		</tr><tr>
			<td>Data otrzymania</td>
			<td><input type="text" name="dataOtrzymania"/></td>
		</tr><tr>
			<td>Wykonawca</td>
			<td><input type="text" name="wykonawca"/></td>
		</tr><tr>
			<td>NR zlecenia</td>
			<td><input type="text" name="nrZlecenia"/></td>
		</tr><tr>
			<td>Data zlecenia</td>
			<td><input type="text" name="dataZlecenia"/></td>
		</tr><tr>
			<td>Planowany odbior</td>
			<td><input type="text" name="planowanyOdbior"/></td>
		</tr><tr>
			<td>Odbior rzeczywisty</td>
			<td><input type="text" name="odbiorRzeczywisty"/></td>
		</tr><tr>
			<td>Ilosc HP w zleceniu</td>
			<td><input type="text" name="hpWZleceniu"/></td>
		</tr><tr>
			<td>mb Dystr. z Kan. TP do budynku</td>
			<td><input type="text" name="dystrybucjaKanalBudynek"/></td>
		</tr><tr>
			<td>mb DYSTR. W budynku</td>
			<td><input type="text" name="dystrybucjaBudynek"/></td>
		</tr><tr>
			<td>mb Dystr Razem</td>
			<td><input type="text" name="dystrybucjaRazem"/></td>
		</tr><tr>
			<td>mb MAGISTRALI</td>
			<td><input type="text" name="magistrala"/></td>
		</tr><tr>
			<td>ilosc gniazd</td>
			<td><input type="text" name="iloscGniazd"/></td>
		</tr><tr>
			<td>ilosc abonentow VECTRA</td>
			<td><input type="text" name="iloscAbVectra"/></td>
		</tr><tr>
			<td>ilosc abonentow pakietu socjalnego</td>
			<td><input type="text" name="iloscAbSocjal"/></td>
		</tr><tr>
			<td>ilosc abonentow pakietu socjalnego fakturowanego adm</td>
			<td><input type="text" name="iloscAbSocjalAdm"/></td>
			<td width="150px">cena</td>
			<td><input type="text" name="cenaAbSocjalAdm"/></td>
		</tr><tr>
			<td>Zawartosc pakietu 1 Vectry</td>
			<td><input type="text" name="zawartoscPakietu"/></td>
		</tr><tr>
			<td>Ilosc abonentow konkurencji</td>
			<td><input type="text" name="ilecoAbKonkurent"/></td>
		</tr><tr>
			<td>Pakiet konkurencji - cena</td>
			<td><input type="text" name="cenaPakietuKonkurent"/></td>
			<td>Pakiet konkurencji - zawartosc</td>
			<td><input type="text" name="zawartoscPakietuKonkurent"/></td>
		</tr><tr>
			<td>Uwagi</td>
			<td><input type="text" name="uwagi"/></td>
		</tr><tr>
			<td/><td><input type="button" name="save" value="zapisz" onclick="submitForm('save')"/></td>
		</tr>
		
		
	</table>
	
	</form>

</body>	
</html>
</jsp:root>