package com.xkxx.util;

public class AmountUtil {
	
	/**Ԫת��*/
	public static String changeY2F(String amount) {
		// �������, �� ����$�Ľ��
		String currency = amount.replaceAll("\\$|\\��|\\,|\\ ", "");

		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

}
