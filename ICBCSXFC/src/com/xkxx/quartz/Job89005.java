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
 * ��ʱ��ѯ89005��ѯ�ɷ�״̬
 * @author sxfh-jcz3
 *
 */
@Component
public class Job89005 extends BaseController{
	
	@Resource
	Config config;
	
	@Resource
	TrxDetailDao trxDetailDao;
	
	/**����ִ��ʱ��  �� �� ʱ �� �� ��
	 * @throws Exception */
	@Scheduled(cron="0 0 0/2 * * ?")
	public void  doJob2() throws Exception{
		try {
			logger.log("===================================================================");
			logger.log(">>>>>>>>>>>>>>>>>>>>>>���붨ʱ����89005��ѯ����״̬ >>>>>>>>>>>>>>>>>");
			logger.log(">>>>>>>>>>>>>>>>>>>>>>�ٲ�ѯ����״̬Ϊδ֪�ĽɷѼ�¼>>>>>>>>>>>>>>>>>");
			List<TrxDetail> list = trxDetailDao.getAllUnKnownDetail();
			if(list.size() > 0){
				for(TrxDetail obj : list){
					logger.log(">>>>>>>>>>>>>>>>>>>>>>��׼����ѯ��"+obj.getDevid()+"��,��ˮ�š�"+obj.getLsh()+"���ɷѼ�¼�Ľɷ�״̬...");
					Trx89005 req_89005 = new Trx89005();
					req_89005.setFindstartdate(obj.getDate8());
					req_89005.setFindenddate(VDate.getCurDate8());
					req_89005.setAccno(obj.getPaycardno());
					req_89005.setUserno(obj.getDevid());
					req_89005.setUnitno(obj.getType());
					req_89005.setRevtranf("0");//�������ױ�־
					req_89005.setAmount(AmountUtil.changeY2F(obj.getAmt()));//ת���ɷ�
					req_89005.setNote1(obj.getLsh());//����ɷѵ���ˮ��
					logger.log(">>>>>>>>>>>>>>>>>>>>>>����ˮ�š�"+obj.getLsh()+"����ѯ����״̬�������ģ�"+req_89005.req89005());
					//����������
					String revString = SendToGtcg.commCospOnlineBatch(logger, config, "89005", req_89005.req89005());
					//��ⱨ��
					Trx89005 response = req_89005.splitResponseString(revString);
					if(FcUtils.isAllChar0(response.getRetCode())){
						 //���������ڹ��캯�������˴����˴�ֻ����¿⼴��
						if(Integer.parseInt(response.getResp_totalnum())>0){//���ؼ�¼����0��ȥ����
							if(obj != null){
								//�趨����������Ϣ
								obj.setErrmsg(response.getRetMsg());
								//���½��ױ�ʶ
								obj.setTrx_flag(response.getResp_banktradestatus());
								//�趨������������
								obj.setWorkdate(VDate.formatDateString(response.getResp_workdate()+response.getResp_worktime(), "yyyy-MM-dd HH:mm:ss"));
								//�趨����������ˮ��
								obj.setBusiserialno(response.getResp_serialno());
								trxDetailDao.updateOne(obj);
								logger.log("��"+obj.getDevid()+"��---->>>>�ܽɷ���ˮ�š�"+obj.getLsh()+"������״̬�ɹ�!");
							}
						}
					 }
				}
			}else{
				logger.log(">>>>>>>>>>>>>>>>>>>>>>��û��״̬Ϊδ֪�ļ�¼!");
			}
		} catch (Exception e) {
			logger.log("===========================��ʱ����89005��ѯ�ɷ�״̬�����쳣====================");
			logger.log(e.toString());//д��ÿ�����־�ļ��У�����ϸ����־��¼
			logger.logError("����ʱ����89005��---->>>>", e);//д��log/Error.log�ļ��У����ǲ���ϸ
			logger.log("===============================================================================");
			throw new Exception(e);
		}
		
	}

}
