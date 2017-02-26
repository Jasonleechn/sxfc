package com.xkxx.util;

public class FcUtils {
	
	/**是否任意个0的返回码*/
	public static boolean isAllChar0(String code){ 
		if (code==null||code.trim().length()==0){
			return false;
		}
		String str = code.trim();
		for (int i=0; i< str.length(); i++){
			if (str.charAt(i)!='0'){
				return false;
			}
		}
		return true;
	}
	

}
