package com.xkxx.entity;

/**
 * ����Ͷעվ����������
 * @author sxfh-jcz3
 *
 */
public class Account {
	
	private String devid;//Ͷע����
	private String accountno;//�����˺�
	private String personid;//���֤��
	private String zoneno;//������
	private String date8;//����ʱ��
	private String openid;//��e������ID
	private String note1;//��ע1
	private String note2;//��ע2
	private String note3;//��ע3
	private String pwd;//��¼����
	private String username;//�û�����
	private String type;//��������
	private String devidaddr;//����Ͷעվ��ַ
	private String custid;//�ͻ����
	private String telphone;//��ϵ�绰
	
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
	
	/**��ȡ��Ʊ����**/
	public static String getTypeCH(String type){
		if("".equals(type)){
			return "��Ʊ���Ͳ���Ϊ��";
		}else if("0".equals(type)){
			return "�����Ͳ�Ʊ";
		}else if("1".equals(type)){
			return "���㼴��Ʊ";
		}else{
			return "�쳣�Ĳ�Ʊ����";
		}
	}
	
}
