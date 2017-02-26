package com.xkxx.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xkxx.dao.AccountDao;
import com.xkxx.dao.TrxDetailDao;
import com.xkxx.entity.Account;
import com.xkxx.entity.Config;
import com.xkxx.tcpExchange.SendToGtcg;
import com.xkxx.tcpExchange.Trx89009;
import com.xkxx.tcpExchange.Trx89010;
import com.xkxx.util.FcUtils;
import com.xkxx.util.msgUtil.SendPhoneMsg;

@Controller
@Scope("prototype")
@RequestMapping("/login")
public class LoginController extends BaseController {
	
	@Resource
	AccountDao accountDao;
	
	@Resource
	Config config;
	
	@Resource
	TrxDetailDao trxDetailDao;
	
	/**��¼*/
	/*@RequestMapping("/toLogin.do")
	public String toLogin() {
		return "main/login";
	}*/
	
	/**����ע��ҳ��*/
	@RequestMapping("/toRegister.do")
	public String toRegister(HttpSession session, HttpServletRequest request) {
		logger.log("======================================================");
		logger.log(">>>>>>>>>>>>>>ע�������ʼ����>>>>>>>>>>>>>>>>>>>>>>>>>");
		return "register/register";
	}
	
	/**
	 * ע���1��
	 * ͨ��Ͷעվ�������������ѯ,���ز�ѯ��Ϣ
	 * @throws Exception 
	 * */
	@RequestMapping("/regDevid.do")
	public String regDevid(HttpSession session,
						   HttpServletRequest request) throws Exception {
		 try{
			 //Ͷעվ��ţ��˺ţ�
			 String p_devid = request.getParameter("p_devid");
			 //����
			 String p_pwd = request.getParameter("p_pwd");
			 //֤������
			 String p_idcard = request.getParameter("p_idcard");
			 
			 logger.log("��ע���˺š�,�����ֶ�---->>>>��" +
					 "devid:"+p_devid+",pwd:"+p_pwd+",idcard:"+p_idcard+"��");
			 
			 //�п�ֵ�򷵻�ע��ҳ��
			 if("".equals(p_devid.length()) || "".equals(p_pwd) || "".equals(p_idcard)){
				 logger.log("��ע��ʧ�ܡ�----->>>>�����ֶεĳ����쳣!");
				 request.setAttribute("alertMsg", "ע��ʧ��!�밴����ʾ��дע����Ϣ!");
				 return "register/registerError";
			 }
			 logger.log("======================================================");
			 logger.log(">>>>>>>>>>>>>>89010�������ʲ�ѯͶעվ��Ϣ��>>>>>>>>>>>>>");
			 //1.ƴ�ӷ��ͱ���,�����ʷ���У����Ϣ
			 StringBuffer send89010 = new StringBuffer();
			 send89010.append("0").append("|");//FLOWFLAG--�̶�
			 send89010.append("0").append("|");//UNITNO--�̶�
			 send89010.append("0023").append("|");//AGENTCODE--�̶�
			 send89010.append("01").append("|");//AGENTFLAG--�̶�
			 send89010.append("410").append("|");//CHANTYPE--�̶�
			 send89010.append("00502").append("|");//AGENTZONENO--������
			 send89010.append("01210").append("|");//�����
			 send89010.append("00010").append("|");//��Ա��
			 send89010.append("").append("|");//���ܺ�:��
			 send89010.append(p_devid).append("|");//Ͷעվ���
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 send89010.append("").append("|").append("").append("|");//�ֶ�Ϊ��
			 logger.log("��"+p_devid+"��---->>>>����89010��ѯͶעվ������Ϣ��"+send89010.toString()+"��");
			 
			 //2.����GTCG
			 String revString = SendToGtcg.commCospOnlineBatch(logger, config, "89010", send89010.toString());
			 //String revString = "0|���׳ɹ�|20161221100000074461|0000|���׳ɹ�||0|����|ӭ����||000||20161221|213331|";
			 //3.��ⷵ�ر���
			 Trx89010 obj_89010 = new Trx89010().splitResponseString(revString);
			 //4.�жϷ��ؽ��
			 if(FcUtils.isAllChar0(obj_89010.getRetCode())){//������ɹ�
				 Account account = new Account();
				 account.setDevid(p_devid);
				 account.setDevidaddr(obj_89010.getZcaddr());
				 account.setPwd(p_pwd);
				 account.setPersonid(p_idcard);
				 account.setDate8(new SimpleDateFormat("yyyyMMdd").format(new Date()));
				 account.setZoneno("00502");
				 account.setUsername(obj_89010.getZcname());
				 account.setOpenid("");
				 account.setType("");
				 account.setAccountno("");
				 
				 logger.log("======================================================");
				 logger.log(">>>>>>>>>>>>>>89009_4����ѯ�ͻ���Ϣ������>>>>>>>>>>>>>>");
				 //1.ƴ��������
				 Trx89009 trx89009_4 = new Trx89009();
				 p_idcard = p_idcard==null?"":p_idcard.trim();
				 trx89009_4.setLgldocno(p_idcard);//�������֤����
				 logger.log(">>>>>>>>>>>>>>>>>>>>>>��׼����ѯ��"+p_idcard+"�����֤�Ŀͻ����...");
				 //2.���ͽ�������
				 String rev_89009_4 = SendToGtcg.commCospOnlineBatch(logger, config, "89009", trx89009_4.req89009_4());
				 Trx89009 obj_89009_4 = trx89009_4.splitResponseString(rev_89009_4, Trx89009.GET_CUSTNO);
				 //3.��ȡ���жϷ��ر���
				 if(FcUtils.isAllChar0(obj_89009_4.getRetCode())){
					 logger.log(">>>>>>>>>>>>>>>>>>>>>>��֤�����롾"+p_idcard+"��,��ѯ�ɹ����ͱ�Ϊ��" +obj_89009_4.getResp_custno_4()+"��");
					 account.setCustid(obj_89009_4.getResp_custno_4());//ͨ����ⷵ�ص���Ϣ���ÿͻ��Ŀͻ������Ϣ
					 //�жϸ��ʷ��ص�����������ͨ�����֤���ص������Ƿ�һ��
					 if(account.getUsername().equals(obj_89009_4.getResp_name_4()) == false){
						 logger.log(">>>>>>>>>>>>>>>>>>>>>>�۸��ʵǼ�������"+account.getUsername()+"��," +
						 		"�����еǼ�������" + obj_89009_4.getResp_name_4()+"��,��һ��!");
						 request.setAttribute("alertMsg", "���ʵǼ�������֤��������һ��!");
						 return "register/registerError";
					 }
				 }else{
					 logger.log(">>>>>>>>>>>>>>>>>>>>>>��֤�����롾"+p_idcard+"��," + obj_89009_4.getRetMsg());
					 request.setAttribute("alertMsg", obj_89009_4.getRetMsg());
					 return "register/registerError";
				 }
				 
				 //�ŵ�session��,�󶨿���ҳ������
				 session.setAttribute("regObj", account);
				 logger.log("��"+p_devid+"��---->>>>"+account.toString());
				 return "register/regDevid";//���뿨�Ű�ҳ��
			 }else{
				 request.setAttribute("alertMsg", obj_89010.getRetMsg());
				 return "register/registerError";
//				 return "register/regDevid";//����
			 }
		 }catch(Exception e){
			 this.logSxFcError("ע���˺ŵ�һ����LoginController.java/regDevid.do���쳣", e);
			 throw new Exception(e);
		 }
	}
	
	/**
	 * ע���2��
	 * �������֤�Ų�ѯ�������п��ţ����û�ѡ��󶨣�ֻ��Ҫ���ؽ�ǿ�
	 * @throws Exception 
	 * */
	@RequestMapping("/regCardno.do")
	public String regCardno(HttpSession session,HttpServletRequest request) throws Exception{
		try {
			//1.��ȡͶע���ŵ���Ϣ
			Account account = (Account)session.getAttribute("regObj");
			if(account == null){
				request.setAttribute("alertMsg", "ҳ�泬ʱ,��������дע����Ϣ!");
				return "register/registerError";
				//return "register/regCardno";������
			}
			//2.��ȡ��Ҫ�󶨵Ĳ�Ʊ����
			String type = request.getParameter("p_type");
			logger.log("��"+account.getDevid()+"��---->>>>ѡ��ע�᣺"+Account.getTypeCH(type));
			account.setType(type);
			//����session����
			session.setAttribute("regObj", account);
			
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>89009_5����ѯ�ͻ����š�����>>>>>>>>>>>>>>");
			//3.ͨ���ͻ������GTCG�����ѯ���п��Ž���
			List<String> list = new ArrayList<String>();
			Trx89009 trx89009_5 = new Trx89009();
			trx89009_5.setInit_flag("1");
			trx89009_5.setCustno(account.getCustid());
			trx89009_5.setAcctno("");
			trx89009_5.setProdcode("");
			String rev_89009_5 = SendToGtcg.commCospOnlineBatch(logger, config, "89009", trx89009_5.req89009_5());
			Trx89009 obj_89009_5 = trx89009_5.splitResponseString(rev_89009_5, Trx89009.GET_CARDNO);
			if(FcUtils.isAllChar0(obj_89009_5.getRetCode())){//������ɹ�
				list.addAll((List<String>)obj_89009_5.getAcctnoList());
				//���ص�������21˵������һҳ����Ҫ��ҳ
				while("21".equals(obj_89009_5.getResp_items_5())){
					trx89009_5.setInit_flag("3");
					trx89009_5.setAcctno(obj_89009_5.getResp_acctno_5());
					trx89009_5.setProdcode(obj_89009_5.getResp_prodcode_5());
					//���÷�ҳ��������������ѯ
					rev_89009_5 = SendToGtcg.commCospOnlineBatch(logger, config, "89009", trx89009_5.req89009_5());
					obj_89009_5 = trx89009_5.splitResponseString(rev_89009_5, Trx89009.GET_CARDNO);
					if(FcUtils.isAllChar0(obj_89009_5.getRetCode())){//������ɹ�
						list.addAll(obj_89009_5.getAcctnoList());//��������ӵ�list��
					}//�ٴβ�ѯ���ͨѶʧ�ܵĻ����˴��Ͳ���������
				}
			}else{
				logger.log(">>>>>>>>>>>>>>>>>>>>>>��ѯ�ͻ�������Ϣʧ��!");
				request.setAttribute("alertMsg", obj_89009_5.getRetMsg());
				return "register/registerError";
				//return "register/regDevid";//����
			}
			session.setAttribute("cardList", list);//�ÿͻ�ѡ����Ҫ��󶨵����п�
			
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>89009_2����ѯ�ͻ��ֻ����롿����>>>>>>>>>>>");
			//4.ͨ���ͻ������GTCG�����ѯ�ͻ����ֻ����뽻��
			Trx89009 trx89009_2 = new Trx89009();
			trx89009_2.setCustno(account.getCustid());
			String rev_89009_2 = SendToGtcg.commCospOnlineBatch(logger, config, "89009", trx89009_2.req89009_2());
			Trx89009 obj_89009_2 = trx89009_2.splitResponseString(rev_89009_2, Trx89009.GET_PHONE);
			if(FcUtils.isAllChar0(obj_89009_2.getRetCode())){//������ɹ�
				account.setTelphone(obj_89009_2.getResp_no_body_2());
			}else{
				logger.log(">>>>>>>>>>>>>>>>>>>>>>��ѯ�ͻ��ֻ�����ʧ��!");
				request.setAttribute("alertMsg", obj_89009_2.getRetMsg());
				return "register/registerError";
				//����return "register/regDevid";
			}
			return "register/regCardno";
		} catch (Exception e) {
			 this.logSxFcError("ע���˺ŵڶ�����LoginController.java/regCardno.do���쳣", e);
			 throw new Exception(e);
		}
		
	}
	
	/**
	 * ע����ŷ���
	 * @throws Exception 
	 * */
	@RequestMapping("/sendRegMsg.do")
	@ResponseBody
	public String sendRegMsg(HttpServletRequest request,HttpSession session) throws Exception{
		try {
			Account account = (Account)session.getAttribute("regObj");
			String cardno = request.getParameter("p_cardno");
			System.out.println("cardno:"+cardno);
			//û��ע����Ϣ
			if(account == null){
				return "1111";
			}
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>׼��������֤��>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			//1.����6λ������֤��
			String msgCode = "";
			for(int i=0; i<6; i++){
				int j = (int)Double.parseDouble(((Math.random()*9)+""));
				msgCode += j;
			}
			logger.log(">>>>>>>>>>>>>>�١�"+account.getDevid()+"��,��֤�롾"+msgCode+"��");
			//2.�༭��������
			String msg = "��������ɽ���������������,��֤��:"+msgCode+",�󶨿�β��:"
						 + cardno.substring(cardno.length()-4)
						 + ".����������ֹͣ���������������С�";
			logger.log(">>>>>>>>>>>>>>�ڡ�"+account.getDevid()+"��,�������ݡ�"+msg+"��");
			//3.��������֤��͵�ǰʱ��ŵ�session��
			session.setAttribute("RegMsgStart", System.currentTimeMillis()+"");
			session.setAttribute("RegMsgCode", msgCode);
			//4.���Ͷ��� 
			boolean flag = new SendPhoneMsg().sendToClient(account.getTelphone(), msg, config.getServerIp(), config.getServerPort(), config.getMsgUser(), config.getMsgPasswd(), 0, logger);
			logger.log(">>>>>>>>>>>>>>�ۡ�"+account.getDevid()+"��,���ŷ��ͳɹ�!");
			return flag+"";
		} catch (Exception e) {
			this.logSxFcError("���ŷ��͡�LoginController.java/sendRegMsg.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * ע���3��
	 * ��Ͷעվ���š����֤�š����п��š��ͻ���ŵ���Ϣ
	 * @throws Exception 
	 * */
	@RequestMapping("/regResult.do")
	public String regResult(HttpSession session,HttpServletRequest request) throws Exception{
		try {
			//ȡѡ���Ŀ��Ž��а�
			String cardno = request.getParameter("p_cardno");
			Account account = (Account)session.getAttribute("regObj");
			if(account == null){
				request.setAttribute("alertMsg", "ҳ�泬ʱ,��������дע����Ϣ!");
				return "register/registerError"; 
				//return "register/regResult";
			}
			String msgStart = (String)session.getAttribute("RegMsgStart");//��ȡ��֤��Ŀ�ʼʱ��
			String msgCode = (String)session.getAttribute("RegMsgCode");//��ȡ��֤��
			msgCode = msgCode==null?"":msgCode;
			String p_msgCode = request.getParameter("p_msg");//��ȡ�������֤��
			logger.log(">>>>>>>>>>>>>>�١�"+account.getDevid()+"��,ϵͳ��֤�룺"+msgCode+",�ͻ�������֤�룺"+p_msgCode
					+",��֤�����ʱ�䣺"+msgStart+",�ͻ�����ʱ�䣺"+System.currentTimeMillis()+"(����)");
			if(msgStart != null){
				double msgTime = Double.parseDouble(msgStart);
				if(System.currentTimeMillis() - msgTime > 120000){
					request.setAttribute("alertMsg", "��֤���ѹ���!");
					return "register/regCardno";
				}
			}else{
				request.setAttribute("alertMsg", "��Ч��֤��!");
				return "register/regCardno";
			}
			if(msgCode.equals(p_msgCode) == false){
				request.setAttribute("alertMsg", "��֤���������!");
				return "register/regCardno";
			}
			account.setAccountno(cardno);
			Account obj = accountDao.findByUniqueKey(account.getDevid(), account.getType());
			if(obj == null){//���ݿ����޼�¼�����һ��
				accountDao.insertOne(account);
			}else{//�м�¼�͸���
				if("".equals(account.getDevid())||account.getDevid().length()<=0){
					logger.log("Ͷע���Ż�ȡ�쳣,���ܽ��и��²���!");
					request.setAttribute("alertMsg", "���»�����Ϣ�쳣!");
					return "register/registerError";
				}
				logger.log("��������Ͷע���š�"+account.getDevid()+"��,��Ʊ���͡�"+account.getType()+"���İ���Ϣ!");
				accountDao.updateOne(account);
			}
			return "register/regResult";
		} catch (Exception e) {
			this.logSxFcError("ע���˺ŵ�������LoginController.java/regResult.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * �����ҳ��
	 * */
	@RequestMapping("/toCancel.do")
	public String toCancel() {
		return "cancleBind/toCancelBind";
	}
	
	@RequestMapping("/searchResult.do")
	public String searchResult(HttpSession session,HttpServletRequest request) throws Exception{
		try {
			//��ѯ����
			String devid = request.getParameter("p_account");
			//��ѯ��Ʊ����
			String type = request.getParameter("p_type");
			//֤������
			String idcard = request.getParameter("p_idcard");
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>��ʼ��ѯͶע������Ϣ>>>>>>>>>>>>>>>>>>>>>>");
			logger.log(">>>>>>>>>>>>>>��ѯ���š�"+devid+"��,��Ʊ���͡�"+type+"��,֤�����롾"+idcard+"��");
			Account obj = accountDao.findByUniqueKey(devid, type);
			if(obj == null){
				request.setAttribute("alertMsg", "�ޡ�"+devid+"��������Ϣ,����ע��!");
				return "cancleBind/toCancelBind";
			}else if(idcard.equals(obj.getPersonid()) == false){
				logger.log(">>>>>>>>>>>>>>��ѯ�����"+obj.toString()+"��");
				request.setAttribute("alertMsg", "֤�������쳣,������ע����ٲ�ѯ!");
				return "cancleBind/toCancelBind";
			}else{
				logger.log(">>>>>>>>>>>>>>��ѯ�����"+obj.toString()+"��");
				session.setAttribute("cancleInfo", obj);
				return "cancleBind/searchResult";
			}
		} catch (Exception e) {
			this.logSxFcError("����˺ŵڶ�����LoginController.java/searchResult.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	/**��󿨺�
	 * @throws Exception */
	@RequestMapping("/cancelBind.do")
	public String cancelBind(HttpSession session,HttpServletRequest request ) throws Exception {
		try {
			//����
			String pwd = request.getParameter("p_pwd");
			Account userInfo = (Account) session.getAttribute("cancleInfo");
			if(userInfo == null){
				request.setAttribute("alertMsg", "��ҳ�ѹ���!");
				return "cancleBind/toCancelBind";
			}
			if(userInfo.getPwd().equals(pwd) == false){
				request.setAttribute("alertMsg", "�����������!");
				return "cancleBind/searchResult";
			}
			accountDao.deleteByUniqueKey(userInfo);
			request.setAttribute("alertMsg", "���ɹ�!");
			logger.log("��"+userInfo.getDevid()+"��---->>>>>���ɹ�!");
			return "cancleBind/toCancelBind";
		} catch (Exception e) {
			this.logSxFcError("����˺ŵ�������LoginController.java/cancelBind.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	/**��ѯͶעվ��Ϣ*/
	@RequestMapping("/toUpdateOne.do")
	public String toUpdateOne(HttpServletRequest request){
		//������־���������޸Ĺ��ò�ѯҳ�棬�����޸�ʱ���˲���
		return "personInfo/toUpdateOne";
	}
	
	@RequestMapping("/getUpdateOne.do")
	public String updateOne(HttpSession session,HttpServletRequest request) throws Exception{
		try {
			//��ѯ����
			String devid = request.getParameter("p_account");
			//��ѯ��Ʊ����
			String type = request.getParameter("p_type");
			//֤������
			String idcard = request.getParameter("p_idcard");
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>��ʼ��ѯͶע������Ϣ>>>>>>>>>>>>>>>>>>>>>>");
			logger.log(">>>>>>>>>>>>>>��ѯ���š�"+devid+"��,��Ʊ���͡�"+type+"��,֤�����롾"+idcard+"��");
			Account obj = accountDao.findByUniqueKey(devid, type);
			if(obj == null){
				request.setAttribute("alertMsg", "�ޡ�"+devid+"��������Ϣ,����ע��!");
				return "personInfo/updateError";
			}else if(idcard.equals(obj.getPersonid()) == false){
				logger.log(">>>>>>>>>>>>>>��ѯ�����"+obj.toString()+"��");
				request.setAttribute("alertMsg", "֤�������쳣,������ע����ٲ�ѯ!");
				return "personInfo/updateError";
			}else{
				logger.log(">>>>>>>>>>>>>>��ѯ�����"+obj.toString()+"��");
				session.setAttribute("devidInfo", obj);
				return "personInfo/getUpdateOne";
			}
		} catch (Exception e) {
			this.logSxFcError("��������ڶ�����LoginController.java/getUpdateOne.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * ���ŷ���
	 * @throws Exception */
	@RequestMapping("/sendResetMsg.do")
	@ResponseBody
	public String sendResetMsg(HttpServletRequest request,HttpSession session) throws Exception{
		try {
			Account account = (Account)session.getAttribute("devidInfo");
			//û��ע����Ϣ
			if(account == null){
				return "1111";
			}
			logger.log(">>>>>>>>>>>>>>׼��������֤��>>>>>>>>>>>>>>>>>>>>>>");
			//1.����6λ������֤��
			String msgCode = "";
			for(int i=0; i<6; i++){
				 int j = (int)Double.parseDouble(((Math.random()*9)+""));
				 msgCode += j;
			}
			logger.log(">>>>>>>>>>>>>>�١�"+account.getDevid()+"��,��֤�롾"+msgCode+"��");
			//2.�༭��������
			String msg = "������������ĸ�������,��֤��:"+msgCode+",����������ֹͣ���������������С�";
			logger.log(">>>>>>>>>>>>>>�ڡ�"+account.getDevid()+"��,�������ݡ�"+msg+"��");
			//3.��������֤��͵�ǰʱ��ŵ�session��
			session.setAttribute("updateMsgStart", System.currentTimeMillis()+"");
			session.setAttribute("updateMsgCode", msgCode);
			//4.���Ͷ���
			boolean flag = new SendPhoneMsg().sendToClient(account.getTelphone(), msg, "serverIp", "serverPort", "user", "passwd", 1, logger);
			logger.log(">>>>>>>>>>>>>>�ۡ�"+account.getDevid()+"��,���ŷ��ͳɹ�!");
			return flag+"";
		} catch (Exception e) {
			this.logSxFcError("����������ŷ��͡�LoginController.java/sendResetMsg.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * �޸�����
	 * @throws Exception 
	 * */
	@RequestMapping("/updatePerInfo.do")
	public String updatePerInfo(HttpSession session,HttpServletRequest request) throws Exception {
		try {
			Account acc = (Account) session.getAttribute("devidInfo");
			//��ȡ������
			String new_pwd = request.getParameter("p_newpwd");
			if("".equals(new_pwd)){
				request.setAttribute("alertMsg", "���볤���쳣!");
				return "personInfo/getUpdateOne";
			}
			String msgStart = (String)session.getAttribute("updateMsgStart");//��ȡ��֤��Ŀ�ʼʱ��
			String msgCode = (String)session.getAttribute("updateMsgCode");//��ȡ��֤��
			msgCode = msgCode==null?"":msgCode;
			String p_msgCode = request.getParameter("p_msg");//��ȡ�������֤��
			if(msgStart != null){
				double msgTime = Double.parseDouble(msgStart);
				if(System.currentTimeMillis() - msgTime > 120000){
					request.setAttribute("alertMsg", "��֤���ѹ���!");
					return "personInfo/getUpdateOne";
				}
			}else{
				request.setAttribute("alertMsg", "��Ч��֤��!");
				return "personInfo/getUpdateOne";
			}
			if(msgCode.equals(p_msgCode) == false){
				request.setAttribute("alertMsg", "��֤���������!");
				return "personInfo/getUpdateOne";
			}
			acc.setPwd(new_pwd);
			accountDao.updatePwd(acc);
			request.setAttribute("alertMsg", "�����޸ĳɹ�!");
			logger.log("��"+acc.getDevid()+"��---->>>>>�����޸ĳɹ�!");
			return "personInfo/getUpdateOne";
		} catch (Exception e) {
			this.logSxFcError("���������������LoginController.java/updatePerInfo.do���쳣", e);
			throw new Exception(e);
		}
	}
	
}
