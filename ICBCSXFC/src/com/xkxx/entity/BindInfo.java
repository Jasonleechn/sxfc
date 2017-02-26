package com.xkxx.entity;

/**
 * 投注站编号的绑定信息
 * @author sxfh-jcz3
 *
 */
public class BindInfo {
	
	private String devid;//投注站编号
	private String personid;//身份证号码
	private String cardno;//卡号
	private String date8;//绑定日期
	private String status;//绑定状态
	private String errmsg;//错误信息
	private String note1;//备注
	private String username;//客户姓名
	private String zoneno;//地区号
	private String zonenoname;//地区名称
	
	public String getDevid() {return devid;}
	public String getPersonid() {return personid;}
	public String getCardno() {return cardno;}
	public String getDate8() {return date8;}
	public String getStatus() {return status;}
	public String getErrmsg() {return errmsg;}
	public String getNote1() {return note1;}
	public String getUsername() {return username;}
	public String getZoneno() {return zoneno;}
	public String getZonenoname() {return zonenoname;}
	
	public void setDevid(String devid) {this.devid = devid;}
	public void setPersonid(String personid) {this.personid = personid;}
	public void setCardno(String cardno) {this.cardno = cardno;}
	public void setDate8(String date8) {this.date8 = date8;}
	public void setStatus(String status) {this.status = status;}
	public void setErrmsg(String errmsg) {this.errmsg = errmsg;}
	public void setNote1(String note1) {this.note1 = note1;}
	public void setUsername(String username) {this.username = username;}
	public void setZoneno(String zoneno) {this.zoneno = zoneno;}
	public void setZonenoname(String zonenoname) {this.zonenoname = zonenoname;}
	
}
