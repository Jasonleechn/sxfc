package com.xkxx.tcpExchange;

import com.icbc.cosp.hrpub.util.HrStringUtil;
import com.xkxx.util.FcUtils;

/**
 * 查询银行卡账户信息
 * @author sxfh-jcz3
 *
 */
public class Trx89007 extends TrxNode{
	
	public static final int BIND_SUCCESS = 1;//绑定成功
	public static final int BIND_FAIL = 0;//绑定失败
	
	private final int NAME = 2;//姓名
//	private final int IDTYPE = 3;//证件类型
	private final int IDCODE = 4;//证件号码
//	private final int BALANCE = 5;//余额
//	private final int CUSTNO = 6;//客户编号
	
	private String cname;//客户姓名
	private String idcard;//证件号码
	
	public String getCname() {return cname;}
	public String getIdcard() {return idcard;}
	
	public void setCname(String cname) {this.cname = cname;}
	public void setIdcard(String idcard) {this.idcard = idcard;}

	public Trx89007(){}
	
	/**拆解报文*/
	public Trx89007(String revString){
		if("".equals(revString) || revString.length()==0){
			this.setRetCode("");
			this.setRetMsg("通讯异常!");
		}
		String[] responseArr = HrStringUtil.split(revString, '|');
		this.setRetCode(responseArr[RETCODE]);
		this.setRetMsg(responseArr[RETMSG]);
		if(FcUtils.isAllChar0(responseArr[RETCODE])){
			this.setCname(responseArr[NAME]);
			this.setIdcard(responseArr[IDCODE]);
		}
	}

}
