package com.xkxx.tcpExchange;

/**
 * 交易公用属性
 * @author sxfh-jcz3
 *
 */
public class TrxNode {
	
	protected final int RETCODE = 0;//返回码
	protected final int RETMSG = 1;//返回信息
	
	private String RetCode;//交易返回码
	private String RetMsg;//交易返回信息
	
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
