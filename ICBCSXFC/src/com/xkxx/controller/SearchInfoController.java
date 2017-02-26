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
 * 查询相关控制器
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
	
	/**初始化日志路径*/
	@ModelAttribute
	public void initLogger(){
		VWorkSpace.setFullWorkdDir(config.getLogDir());
	}
	
	/**查询投注站信息*/
	@RequestMapping("/toSearchOne.do")
	public String toSeachOne(HttpServletRequest request){
		return "search/toSeachOne";
	}
	
	@RequestMapping("/searchOne.do")
	public String seachOne(HttpSession session,HttpServletRequest request) throws Exception{
		try {
			//查询机号
			String devid = request.getParameter("p_account");
			//查询彩票类型
			String type = request.getParameter("p_type");
			//证件类型
			String idcard = request.getParameter("p_idcard");
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>开始查询投注机号信息>>>>>>>>>>>>>>>>>>>>>>");
			logger.log(">>>>>>>>>>>>>>查询机号【"+devid+"】,彩票类型【"+type+"】,证件号码【"+idcard+"】");
			Account obj = accountDao.findByUniqueKey(devid, type);
			if(obj == null){
				request.setAttribute("alertMsg", "无【"+devid+"】机号信息,请先注册!");
				return "search/searchError";
			}else if(idcard.equals(obj.getPersonid()) == false){
				logger.log(">>>>>>>>>>>>>>查询结果【"+obj.toString()+"】");
				request.setAttribute("alertMsg", "证件号码异常,请重新注册后再查询!");
				return "search/searchError";
			}else{
				logger.log(">>>>>>>>>>>>>>查询结果【"+obj.toString()+"】");
				session.setAttribute("devidInfo", obj);
				return "search/searchResult";
			}
		} catch (Exception e) {
			this.logSxFcError("查询投注站信息【SearchInfoController.java/searchOne.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	/**进入查询明细页面*/
	@RequestMapping("/queryDetails.do")
	public String queryDetails(HttpSession session){
//		session.removeAttribute("trxDetailPage");
		return "detail/queryDetails";
	}
	
	/**
	 * 获取缴费明细
	 * @throws Exception 
	 * */
	@RequestMapping("/getPayDetails.do")
	public String getPayDetails(
			Model model,
			TrxDetailPage page,
			HttpSession session,
			HttpServletRequest request) throws Exception { 
		try {
			//获取查询机号
			String devid = request.getParameter("p_devid");
			//获取查询类型：0,电脑彩票；1,网点即开彩票
			String type = request.getParameter("p_type");
			//此变量是用来区分是不是从明细页面发起的，
			//如果有值说明是要在明细页面进行翻页，就不要再进行验密操作
			String flag = request.getParameter("p_flag");
			//获取注册的信息
			Account accInfo = accountDao.findByUniqueKey(devid, type);
			session.setAttribute("accInfo", accInfo);
			//已经登录了，做翻页操作时，直接从session中取devid和type
			if(flag != null){
				Account account = (Account)session.getAttribute("accInfo");
				devid = account.getDevid();
				type = account.getType();
			}else{
				//解决登录一次后，再次登录时显示上次登录最后退出页面的bug
				page.setCurrentPage(1);
			}
			//1.获取查询机号注册信息
			if(accInfo == null){
				request.setAttribute("alertMsg", "【"+Account.getTypeCH(type)+"】中无【"+devid+"】的注册信息,请先注册!");
				return "detail/queryDetails";
			}
			//2.获取查询密码
			String pwd = request.getParameter("p_pwd");
			if(accInfo.getPwd().equals(pwd) == false && flag==null){
				request.setAttribute("alertMsg", "密码错误!");
				return "detail/queryDetails";
			}
			//查询开始日期
			String beginDate = request.getParameter("beginDate");
			//查询结束日期
			String endDate = request.getParameter("endDate");
			page.setPaycardno(accInfo.getAccountno());//缴款卡号
			page.setType(type);//设置彩票类型
			page.setDevid(accInfo.getDevid());//设置投注站编号
			//查询开始日期
			if(beginDate!=null&&beginDate.length()>0){
				page.setBeginDate(beginDate.replaceAll("-", ""));
			}else{
				page.setBeginDate("19700101");//默认开始日期
			}
			//查询结束日期
			if(endDate!=null&&endDate.length()>0){
				page.setEndDate(endDate.replaceAll("-", ""));
			}else{
				page.setEndDate("99991230");//默认结束日期
			}
			page.setRows(trxDetailDao.findRows(page));
			model.addAttribute("trxDetailPage", page);
			List<TrxDetail> list = trxDetailDao.findByPage(page);
			model.addAttribute("trxDetails", list);
			logger.log("【"+accInfo.getDevid()+"】---->>>>>查询卡号【"+accInfo.getAccountno()+"】明细成功!");
			return "detail/trxDetail_list";
		} catch (Exception e) {
			this.logSxFcError("查询缴费明细【SearchInfoController.java/getPayDetails.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * 获取一条明细记录
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
			this.logSxFcError("查询单条明细【SearchInfoController.java/oneDetail.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
}
