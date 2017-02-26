package com.xkxx.entity;
/**
 * 福彩交易明细
 * @author sxfh-jcz3
 *
 */
public class TrxDetail {
	//0-成功 1-失败 2-可疑 3-已冲正 9-批量
	public static final String SUCCESS = "0";//缴费成功
	public static final String CHONGZHENG = "3";//冲正
	public static final String FAIL = "1";//缴费失败
	public static final String UNKNOWN = "2";//状态未知
	
	private String devid;//投注站编号
	private String paycardno;//付款卡号
	private String amt;//付款金额
	private String revcardno;//收款卡号
	private String workdate;//交易日期 yyyyMMdd hh:mm:ss
	private String date8;//8位日期
	private String trx_flag;//交易状态,0:成功，-1：失败；1：冲正
	private String lsh;//流水号
	private String errmsg;//错误信息
	private String username;//用户姓名
	private String type;//彩票类型。0：电脑彩票；1：网店即开票
	private String note1;//备用1
	private String note2;//备用2
	private String note3;//备用3
	private String devidname;//投注站名称
	private String busiserialno;//交易流水号
	private String czserialno;//冲正交易流水号
	
	public String getDevid() {return devid;}
	public String getPaycardno() {return paycardno;}
	public String getAmt() {return amt;}
	public String getRevcardno() {return revcardno;}
	public String getWorkdate() {return workdate;}
	public String getDate8() {return date8;}
	public String getTrx_flag() {return trx_flag;}
	public String getLsh() {return lsh;}
	public String getErrmsg() {return errmsg;}
	public String getUsername() {return username;}
	public String getType() {return type;}
	public String getNote1() {return note1;}
	public String getNote2() {return note2;}
	public String getNote3() {return note3;}
	public String getDevidname() {return devidname;}
	public String getBusiserialno() {return busiserialno;}
	public String getCzserialno() {return czserialno;}
	
	public void setDevid(String devid) {this.devid = devid;}
	public void setPaycardno(String paycardno) {this.paycardno = paycardno;}
	public void setAmt(String amt) {this.amt = amt;}
	public void setRevcardno(String revcardno) {this.revcardno = revcardno;}
	public void setWorkdate(String workdate) {this.workdate = workdate;}
	public void setDate8(String date8) {this.date8 = date8;}
	public void setTrx_flag(String trxFlag) {trx_flag = trxFlag;}
	public void setLsh(String lsh) {this.lsh = lsh;}
	public void setErrmsg(String errmsg) {this.errmsg = errmsg;}
	public void setUsername(String username) {this.username = username;}
	public void setType(String type) {this.type = type;}
	public void setNote1(String note1) {this.note1 = note1;}
	public void setNote2(String note2) {this.note2 = note2;}
	public void setNote3(String note3) {this.note3 = note3;}
	public void setDevidname(String devidname) {this.devidname = devidname;}
	public void setBusiserialno(String busiserialno) {this.busiserialno = busiserialno;}
	public void setCzserialno(String czserialno) {this.czserialno = czserialno;}
	@Override
	public String toString() {
		return "TrxDetail [amt=" + amt + ", busiserialno=" + busiserialno
				+ ", date8=" + date8 + ", devid=" + devid + ", devidname="
				+ devidname + ", errmsg=" + errmsg + ", lsh=" + lsh
				+ ", note1=" + note1 + ", note2=" + note2 + ", note3=" + note3
				+ ", paycardno=" + paycardno + ", revcardno=" + revcardno
				+ ", trx_flag=" + trx_flag + ", type=" + type + ", username="
				+ username + ", workdate=" + workdate + "]";
	}
	
	
	
}
