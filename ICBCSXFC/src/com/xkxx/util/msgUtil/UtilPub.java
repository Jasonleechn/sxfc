package com.xkxx.util.msgUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UtilPub {
	
    /*
     * �µĲ���ַ����ķ���
     */
	@SuppressWarnings("unchecked")
	public static String[] newToken(String instr, String sep)
            throws Exception
    {

        if (instr == null)
        {
            return null;
        }
        if (sep == null)
        {
            throw new Exception("����ַ����ķָ�������Ϊ��");
        }
        char[] chSingle = sep.toCharArray();
        int startpos = 0;
        ArrayList alist = new ArrayList();
        while (instr.indexOf(sep, startpos) > -1)
        {
            String temp = instr.substring(startpos, instr.indexOf(sep, startpos));
            if (temp == null)
            {
                temp = "";
            }
            alist.add(temp);
            startpos = instr.indexOf(sep, startpos) + chSingle.length;
            if (instr.indexOf(sep, startpos) < 0)
            {
                temp = instr.substring(startpos);
                if (temp == null)
                {
                    temp = "";
                }
                alist.add(temp);
            }
        }

        String[] arr = new String[alist.size()];
        Object[] obarr = alist.toArray();
        System.arraycopy(obarr, 0, arr, 0, alist.size());
        return arr;
    }
    /*
     * ��߲��㷽��
     */
	public static String leftPad(String strSrc,int strSrclength){
		String strtemp="";
		int curLength=strSrc.trim().length();
		if(curLength>=strSrclength){
			return strSrc;
		}
		 for (int i = 0; i < (strSrclength - curLength); i++){
			 strtemp="0"+strtemp;
		 }
		return strtemp+strSrc.trim();
	}
    /*
     * �ұ߲߱��㷽��
     */
	public static String rightPad(String strSrc,int strSrclength){
		String strtemp="";
		int curLength=strSrc.trim().length();
		if(curLength>=strSrclength){
			return strSrc;
		}
		 for (int i = 0; i < (strSrclength - curLength); i++){
			 strtemp="0"+strtemp;
		 }
		return strSrc.trim()+strtemp;
	}
	/**
	 * ����𱨼۲�ѯ������ת��
	 * @param index
	 * @return
	 */
    public static  int converTradeType(int index){
    	switch(index)
    	{
    	case 0:
    		return 1;
    	case 1:
    		return 2;
    	}
		return -1;
    }
    /**
     * �������ʱ��ѯ��������ת��
     * @param index
     * @return
     */
    public static  String converFeeType(int index){
    	switch(index)
    	{
    	case 0:
    		return "2";
    	case 1:
    		return "3";
    	case 2:
    		return "6";
    	}
		return null;
    }
    /**
     * ת������ͨҵ���֤������
     * @param index
     * @return
     */
    public static  String converIDType(int index){
    	switch(index)
    	{
    	case 0:
    		return "000";
    	case 1:
    		return "001";
    	case 2:
    		return "002";
    	case 3:
    		return "003";
    	case 4:
    		return "004";
    	case 5:
    		return "005";
    	case 6:
    		return "006";
    	case 7:
    		return "007";
    	case 8:
    		return "009";
    	case 9:
    		return "012";
    	}
		return null;
    }
    /**
     * ת��������֤������
     * @param index
     * @return
     */
    public static  String converProdIDType(int index){
    	switch(index)
    	{
    	case 0:
    		return "0";
    	case 1:
    		return "1";
    	case 2:
    		return "2";
    	case 3:
    		return "3";
    	case 4:
    		return "4";
    	case 5:
    		return "5";
    	case 6:
    		return "6";
    	case 7:
    		return "7";
    	case 8:
    		return "9";
    	case 9:
    		return "12";
    	}
		return "";
    }
    /**
     * Ʒ�ƽ������������ת��
     * @param index
     * @return
     */
    public static  String converOpeType(int index){
    	switch(index)
    	{
    	case 0:
    		return "4";
    	case 1:
    		return "2";
    	case 2:
    		return "3";
    	}
		return null;
    }
    /**
     * Ʒ�ƽ��������ת��
     * @param index
     * @return
     */
    public static  String converMaterialType(int index){
    	switch(index)
    	{
    	case 0:
    		return "1";
    	case 1:
    		return "2";
    	case 2:
    		return "3";
    	case 3:
    		return "9";
    	}
		return null;
    }
    /**
     * Ʒ�ƽ��������б��ѯ״̬ת��
     * @param index
     * @return
     */
    public static  String converQrystatusType(int index){
    	switch(index)
    	{
    	case 0:
    		return "0";
    	case 1:
    		return "9";
    	case 2:
    		return "11";
    	case 3:
    		return "12";
    	case 4:
    		return "13";
    	case 5:
    		return "14";
    	case 6:
    		return "16";
    	case 7:
    		return "17";
    	case 8:
    		return "18";
    	case 9:
    		return "19";
    	case 10:
    		return "20";
    	}
		return null;
    }
    /**
     * �ж��ַ����Ƿ�Ϊ�������
     * @param paramString
     * @return
     */
    public static boolean isNum(String paramString)
    {
      try
      {
        for (int i = 0; i < paramString.length(); i++)
          if ((paramString.charAt(i) < '0') || (paramString.charAt(i) > '9'))
            return false;
        return true;
      }
      catch (Exception localException)
      {
      }
      return false;
    }
    /**
     * ��ȡϵͳ����"yyyy-MM-dd"
     * @return
     */
    public static String getSysDate(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Date dt=new Date();
    	String date=sdf.format(dt);
    	return date;
    }
    /**
     * �Ѵ��������yyyyMMddת��Ϊ��yyyy-MM-dd��
     * @param date
     * @return
     */
    public static String convertToSysDate(String date){
    	String yearStr=date.substring(0, 4);
    	String monthStr=date.substring(4, 6);
    	String dayStr=date.substring(6, 8);
    	String dateNew=yearStr+"-"+monthStr+"-"+dayStr;
    	return dateNew;
    }
    /**
     * �Ѵ��������yyyy-MM-ddת��ΪyyyyMMdd
     * @param date
     * @return
     */
    public static String convertToSysDate1(String date){
		String yearStr=date.substring(0, 4);
    	String monthStr=date.substring(5, 7);
    	String dayStr=date.substring(8, 10);
    	String dateNew=yearStr+monthStr+dayStr;
    	return dateNew;
    }
    /**
     * ��ȡϵͳ����"yyyyMMdd"
     * @return
     */
    public static String getSysDate1(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
    	Date dt=new Date();
    	String date=sdf.format(dt);
    	return date;
    }
    
    /**
     * ��ȡϵͳ����"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getSysDateAndTime(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date dt=new Date();
    	String date=sdf.format(dt);
    	return date;
    }
    /**
     * ��ȡϵͳ��һ����"yyyyMMdd"
     * @return
     */
    public static String getPreSysDate(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);    //�õ�ǰһ��
		Date date = calendar.getTime();
    	String newdate=sdf.format(date);
    	return newdate;
    }
    public static String getPreSysYear(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);    //�õ�ǰһ��
		Date date = calendar.getTime();
    	String newdate=sdf.format(date);
    	return newdate;
    }
    /**
     * ��ȡϵͳʱ��
     * @return
     */
    public static String getSysTime(){
    	SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
    	Date dt=new Date();
    	String date=sdf.format(dt);
    	return date;
    }

    /**
     * ��ȡ���κ�
     * @return
     */
    public static String getBatchNo(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHH");
    	Date dt=new Date();
    	String date=sdf.format(dt);
    	return date;
    }
    /**
     * ��ȡ��ǰ����
     * @return
     */
    public static String getSysMonth(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
    	Date dt=new Date();
    	String date=sdf.format(dt);
    	return date;
    }
    /**
     * ��ȡϵͳʱ��HHmmss
     * @return
     */
    public static String getSysTime1(){
    	SimpleDateFormat sdf=new SimpleDateFormat("HHmmssSSS");
    	Date dt=new Date();
    	String date=sdf.format(dt);
    	return date;
    }
    public static String getSysYear(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
    	Date dt=new Date();
    	String date=sdf.format(dt);
    	return date;
    }
    /**
     * ��ȡ���������ַ
     * @return
     */
	 public static String getWindowsMACAddress() {      
         String mac = null;      
         BufferedReader bufferedReader = null;      
         Process process = null;      
         try {      
                  
             process = Runtime.getRuntime().exec("ipconfig /all");    
             bufferedReader = new BufferedReader(new InputStreamReader(process      
                     .getInputStream()));      
             String line = null;      
             int index = -1;      
             while ((line = bufferedReader.readLine()) != null) {      
                       
                 index = line.toLowerCase().indexOf("physical address");     
                 if (index != -1) {    
                     index = line.indexOf(":");    
                     if (index != -1) {    
                               
                        mac = line.substring(index + 1).trim();     
                    }    
                     break;      
                 }    
             }    
         } catch (IOException e) {      
             e.printStackTrace();      
         }finally {      
             try {      
                 if (bufferedReader != null) {      
                     bufferedReader.close();      
                   }      
             }catch (IOException e1) {      
                 e1.printStackTrace();      
               }      
             bufferedReader = null;      
             process = null;      
         }      
       
         return mac;      
     }    
	 /**
	  * ��ȡ����ip��ַ
	  * @return
	  */
	 public static String getWindowsAddress(){
		 String address=null;
			try {
				InetAddress addr=InetAddress.getLocalHost();
				address=addr.getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return address;
	 }
    public static String stringLeftPading(int strSrcLength, String strSrc, int flag)
    {
        String strReturn = "";
        String strtemp = "";
        int curLength = strSrc.trim().length();
        if (strSrc != null && curLength > strSrcLength)
        {
            strReturn = strSrc.trim().substring(0, strSrcLength);
        }
        else if (strSrc != null && curLength == strSrcLength)
        {
            strReturn = strSrc.trim();
        }
        else
        {
            if (flag == 1)
            {
                for (int i = 0; i < (strSrcLength - curLength); i++)
                {
                    strtemp = strtemp + "0";
                }
            }
            else if (flag == 2)
            {
                for (int i = 0; i < (strSrcLength - curLength); i++)
                {
                    strtemp = strtemp + " ";
                }
            }
            strReturn = strtemp + strSrc.trim();
        }
        return strReturn;
    }
    public static long getDistDates(String startdate,String enddate) throws ParseException{
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date start=df.parse(startdate);
		Date end=df.parse(enddate);
		long totalDate=0;
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(start);
		long timestart=calendar.getTimeInMillis();
		calendar.setTime(end);
		long timeend=calendar.getTimeInMillis();
		totalDate=Math.abs((timeend-timestart))/(1000*60*60*24);
		return totalDate;
	}


    public static void main(String[] args) throws ParseException {
    	System.out.println("sdff group by".contains("group"));
	}
}


 
