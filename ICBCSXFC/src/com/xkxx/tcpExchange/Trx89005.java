package com.xkxx.tcpExchange;

import com.icbc.cosp.hrpub.util.HrStringUtil;
import com.xkxx.util.FcUtils;

/***
 * 缴费状态查询
 * @author sxfh-jcz3
 *
 */
public class Trx89005 extends TrxNode{
	
	///////////////////////////返回报文字段位置/////////////////////////////////////////////////
	protected final int RECNUM = 2;
	protected final int TOTALNUM = 3;
	protected final int SERIALNO = 4;
	protected final int WORKDATE = 5;
	protected final int WORKTIME = 6;
	protected final int UNITNO = 9;//单位代码('0'—电脑彩票,'1'—网点即开票)
	protected final int ACCNO = 18;
	protected final int USERNO = 21;
	protected final int USERNAME = 23;
	protected final int AMOUNT = 29;
	protected final int BANKTRADESTATUS = 33;//银行交易状态:0-成功 1-失败 2-可疑 3-已冲正 9-批量
	protected final int NOTE1 = 35;//融E联发报流水号
	
	///////////////////////////请求字段/////////////////////////////////////////////////
	private String subproctype = "1";//--查询方式(1:首次,2:上页,3:下页)
	private String findstartdate ;//--查询起始日期
	private String findenddate ;//--查询截止日期
	private String zoneno = "00502";//--查询地区号
	private String accno ;//--银行卡号
	private String userno ;//--投注站编号
	private String amount ;//--交易金额, 单位: 分
	private String agentcode = "0023";//--业务代码, 固定'0023'
	private String agentflag = "01";//--业务标志, 固定'01'
	private String unitno;//--0:电脑彩票;1:网点即开票
	private String chantype = "410";//--渠道种类, 固定'410'
	private String revtranf = "0";//--正反交易标志, 固定'0'
	private String note1 ;//--Note1字段, 融E联发报流水号
	
	//////////////////////////返回字段/////////////////////////////////////////////////////////
	private String resp_recnum;
	private String resp_totalnum;
	private String resp_serialno;
	private String resp_workdate;
	private String resp_worktime;
	private String resp_unitno;
	private String resp_accno;
	private String resp_userno;
	private String resp_username;
	private String resp_amount;
	private String resp_banktradestatus;
	private String resp_note1;
	
	public String getResp_recnum() {return resp_recnum;}
	public String getResp_totalnum() {return resp_totalnum;}
	public String getResp_serialno() {return resp_serialno;}
	public String getResp_workdate() {return resp_workdate;}
	public String getResp_worktime() {return resp_worktime;}
	public String getResp_unitno() {return resp_unitno;}
	public String getResp_accno() {return resp_accno;}
	public String getResp_userno() {return resp_userno;}
	public String getResp_username() {return resp_username;}
	public String getResp_amount() {return resp_amount;}
	public String getResp_banktradestatus() {return resp_banktradestatus;}
	public String getResp_note1() {return resp_note1;}
	
	public void setResp_recnum(String respRecnum) {resp_recnum = respRecnum;}
	public void setResp_totalnum(String respTotalnum) {resp_totalnum = respTotalnum;}
	public void setResp_serialno(String respSerialno) {resp_serialno = respSerialno;}
	public void setResp_workdate(String respWorkdate) {resp_workdate = respWorkdate;}
	public void setResp_worktime(String respWorktime) {resp_worktime = respWorktime;}
	public void setResp_unitno(String respUnitno) {resp_unitno = respUnitno;}
	public void setResp_accno(String respAccno) {resp_accno = respAccno;}
	public void setResp_userno(String respUserno) {resp_userno = respUserno;}
	public void setResp_username(String respUsername) {resp_username = respUsername;}
	public void setResp_amount(String respAmount) {resp_amount = respAmount;}
	public void setResp_banktradestatus(String respBanktradestatus) {resp_banktradestatus = respBanktradestatus;}
	public void setResp_note1(String respNote1) {resp_note1 = respNote1;}
	
	public String getSubproctype() {return subproctype;}
	public String getFindstartdate() {return findstartdate;}
	public String getFindenddate() {return findenddate;}
	public String getZoneno() {return zoneno;}
	public String getAccno() {return accno;}
	public String getUserno() {return userno;}
	public String getAmount() {return amount;}
	public String getAgentcode() {return agentcode;}
	public String getAgentflag() {return agentflag;}
	public String getChantype() {return chantype;}
	public String getRevtranf() {return revtranf;}
	public String getNote1() {return note1;}
	public String getUnitno() {return unitno;}
	
	public void setUnitno(String unitno) {this.unitno = unitno;}
	public void setFindstartdate(String findstartdate) {this.findstartdate = findstartdate;}
	public void setFindenddate(String findenddate) {this.findenddate = findenddate;}
	public void setAccno(String accno) {this.accno = accno;}
	public void setUserno(String userno) {this.userno = userno;}
	public void setAmount(String amount) {this.amount = amount;}
	public void setAgentcode(String agentcode) {
		if("".equals(agentcode)||agentcode.length()==0){
			agentcode = "0023";//业务代码
		}
		this.agentcode = agentcode;
	}
	public void setRevtranf(String revtranf) {
		this.revtranf = revtranf;//正反交易标志
	}
	public void setSubproctype(String subproctype) {this.subproctype = subproctype;}
	public void setZoneno(String zoneno) {this.zoneno = zoneno;}
	public void setAgentflag(String agentflag) {this.agentflag = agentflag;}
	public void setChantype(String chantype) {this.chantype = chantype;}
	public void setNote1(String note1) {this.note1 = note1;}
	
	/**发送89005交易的报文*/
	public String req89005() {
		//初始化默认参数
		this.subproctype = "1";//查询方式(1:首次,2:上页,3:下页)
		this.zoneno = "00502";//地区号
		this.agentflag = "01";//业务标志
		this.chantype = "410";//渠道种类
		this.agentcode = "0023";//--业务代码
		StringBuffer sdata = new StringBuffer();
		sdata.append(this.subproctype).append("|");
		sdata.append(this.findstartdate).append("|");
		sdata.append(this.findenddate).append("|");
		sdata.append(this.zoneno).append("|");
		sdata.append("").append("|");//brno
		sdata.append("").append("|");//tellerno
		sdata.append("").append("|");//SERIALNO
		sdata.append(this.accno).append("|");
		sdata.append("").append("|");//username
		sdata.append(this.userno).append("|");
		sdata.append("").append("|");//SUBUSERNO
		sdata.append(this.amount).append("|");
		sdata.append(this.agentcode).append("|");
		sdata.append(this.agentflag).append("|");
		sdata.append(this.unitno).append("|");
		sdata.append(this.chantype).append("|");
		sdata.append(this.revtranf).append("|");
		sdata.append("").append("|");//BANKTRADESTATUS
		sdata.append("").append("|");//CORPTRADESTATUS
		sdata.append(this.note1).append("|");
		sdata.append("").append("|");//note2
		sdata.append("").append("|");
		sdata.append("").append("|");
		sdata.append("").append("|");
		sdata.append("").append("|");
		sdata.append("").append("|");
		sdata.append("").append("|");
		sdata.append("").append("|");
		sdata.append("").append("|");//note10
		
		return sdata.toString();
	}
	
	/**无参构造器，发送报文时使用*/
	public Trx89005() {}
	
	/**拆解返回报文*/
	public Trx89005 splitResponseString(String revString) {
		Trx89005 obj = new Trx89005();
		if("".equals(revString) || revString.length()==0){
			obj.setRetCode("");
			obj.setRetMsg("通讯异常!");
		}
		String[] responseArr = HrStringUtil.split(revString, '|');
		obj.setRetCode(responseArr[RETCODE]);
		obj.setRetMsg(responseArr[RETMSG]);
		if(FcUtils.isAllChar0(responseArr[RETCODE])){
			//只有返回成功时才要去拆解返回条数
			obj.resp_recnum = responseArr[RECNUM];
			obj.resp_totalnum = responseArr[TOTALNUM];
			if(Integer.parseInt(obj.resp_totalnum)>0){
				obj.resp_serialno = responseArr[SERIALNO];
				obj.resp_workdate = responseArr[WORKDATE];
				obj.resp_worktime = responseArr[WORKTIME];
				obj.resp_unitno = responseArr[UNITNO];
				obj.resp_accno = responseArr[ACCNO];
				obj.resp_userno = responseArr[USERNO];
				obj.resp_username = responseArr[USERNAME];
				obj.resp_amount = responseArr[AMOUNT];
				obj.resp_banktradestatus = responseArr[BANKTRADESTATUS];
				obj.resp_note1 = responseArr[NOTE1];
			}
		}
		return obj;
	}

}
