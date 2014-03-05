package org.arttel.util;

import java.text.*;

public class DecimalWriter {

	public static String getDecimalSpoken(final String kwota) {
		
		final Double dkwota = Translator.parseDoubleIfNotNull(kwota);
		return getValueStatement(dkwota);
	}

	// Tblica opisów wartoœci jednostek.
	static String[] Units = { "zero", "jeden", "dwa", "trzy", "cztery", "piêæ",
			"szeœæ", "siedem", "osiem", "dziewiêæ", "dziesiêæ", "jedenaœcie",
			"dwanaœcie", "trzynaœcie", "czternaœcie", "piêtnaœcie",
			"szesnaœcie", "siedemnaœcie", "osiemnaœcie", "dziewiêtnaœcie" };

	// Tablica opisów dziesi¹tek
	static String[] Tens = { "dwadzieœcia", "trzydzieœci", "czterdzieœci",
			"piêædziesi¹t", "szeœædziesi¹t", "siedemdziesi¹t", "osiemdziesi¹t",
			"dziewiêædziesi¹t" };

	// Tablica obisów setek
	static String[] Hundreds = { "", "sto", "dwieœcie", "trzysta", "czterysta",
			"piêæset", "szeœæset", "siedemset", "osiemset", "dziewiêæset" };

	// Dwuwymiarowa tablica tysiêcy,milionów,miliarów ...
	static String[][] OtherUnits = { { "tysi¹c", "tysi¹ce", "tysiêcy" },
			{ "milion", "miliony", "milionów" },
			{ "miliard", "miliardy", "miliardów" } };

	// Konwersja ma³ych liczb ....
	static String SmallValueToWords(int n) {
		if (n == 0) {
			return null;
		}

		StringBuilder valueInWords = new StringBuilder();

		// Konwertuj setki.

		int temp = n / 100;
		if (temp > 0) {
			valueInWords.append(Hundreds[temp]);
			n -= temp * 100;
		}

		// Konwertuj dziesi¹tki i jednoœci.

		if (n > 0) {
			if (valueInWords.length() > 0) {
				valueInWords.append(" ");
			}

			if (n < 20) {
				// Liczby poni¿ej 20 przekonwertuj na podstawie
				// tablicy jednoœci.

				valueInWords.append(Units[n]);
			} else {
				// Wiêksze liczby przekonwertuj ³¹cz¹c nazwy
				// krotnoœci dziesi¹tek z nazwami jednoœci.
				valueInWords.append(Tens[(n / 10) - 2]);
				int lastDigit = n % 10;

				if (lastDigit > 0) {
					valueInWords.append(" ");
					valueInWords.append(Units[lastDigit]);
				}
			}
		}
		return valueInWords.toString();
	}

	// Obliczenia dla du¿ych liczb ... i odmiana prawid³owa ich wartoœci..
	static int GetBigUnitIndex(long n) {
		int lastDigit = (int) n % 10;

		if ((n >= 10 && (n <= 20 || lastDigit == 0)) || (lastDigit > 4)) {
			return 2;
		}
		return (lastDigit == 1) ? 0 : 1;
	}

	static long ToWords(StringBuilder valueInWords, long n, int level) {
		int smallValue = 0;
		// long divisor = (long)Math.pow(10000, (long)level + 1);
		long divisor = (long) Math.pow(1000, (long) level + 1);

		if (divisor <= n) {
			// Jeœli liczbê da siê podzieliæ przez najbli¿sz¹
			// potêgê 1000, kontynuuj rekurencjê.

			n = ToWords(valueInWords, n, level + 1);
			smallValue = (int) (n / divisor);

			if (valueInWords.length() > 0) {
				valueInWords.append(" ");
			}

			if (smallValue > 1) {
				valueInWords.append(SmallValueToWords(smallValue));
				valueInWords.append(" ");
			}
			valueInWords.append(OtherUnits[level][GetBigUnitIndex(smallValue)]);
		}

		return n - smallValue * divisor;
	}

	static String ToWords(long value) {
		if (value == 0) {
			// Zero.
			return Units[0];
		}
		StringBuilder valueInWords = new StringBuilder();
		int smallValue = (int) ToWords(valueInWords, value, 0);
		if (smallValue > 0) {
			if (valueInWords.length() > 0) {
				valueInWords.append(" ");
			}
			valueInWords.append(SmallValueToWords(smallValue));
		}
		return valueInWords.toString();
	}

	static long liczba_zlotych(double kwota) {

		String kwotaString = "" + kwota;
		kwotaString = kwotaString.substring(0, kwotaString.indexOf("."));
		Long dzlote = new Long(kwotaString);
		return (long) dzlote;
	}

	static long liczba_groszy(double grosze) {
		// Tworzê format zmiennych aby uzyskaæ liczbê w frmie tekstowej
		DecimalFormat dfx = new DecimalFormat("0.00");
		String szlote = dfx.format(grosze);
		// Odcinam grosze
		String bgzlote = (String) szlote.substring(0, szlote.length() - 3);
		Double dzlote = new Double(bgzlote);
		// Od kowty z groszami odejmujê kwotê bez.
		Long groszy = (long) (grosze * 100 - dzlote * 100);
		return groszy;
	}

	static String getValueStatement(final Double amount) {
		if(amount == null){
			return "";
		} else {	
			final Double abs = Math.abs(amount);
			return minusHandled(ToWords(liczba_zlotych(abs)) + " z³, "
						+ ToWords(liczba_groszy(abs)) + " gr.", amount);
		}
	}

	private static String minusHandled(final String amountStatement, final Double amount) {
		if(amount.compareTo(0.0) < 0){
			return "minus ".concat(amountStatement);
		} else {
			return amountStatement;
		}
	}
}