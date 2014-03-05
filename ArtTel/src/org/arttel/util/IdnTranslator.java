package org.arttel.util;

import java.util.HashMap;
import java.util.Map;

public class IdnTranslator {

	static Map<Character, Character> polishLettersMap;
	static {
		polishLettersMap = new HashMap<Character, Character>();
		polishLettersMap.put(' ', '_');
		polishLettersMap.put('¹', 'a');
		polishLettersMap.put('ê', 'a');
		polishLettersMap.put('ó', 'o');
		polishLettersMap.put('œ', 's');
		polishLettersMap.put('³', 'l');
		polishLettersMap.put('¿', 'z');
		polishLettersMap.put('Ÿ', 'z');
		polishLettersMap.put('æ', 'c');
		polishLettersMap.put('ñ', 'n');
		
	}
	
	private  boolean isNormalLetter(int chVal) {
		return (chVal >= 65 && chVal <= 90) || (chVal >= 97 && chVal <= 122);
	}
	
	private  String substitutePolishLettersWithNormal(String org) {
		StringBuilder sb = new StringBuilder();
		for (char ch: org.toCharArray()) {
			if (Character.isLetter(ch)) {
				sb.append(isNormalLetter(ch)? ch : polishLettersMap.get(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	
	public String convertNameToIdn(String value) {
		final String nameLowerCase = value.toLowerCase();
		return substitutePolishLettersWithNormal(nameLowerCase);
	}
	
}
