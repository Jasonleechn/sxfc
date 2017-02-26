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
	
	/**登录*/
	/*@RequestMapping("/toLogin.do")
	public String toLogin() {
		return "main/login";
	}*/
	
	/**进入注册页面*/
	@RequestMapping("/toRegister.do")
	public String toRegister(HttpSession session, HttpServletRequest request) {
		logger.log("======================================================");
		logger.log(">>>>>>>>>>>>>>注册组件开始运行>>>>>>>>>>>>>>>>>>>>>>>>>");
		return "register/register";
	}
	
	/**
	 * 注册第1步
	 * 通过投注站编号往第三方查询,返回查询信息
	 * @throws Exception 
	 * */
	@RequestMapping("/regDevid.do")
	public String regDevid(HttpSession session,
						   HttpServletRequest request) throws Exception {
		 try{
			 //投注站编号（账号）
			 String p_devid = request.getParameter("p_devid");
			 //密码
			 String p_pwd = request.getParameter("p_pwd");
			 //证件号码
			 String p_idcard = request.getParameter("p_idcard");
			 
			 logger.log("【注册账号】,上送字段---->>>>【" +
					 "devid:"+p_devid+",pwd:"+p_pwd+",idcard:"+p_idcard+"】");
			 
			 //有空值则返回注册页面
			 if("".equals(p_devid.length()) || "".equals(p_pwd) || "".equals(p_idcard)){
				 logger.log("【注册失败】----->>>>上送字段的长度异常!");
				 request.setAttribute("alertMsg", "注册失败!请按照提示填写注册信息!");
				 return "register/registerError";
			 }
			 logger.log("======================================================");
			 logger.log(">>>>>>>>>>>>>>89010【往福彩查询投注站信息】>>>>>>>>>>>>>");
			 //1.拼接发送报文,往福彩发送校验信息
			 StringBuffer send89010 = new StringBuffer();
			 send89010.append("0").append("|");//FLOWFLAG--固定
			 send89010.append("0").append("|");//UNITNO--固定
			 send89010.append("0023").append("|");//AGENTCODE--固定
			 send89010.append("01").append("|");//AGENTFLAG--固定
			 send89010.append("410").append("|");//CHANTYPE--固定
			 send89010.append("00502").append("|");//AGENTZONENO--地区号
			 send89010.append("01210").append("|");//网点号
			 send89010.append("00010").append("|");//柜员号
			 send89010.append("").append("|");//功能号:空
			 send89010.append(p_devid).append("|");//投注站编号
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 send89010.append("").append("|").append("").append("|");//字段为空
			 logger.log("【"+p_devid+"】---->>>>发送89010查询投注站机号信息【"+send89010.toString()+"】");
			 
			 //2.发往GTCG
			 String revString = SendToGtcg.commCospOnlineBatch(logger, config, "89010", send89010.toString());
			 //String revString = "0|交易成功|20161221100000074461|0000|交易成功||0|张三|迎泽大街||000||20161221|213331|";
			 //3.拆解返回报文
			 Trx89010 obj_89010 = new Trx89010().splitResponseString(revString);
			 //4.判断返回结果
			 if(FcUtils.isAllChar0(obj_89010.getRetCode())){//返回码成功
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
				 logger.log(">>>>>>>>>>>>>>89009_4【查询客户信息】交易>>>>>>>>>>>>>>");
				 //1.拼接请求报文
				 Trx89009 trx89009_4 = new Trx89009();
				 p_idcard = p_idcard==null?"":p_idcard.trim();
				 trx89009_4.setLgldocno(p_idcard);//设置身份证号码
				 logger.log(">>>>>>>>>>>>>>>>>>>>>>①准备查询【"+p_idcard+"】身份证的客户编号...");
				 //2.发送交易请求
				 String rev_89009_4 = SendToGtcg.commCospOnlineBatch(logger, config, "89009", trx89009_4.req89009_4());
				 Trx89009 obj_89009_4 = trx89009_4.splitResponseString(rev_89009_4, Trx89009.GET_CUSTNO);
				 //3.获取并判断返回报文
				 if(FcUtils.isAllChar0(obj_89009_4.getRetCode())){
					 logger.log(">>>>>>>>>>>>>>>>>>>>>>②证件号码【"+p_idcard+"】,查询成功。客编为【" +obj_89009_4.getResp_custno_4()+"】");
					 account.setCustid(obj_89009_4.getResp_custno_4());//通过拆解返回的信息设置客户的客户编号信息
					 //判断福彩返回的姓名与银行通过身份证返回的姓名是否一致
					 if(account.getUsername().equals(obj_89009_4.getResp_name_4()) == false){
						 logger.log(">>>>>>>>>>>>>>>>>>>>>>③福彩登记姓名【"+account.getUsername()+"】," +
						 		"与银行登记姓名【" + obj_89009_4.getResp_name_4()+"】,不一致!");
						 request.setAttribute("alertMsg", "福彩登记姓名与证件姓名不一致!");
						 return "register/registerError";
					 }
				 }else{
					 logger.log(">>>>>>>>>>>>>>>>>>>>>>②证件号码【"+p_idcard+"】," + obj_89009_4.getRetMsg());
					 request.setAttribute("alertMsg", obj_89009_4.getRetMsg());
					 return "register/registerError";
				 }
				 
				 //放到session中,绑定卡号页面中用
				 session.setAttribute("regObj", account);
				 logger.log("【"+p_devid+"】---->>>>"+account.toString());
				 return "register/regDevid";//进入卡号绑定页面
			 }else{
				 request.setAttribute("alertMsg", obj_89010.getRetMsg());
				 return "register/registerError";
//				 return "register/regDevid";//测试
			 }
		 }catch(Exception e){
			 this.logSxFcError("注册账号第一步【LoginController.java/regDevid.do】异常", e);
			 throw new Exception(e);
		 }
	}
	
	/**
	 * 注册第2步
	 * 根据身份证号查询出来银行卡号，供用户选择绑定，只需要返回借记卡
	 * @throws Exception 
	 * */
	@RequestMapping("/regCardno.do")
	public String regCardno(HttpSession session,HttpServletRequest request) throws Exception{
		try {
			//1.获取投注机号的信息
			Account account = (Account)session.getAttribute("regObj");
			if(account == null){
				request.setAttribute("alertMsg", "页面超时,请重新填写注册信息!");
				return "register/registerError";
				//return "register/regCardno";测试用
			}
			//2.获取需要绑定的彩票类型
			String type = request.getParameter("p_type");
			logger.log("【"+account.getDevid()+"】---->>>>选择注册："+Account.getTypeCH(type));
			account.setType(type);
			//更新session对象
			session.setAttribute("regObj", account);
			
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>89009_5【查询客户卡号】交易>>>>>>>>>>>>>>");
			//3.通过客户编号往GTCG发起查询银行卡号交易
			List<String> list = new ArrayList<String>();
			Trx89009 trx89009_5 = new Trx89009();
			trx89009_5.setInit_flag("1");
			trx89009_5.setCustno(account.getCustid());
			trx89009_5.setAcctno("");
			trx89009_5.setProdcode("");
			String rev_89009_5 = SendToGtcg.commCospOnlineBatch(logger, config, "89009", trx89009_5.req89009_5());
			Trx89009 obj_89009_5 = trx89009_5.splitResponseString(rev_89009_5, Trx89009.GET_CARDNO);
			if(FcUtils.isAllChar0(obj_89009_5.getRetCode())){//返回码成功
				list.addAll((List<String>)obj_89009_5.getAcctnoList());
				//返回的条数是21说明有下一页，需要翻页
				while("21".equals(obj_89009_5.getResp_items_5())){
					trx89009_5.setInit_flag("3");
					trx89009_5.setAcctno(obj_89009_5.getResp_acctno_5());
					trx89009_5.setProdcode(obj_89009_5.getResp_prodcode_5());
					//设置翻页条件后上主机查询
					rev_89009_5 = SendToGtcg.commCospOnlineBatch(logger, config, "89009", trx89009_5.req89009_5());
					obj_89009_5 = trx89009_5.splitResponseString(rev_89009_5, Trx89009.GET_CARDNO);
					if(FcUtils.isAllChar0(obj_89009_5.getRetCode())){//返回码成功
						list.addAll(obj_89009_5.getAcctnoList());//将卡号添加到list中
					}//再次查询如果通讯失败的话，此处就不做处理了
				}
			}else{
				logger.log(">>>>>>>>>>>>>>>>>>>>>>查询客户卡号信息失败!");
				request.setAttribute("alertMsg", obj_89009_5.getRetMsg());
				return "register/registerError";
				//return "register/regDevid";//测试
			}
			session.setAttribute("cardList", list);//让客户选择需要帮绑定的银行卡
			
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>89009_2【查询客户手机号码】交易>>>>>>>>>>>");
			//4.通过客户编号往GTCG发起查询客户的手机号码交易
			Trx89009 trx89009_2 = new Trx89009();
			trx89009_2.setCustno(account.getCustid());
			String rev_89009_2 = SendToGtcg.commCospOnlineBatch(logger, config, "89009", trx89009_2.req89009_2());
			Trx89009 obj_89009_2 = trx89009_2.splitResponseString(rev_89009_2, Trx89009.GET_PHONE);
			if(FcUtils.isAllChar0(obj_89009_2.getRetCode())){//返回码成功
				account.setTelphone(obj_89009_2.getResp_no_body_2());
			}else{
				logger.log(">>>>>>>>>>>>>>>>>>>>>>查询客户手机号码失败!");
				request.setAttribute("alertMsg", obj_89009_2.getRetMsg());
				return "register/registerError";
				//测试return "register/regDevid";
			}
			return "register/regCardno";
		} catch (Exception e) {
			 this.logSxFcError("注册账号第二步【LoginController.java/regCardno.do】异常", e);
			 throw new Exception(e);
		}
		
	}
	
	/**
	 * 注册短信发送
	 * @throws Exception 
	 * */
	@RequestMapping("/sendRegMsg.do")
	@ResponseBody
	public String sendRegMsg(HttpServletRequest request,HttpSession session) throws Exception{
		try {
			Account account = (Account)session.getAttribute("regObj");
			String cardno = request.getParameter("p_cardno");
			System.out.println("cardno:"+cardno);
			//没有注册信息
			if(account == null){
				return "1111";
			}
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>准备生成验证码>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			//1.生成6位短信验证码
			String msgCode = "";
			for(int i=0; i<6; i++){
				int j = (int)Double.parseDouble(((Math.random()*9)+""));
				msgCode += j;
			}
			logger.log(">>>>>>>>>>>>>>①【"+account.getDevid()+"】,验证码【"+msgCode+"】");
			//2.编辑短信内容
			String msg = "您正在向山西福彩中心申请绑定,验证码:"+msgCode+",绑定卡尾号:"
						 + cardno.substring(cardno.length()-4)
						 + ".如有疑问请停止操作。【工商银行】";
			logger.log(">>>>>>>>>>>>>>②【"+account.getDevid()+"】,短信内容【"+msg+"】");
			//3.将短信验证码和当前时间放到session中
			session.setAttribute("RegMsgStart", System.currentTimeMillis()+"");
			session.setAttribute("RegMsgCode", msgCode);
			//4.发送短信 
			boolean flag = new SendPhoneMsg().sendToClient(account.getTelphone(), msg, config.getServerIp(), config.getServerPort(), config.getMsgUser(), config.getMsgPasswd(), 0, logger);
			logger.log(">>>>>>>>>>>>>>③【"+account.getDevid()+"】,短信发送成功!");
			return flag+"";
		} catch (Exception e) {
			this.logSxFcError("短信发送【LoginController.java/sendRegMsg.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * 注册第3步
	 * 绑定投注站机号、身份证号、银行卡号、客户编号等信息
	 * @throws Exception 
	 * */
	@RequestMapping("/regResult.do")
	public String regResult(HttpSession session,HttpServletRequest request) throws Exception{
		try {
			//取选定的卡号进行绑定
			String cardno = request.getParameter("p_cardno");
			Account account = (Account)session.getAttribute("regObj");
			if(account == null){
				request.setAttribute("alertMsg", "页面超时,请重新填写注册信息!");
				return "register/registerError"; 
				//return "register/regResult";
			}
			String msgStart = (String)session.getAttribute("RegMsgStart");//获取验证码的开始时间
			String msgCode = (String)session.getAttribute("RegMsgCode");//获取验证码
			msgCode = msgCode==null?"":msgCode;
			String p_msgCode = request.getParameter("p_msg");//获取输入的验证码
			logger.log(">>>>>>>>>>>>>>①【"+account.getDevid()+"】,系统验证码："+msgCode+",客户输入验证码："+p_msgCode
					+",验证码产生时间："+msgStart+",客户输入时间："+System.currentTimeMillis()+"(纳秒)");
			if(msgStart != null){
				double msgTime = Double.parseDouble(msgStart);
				if(System.currentTimeMillis() - msgTime > 120000){
					request.setAttribute("alertMsg", "验证码已过期!");
					return "register/regCardno";
				}
			}else{
				request.setAttribute("alertMsg", "无效验证码!");
				return "register/regCardno";
			}
			if(msgCode.equals(p_msgCode) == false){
				request.setAttribute("alertMsg", "验证码输入错误!");
				return "register/regCardno";
			}
			account.setAccountno(cardno);
			Account obj = accountDao.findByUniqueKey(account.getDevid(), account.getType());
			if(obj == null){//数据库中无记录则插入一条
				accountDao.insertOne(account);
			}else{//有记录就更新
				if("".equals(account.getDevid())||account.getDevid().length()<=0){
					logger.log("投注机号获取异常,不能进行更新操作!");
					request.setAttribute("alertMsg", "更新机号信息异常!");
					return "register/registerError";
				}
				logger.log("即将更新投注机号【"+account.getDevid()+"】,彩票类型【"+account.getType()+"】的绑定信息!");
				accountDao.updateOne(account);
			}
			return "register/regResult";
		} catch (Exception e) {
			this.logSxFcError("注册账号第三步【LoginController.java/regResult.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * 到解绑页面
	 * */
	@RequestMapping("/toCancel.do")
	public String toCancel() {
		return "cancleBind/toCancelBind";
	}
	
	@RequestMapping("/searchResult.do")
	public String searchResult(HttpSession session,HttpServletRequest request) throws Exception{
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
				return "cancleBind/toCancelBind";
			}else if(idcard.equals(obj.getPersonid()) == false){
				logger.log(">>>>>>>>>>>>>>查询结果【"+obj.toString()+"】");
				request.setAttribute("alertMsg", "证件号码异常,请重新注册后再查询!");
				return "cancleBind/toCancelBind";
			}else{
				logger.log(">>>>>>>>>>>>>>查询结果【"+obj.toString()+"】");
				session.setAttribute("cancleInfo", obj);
				return "cancleBind/searchResult";
			}
		} catch (Exception e) {
			this.logSxFcError("解绑账号第二步【LoginController.java/searchResult.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	/**解绑卡号
	 * @throws Exception */
	@RequestMapping("/cancelBind.do")
	public String cancelBind(HttpSession session,HttpServletRequest request ) throws Exception {
		try {
			//密码
			String pwd = request.getParameter("p_pwd");
			Account userInfo = (Account) session.getAttribute("cancleInfo");
			if(userInfo == null){
				request.setAttribute("alertMsg", "网页已过期!");
				return "cancleBind/toCancelBind";
			}
			if(userInfo.getPwd().equals(pwd) == false){
				request.setAttribute("alertMsg", "密码输入错误!");
				return "cancleBind/searchResult";
			}
			accountDao.deleteByUniqueKey(userInfo);
			request.setAttribute("alertMsg", "解绑成功!");
			logger.log("【"+userInfo.getDevid()+"】---->>>>>解绑成功!");
			return "cancleBind/toCancelBind";
		} catch (Exception e) {
			this.logSxFcError("解绑账号第三步【LoginController.java/cancelBind.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	/**查询投注站信息*/
	@RequestMapping("/toUpdateOne.do")
	public String toUpdateOne(HttpServletRequest request){
		//动作标志，与密码修改共用查询页面，密码修改时传此参数
		return "personInfo/toUpdateOne";
	}
	
	@RequestMapping("/getUpdateOne.do")
	public String updateOne(HttpSession session,HttpServletRequest request) throws Exception{
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
				return "personInfo/updateError";
			}else if(idcard.equals(obj.getPersonid()) == false){
				logger.log(">>>>>>>>>>>>>>查询结果【"+obj.toString()+"】");
				request.setAttribute("alertMsg", "证件号码异常,请重新注册后再查询!");
				return "personInfo/updateError";
			}else{
				logger.log(">>>>>>>>>>>>>>查询结果【"+obj.toString()+"】");
				session.setAttribute("devidInfo", obj);
				return "personInfo/getUpdateOne";
			}
		} catch (Exception e) {
			this.logSxFcError("更改密码第二步【LoginController.java/getUpdateOne.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * 短信发送
	 * @throws Exception */
	@RequestMapping("/sendResetMsg.do")
	@ResponseBody
	public String sendResetMsg(HttpServletRequest request,HttpSession session) throws Exception{
		try {
			Account account = (Account)session.getAttribute("devidInfo");
			//没有注册信息
			if(account == null){
				return "1111";
			}
			logger.log(">>>>>>>>>>>>>>准备生成验证码>>>>>>>>>>>>>>>>>>>>>>");
			//1.生成6位短信验证码
			String msgCode = "";
			for(int i=0; i<6; i++){
				 int j = (int)Double.parseDouble(((Math.random()*9)+""));
				 msgCode += j;
			}
			logger.log(">>>>>>>>>>>>>>①【"+account.getDevid()+"】,验证码【"+msgCode+"】");
			//2.编辑短信内容
			String msg = "您正在申请更改福彩密码,验证码:"+msgCode+",如有疑问请停止操作。【工商银行】";
			logger.log(">>>>>>>>>>>>>>②【"+account.getDevid()+"】,短信内容【"+msg+"】");
			//3.将短信验证码和当前时间放到session中
			session.setAttribute("updateMsgStart", System.currentTimeMillis()+"");
			session.setAttribute("updateMsgCode", msgCode);
			//4.发送短信
			boolean flag = new SendPhoneMsg().sendToClient(account.getTelphone(), msg, "serverIp", "serverPort", "user", "passwd", 1, logger);
			logger.log(">>>>>>>>>>>>>>③【"+account.getDevid()+"】,短信发送成功!");
			return flag+"";
		} catch (Exception e) {
			this.logSxFcError("更改密码短信发送【LoginController.java/sendResetMsg.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * 修改密码
	 * @throws Exception 
	 * */
	@RequestMapping("/updatePerInfo.do")
	public String updatePerInfo(HttpSession session,HttpServletRequest request) throws Exception {
		try {
			Account acc = (Account) session.getAttribute("devidInfo");
			//获取新密码
			String new_pwd = request.getParameter("p_newpwd");
			if("".equals(new_pwd)){
				request.setAttribute("alertMsg", "密码长度异常!");
				return "personInfo/getUpdateOne";
			}
			String msgStart = (String)session.getAttribute("updateMsgStart");//获取验证码的开始时间
			String msgCode = (String)session.getAttribute("updateMsgCode");//获取验证码
			msgCode = msgCode==null?"":msgCode;
			String p_msgCode = request.getParameter("p_msg");//获取输入的验证码
			if(msgStart != null){
				double msgTime = Double.parseDouble(msgStart);
				if(System.currentTimeMillis() - msgTime > 120000){
					request.setAttribute("alertMsg", "验证码已过期!");
					return "personInfo/getUpdateOne";
				}
			}else{
				request.setAttribute("alertMsg", "无效验证码!");
				return "personInfo/getUpdateOne";
			}
			if(msgCode.equals(p_msgCode) == false){
				request.setAttribute("alertMsg", "验证码输入错误!");
				return "personInfo/getUpdateOne";
			}
			acc.setPwd(new_pwd);
			accountDao.updatePwd(acc);
			request.setAttribute("alertMsg", "密码修改成功!");
			logger.log("【"+acc.getDevid()+"】---->>>>>密码修改成功!");
			return "personInfo/getUpdateOne";
		} catch (Exception e) {
			this.logSxFcError("更改密码第三步【LoginController.java/updatePerInfo.do】异常", e);
			throw new Exception(e);
		}
	}
	
}
