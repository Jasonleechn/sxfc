package com.xkxx.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ToolsApi.global.VWorkSpace;
import ToolsApi.zxy.com.util.date.VDate;

import com.xkxx.dao.AccountDao;
import com.xkxx.dao.TrxDetailDao;
import com.xkxx.entity.Account;
import com.xkxx.entity.Config;
import com.xkxx.entity.TrxDetail;
import com.xkxx.tcpExchange.SendToGtcg;
import com.xkxx.tcpExchange.Trx89011;
import com.xkxx.util.AmountUtil;
import com.xkxx.util.FcUtils;
import com.xkxx.util.ImageUtil;
import com.xkxx.util.msgUtil.SendPhoneMsg;

/**
 * �ɿ���ؿ�����
 * @author sxfh-jcz3
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/pay")
public class PayInfoController extends BaseController {
	
	@Resource
	Config config;
	
	@Resource
	AccountDao accountDao;
	
	@Resource
	TrxDetailDao trxDetailDao;
	
	/**��ʼ����־·��*/
	@ModelAttribute
	public void initLogger(){
		VWorkSpace.setFullWorkdDir(config.getLogDir());
	}
	
	/**
	 * ���Բ�Ʊ�ɷ�ҳ��
	 * */
	@RequestMapping("/toComputePay.do")
	public String toComPay(){
		return "pay/computerPayInfo";
	}
	
	/**
	 * ���㼴��Ʊ�ɷ�ҳ��
	 * */
	@RequestMapping("/toNetPay.do")
	public String toNetPay(){
		return "pay/netPayInfo";
	}
	
	/**
	 * �ɷ���Ϣȷ��ҳ��
	 * ����ɷ�ȷ������Ͷ�����֤��
	 * @throws Exception 
	 * */
	@RequestMapping("/payDetail.do")
	public String payDetail(HttpSession session,HttpServletRequest request) throws Exception{
		try {
			//������ˮ��
			//request.setAttribute("SysLsh", System.currentTimeMillis());
			//ȡͶעվ��
			String devid = request.getParameter("p_devid");
			//��ȡ��Ʊ����:0,���Բ�Ʊ;1,���㼴����Ʊ
			String type = request.getParameter("p_type");
			//�ж���֤��
			String code = request.getParameter("p_code");
			session.setAttribute("p_code", code);
			String imageCode = (String) session.getAttribute("imageCode");
			if(code == null || !code.equalsIgnoreCase(imageCode)) {
				request.setAttribute("alertMsg", "��֤�����");
				if("0".equals(type)){
					return "pay/computerPayInfo";//���Բ�Ʊ
				}else if("1".equals(type)){
					return "pay/netPayInfo";//���㼴��Ʊ
				}else{
					request.setAttribute("alertMsg", "ϵͳ�ѳ�ʱ!");
					return "pay/payError";
				}
			}
			
			Account account = accountDao.findByUniqueKey(devid, type);
			//û��ע����Ϣ
			if(account == null){
				request.setAttribute("alertMsg", Account.getTypeCH(type)+"��,�ޡ�"+devid+"����Ϣ,����ע��!");
				return "register/register";//����ע��ҳ��
			}
			Account obj = accountDao.findByUniqueKey(devid, type);
			//ͨ��GTCG���ݿͻ���Ż�ȡ�ͻ����ֻ�����
			session.setAttribute("payDetail", obj);
			
			return "pay/payDetail";
		} catch (Exception e) {
			this.logSxFcError("�ɷѵڶ�����PayInfoController.java/payDetail.do���쳣", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * ���ŷ���
	 * @throws Exception */
	@RequestMapping("/sendMsg.do")
	@ResponseBody
	public String sendMsg(HttpServletRequest request,HttpSession session) throws Exception{
		try {
			String amount = request.getParameter("p_amount");
			Account account = (Account)session.getAttribute("payDetail");
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
			String msg = "��������ɽ���������Ľɿ�,��֤��:"+msgCode+",���β��:"
				+account.getAccountno().substring(account.getAccountno().length()-4)
				+",���:"+amount+"Ԫ.����������ֹͣ���������������С�";
			logger.log(">>>>>>>>>>>>>>�ڡ�"+account.getDevid()+"��,�������ݡ�"+msg+"��");
			//3.��������֤��͵�ǰʱ��ŵ�session��
			session.setAttribute("MsgStart", System.currentTimeMillis()+"");
			session.setAttribute("MsgCode", msgCode);
			//4.���Ͷ���
			boolean flag = new SendPhoneMsg().sendToClient(account.getTelphone(), msg, "serverIp", "serverPort", "user", "passwd", 1, logger);
			logger.log(">>>>>>>>>>>>>>�ۡ�"+account.getDevid()+"��,���ŷ��ͳɹ�!");
			return flag+"";
		} catch (Exception e) {
			this.logSxFcError("�ɷѶ��ŷ��͡�PayInfoController.java/sendMsg.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	
	/**
	 * �ɷ�
	 * �����ֶΣ����п��ţ�Ͷעվ��ţ����֣��������ţ�������������Ͷעվ��ַ
	 * @throws Exception 
	 * */
	@RequestMapping("/pay.do")
	public String pay(HttpServletRequest request,HttpSession session) throws Exception {
		try {
			String msgStart = (String)session.getAttribute("MsgStart");//��ȡ��֤��Ŀ�ʼʱ��
			String msgCode = (String)session.getAttribute("MsgCode");//��ȡ��֤��
			msgCode = msgCode==null?"":msgCode;
			String amount = request.getParameter("p_amount");//�ɷѽ��
			String lsh = request.getParameter("p_lsh");//��ȡҳ�洫��������ˮ��
			//����ˮ�Ŵ�����
			request.setAttribute("SysLsh", lsh);
			String p_msgCode = request.getParameter("p_msg");//��ȡ�������֤��
			String pwd = request.getParameter("p_pwd");//��ȡ�ɷ�����
			Account account = (Account)session.getAttribute("payDetail");
			if(account == null){
				request.setAttribute("alertMsg", "ϵͳ�ѳ�ʱ!");
				return "pay/payError";
			}
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>��"+account.getDevid()+"���ɷѿ�ʼ>>>>>>>>>>>>>>>>>>>>");
			logger.log(">>>>>>>>>>>>>>�١�"+account.getDevid()+"��,lsh:"+lsh+",ϵͳ��֤�룺"+msgCode+",�ͻ�������֤�룺"+p_msgCode
					+",��֤�����ʱ�䣺"+msgStart+",�ͻ�����ʱ�䣺"+System.currentTimeMillis()+"(����)");
			if(msgStart != null){
				double msgTime = Double.parseDouble(msgStart);
				if(System.currentTimeMillis() - msgTime > 120000){
					request.setAttribute("alertMsg", "��֤���ѹ���!");
					return "pay/payDetail";
				}
			}else{
				request.setAttribute("alertMsg", "��Ч��֤��!");
				return "pay/payDetail";
			}
			if(msgCode.equals(p_msgCode) == false){
				request.setAttribute("alertMsg", "��֤���������!");
				return "pay/payDetail";
			}
			if(account.getPwd().equals(pwd) == false){
				request.setAttribute("alertMsg", "�ɷ������������!");
				return "pay/payDetail";
			}
			
			//��ѯ���µ�������Ϣ
			logger.log(">>>>>>>>>>>��"+account.getDevid()+"��׼����ʼ�ɷ�...>>>>>>>>>>>>>>");
			//ǰ�˷��ظ�ʱ�伫�̵���������ز�ס���˴�����һ������
			if(trxDetailDao.getOneDetail(account.getDevid(), lsh) != null){
				logger.log("��"+account.getDevid()+"��---->>>>��ˮ��:"+lsh+"�Ѿ��ɿ�!");
				request.setAttribute("alertMsg", "����ˮ���Ѿ��ɿ�,��鿴��ϸ!");
				Thread.sleep(3000);
				return "pay/payError";
			}
			//1.���ɷ������¼��״̬��Ϊδ֪
			TrxDetail trxObj = new TrxDetail();
			trxObj.setAmt(amount);//��λΪԪ
			trxObj.setDate8(VDate.getCurDate8());
			trxObj.setDevid(account.getDevid());
			trxObj.setDevidname(account.getDevidaddr());
			trxObj.setLsh(lsh);
			trxObj.setPaycardno(account.getAccountno());
			trxObj.setTrx_flag(TrxDetail.UNKNOWN);//״̬δ֪
			trxObj.setType(account.getType());
			trxObj.setUsername(account.getUsername());
			trxDetailDao.insertOne(trxObj);
			logger.log("��"+trxObj.getDevid()+"��---->>>>�ٽɷ���ˮ�š�"+lsh+"���Ѿ����,��ʼ״̬Ϊδ֪>>>>>>>>");
			
			//2.ƴ��89011����������
			StringBuffer send89011 = new StringBuffer();
			send89011.append("0").append("|");//FLOWFLAG--�̶�
			send89011.append(account.getType()).append("|");//UNITNO--�̶�
			send89011.append("0023").append("|");//AGENTCODE--�̶�
			send89011.append("01").append("|");//AGENTFLAG--�̶�
			send89011.append("410").append("|");//CHANTYPE--�̶�
			send89011.append("00502").append("|");//AGENTZONENO--������
			send89011.append("0").append("|");//AGENTMBRNO--�̶�
			send89011.append("0").append("|");//AGENTTLACTBRNO--�̶�
			send89011.append("0").append("|");//AGENTBRNO--�̶�
			send89011.append("").append("|");//AGENTTELLER--�̶�
			send89011.append("").append("|");//IFTRXSERNB--�̶�
			send89011.append("").append("|");//ACCTYPE--�̶�
			send89011.append(account.getAccountno()).append("|");//ACCNO--���п���
			send89011.append("").append("|");//ACCPWD--�̶�
			send89011.append(account.getDevid()).append("|");//ACCPWD--Ͷעվ���
			send89011.append("").append("|");//SUBUSERNO
			send89011.append(account.getUsername()).append("|");//վ�㸺��������
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append(AmountUtil.changeY2F(amount)).append("|");//AMOUNT--�ɷѽ���λ�֣�
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append(lsh).append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append(account.getDevidaddr()).append("|");//Ͷעվ��ַ
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			send89011.append("").append("|");//�̶�Ϊ��
			logger.log("��"+trxObj.getDevid()+"��---->>>>�ڽɷ���ˮ�š�"+lsh+"��׼����GTCG����89011�ɷѽ���>>>>>>>>");
			logger.log("��"+trxObj.getDevid()+"��---->>>>�۽ɷ���ˮ�š�"+lsh+"���ɷ������ġ�"+send89011.toString()+"��");
			//3.����������
			 String revString = SendToGtcg.commCospOnlineBatch(logger, config, "89011", send89011.toString());
			//4.���������
			 Trx89011 obj_89011 = new Trx89011().splitResponseString(revString);
			 logger.log("��"+trxObj.getDevid()+"��---->>>>�ܽɷ���ˮ�š�"+lsh+"����ⷵ�ر��ġ�"+obj_89011.toString()+"��");
			 TrxDetail updateObj = trxDetailDao.getOneDetail(trxObj.getDevid(), trxObj.getLsh());
			 if(FcUtils.isAllChar0(obj_89011.getRetCode())){
				 //5.����״̬,���������ڹ��캯�������˴����˴�ֻ����¿⼴��
				 //a.��ȷ�����ݿ����Ƿ���������¼,���ɷ�ʧ�ܴ˱ʼ�¼���ṩ��ѯ����
				 if(updateObj != null){
					 //�趨����������Ϣ
					 updateObj.setErrmsg(obj_89011.getRetMsg());
					 //���½��ױ�ʶ
					 updateObj.setTrx_flag(TrxDetail.SUCCESS);
					 //�趨������������
					 updateObj.setWorkdate(obj_89011.getWorkdate()+" "+obj_89011.getWorktime());
					 //�趨����������ˮ��
					 updateObj.setBusiserialno(obj_89011.getBusiSerialNo());
					 trxDetailDao.updateOne(updateObj);
					 logger.log("��"+trxObj.getDevid()+"��---->>>>�ݽɷ���ˮ�š�"+lsh+"�����׳ɹ�!");
					 logger.log("��"+trxObj.getDevid()+"��---->>>>�޽ɷ���ˮ�š�"+lsh+"�����ݿ⽻��״̬���³ɹ�!");
					//���¶���
					session.setAttribute("userInfo", account);
					session.setAttribute("s_alertMsg", "�ɷѡ�"+obj_89011.getRetMsg()+"��");
				 }
				 return "redirect:../search/queryDetails.do";
			 }else{
				//�趨����������Ϣ
				 updateObj.setErrmsg(obj_89011.getRetMsg());
				 //���½��ױ�ʶ
				 if("".equals(obj_89011.getRetCode())){
					 logger.log("��"+trxObj.getDevid()+"��---->>>>�ݽɷ���ˮ�š�"+lsh+"���ɷ�δ֪!");
				 }else{
					 updateObj.setTrx_flag(TrxDetail.FAIL);
					 trxDetailDao.updateOne(updateObj);
					 logger.log("��"+trxObj.getDevid()+"��---->>>>�ݽɷ���ˮ�š�"+lsh+"���ɷ�ʧ��!");
					 logger.log("��"+trxObj.getDevid()+"��---->>>>�޽ɷ���ˮ�š�"+lsh+"�����ݿ⽻��״̬���³ɹ�!");
				 }
				 request.setAttribute("alertMsg", obj_89011.getRetMsg());
				 return "pay/payError";
			 }
		} catch (Exception e) {
			this.logSxFcError("�ɷѵ�������PayInfoController.java/pay.do���쳣", e);
			throw new Exception(e);
		}
		 
	}
	
	/**
	 * ���ճ���
	 * ����:1.����Ϊ����
	 *      2.�ɿ�ɹ��ļ�¼
	 *      3.�����������ɽ�����һ��
	 * @throws Exception 
	 * */
	/*@RequestMapping("/chongZheng.do")
	public String chongZheng(HttpSession session,HttpServletRequest request,Model model) throws Exception{
		String devid =  request.getParameter("p_devid");
		String lsh =  request.getParameter("p_lsh");
		BindInfo userInfo = (BindInfo) session.getAttribute("userInfo");
		//1.��ѯ������¼
		TrxDetail obj = trxDetailDao.getOneDetail(devid, lsh);
		if(obj == null){
			request.setAttribute("alertMsg", "�Ҳ�����Ӧ�ĽɷѼ�¼!");
		}
		if(TrxDetail.CHONGZHENG.equals(obj.getTrx_flag())){
			request.setAttribute("alertMsg", "������¼�Ѿ�����!");
		}else{
			logger.log(">>>>>>>>>>>��"+obj.getDevid()+"��׼����ʼ����...>>>>>>>>>>>>>>");
			logger.log(">>>>>>>>>>>������ˮ�š�"+obj.getLsh()+"��>>>>>>>>>>>>>>");
			//2.ƴ��89012����������
			StringBuffer send89012 = new StringBuffer();
			send89012.append("0").append("|");//FLOWFLAG --�̶�'0'
			send89012.append(obj.getType()).append("|");//UNITNO--�ɿ�����ϵͳ('0'�����Բ�Ʊ,'1'�����㼴��Ʊ)
			send89012.append("0023").append("|");//AGENTCODE--�̶�'0023'
			send89012.append("01").append("|");//AGENTFLAG--�̶�'01'
			send89012.append("410").append("|");//CHANTYPE--�̶�'410'
			send89012.append("00502").append("|");//AGENTZONENO--������(ȡǩԼ��)
			send89012.append("0").append("|");//AGENTMBRNO--�̶�'0'
			send89012.append("0").append("|");//AGENTTLACTBRNO--�̶�'0'
			send89012.append("0").append("|");//AGENTBRNO--�̶�'0'
			send89012.append("00010").append("|");//AGENTTELLER
			send89012.append("").append("|");//IFTRXSERNB
			send89012.append(obj.getBusiserialno()).append("|");//PRESERIALNO--ԭ������ˮ��
			send89012.append("").append("|");//ACCTYPE
			send89012.append(obj.getPaycardno()).append("|");//ACCNO--�ɿ����п��ţ�����һ�£�
			send89012.append("").append("|");//ACCPWD
			send89012.append(obj.getDevid()).append("|");//USERNO--Ͷעվ���
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("1").append("|");
			send89012.append(AmountUtil.changeY2F(obj.getAmt())).append("|");//AMOUNT--ԭ�ɷѽ�����һ�£�
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append(obj.getDevidname()).append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			send89012.append("").append("|");
			logger.log("��"+obj.getDevid()+"��---->>>>�ٽɷ���ˮ�š�"+lsh+"��׼����GTCG����89012��������>>>>>>>>");
			logger.log("��"+obj.getDevid()+"��---->>>>�ڽɷ���ˮ�š�"+lsh+"���ɷ������ġ�"+send89012.toString()+"��");
			//3.����������
			String revString = SendToGtcg.commCospOnlineBatch(logger, config, "89012", send89012.toString());
			//4.���������
			Trx89012 obj_89012 = new Trx89012().splitResponseString(revString);
			logger.log("��"+obj.getDevid()+"��---->>>>�۽ɷ���ˮ�š�"+lsh+"����ⷵ�ر��ġ�"+obj_89012.toString()+"��");
			//���׳ɹ�
			if(FcUtils.isAllChar0(obj_89012.getRetCode())){
				//�������ݿ�
				obj.setTrx_flag(TrxDetail.CHONGZHENG);
				obj.setWorkdate(obj_89012.getWorkdate()+" "+obj_89012.getWorktime());
				obj.setCzserialno(obj_89012.getCZSerialNo());
				obj.setErrmsg(obj_89012.getRetMsg());
				trxDetailDao.updateOne(obj);
				request.setAttribute("alertMsg", "���ʽɷ��Ѿ�����!");
				logger.log("��"+obj.getDevid()+"��---->>>>�ܽɷ���ˮ�š�"+lsh+"���ѳ���,�������ݿ�ɹ�!");
			}else{
				request.setAttribute("alertMsg", "���ʽɷѳ���ʧ��!"+obj_89012.getRetMsg());
				logger.log("��"+obj.getDevid()+"��---->>>>�ܽɷ���ˮ�š�"+lsh+"������ʧ��!");
				logger.log("��"+obj.getDevid()+"��---->>>>����ʧ�ܣ�"+obj_89012.getRetCode()+"|"+obj_89012.getRetMsg());
			}
		}
		TrxDetail oneDtl = trxDetailDao.getOneDetail(obj.getDevid(), lsh);
		model.addAttribute("oneDtl", oneDtl);
		return "detail/oneDetail";
	}*/
	
	/**������֤��*/
	@RequestMapping("/createImage.do")
	public void createImage(
			HttpServletResponse response, HttpSession session)
			throws Exception {
		try {
			Map<String, BufferedImage> imageMap = ImageUtil.createImage();
			String code = imageMap.keySet().iterator().next();
			session.setAttribute("imageCode", code);
			
			BufferedImage image = imageMap.get(code);
			
			response.setContentType("image/jpeg");
			OutputStream ops = response.getOutputStream();
			ImageIO.write(image, "jpeg", ops);
			ops.close();
		} catch (Exception e) {
			this.logSxFcError("������֤�롾PayInfoController.java/createImage.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	
}
