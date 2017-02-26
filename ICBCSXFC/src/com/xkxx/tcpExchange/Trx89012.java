package com.xkxx.tcpExchange;

import com.icbc.cosp.hrpub.util.HrStringUtil;
import com.xkxx.util.FcUtils;

/**
 * 用户手工冲正
 * @author sxfh-jcz3
 */
public class Trx89012 extends TrxNode{
	
	private final int CZSERIALNO = 2;//冲正交易流水号
	private final int WORKDATE = 16;//冲正主机返回时间
	private final int WORKTIME = 17;//冲正主机返回日期
//	private final int CORPRETCODE = 6;//冲正主机返回码
//	private final int CORPRETMSG = 7;//冲正主机返回信息
	
//	private String hostRetCode;//主机返回码
//	private String hostRetMsg;//主机返回信息
	private String CZSerialNo;//交易流水号
	private String workdate;//主机日期
	private String worktime;//主机时间
	
//	public String getHostRetCode() {return hostRetCode;}
//	public String getHostRetMsg() {return hostRetMsg;}
	public String getWorkdate() {return workdate;}
	public String getWorktime() {return worktime;}
	public String getCZSerialNo() {return CZSerialNo;}
    
	public void setCZSerialNo(String cZSerialNo) {CZSerialNo = cZSerialNo;}
//	public void setHostRetCode(String hostRetCode) {this.hostRetCode = hostRetCode;}
//	public void setHostRetMsg(String hostRetMsg) {this.hostRetMsg = hostRetMsg;}
	public void setWorkdate(String workdate) {this.workdate = workdate;}
	public void setWorktime(String worktime) {this.worktime = worktime;}
	
	@Override
	public String toString() {
		return "Trx89012 [CZSerialNo=" + CZSerialNo +  ", workdate="
				+ workdate + ", worktime=" + worktime + "]";
	}
	/**
	 * 返回报文：
	 * 1.如果与GTCG通讯失败则返回为空
	 * 2.如果GTCG与主机或者第三方失败则必定有返回码和返回信息
	 * 3.如果返回码全为0，则进行可以进行下一步拆解
	 * */
	public Trx89012 splitResponseString(String revString){
		Trx89012 obj = new Trx89012();
		//通讯失败
		if(revString.length()==0 || "".equals(revString)){
			obj.setRetCode("");
			obj.setRetMsg("通讯失败!");
		}else{
			String[] respArr = HrStringUtil.split(revString, '|');
			//返回码处理
			obj.retInfo(respArr[RETCODE], respArr[RETMSG]);
			if(FcUtils.isAllChar0(respArr[RETCODE])){
				obj.setCZSerialNo(respArr[CZSERIALNO]);
				obj.setWorkdate(respArr[WORKDATE]);
				obj.setWorktime(respArr[WORKTIME]);
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
