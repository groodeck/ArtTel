package org.arttel.util;

import java.math.BigDecimal;


public class WebUtils {

	public static String getDownstreamLevelColor(final BigDecimal signalLevel){
		if(signalLevel == null){
			return "";
		} else {
			return getDownstreamLevelColor(signalLevel.toPlainString());
		}
	}

	public static String getDownstreamLevelColor(final String signalLevelStr){
		String color = "inherit";
		if(signalLevelStr != null && !"".equals(signalLevelStr)){
			final double signalLevel = Double.parseDouble(signalLevelStr);
			if(signalLevel >= -6.0 && signalLevel <= 10.0 ){
				color = "#009900";
			} else if(signalLevel >= -6.1 && signalLevel <= -10.0
					|| signalLevel >= 10.1 && signalLevel <= 15.0) {
				color = "#FFCC00";
			} else {
				color = "red";
			}
		}
		return color;
	}

	public static String getUpstreamLevelColor(final BigDecimal signalLevel){
		if(signalLevel == null){
			return "";
		} else {
			return getUpstreamLevelColor(signalLevel.toPlainString());
		}
	}

	public static String getUpstreamLevelColor(final String signalLevelStr){
		String color = "inherit";
		if(signalLevelStr != null && !"".equals(signalLevelStr)){
			final double signalLevel = Double.parseDouble(signalLevelStr);
			if(signalLevel >= 43.0 && signalLevel <= 51.0 ){
				color = "#009900";
			} else if(signalLevel >= 40.9 && signalLevel <= 42.9
					|| signalLevel >= 51.1 && signalLevel <= 52.9) {
				color = "#FFCC00";
			} else  {
				color = "red";
			}
		}
		return color;
	}

	public static boolean isAdmin(final String userName){
		return userName.equals(ADMIN_USER);
	}

	private static final Object ADMIN_USER = "leszek";

	private WebUtils(){}
}
