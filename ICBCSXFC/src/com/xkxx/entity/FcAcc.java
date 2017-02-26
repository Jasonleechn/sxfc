package com.xkxx.entity;
/**
 * 山西福彩收款账户信息
 * @author Lee
 *
 */
public class FcAcc {
	private String typename;//彩票类型
	private String accno;//福彩中心收款账号
	private String accname;//福彩中心账户名称
	private String type;//福彩账户类型
	
	public String getTypename() {return typename;}
	public String getAccno() {return accno;}
	public String getAccname() {return accname;}
	public String getType() {return type;}
	
	public void setTypename(String typename) {this.typename = typename;}
	public void setAccno(String accno) {this.accno = accno;}
	public void setAccname(String accname) {this.accname = accname;}
	public void setType(String type) {this.type = type;}
}
