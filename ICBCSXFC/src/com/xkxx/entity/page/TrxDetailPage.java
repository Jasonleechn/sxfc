package com.xkxx.entity.page;

/**
 * ������ϸ��ҳ
 * @author sxfh-jcz3
 *
 */
public class TrxDetailPage extends Page{
	private String devid;//Ͷעվ����
	private String username;//�û���
	private String amt;//�ɷѽ��
	private String lsh;//�ɷ���ˮ��
	private String date8;//�ɷ�����
	private String trx_flag;//�ɷ�״̬
	private String type;//��������
	private String paycardno;//�����˺�
	private String beginDate;//��ʼ����
	private String endDate;//��ֹ����
	
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
