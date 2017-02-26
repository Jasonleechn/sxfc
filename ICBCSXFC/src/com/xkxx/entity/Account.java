package com.xkxx.entity;

/**
 * 福彩投注站档案管理类
 * @author sxfh-jcz3
 *
 */
public class Account {
	
	private String devid;//投注机号
	private String accountno;//银行账号
	private String personid;//身份证号
	private String zoneno;//地区号
	private String date8;//启用时间
	private String openid;//融e联个人ID
	private String note1;//备注1
	private String note2;//备注2
	private String note3;//备注3
	private String pwd;//登录密码
	private String username;//用户姓名
	private String type;//福彩类型
	private String devidaddr;//福彩投注站地址
	private String custid;//客户编号
	private String telphone;//联系电话
	
	public String getDevid() {return devid;}
	public String getAccountno() {return accountno;}
	public String getPersonid() {return personid;}
	public String getZoneno() {return zoneno;}
	public String getDate8() {return date8;}
	public String getOpenid() {return openid;}
	public String getNote1() {return note1;}
	public String getNote2() {return note2;}
	public String getNote3() {return note3;}
	public String getPwd() {return pwd;}
	public String getUsername() {return username;}
	public String getType() {return type;}
	public String getCustid() {return custid;}
	public String getDevidaddr() {return devidaddr;}
	public String getTelphone() {return telphone;}
	
	public void setTelphone(String telphone) {this.telphone = telphone;}
	public void setDevidaddr(String devidaddr) {this.devidaddr = devidaddr;}
	public void setCustid(String custid) {this.custid = custid;}
	public void setType(String type) {this.type = type;}
	public void setDevid(String devid) {this.devid = devid;}
	public void setAccountno(String accountno) {this.accountno = accountno;}
	public void setPersonid(String personid) {this.personid = personid;}
	public void setZoneno(String zoneno) {this.zoneno = zoneno;}
	public void setDate8(String date8) {this.date8 = date8;}
	public void setOpenid(String openid) {this.openid = openid;}
	public void setNote1(String note1) {this.note1 = note1;}
	public void setNote2(String note2) {this.note2 = note2;}
	public void setNote3(String note3) {this.note3 = note3;}
	public void setPwd(String pwd) {this.pwd = pwd;}
	public void setUsername(String username) {this.username = username;}
	
	@Override
	public String toString() {
		return "Account [devid=" + devid + ", devidAddr=" + devidaddr
				+ ", personid=" + personid + ", username=" + username + "]";
	}
	
	/**获取彩票名称**/
	public static String getTypeCH(String type){
		if("".equals(type)){
			return "彩票类型不能为空";
		}else if("0".equals(type)){
			return "电脑型彩票";
		}else if("1".equals(type)){
			return "网点即开票";
		}else{
			return "异常的彩票类型";
		}
	}
	
}
