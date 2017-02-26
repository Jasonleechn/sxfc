package com.xkxx.tcpExchange;

import com.icbc.cosp.hrpub.util.HrStringUtil;
import com.xkxx.util.FcUtils;

/**
 * ��ѯ���п��˻���Ϣ
 * @author sxfh-jcz3
 *
 */
public class Trx89007 extends TrxNode{
	
	public static final int BIND_SUCCESS = 1;//�󶨳ɹ�
	public static final int BIND_FAIL = 0;//��ʧ��
	
	private final int NAME = 2;//����
//	private final int IDTYPE = 3;//֤������
	private final int IDCODE = 4;//֤������
//	private final int BALANCE = 5;//���
//	private final int CUSTNO = 6;//�ͻ����
	
	private String cname;//�ͻ�����
	private String idcard;//֤������
	
	public String getCname() {return cname;}
	public String getIdcard() {return idcard;}
	
	public void setCname(String cname) {this.cname = cname;}
	public void setIdcard(String idcard) {this.idcard = idcard;}

	public Trx89007(){}
	
	/**��ⱨ��*/
	public Trx89007(String revString){
		if("".equals(revString) || revString.length()==0){
			this.setRetCode("");
			this.setRetMsg("ͨѶ�쳣!");
		}
		String[] responseArr = HrStringUtil.split(revString, '|');
		this.setRetCode(responseArr[RETCODE]);
		this.setRetMsg(responseArr[RETMSG]);
		if(FcUtils.isAllChar0(responseArr[RETCODE])){
			this.setCname(responseArr[NAME]);
			this.setIdcard(responseArr[IDCODE]);
		}
	}

}
