package com.xkxx.tcpExchange;

/**
 * ���׹�������
 * @author sxfh-jcz3
 *
 */
public class TrxNode {
	
	protected final int RETCODE = 0;//������
	protected final int RETMSG = 1;//������Ϣ
	
	private String RetCode;//���׷�����
	private String RetMsg;//���׷�����Ϣ
	
	public String getRetCode() {
		return RetCode;
	}
	public void setRetCode(String retCode) {
		RetCode = retCode;
	}
	public String getRetMsg() {
		return RetMsg;
	}
	public void setRetMsg(String retMsg) {
		RetMsg = retMsg;
	}
	
}
