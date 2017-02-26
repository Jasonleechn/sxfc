package com.xkxx.tcpExchange;

import java.util.ArrayList;

import com.icbc.cosp.hrpub.util.HrStringUtil;
import com.xkxx.util.FcUtils;

/***
 * 
 * funccode = 2; 根据身份证查询客编
 * funccode = 4; 根据客编查手机号码
 * funccode = 5; 根据客编查询卡号
 * @author sxfh-jcz3
 *
 */
public class Trx89009 extends TrxNode{
	
	/** 2 - 查手机号码*/ 
	public static final String GET_PHONE = "2";
	/** 4 - 查客编*/ 
	public static final String GET_CUSTNO = "4";
	/** 5 - 查卡号*/
	public static final String GET_CARDNO = "5";
	
	///////////////////////////请求字段//////////////////////////////////////////////
	//=======================89009公用请求公用字段==================================/
	private String chanType = "";   //--CHANTYPE
	private String agentCode = "" ; //--AGENTCODE
	private String agentzoneno = "";//--查询地区号
	private String funccode = "" ;  //--FUNCCODE
	//=======================根据客户编号查询手机号码funccode=2======================/
	private String custno = "" ;//--客户编号
	//=======================根据身份证号码查询客户编号funccode=4====================/
	private String init_flag = "" ;   //--翻页标志（1.查询2.上页3.下页）
	private String regis_id = "";     //--翻页条件。翻下页时送查询到的最后一条，翻上页时送上次查询到的第一条，正常时送空值
	private String name = "";         //--翻页条件。翻下页时送查询到的最后一条，翻上页时送上次查询到的第一条，正常时送空值
	private String operflag = "";     //--操作类型：1-客户信息查询，2-客户信息预查询，3-客户评价信息查询,4-持卡人客户信息查询
	private String searchtype = "";   //--查询类型
	private String bk1 = "0";    //--操作类型=3时选输
	private String ckpin_f = "" ;     //--操作类型=4时选输
	private String custpin = "" ;     //--操作类型=4，需要校验密码时必输
	private String individualid = "" ;//--操作类型=1时
	private String lgldocno = "" ;    //--操作类型=1时,查询类型=2时必输，否则不输
	private String lgldoctp = "" ;    //--操作类型=1时，查询类型=2时必输
	private String mdcardno = "" ;    //--操作类型=4时必输，其他情况下不输
	//=======================根据客户编号查询卡号信息funccode=5======================/
	//private String init_flag ;  //--翻页标志（1.查询2.上页3.下页）
	//private String search_type; //--操作方法 1:按客户编号查询；2：按客户编号+账号属性查询；3：按客户编号+账号属性+地区号查询;11:按客户编号查询个人客户所有账户信息，返回挂卡标志
	//private String custno ;     //--客户编号
	private String custno_type = "" ;  //--客户类型	1010：个人客户；1020：单位客户
	private String zoneno = "" ;       //--地区号
	private String acctno = "" ;       //--账号号码(翻页条件)
	private String prodcode = "";      //--账户属性(翻页条件)
	
	///////////////////////////响应字段//////////////////////////////////////////////
	//=======================返回报文字段  ===========================================/
	private final int ITEMS_2 = 2;        //返回记录数
	private final int MOB_ADDR_ID_2 = 3;  //记录ID
	private final int NO_TYPE_2 = 4;      //电话类型
	private final int NO_BODY_2 = 5;      //电话号码0-手机
	private final int DEFAULT_TYPE_2 = 6; //是否主要联络方式 0-否 1-是
	private final int PARTY_ID_TYPE_2 = 7;//客户ID类型  1010-个人 1020-对公
	private String resp_items_2;          //返回记录数
	private String resp_mob_addr_id_2;    //记录ID
	private String resp_no_type_2;        //电话类型
	private String resp_no_body_2;        //电话号码0-手机
	private String resp_default_type_2;   //是否主要联络方式 0-否 1-是
	private String resp_party_id_type_2;  //客户ID类型  1010-个人 1020-对公
	
	private final int ITEMS_4 = 2;
	private final int CUSTNO_4 = 3;       //客户编号
	private final int NAME_4 = 4;         //客户名称
	private String resp_items_4;
	private String resp_custno_4;
	private String resp_name_4;
	
	private final int ITEMS_5 = 2;        //返回记录数
	private int ACCTNO_5 = 3;             //账号号码
	private String resp_items_5;          //返回记录数
	private String resp_acctno_5;         //账号号码,存放最后一条记录的账号，用来做下一次查询的参数
	private String resp_prodcode_5;       //账号属性,存放最后一条记录的账号属性，用来做下一次的查询参数
	private ArrayList<String> acctnoList = new ArrayList<String>();//存放本次返回的所有卡号信息

	/**
	 * 发送89009 根据客户编号查询手机号码
	 * funcode=2
	 * */
	public String req89009_2() {
		this.chanType = "410";     //渠道种类，固定'410'
		this.agentCode = "0023";   //业务代码，固定'0023'
		this.agentzoneno = "00502";
		this.funccode = "2";       //内部功能码，固定'2'
		StringBuffer sdata = new StringBuffer();
		sdata.append(this.chanType).append("|");
		sdata.append(this.agentCode).append("|");
		sdata.append(this.agentzoneno).append("|");
		sdata.append(this.funccode).append("|");
		sdata.append(this.custno).append("|");//brno
		return sdata.toString();
	}
	
	/**
	 * 发送89009  根据身份证号码查询客户编号
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
	 * 发送89009  根据客户编号查询卡号信息
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
	
	/**无参构造器，发送报文时使用*/
	public Trx89009(){}
	
	/**拆解返回报文*/
	public Trx89009 splitResponseString(String revString,String funccode) {
		Trx89009 obj = new Trx89009();
		if("".equals(revString) || revString.length()==0){
			obj.setRetCode("");
			obj.setRetMsg("通讯异常!");
		}
		//返回报文中联系方式可能会有多条，第一条为主要联系方式，此处只需获取主要联系方式即可
		String[] responseArr = HrStringUtil.split(revString, '|');
		obj.setRetCode(responseArr[RETCODE]);
		obj.setRetMsg(responseArr[RETMSG]);
		if(FcUtils.isAllChar0(responseArr[RETCODE])){
			//查询手机号码
			if(Trx89009.GET_PHONE.equals(funccode)){
				System.out.println(">>>>>>>>>>>>>>>>>>>获取返回报文的手机号码信息>>>>>>>>>>>");
				obj.resp_items_2 = responseArr[ITEMS_2];
				System.out.println(">>>>>>>>>>>>>>>>>>>获取"+obj.resp_items_2+"条手机信息>>>>>>>>>>>");
				//返回记录大于0,说明有登记联系方式
				if(Integer.parseInt(obj.resp_items_2) > 0  ){
					//电话类型 0-手机
					obj.resp_no_type_2 = responseArr[NO_TYPE_2];      
					if("0".equals(obj.resp_no_type_2)){
						obj.resp_mob_addr_id_2 = responseArr[MOB_ADDR_ID_2];  
						obj.resp_no_body_2 = responseArr[NO_BODY_2];
						obj.resp_default_type_2 = responseArr[DEFAULT_TYPE_2];
						obj.resp_party_id_type_2 = responseArr[PARTY_ID_TYPE_2];
					}else{
						obj.setRetCode("9999");
						obj.setRetMsg("未登记手机号码!");
					}
				}else{
					obj.setRetCode("9999");
					obj.setRetMsg("未登记联系电话!");
				}
			}else if(Trx89009.GET_CUSTNO.equals(funccode)){//查询客户编号
				System.out.println(">>>>>>>>>>>>>>>>>>>获取返回报文的客户编号信息>>>>>>>>>>>");
				obj.resp_items_4 = responseArr[ITEMS_4];
				if(Integer.parseInt(obj.resp_items_4) > 0){
					obj.resp_custno_4 = responseArr[CUSTNO_4];
					obj.resp_name_4 = responseArr[NAME_4];
				}else{
					obj.setRetCode("9999");
					obj.setRetMsg("此证件号码在工行无客户编号!");
				}
			}else if(Trx89009.GET_CARDNO.equals(funccode)){//查询卡号信息
				System.out.println(">>>>>>>>>>>>>>>>>>>获取返回报文的卡号信息>>>>>>>>>>>");
				obj.resp_items_5 = responseArr[ITEMS_5];
				int items = Integer.parseInt(obj.resp_items_5);
				System.out.println(">>>>>>>>>>>>>>>>>>>获取"+items+"条卡号信息>>>>>>>>>>>");
				if(items > 0){
					for(int i=0; i<items; i++){
						if(responseArr[ACCTNO_5].length() == 19 && 
								(responseArr[ACCTNO_5].startsWith("95")
										||responseArr[ACCTNO_5].startsWith("62"))){//不是借记卡则跳过
							if(i==(items-1)){//本次查询的最后一条记录，存起来做下一次查询的参数
								obj.resp_acctno_5 = responseArr[ACCTNO_5];//最后一条的账号
								obj.resp_acctno_5 = responseArr[ACCTNO_5 + 5];//账号属性相对于账号的偏移量是5
							}
							acctnoList.add(responseArr[ACCTNO_5]);
						}
						ACCTNO_5 += 7;//取下一条的偏移量
					}
					obj.setAcctnoList(acctnoList);
				}else{
					obj.setRetCode("9999");
					obj.setRetMsg("此身份证未查询到对应的卡号!");
				}
			}else{
				obj.setRetMsg(responseArr[RETMSG]+",[功能码异常]");
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
