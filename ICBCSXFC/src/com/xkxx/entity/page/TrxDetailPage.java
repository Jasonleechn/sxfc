package com.xkxx.entity.page;

/**
 * 交易明细分页
 * @author sxfh-jcz3
 *
 */
public class TrxDetailPage extends Page{
	private String devid;//投注站机号
	private String username;//用户名
	private String amt;//缴费金额
	private String lsh;//缴费流水号
	private String date8;//缴费日期
	private String trx_flag;//缴费状态
	private String type;//福彩类型
	private String paycardno;//付款账号
	private String beginDate;//开始日期
	private String endDate;//截止日期
	
	public String getDevid() {return devid;}
	public String getUsername() {return username;}
	public String getAmt() {return amt;}
	public String getLsh() {return lsh;}
	public String getDate8() {return date8;}
	public String getTrx_flag() {return trx_flag;}
	public String getType() {return type;}
	public String getPaycardno() {return paycardno;}
	public String getBeginDate() {return beginDate;}
	public String getEndDate() {return endDate;}
	
	public void setDevid(String devid) {this.devid = devid;}
	public void setUsername(String username) {this.username = username;}
	public void setAmt(String amt) {this.amt = amt;}
	public void setLsh(String lsh) {this.lsh = lsh;}
	public void setDate8(String date8) {this.date8 = date8;}
	public void setTrx_flag(String trxFlag) {trx_flag = trxFlag;}
	public void setType(String type) {this.type = type;}
	public void setPaycardno(String paycardno) {this.paycardno = paycardno;}
	public void setBeginDate(String beginDate) {this.beginDate = beginDate;}
	public void setEndDate(String endDate) {this.endDate = endDate;}
	
}
