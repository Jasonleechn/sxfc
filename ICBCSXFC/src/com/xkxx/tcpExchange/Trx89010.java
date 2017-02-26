package com.xkxx.tcpExchange;

import com.icbc.cosp.hrpub.util.HrStringUtil;
import com.xkxx.util.FcUtils;

/**
 * 校验用户的注册机号
 * 通过注册号发往福彩中心，福彩返回注册人姓名和注册地址
 * @author sxfh-jcz3
 *
 */
public class Trx89010 extends TrxNode{
	
	private final int USERNAME = 7;//福彩返回的姓名
	private final int USERADDR = 8;//福彩返回的注册地址
	//private final int CORP_RETCODE = 3;//第三方通讯返回码
	//private final int CORP_RETMSG = 4;//第三方通讯返回信息
	
	private String zcname;//注册姓名
	private String zcaddr;//注册地址
	
	public String getZcname() {
		return zcname;
	}
	public void setZcname(String zcname) {
		this.zcname = zcname;
	}
	public String getZcaddr() {
		return zcaddr;
	}
	public void setZcaddr(String zcaddr) {
		this.zcaddr = zcaddr;
	}
	
	/**
	 * 返回报文：
	 * 1.如果与GTCG通讯失败则返回为空
	 * 2.如果GTCG与主机或者第三方失败则必定有返回码和返回信息
	 * 3.如果返回码全为0，则进行可以进行下一步拆解
	 * */
	public Trx89010 splitResponseString(String revString){
		Trx89010 obj = new Trx89010();
		if("".equals(revString) || revString.length()==0){
			obj.setRetCode("");
			obj.setRetMsg("通讯异常!");
		}else{
			String[] respArr = HrStringUtil.split(revString, '|');
			obj.retInfo(respArr[RETCODE], respArr[RETMSG]);
			if(FcUtils.isAllChar0(respArr[RETCODE])){
				//设置返回信息
				obj.setZcname(respArr[USERNAME]);
				obj.setZcaddr(respArr[USERADDR]);
			}
		}
		return obj;
	}
	
	//处理返回码和返回信息
	private void retInfo(String retCode,String retMsg){
		//只需判断GTCG的返回码和返回信息，第三方的返回码和返回信息会更新到第一个和第二个返回字段中
		this.setRetCode(retCode);
		this.setRetMsg(retMsg);
	}
	

}
