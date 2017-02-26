package com.xkxx.tcpExchange;

import com.icbc.cosp.hrpub.util.HrStringUtil;
import com.xkxx.util.FcUtils;


/**
 * �û��ɷ�
 * @author sxfh-jcz3
 *
 */
public class Trx89011 extends TrxNode{
	
	private final int AGENTSERIALNO = 2;//������ˮ�ŵ�λ��
	private final int WORKDATE = 3;//��������ʱ��
	private final int WORKTIME = 4;//������������
//	private final int CORPRETCODE = 6;//����������
//	private final int CORPRETMSG = 7;//����������Ϣ
	
//	private String hostRetCode;//����������
//	private String hostRetMsg;//����������Ϣ
	private String busiSerialNo;//������ˮ��
	private String workdate;//��������
	private String worktime;//����ʱ��
	
//	public String getHostRetCode() {return hostRetCode;}
//	public String getHostRetMsg() {return hostRetMsg;}
	public String getBusiSerialNo() {return busiSerialNo;}
	public String getWorkdate() {return workdate;}
	public String getWorktime() {return worktime;}

//	public void setHostRetCode(String hostRetCode) {this.hostRetCode = hostRetCode;}
//	public void setHostRetMsg(String hostRetMsg) {this.hostRetMsg = hostRetMsg;}
	public void setBusiSerialNo(String busiSerialNo) {this.busiSerialNo = busiSerialNo;}
	public void setWorkdate(String workdate) {this.workdate = workdate;}
	public void setWorktime(String worktime) {this.worktime = worktime;}

	@Override
	public String toString() {
		return "Trx89011 [busiSerialNo=" + busiSerialNo +  ", workdate="
				+ workdate + ", worktime=" + worktime + "]";
	}
	/**
	 * ���ر��ģ�
	 * 1.�����GTCGͨѶʧ���򷵻�Ϊ��
	 * 2.���GTCG���������ߵ�����ʧ����ض��з�����ͷ�����Ϣ
	 * 3.���������ȫΪ0������п��Խ�����һ�����
	 * */
	public Trx89011 splitResponseString(String revString){
		Trx89011 obj = new Trx89011();
		//ͨѶʧ��
		if(revString.length()==0 || "".equals(revString)){
			obj.setRetCode("");
			obj.setRetMsg("ͨѶʧ��!");
		}else{
			String[] respArr = HrStringUtil.split(revString, '|');
			//�����봦��
			obj.retInfo(respArr[RETCODE], respArr[RETMSG]);
			if(FcUtils.isAllChar0(respArr[RETCODE])){
				obj.setBusiSerialNo(respArr[AGENTSERIALNO]);
				obj.setWorkdate(respArr[WORKDATE]);
				obj.setWorktime(respArr[WORKTIME]);
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
