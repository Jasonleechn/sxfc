package com.xkxx.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import ToolsApi.global.VWorkSpace;
import ToolsApi.zxy.com.util.date.VDate;

import com.xkxx.dao.AccountDao;
import com.xkxx.dao.TrxDetailDao;
import com.xkxx.entity.Account;
import com.xkxx.entity.Config;
import com.xkxx.entity.TrxDetail;
import com.xkxx.entity.page.TrxDetailPage;

/**
 * ��ѯ��ؿ�����
 * @author sxfh-jcz3
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/search")
@SessionAttributes("trxDetailPage")
public class SearchInfoController extends BaseController {
	
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
	
	/**��ѯͶעվ��Ϣ*/
	@RequestMapping("/toSearchOne.do")
	public String toSeachOne(HttpServletRequest request){
		return "search/toSeachOne";
	}
	
	@RequestMapping("/searchOne.do")
	public String seachOne(HttpSession session,HttpServletRequest request) throws Exception{
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
				return "search/searchError";
			}else if(idcard.equals(obj.getPersonid()) == false){
				logger.log(">>>>>>>>>>>>>>��ѯ�����"+obj.toString()+"��");
				request.setAttribute("alertMsg", "֤�������쳣,������ע����ٲ�ѯ!");
				return "search/searchError";
			}else{
				logger.log(">>>>>>>>>>>>>>��ѯ�����"+obj.toString()+"��");
				session.setAttribute("devidInfo", obj);
				return "search/searchResult";
			}
		} catch (Exception e) {
			this.logSxFcError("��ѯͶעվ��Ϣ��SearchInfoController.java/searchOne.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	/**�����ѯ��ϸҳ��*/
	@RequestMapping("/queryDetails.do")
	public String queryDetails(HttpSession session){
//		session.removeAttribute("trxDetailPage");
		return "detail/queryDetails";
	}
	
	/**
	 * ��ȡ�ɷ���ϸ
	 * @throws Exception 
	 * */
	@RequestMapping("/getPayDetails.do")
	public String getPayDetails(
			Model model,
			TrxDetailPage page,
			HttpSession session,
			HttpServletRequest request) throws Exception { 
		try {
			//��ȡ��ѯ����
			String devid = request.getParameter("p_devid");
			//��ȡ��ѯ���ͣ�0,���Բ�Ʊ��1,���㼴����Ʊ
			String type = request.getParameter("p_type");
			//�˱��������������ǲ��Ǵ���ϸҳ�淢��ģ�
			//�����ֵ˵����Ҫ����ϸҳ����з�ҳ���Ͳ�Ҫ�ٽ������ܲ���
			String flag = request.getParameter("p_flag");
			//��ȡע�����Ϣ
			Account accInfo = accountDao.findByUniqueKey(devid, type);
			session.setAttribute("accInfo", accInfo);
			//�Ѿ���¼�ˣ�����ҳ����ʱ��ֱ�Ӵ�session��ȡdevid��type
			if(flag != null){
				Account account = (Account)session.getAttribute("accInfo");
				devid = account.getDevid();
				type = account.getType();
			}else{
				//�����¼һ�κ��ٴε�¼ʱ��ʾ�ϴε�¼����˳�ҳ���bug
				page.setCurrentPage(1);
			}
			//1.��ȡ��ѯ����ע����Ϣ
			if(accInfo == null){
				request.setAttribute("alertMsg", "��"+Account.getTypeCH(type)+"�����ޡ�"+devid+"����ע����Ϣ,����ע��!");
				return "detail/queryDetails";
			}
			//2.��ȡ��ѯ����
			String pwd = request.getParameter("p_pwd");
			if(accInfo.getPwd().equals(pwd) == false && flag==null){
				request.setAttribute("alertMsg", "�������!");
				return "detail/queryDetails";
			}
			//��ѯ��ʼ����
			String beginDate = request.getParameter("beginDate");
			//��ѯ��������
			String endDate = request.getParameter("endDate");
			page.setPaycardno(accInfo.getAccountno());//�ɿ��
			page.setType(type);//���ò�Ʊ����
			page.setDevid(accInfo.getDevid());//����Ͷעվ���
			//��ѯ��ʼ����
			if(beginDate!=null&&beginDate.length()>0){
				page.setBeginDate(beginDate.replaceAll("-", ""));
			}else{
				page.setBeginDate("19700101");//Ĭ�Ͽ�ʼ����
			}
			//��ѯ��������
			if(endDate!=null&&endDate.length()>0){
				page.setEndDate(endDate.replaceAll("-", ""));
			}else{
				page.setEndDate("99991230");//Ĭ�Ͻ�������
			}
			page.setRows(trxDetailDao.findRows(page));
			model.addAttribute("trxDetailPage", page);
			List<TrxDetail> list = trxDetailDao.findByPage(page);
			model.addAttribute("trxDetails", list);
			logger.log("��"+accInfo.getDevid()+"��---->>>>>��ѯ���š�"+accInfo.getAccountno()+"����ϸ�ɹ�!");
			return "detail/trxDetail_list";
		} catch (Exception e) {
			this.logSxFcError("��ѯ�ɷ���ϸ��SearchInfoController.java/getPayDetails.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * ��ȡһ����ϸ��¼
	 * @throws Exception 
	 * */
	@RequestMapping("/oneDetail.do")
	public String oneDetails(HttpSession session,HttpServletRequest request,Model model) throws Exception{
		try {
			String devid = request.getParameter("p_devid");
			String lsh = request.getParameter("p_lsh");
			TrxDetail oneDtl = trxDetailDao.getOneDetail(devid, lsh);
			boolean isToday = VDate.getCurDate("yyyyMMdd").equals(oneDtl.getDate8());
			model.addAttribute("oneDtl", oneDtl);
			model.addAttribute("isToday", isToday);
			return "detail/oneDetail";
		} catch (Exception e) {
			this.logSxFcError("��ѯ������ϸ��SearchInfoController.java/oneDetail.do���쳣", e);
			throw new Exception(e);
		}
		
	}
	
}
