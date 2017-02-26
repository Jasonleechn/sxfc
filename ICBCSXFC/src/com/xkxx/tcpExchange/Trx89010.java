package com.xkxx.tcpExchange;

import com.icbc.cosp.hrpub.util.HrStringUtil;
import com.xkxx.util.FcUtils;

/**
 * У���û���ע�����
 * ͨ��ע��ŷ����������ģ����ʷ���ע����������ע���ַ
 * @author sxfh-jcz3
 *
 */
public class Trx89010 extends TrxNode{
	
	private final int USERNAME = 7;//���ʷ��ص�����
	private final int USERADDR = 8;//���ʷ��ص�ע���ַ
	//private final int CORP_RETCODE = 3;//������ͨѶ������
	//private final int CORP_RETMSG = 4;//������ͨѶ������Ϣ
	
	private String zcname;//ע������
	private String zcaddr;//ע���ַ
	
	public String getZcname() {
		return zcname;
	}
	public void setZcname(String zcname) {
		this.zcname = zcname;
	}
	public String getZcaddr() {
		return zcaddr;
	}
	public void setZcaddr(String zcaddr) {
		this.zcaddr = zcaddr;
	}
	
	/**
	 * ���ر��ģ�
	 * 1.�����GTCGͨѶʧ���򷵻�Ϊ��
	 * 2.���GTCG���������ߵ�����ʧ����ض��з�����ͷ�����Ϣ
	 * 3.���������ȫΪ0������п��Խ�����һ�����
	 * */
	public Trx89010 splitResponseString(String revString){
		Trx89010 obj = new Trx89010();
		if("".equals(revString) || revString.length()==0){
			obj.setRetCode("");
			obj.setRetMsg("ͨѶ�쳣!");
		}else{
			String[] respArr = HrStringUtil.split(revString, '|');
			obj.retInfo(respArr[RETCODE], respArr[RETMSG]);
			if(FcUtils.isAllChar0(respArr[RETCODE])){
				//���÷�����Ϣ
				obj.setZcname(respArr[USERNAME]);
				obj.setZcaddr(respArr[USERADDR]);
			}
		}
		return obj;
	}
	
	//��������ͷ�����Ϣ
	private void retInfo(String retCode,String retMsg){
		//ֻ���ж�GTCG�ķ�����ͷ�����Ϣ���������ķ�����ͷ�����Ϣ����µ���һ���͵ڶ��������ֶ���
		this.setRetCode(retCode);
		this.setRetMsg(retMsg);
	}
	

}
