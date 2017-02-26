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
 * 缴款相关控制器
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
	
	/**初始化日志路径*/
	@ModelAttribute
	public void initLogger(){
		VWorkSpace.setFullWorkdDir(config.getLogDir());
	}
	
	/**
	 * 电脑彩票缴费页面
	 * */
	@RequestMapping("/toComputePay.do")
	public String toComPay(){
		return "pay/computerPayInfo";
	}
	
	/**
	 * 网点即开票缴费页面
	 * */
	@RequestMapping("/toNetPay.do")
	public String toNetPay(){
		return "pay/netPayInfo";
	}
	
	/**
	 * 缴费信息确认页面
	 * 输入缴费确认密码和短信验证码
	 * @throws Exception 
	 * */
	@RequestMapping("/payDetail.do")
	public String payDetail(HttpSession session,HttpServletRequest request) throws Exception{
		try {
			//设置流水号
			//request.setAttribute("SysLsh", System.currentTimeMillis());
			//取投注站号
			String devid = request.getParameter("p_devid");
			//获取彩票类型:0,电脑彩票;1,网点即开彩票
			String type = request.getParameter("p_type");
			//判断验证码
			String code = request.getParameter("p_code");
			session.setAttribute("p_code", code);
			String imageCode = (String) session.getAttribute("imageCode");
			if(code == null || !code.equalsIgnoreCase(imageCode)) {
				request.setAttribute("alertMsg", "验证码错误");
				if("0".equals(type)){
					return "pay/computerPayInfo";//电脑彩票
				}else if("1".equals(type)){
					return "pay/netPayInfo";//网点即开票
				}else{
					request.setAttribute("alertMsg", "系统已超时!");
					return "pay/payError";
				}
			}
			
			Account account = accountDao.findByUniqueKey(devid, type);
			//没有注册信息
			if(account == null){
				request.setAttribute("alertMsg", Account.getTypeCH(type)+"中,无【"+devid+"】信息,请先注册!");
				return "register/register";//返回注册页面
			}
			Account obj = accountDao.findByUniqueKey(devid, type);
			//通过GTCG根据客户编号获取客户的手机号码
			session.setAttribute("payDetail", obj);
			
			return "pay/payDetail";
		} catch (Exception e) {
			this.logSxFcError("缴费第二步【PayInfoController.java/payDetail.do】异常", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 短信发送
	 * @throws Exception */
	@RequestMapping("/sendMsg.do")
	@ResponseBody
	public String sendMsg(HttpServletRequest request,HttpSession session) throws Exception{
		try {
			String amount = request.getParameter("p_amount");
			Account account = (Account)session.getAttribute("payDetail");
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
			String msg = "您正在向山西福彩中心缴款,验证码:"+msgCode+",付款卡尾号:"
				+account.getAccountno().substring(account.getAccountno().length()-4)
				+",金额:"+amount+"元.如有疑问请停止操作。【工商银行】";
			logger.log(">>>>>>>>>>>>>>②【"+account.getDevid()+"】,短信内容【"+msg+"】");
			//3.将短信验证码和当前时间放到session中
			session.setAttribute("MsgStart", System.currentTimeMillis()+"");
			session.setAttribute("MsgCode", msgCode);
			//4.发送短信
			boolean flag = new SendPhoneMsg().sendToClient(account.getTelphone(), msg, "serverIp", "serverPort", "user", "passwd", 1, logger);
			logger.log(">>>>>>>>>>>>>>③【"+account.getDevid()+"】,短信发送成功!");
			return flag+"";
		} catch (Exception e) {
			this.logSxFcError("缴费短信发送【PayInfoController.java/sendMsg.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	
	/**
	 * 缴费
	 * 上送字段：银行卡号，投注站编号，金额（分），地区号，负责人姓名，投注站地址
	 * @throws Exception 
	 * */
	@RequestMapping("/pay.do")
	public String pay(HttpServletRequest request,HttpSession session) throws Exception {
		try {
			String msgStart = (String)session.getAttribute("MsgStart");//获取验证码的开始时间
			String msgCode = (String)session.getAttribute("MsgCode");//获取验证码
			msgCode = msgCode==null?"":msgCode;
			String amount = request.getParameter("p_amount");//缴费金额
			String lsh = request.getParameter("p_lsh");//获取页面传过来的流水号
			//将流水号存起来
			request.setAttribute("SysLsh", lsh);
			String p_msgCode = request.getParameter("p_msg");//获取输入的验证码
			String pwd = request.getParameter("p_pwd");//获取缴费密码
			Account account = (Account)session.getAttribute("payDetail");
			if(account == null){
				request.setAttribute("alertMsg", "系统已超时!");
				return "pay/payError";
			}
			logger.log("======================================================");
			logger.log(">>>>>>>>>>>>>>【"+account.getDevid()+"】缴费开始>>>>>>>>>>>>>>>>>>>>");
			logger.log(">>>>>>>>>>>>>>①【"+account.getDevid()+"】,lsh:"+lsh+",系统验证码："+msgCode+",客户输入验证码："+p_msgCode
					+",验证码产生时间："+msgStart+",客户输入时间："+System.currentTimeMillis()+"(纳秒)");
			if(msgStart != null){
				double msgTime = Double.parseDouble(msgStart);
				if(System.currentTimeMillis() - msgTime > 120000){
					request.setAttribute("alertMsg", "验证码已过期!");
					return "pay/payDetail";
				}
			}else{
				request.setAttribute("alertMsg", "无效验证码!");
				return "pay/payDetail";
			}
			if(msgCode.equals(p_msgCode) == false){
				request.setAttribute("alertMsg", "验证码输入错误!");
				return "pay/payDetail";
			}
			if(account.getPwd().equals(pwd) == false){
				request.setAttribute("alertMsg", "缴费密码输入错误!");
				return "pay/payDetail";
			}
			
			//查询更新到最新信息
			logger.log(">>>>>>>>>>>【"+account.getDevid()+"】准备开始缴费...>>>>>>>>>>>>>>");
			//前端防重复时间极短的情况下拦截不住，此处再做一次拦截
			if(trxDetailDao.getOneDetail(account.getDevid(), lsh) != null){
				logger.log("【"+account.getDevid()+"】---->>>>流水号:"+lsh+"已经缴款!");
				request.setAttribute("alertMsg", "此流水号已经缴款,请查看明细!");
				Thread.sleep(3000);
				return "pay/payError";
			}
			//1.入库缴费请求记录，状态记为未知
			TrxDetail trxObj = new TrxDetail();
			trxObj.setAmt(amount);//单位为元
			trxObj.setDate8(VDate.getCurDate8());
			trxObj.setDevid(account.getDevid());
			trxObj.setDevidname(account.getDevidaddr());
			trxObj.setLsh(lsh);
			trxObj.setPaycardno(account.getAccountno());
			trxObj.setTrx_flag(TrxDetail.UNKNOWN);//状态未知
			trxObj.setType(account.getType());
			trxObj.setUsername(account.getUsername());
			trxDetailDao.insertOne(trxObj);
			logger.log("【"+trxObj.getDevid()+"】---->>>>①缴费流水号【"+lsh+"】已经入库,初始状态为未知>>>>>>>>");
			
			//2.拼接89011交易请求报文
			StringBuffer send89011 = new StringBuffer();
			send89011.append("0").append("|");//FLOWFLAG--固定
			send89011.append(account.getType()).append("|");//UNITNO--固定
			send89011.append("0023").append("|");//AGENTCODE--固定
			send89011.append("01").append("|");//AGENTFLAG--固定
			send89011.append("410").append("|");//CHANTYPE--固定
			send89011.append("00502").append("|");//AGENTZONENO--地区号
			send89011.append("0").append("|");//AGENTMBRNO--固定
			send89011.append("0").append("|");//AGENTTLACTBRNO--固定
			send89011.append("0").append("|");//AGENTBRNO--固定
			send89011.append("").append("|");//AGENTTELLER--固定
			send89011.append("").append("|");//IFTRXSERNB--固定
			send89011.append("").append("|");//ACCTYPE--固定
			send89011.append(account.getAccountno()).append("|");//ACCNO--银行卡号
			send89011.append("").append("|");//ACCPWD--固定
			send89011.append(account.getDevid()).append("|");//ACCPWD--投注站编号
			send89011.append("").append("|");//SUBUSERNO
			send89011.append(account.getUsername()).append("|");//站点负责人姓名
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append(AmountUtil.changeY2F(amount)).append("|");//AMOUNT--缴费金额（单位分）
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append(lsh).append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append(account.getDevidaddr()).append("|");//投注站地址
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			send89011.append("").append("|");//固定为空
			logger.log("【"+trxObj.getDevid()+"】---->>>>②缴费流水号【"+lsh+"】准备往GTCG发起89011缴费交易>>>>>>>>");
			logger.log("【"+trxObj.getDevid()+"】---->>>>③缴费流水号【"+lsh+"】缴费请求报文【"+send89011.toString()+"】");
			//3.发送请求报文
			 String revString = SendToGtcg.commCospOnlineBatch(logger, config, "89011", send89011.toString());
			//4.拆解请求报文
			 Trx89011 obj_89011 = new Trx89011().splitResponseString(revString);
			 logger.log("【"+trxObj.getDevid()+"】---->>>>④缴费流水号【"+lsh+"】拆解返回报文【"+obj_89011.toString()+"】");
			 TrxDetail updateObj = trxDetailDao.getOneDetail(trxObj.getDevid(), trxObj.getLsh());
			 if(FcUtils.isAllChar0(obj_89011.getRetCode())){
				 //5.更新状态,返回码已在构造函数中做了处理，此处只需更新库即可
				 //a.先确认数据库中是否有这条记录,若缴费失败此笔记录，提供查询功能
				 if(updateObj != null){
					 //设定主机返回信息
					 updateObj.setErrmsg(obj_89011.getRetMsg());
					 //更新交易标识
					 updateObj.setTrx_flag(TrxDetail.SUCCESS);
					 //设定主机返回日期
					 updateObj.setWorkdate(obj_89011.getWorkdate()+" "+obj_89011.getWorktime());
					 //设定主机返回流水号
					 updateObj.setBusiserialno(obj_89011.getBusiSerialNo());
					 trxDetailDao.updateOne(updateObj);
					 logger.log("【"+trxObj.getDevid()+"】---->>>>⑤缴费流水号【"+lsh+"】交易成功!");
					 logger.log("【"+trxObj.getDevid()+"】---->>>>⑥缴费流水号【"+lsh+"】数据库交易状态更新成功!");
					//更新对象
					session.setAttribute("userInfo", account);
					session.setAttribute("s_alertMsg", "缴费【"+obj_89011.getRetMsg()+"】");
				 }
				 return "redirect:../search/queryDetails.do";
			 }else{
				//设定主机返回信息
				 updateObj.setErrmsg(obj_89011.getRetMsg());
				 //更新交易标识
				 if("".equals(obj_89011.getRetCode())){
					 logger.log("【"+trxObj.getDevid()+"】---->>>>⑤缴费流水号【"+lsh+"】缴费未知!");
				 }else{
					 updateObj.setTrx_flag(TrxDetail.FAIL);
					 trxDetailDao.updateOne(updateObj);
					 logger.log("【"+trxObj.getDevid()+"】---->>>>⑤缴费流水号【"+lsh+"】缴费失败!");
					 logger.log("【"+trxObj.getDevid()+"】---->>>>⑥缴费流水号【"+lsh+"】数据库交易状态更新成功!");
				 }
				 request.setAttribute("alertMsg", obj_89011.getRetMsg());
				 return "pay/payError";
			 }
		} catch (Exception e) {
			this.logSxFcError("缴费第三步【PayInfoController.java/pay.do】异常", e);
			throw new Exception(e);
		}
		 
	}
	
	/**
	 * 当日冲正
	 * 条件:1.日期为当天
	 *      2.缴款成功的记录
	 *      3.冲正金额与缴纳金额必须一样
	 * @throws Exception 
	 * */
	/*@RequestMapping("/chongZheng.do")
	public String chongZheng(HttpSession session,HttpServletRequest request,Model model) throws Exception{
		String devid =  request.getParameter("p_devid");
		String lsh =  request.getParameter("p_lsh");
		BindInfo userInfo = (BindInfo) session.getAttribute("userInfo");
		//1.查询冲正记录
		TrxDetail obj = trxDetailDao.getOneDetail(devid, lsh);
		if(obj == null){
			request.setAttribute("alertMsg", "找不到对应的缴费记录!");
		}
		if(TrxDetail.CHONGZHENG.equals(obj.getTrx_flag())){
			request.setAttribute("alertMsg", "此条记录已经撤销!");
		}else{
			logger.log(">>>>>>>>>>>【"+obj.getDevid()+"】准备开始冲正...>>>>>>>>>>>>>>");
			logger.log(">>>>>>>>>>>冲正流水号【"+obj.getLsh()+"】>>>>>>>>>>>>>>");
			//2.拼接89012交易请求报文
			StringBuffer send89012 = new StringBuffer();
			send89012.append("0").append("|");//FLOWFLAG --固定'0'
			send89012.append(obj.getType()).append("|");//UNITNO--缴款所属系统('0'—电脑彩票,'1'—网点即开票)
			send89012.append("0023").append("|");//AGENTCODE--固定'0023'
			send89012.append("01").append("|");//AGENTFLAG--固定'01'
			send89012.append("410").append("|");//CHANTYPE--固定'410'
			send89012.append("00502").append("|");//AGENTZONENO--地区号(取签约绑定)
			send89012.append("0").append("|");//AGENTMBRNO--固定'0'
			send89012.append("0").append("|");//AGENTTLACTBRNO--固定'0'
			send89012.append("0").append("|");//AGENTBRNO--固定'0'
			send89012.append("00010").append("|");//AGENTTELLER
			send89012.append("").append("|");//IFTRXSERNB
			send89012.append(obj.getBusiserialno()).append("|");//PRESERIALNO--原交易流水号
			send89012.append("").append("|");//ACCTYPE
			send89012.append(obj.getPaycardno()).append("|");//ACCNO--缴款银行卡号（必须一致）
			send89012.append("").append("|");//ACCPWD
			send89012.append(obj.getDevid()).append("|");//USERNO--投注站编号
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
			send89012.append(AmountUtil.changeY2F(obj.getAmt())).append("|");//AMOUNT--原缴费金额（必须一致）
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
			logger.log("【"+obj.getDevid()+"】---->>>>①缴费流水号【"+lsh+"】准备往GTCG发起89012冲正交易>>>>>>>>");
			logger.log("【"+obj.getDevid()+"】---->>>>②缴费流水号【"+lsh+"】缴费请求报文【"+send89012.toString()+"】");
			//3.发送请求报文
			String revString = SendToGtcg.commCospOnlineBatch(logger, config, "89012", send89012.toString());
			//4.拆解请求报文
			Trx89012 obj_89012 = new Trx89012().splitResponseString(revString);
			logger.log("【"+obj.getDevid()+"】---->>>>③缴费流水号【"+lsh+"】拆解返回报文【"+obj_89012.toString()+"】");
			//交易成功
			if(FcUtils.isAllChar0(obj_89012.getRetCode())){
				//更新数据库
				obj.setTrx_flag(TrxDetail.CHONGZHENG);
				obj.setWorkdate(obj_89012.getWorkdate()+" "+obj_89012.getWorktime());
				obj.setCzserialno(obj_89012.getCZSerialNo());
				obj.setErrmsg(obj_89012.getRetMsg());
				trxDetailDao.updateOne(obj);
				request.setAttribute("alertMsg", "本笔缴费已经撤销!");
				logger.log("【"+obj.getDevid()+"】---->>>>④缴费流水号【"+lsh+"】已冲正,更新数据库成功!");
			}else{
				request.setAttribute("alertMsg", "本笔缴费撤销失败!"+obj_89012.getRetMsg());
				logger.log("【"+obj.getDevid()+"】---->>>>④缴费流水号【"+lsh+"】冲正失败!");
				logger.log("【"+obj.getDevid()+"】---->>>>冲正失败："+obj_89012.getRetCode()+"|"+obj_89012.getRetMsg());
			}
		}
		TrxDetail oneDtl = trxDetailDao.getOneDetail(obj.getDevid(), lsh);
		model.addAttribute("oneDtl", oneDtl);
		return "detail/oneDetail";
	}*/
	
	/**生成验证码*/
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
			this.logSxFcError("生成验证码【PayInfoController.java/createImage.do】异常", e);
			throw new Exception(e);
		}
		
	}
	
	
}
