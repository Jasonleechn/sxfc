package com.xkxx.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class TestLog4j {

	private static Logger logger = Logger.getLogger(TestLog4j.class);

	public static void main(String[] args) {
		String conf = "4|2|46500014|1482997822500|10|2016-12-29|15:50:06|3|6222021901014623761|54010icbc|0|0|0|";
		/*logger.debug("调试信息");
		logger.info("普通信息");
		logger.warn("警告信息");
		logger.error("错误信息");
		logger.fatal("致命信息");
		System.out.println(new SimpleDateFormat("yyyyMMdd").format(new Date()));*/
		char[] charArr = conf.toCharArray();
		long checkCode = 0L;
		for(int i=0;i<charArr.length;i++){
			System.out.println((int)(charArr[i]));
			checkCode += (int)(charArr[i]);
		}
		System.out.println(checkCode);
		System.out.println(conf.length());
//		System.out.println(Integer.parseInt("0"));
//		
//		System.out.println("14|2|14010001|2014102023914781|1000000|2014-10-20|16:59:02|0|0|140101113|0|0|0|4899|".length());
		/*List<String> list = new ArrayList<String>();
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list1.add("1");
		list1.add("2");
		list1.add("3");
		list1.add("4");
		list1.add("5");
		list1.add("11");
		list1.add("22");
		list1.add("33");
		list1.add("44");
		list1.add("55");
		list.addAll(list1);
		list.addAll(list2);
		for(int i=0;i<list.size();i++){
			System.out.println("aa:"+list.get(i));
		}*/
		
		
	//	System.out.println(VDate.getCurDate("yyyyMMdd"));
		
		
	}

}
