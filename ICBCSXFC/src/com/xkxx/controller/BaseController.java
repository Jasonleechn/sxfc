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
		logger.log(e.toString());//写到每天的日志文件中，有详细的日志记录
		logger.logError("【"+ errMsg +"】---->>>>", e);//写到log/Error.log文件中，但是不详细
		
	}
	
}
