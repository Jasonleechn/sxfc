package com.xkxx.quartz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ToolsApi.zxy.com.util.date.VDate;

import com.xkxx.controller.BaseController;
import com.xkxx.dao.TrxDetailDao;
import com.xkxx.entity.Config;
import com.xkxx.entity.TrxDetail;
import com.xkxx.tcpExchange.SendToGtcg;
import com.xkxx.tcpExchange.Trx89005;
import com.xkxx.util.AmountUtil;
import com.xkxx.util.FcUtils;

/**
 * 定时查询89005查询缴费状态
 * @author sxfh-jcz3
 *
 */
@Component
public class Job89005 extends BaseController{
	
	@Resource
	Config config;
	
	@Resource
	TrxDetailDao trxDetailDao;
	
	/**设置执行时间  秒 分 时 日 月 周
	 * @throws Exception */
	@Scheduled(cron="0 0 0/2 * * ?")
	public void  doJob2() throws Exception{
		try {
			logger.log("===================================================================");
			logger.log(">>>>>>>>>>>>>>>>>>>>>>进入定时任务89005查询交易状态 >>>>>>>>>>>>>>>>>");
			logger.log(">>>>>>>>>>>>>>>>>>>>>>①查询所有状态为未知的缴费记录>>>>>>>>>>>>>>>>>");
			List<TrxDetail> list = trxDetailDao.getAllUnKnownDetail();
			if(list.size() > 0){
				for(TrxDetail obj : list){
					logger.log(">>>>>>>>>>>>>>>>>>>>>>②准备查询【"+obj.getDevid()+"】,流水号【"+obj.getLsh()+"】缴费记录的缴费状态...");
					Trx89005 req_89005 = new Trx89005();
					req_89005.setFindstartdate(obj.getDate8());
					req_89005.setFindenddate(VDate.getCurDate8());
					req_89005.setAccno(obj.getPaycardno());
					req_89005.setUserno(obj.getDevid());
					req_89005.setUnitno(obj.getType());
					req_89005.setRevtranf("0");//正反交易标志
					req_89005.setAmount(AmountUtil.changeY2F(obj.getAmt()));//转换成分
					req_89005.setNote1(obj.getLsh());//申请缴费的流水号
					logger.log(">>>>>>>>>>>>>>>>>>>>>>③流水号【"+obj.getLsh()+"】查询交易状态的请求报文："+req_89005.req89005());
					//发送请求报文
					String revString = SendToGtcg.commCospOnlineBatch(logger, config, "89005", req_89005.req89005());
					//拆解报文
					Trx89005 response = req_89005.splitResponseString(revString);
					if(FcUtils.isAllChar0(response.getRetCode())){
						 //返回码已在构造函数中做了处理，此处只需更新库即可
						if(Integer.parseInt(response.getResp_totalnum())>0){//返回记录大于0才去更新
							if(obj != null){
								//设定主机返回信息
								obj.setErrmsg(response.getRetMsg());
								//更新交易标识
								obj.setTrx_flag(response.getResp_banktradestatus());
								//设定主机返回日期
								obj.setWorkdate(VDate.formatDateString(response.getResp_workdate()+response.getResp_worktime(), "yyyy-MM-dd HH:mm:ss"));
								//设定主机返回流水号
								obj.setBusiserialno(response.getResp_serialno());
								trxDetailDao.updateOne(obj);
								logger.log("【"+obj.getDevid()+"】---->>>>④缴费流水号【"+obj.getLsh()+"】更新状态成功!");
							}
						}
					 }
				}
			}else{
				logger.log(">>>>>>>>>>>>>>>>>>>>>>②没有状态为未知的记录!");
			}
		} catch (Exception e) {
			logger.log("===========================定时任务89005查询缴费状态交易异常====================");
			logger.log(e.toString());//写到每天的日志文件中，有详细的日志记录
			logger.logError("【定时任务89005】---->>>>", e);//写到log/Error.log文件中，但是不详细
			logger.log("===============================================================================");
			throw new Exception(e);
		}
		
	}

}
