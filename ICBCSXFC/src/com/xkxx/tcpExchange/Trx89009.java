package com.xkxx.tcpExchange;

import java.util.ArrayList;

import com.icbc.cosp.hrpub.util.HrStringUtil;
import com.xkxx.util.FcUtils;

/***
 * 
 * funccode = 2; �������֤��ѯ�ͱ�
 * funccode = 4; ���ݿͱ���ֻ�����
 * funccode = 5; ���ݿͱ��ѯ����
 * @author sxfh-jcz3
 *
 */
public class Trx89009 extends TrxNode{
	
	/** 2 - ���ֻ�����*/ 
	public static final String GET_PHONE = "2";
	/** 4 - ��ͱ�*/ 
	public static final String GET_CUSTNO = "4";
	/** 5 - �鿨��*/
	public static final String GET_CARDNO = "5";
	
	///////////////////////////�����ֶ�//////////////////////////////////////////////
	//=======================89009�����������ֶ�==================================/
	private String chanType = "";   //--CHANTYPE
	private String agentCode = "" ; //--AGENTCODE
	private String agentzoneno = "";//--��ѯ������
	private String funccode = "" ;  //--FUNCCODE
	//=======================���ݿͻ���Ų�ѯ�ֻ�����funccode=2======================/
	private String custno = "" ;//--�ͻ����
	//=======================�������֤�����ѯ�ͻ����funccode=4====================/
	private String init_flag = "" ;   //--��ҳ��־��1.��ѯ2.��ҳ3.��ҳ��
	private String regis_id = "";     //--��ҳ����������ҳʱ�Ͳ�ѯ�������һ��������ҳʱ���ϴβ�ѯ���ĵ�һ��������ʱ�Ϳ�ֵ
	private String name = "";         //--��ҳ����������ҳʱ�Ͳ�ѯ�������һ��������ҳʱ���ϴβ�ѯ���ĵ�һ��������ʱ�Ϳ�ֵ
	private String operflag = "";     //--�������ͣ�1-�ͻ���Ϣ��ѯ��2-�ͻ���ϢԤ��ѯ��3-�ͻ�������Ϣ��ѯ,4-�ֿ��˿ͻ���Ϣ��ѯ
	private String searchtype = "";   //--��ѯ����
	private String bk1 = "0";    //--��������=3ʱѡ��
	private String ckpin_f = "" ;     //--��������=4ʱѡ��
	private String custpin = "" ;     //--��������=4����ҪУ������ʱ����
	private String individualid = "" ;//--��������=1ʱ
	private String lgldocno = "" ;    //--��������=1ʱ,��ѯ����=2ʱ���䣬������
	private String lgldoctp = "" ;    //--��������=1ʱ����ѯ����=2ʱ����
	private String mdcardno = "" ;    //--��������=4ʱ���䣬��������²���
	//=======================���ݿͻ���Ų�ѯ������Ϣfunccode=5======================/
	//private String init_flag ;  //--��ҳ��־��1.��ѯ2.��ҳ3.��ҳ��
	//private String search_type; //--�������� 1:���ͻ���Ų�ѯ��2�����ͻ����+�˺����Բ�ѯ��3�����ͻ����+�˺�����+�����Ų�ѯ;11:���ͻ���Ų�ѯ���˿ͻ������˻���Ϣ�����عҿ���־
	//private String custno ;     //--�ͻ����
	private String custno_type = "" ;  //--�ͻ�����	1010�����˿ͻ���1020����λ�ͻ�
	private String zoneno = "" ;       //--������
	private String acctno = "" ;       //--�˺ź���(��ҳ����)
	private String prodcode = "";      //--�˻�����(��ҳ����)
	
	///////////////////////////��Ӧ�ֶ�//////////////////////////////////////////////
	//=======================���ر����ֶ�  ===========================================/
	private final int ITEMS_2 = 2;        //���ؼ�¼��
	private final int MOB_ADDR_ID_2 = 3;  //��¼ID
	private final int NO_TYPE_2 = 4;      //�绰����
	private final int NO_BODY_2 = 5;      //�绰����0-�ֻ�
	private final int DEFAULT_TYPE_2 = 6; //�Ƿ���Ҫ���緽ʽ 0-�� 1-��
	private final int PARTY_ID_TYPE_2 = 7;//�ͻ�ID����  1010-���� 1020-�Թ�
	private String resp_items_2;          //���ؼ�¼��
	private String resp_mob_addr_id_2;    //��¼ID
	private String resp_no_type_2;        //�绰����
	private String resp_no_body_2;        //�绰����0-�ֻ�
	private String resp_default_type_2;   //�Ƿ���Ҫ���緽ʽ 0-�� 1-��
	private String resp_party_id_type_2;  //�ͻ�ID����  1010-���� 1020-�Թ�
	
	private final int ITEMS_4 = 2;
	private final int CUSTNO_4 = 3;       //�ͻ����
	private final int NAME_4 = 4;         //�ͻ�����
	private String resp_items_4;
	private String resp_custno_4;
	private String resp_name_4;
	
	private final int ITEMS_5 = 2;        //���ؼ�¼��
	private int ACCTNO_5 = 3;             //�˺ź���
	private String resp_items_5;          //���ؼ�¼��
	private String resp_acctno_5;         //�˺ź���,������һ����¼���˺ţ���������һ�β�ѯ�Ĳ���
	private String resp_prodcode_5;       //�˺�����,������һ����¼���˺����ԣ���������һ�εĲ�ѯ����
	private ArrayList<String> acctnoList = new ArrayList<String>();//��ű��η��ص����п�����Ϣ

	/**
	 * ����89009 ���ݿͻ���Ų�ѯ�ֻ�����
	 * funcode=2
	 * */
	public String req89009_2() {
		this.chanType = "410";     //�������࣬�̶�'410'
		this.agentCode = "0023";   //ҵ����룬�̶�'0023'
		this.agentzoneno = "00502";
		this.funccode = "2";       //�ڲ������룬�̶�'2'
		StringBuffer sdata = new StringBuffer();
		sdata.append(this.chanType).append("|");
		sdata.append(this.agentCode).append("|");
		sdata.append(this.agentzoneno).append("|");
		sdata.append(this.funccode).append("|");
		sdata.append(this.custno).append("|");//brno
		return sdata.toString();
	}
	
	/**
	 * ����89009  �������֤�����ѯ�ͻ����
	 * funcode=4
	 * */
	public String req89009_4(){
		this.chanType = "410";
		this.agentCode = "0023";
		this.agentzoneno = "00502";
		this.funccode = "4";
		this.init_flag = "1";
		this.operflag = "2";
		this.searchtype = "2";
		this.lgldoctp = "0";
		StringBuffer sdata = new StringBuffer();
		sdata.append(this.chanType).append("|");
		sdata.append(this.agentCode).append("|");
		sdata.append(this.agentzoneno).append("|");
		sdata.append(this.funccode).append("|");
		sdata.append(this.init_flag).append("|");
		sdata.append(this.regis_id).append("|");
		sdata.append(this.name).append("|");
		sdata.append(this.operflag).append("|");
		sdata.append(this.searchtype).append("|");
		sdata.append(this.bk1).append("|");
		sdata.append(this.ckpin_f).append("|");
		sdata.append(this.custpin).append("|");
		sdata.append(this.individualid).append("|");
		sdata.append(this.lgldocno).append("|");
		sdata.append(this.lgldoctp).append("|");
		sdata.append(this.mdcardno).append("|");
		return sdata.toString();
	}
	
	/**
	 * ����89009  ���ݿͻ���Ų�ѯ������Ϣ
	 * funcode=5
	 * */
	public String req89009_5() {
		this.chanType = "410";
		this.agentCode = "0023";
		this.agentzoneno = "00502";
		this.funccode = "5";
		this.searchtype = "1";
		this.custno_type = "1010";
		StringBuffer sdata = new StringBuffer();
		sdata.append(this.chanType).append("|");
		sdata.append(this.agentCode).append("|");
		sdata.append(this.agentzoneno).append("|");
		sdata.append(this.funccode).append("|");
		sdata.append(this.init_flag).append("|");
		sdata.append(this.searchtype).append("|");
		sdata.append(this.custno).append("|");
		sdata.append(this.custno_type).append("|");
		sdata.append(this.zoneno).append("|");
		sdata.append(this.acctno).append("|");
		sdata.append(this.prodcode).append("|");
		return sdata.toString();
	}
	
	/**�޲ι����������ͱ���ʱʹ��*/
	public Trx89009(){}
	
	/**��ⷵ�ر���*/
	public Trx89009 splitResponseString(String revString,String funccode) {
		Trx89009 obj = new Trx89009();
		if("".equals(revString) || revString.length()==0){
			obj.setRetCode("");
			obj.setRetMsg("ͨѶ�쳣!");
		}
		//���ر�������ϵ��ʽ���ܻ��ж�������һ��Ϊ��Ҫ��ϵ��ʽ���˴�ֻ���ȡ��Ҫ��ϵ��ʽ����
		String[] responseArr = HrStringUtil.split(revString, '|');
		obj.setRetCode(responseArr[RETCODE]);
		obj.setRetMsg(responseArr[RETMSG]);
		if(FcUtils.isAllChar0(responseArr[RETCODE])){
			//��ѯ�ֻ�����
			if(Trx89009.GET_PHONE.equals(funccode)){
				System.out.println(">>>>>>>>>>>>>>>>>>>��ȡ���ر��ĵ��ֻ�������Ϣ>>>>>>>>>>>");
				obj.resp_items_2 = responseArr[ITEMS_2];
				System.out.println(">>>>>>>>>>>>>>>>>>>��ȡ"+obj.resp_items_2+"���ֻ���Ϣ>>>>>>>>>>>");
				//���ؼ�¼����0,˵���еǼ���ϵ��ʽ
				if(Integer.parseInt(obj.resp_items_2) > 0  ){
					//�绰���� 0-�ֻ�
					obj.resp_no_type_2 = responseArr[NO_TYPE_2];      
					if("0".equals(obj.resp_no_type_2)){
						obj.resp_mob_addr_id_2 = responseArr[MOB_ADDR_ID_2];  
						obj.resp_no_body_2 = responseArr[NO_BODY_2];
						obj.resp_default_type_2 = responseArr[DEFAULT_TYPE_2];
						obj.resp_party_id_type_2 = responseArr[PARTY_ID_TYPE_2];
					}else{
						obj.setRetCode("9999");
						obj.setRetMsg("δ�Ǽ��ֻ�����!");
					}
				}else{
					obj.setRetCode("9999");
					obj.setRetMsg("δ�Ǽ���ϵ�绰!");
				}
			}else if(Trx89009.GET_CUSTNO.equals(funccode)){//��ѯ�ͻ����
				System.out.println(">>>>>>>>>>>>>>>>>>>��ȡ���ر��ĵĿͻ������Ϣ>>>>>>>>>>>");
				obj.resp_items_4 = responseArr[ITEMS_4];
				if(Integer.parseInt(obj.resp_items_4) > 0){
					obj.resp_custno_4 = responseArr[CUSTNO_4];
					obj.resp_name_4 = responseArr[NAME_4];
				}else{
					obj.setRetCode("9999");
					obj.setRetMsg("��֤�������ڹ����޿ͻ����!");
				}
			}else if(Trx89009.GET_CARDNO.equals(funccode)){//��ѯ������Ϣ
				System.out.println(">>>>>>>>>>>>>>>>>>>��ȡ���ر��ĵĿ�����Ϣ>>>>>>>>>>>");
				obj.resp_items_5 = responseArr[ITEMS_5];
				int items = Integer.parseInt(obj.resp_items_5);
				System.out.println(">>>>>>>>>>>>>>>>>>>��ȡ"+items+"��������Ϣ>>>>>>>>>>>");
				if(items > 0){
					for(int i=0; i<items; i++){
						if(responseArr[ACCTNO_5].length() == 19 && 
								(responseArr[ACCTNO_5].startsWith("95")
										||responseArr[ACCTNO_5].startsWith("62"))){//���ǽ�ǿ�������
							if(i==(items-1)){//���β�ѯ�����һ����¼������������һ�β�ѯ�Ĳ���
								obj.resp_acctno_5 = responseArr[ACCTNO_5];//���һ�����˺�
								obj.resp_acctno_5 = responseArr[ACCTNO_5 + 5];//�˺�����������˺ŵ�ƫ������5
							}
							acctnoList.add(responseArr[ACCTNO_5]);
						}
						ACCTNO_5 += 7;//ȡ��һ����ƫ����
					}
					obj.setAcctnoList(acctnoList);
				}else{
					obj.setRetCode("9999");
					obj.setRetMsg("�����֤δ��ѯ����Ӧ�Ŀ���!");
				}
			}else{
				obj.setRetMsg(responseArr[RETMSG]+",[�������쳣]");
			}
		}
		return obj;
	}
	
	public String getProdcode() {return prodcode;}
	public void setProdcode(String prodcode) {this.prodcode = prodcode;}
	public String getChanType() {return chanType;}
	public void setChanType(String chanType) {this.chanType = chanType;}
	public String getAgentCode() {return agentCode;}
	public void setAgentCode(String agentCode) {this.agentCode = agentCode;}
	public String getAgentzoneno() {return agentzoneno;}
	public void setAgentzoneno(String agentzoneno) {this.agentzoneno = agentzoneno;}
	public String getFunccode() {return funccode;}
	public void setFunccode(String funccode) {this.funccode = funccode;}
	public String getCustno() {return custno;}
	public void setCustno(String custno) {this.custno = custno;}
	public String getInit_flag() {return init_flag;}
	public void setInit_flag(String initFlag) {init_flag = initFlag;}
	public String getRegis_id() {return regis_id;}
	public void setRegis_id(String regisId) {regis_id = regisId;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getOperflag() {return operflag;}
	public void setOperflag(String operflag) {this.operflag = operflag;}
	public String getSearchtype() {return searchtype;}
	public void setSearchtype(String searchtype) {this.searchtype = searchtype;}
	public String getBk1() {return bk1;}
	public void setBk1(String bk1) {this.bk1 = bk1;}
	public String getCkpin_f() {return ckpin_f;}
	public void setCkpin_f(String ckpinF) {ckpin_f = ckpinF;}
	public String getCustpin() {return custpin;}
	public void setCustpin(String custpin) {this.custpin = custpin;}
	public String getIndividualid() {return individualid;}
	public void setIndividualid(String individualid) {this.individualid = individualid;}
	public String getLgldocno() {return lgldocno;}
	public void setLgldocno(String lgldocno) {this.lgldocno = lgldocno;}
	public String getLgldoctp() {return lgldoctp;}
	public void setLgldoctp(String lgldoctp) {this.lgldoctp = lgldoctp;}
	public String getMdcardno() {return mdcardno;}
	public void setMdcardno(String mdcardno) {this.mdcardno = mdcardno;}
	public String getCustno_type() {return custno_type;}
	public void setCustno_type(String custnoType) {custno_type = custnoType;}
	public String getZoneno() {return zoneno;}
	public void setZoneno(String zoneno) {this.zoneno = zoneno;}
	public String getResp_items_2() {return resp_items_2;}
	public void setResp_items_2(String respItems_2) {resp_items_2 = respItems_2;}
	public String getResp_mob_addr_id_2() {return resp_mob_addr_id_2;}
	public void setResp_mob_addr_id_2(String respMobAddrId_2) {resp_mob_addr_id_2 = respMobAddrId_2;}
	public String getResp_no_type_2() {return resp_no_type_2;}
	public void setResp_no_type_2(String respNoType_2) {resp_no_type_2 = respNoType_2;}
	public String getResp_no_body_2() {return resp_no_body_2;}
	public void setResp_no_body_2(String respNoBody_2) {resp_no_body_2 = respNoBody_2;}
	public String getResp_default_type_2() {return resp_default_type_2;}
	public void setResp_default_type_2(String respDefaultType_2) {resp_default_type_2 = respDefaultType_2;}
	public String getResp_party_id_type_2() {return resp_party_id_type_2;}
	public void setResp_party_id_type_2(String respPartyIdType_2) {resp_party_id_type_2 = respPartyIdType_2;}
	public String getResp_items_4() {return resp_items_4;}
	public void setResp_items_4(String respItems_4) {resp_items_4 = respItems_4;}
	public String getResp_custno_4() {return resp_custno_4;}
	public void setResp_custno_4(String respCustno_4) {resp_custno_4 = respCustno_4;}
	public String getResp_items_5() {return resp_items_5;}
	public void setResp_items_5(String respItems_5) {resp_items_5 = respItems_5;}
	public String getResp_acctno_5() {return resp_acctno_5;}
	public void setResp_acctno_5(String respAcctno_5) {resp_acctno_5 = respAcctno_5;}
	public ArrayList<String> getAcctnoList() {return acctnoList;}
	public void setAcctnoList(ArrayList<String> acctnoList) {this.acctnoList = acctnoList;}
	public String getResp_prodcode_5() {return resp_prodcode_5;}
	public void setResp_prodcode_5(String respProdcode_5) {resp_prodcode_5 = respProdcode_5;}
	public String getAcctno() {return acctno;}
	public void setAcctno(String acctno) {this.acctno = acctno;}
	public String getResp_name_4() {return resp_name_4;}
	public void setResp_name_4(String respName_4) {resp_name_4 = respName_4;}
	
	
}
