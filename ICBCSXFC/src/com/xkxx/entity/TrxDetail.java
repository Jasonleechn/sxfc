package com.xkxx.entity;
/**
 * ���ʽ�����ϸ
 * @author sxfh-jcz3
 *
 */
public class TrxDetail {
	//0-�ɹ� 1-ʧ�� 2-���� 3-�ѳ��� 9-����
	public static final String SUCCESS = "0";//�ɷѳɹ�
	public static final String CHONGZHENG = "3";//����
	public static final String FAIL = "1";//�ɷ�ʧ��
	public static final String UNKNOWN = "2";//״̬δ֪
	
	private String devid;//Ͷעվ���
	private String paycardno;//�����
	private String amt;//������
	private String revcardno;//�տ��
	private String workdate;//�������� yyyyMMdd hh:mm:ss
	private String date8;//8λ����
	private String trx_flag;//����״̬,0:�ɹ���-1��ʧ�ܣ�1������
	private String lsh;//��ˮ��
	private String errmsg;//������Ϣ
	private String username;//�û�����
	private String type;//��Ʊ���͡�0�����Բ�Ʊ��1�����꼴��Ʊ
	private String note1;//����1
	private String note2;//����2
	private String note3;//����3
	private String devidname;//Ͷעվ����
	private String busiserialno;//������ˮ��
	private String czserialno;//����������ˮ��
	
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
