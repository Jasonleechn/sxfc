package com.xkxx.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import ToolsApi.global.VWorkSpace;

import com.xkxx.entity.Config;
import com.xkxx.log.Logger_FC_Web;
import com.xkxx.util.DateEditor;

@Component
public class BaseController {
	
	public Logger_FC_Web logger = new Logger_FC_Web();
	
	@Resource
	Config config;
	
	@PostConstruct
	public void setParm(){
		VWorkSpace.setFullWorkdDir(config.getLogDir());
	}
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(
				java.sql.Date.class, new DateEditor());
    }
	
	public void logSxFcError(String errMsg,Exception e){
		logger.log("==================="+ errMsg +"=================");
		logger.log(e.toString());//д��ÿ�����־�ļ��У�����ϸ����־��¼
		logger.logError("��"+ errMsg +"��---->>>>", e);//д��log/Error.log�ļ��У����ǲ���ϸ
		
	}
	
}
