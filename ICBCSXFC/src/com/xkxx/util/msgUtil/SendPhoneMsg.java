package com.xkxx.util.msgUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.xkxx.log.Logger_FC_Web;

public class SendPhoneMsg {
	
	/* 拼短信xml报文，并发送到服务端 */
	public boolean sendToClient(String phoneInfo,String writeMsg,String serverIp,String serverPort, String user, String passwd,int num,Logger_FC_Web logger) throws Exception
	{
		String sendBodyMsg = "";
		String strXMLMsg = "";
		String strXMLLength = "";
		String serialNo = ""; //客户流水号
		
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); //获取系统时间
		serialNo = "0502" + df.format(ca.getTime()) + num;
		
		/*拼短信xml报文*/
		strXMLMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>"
					+ "<request>" 
					  + "<cmdid>1</cmdid>"
					  + "<head>"
					    + "<user>" + user + "</user><pwd>" + passwd + "</pwd>"
					    + "<busstype>B04001</busstype><tempno>NONE</tempno>"
					    + "<sendmode>1</sendmode><sendfrom>XML</sendfrom>"
					    + "<clienttrace>" + serialNo + "</clienttrace><prioritylevel>4</prioritylevel>"
					    + "<policyno>PIPGW001</policyno><areacode>00502</areacode><appname>"
					    + "</appname><msgtcode>" + "</msgtcode><msglevel>6</msglevel>"
					    + "<addressinfo><address>"
					      + "<attribute><key>phone</key><value>" + phoneInfo + "</value></attribute>"
					      + "<attribute><key>needreport</key><value>N</value></attribute>"
					      + "<attribute><key>contenttype</key><value>15</value></attribute>"
					      + "<attribute><key>isLongSMS</key><value>Y</value></attribute>"
					    + "</address></addressinfo>"
					  + "</head>"
					  + "<body><![CDATA[" + writeMsg + "]]></body>"
					+ "</request>";
		
		/* 字符串中的汉字length长度为1，需要将其转换为字节求整个字符串的长度，结果才正确 */
		strXMLLength = UtilPub.stringLeftPading(6, String.valueOf(strXMLMsg.getBytes().length), 1);
		
		sendBodyMsg = "88888" + strXMLLength + strXMLMsg;
		
		VBiceTrans biceTrans = new VBiceTrans();
		if(biceTrans.sendmsg(sendBodyMsg, serverIp, serverPort))
		{
			String retMsg = biceTrans.getRetmsg();
			
			if(!biceTrans.isSucceed())
			{
				logger.log(phoneInfo + "发送失败！失败原因：" + retMsg);
				return false;
			}
		}
		else
		{
			return false;
		}
		
		return true;
	}

}
