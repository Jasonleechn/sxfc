package com.xkxx.log;

import ToolsApi.zxy.com.util.log.VLoger;

/**
 * 福彩前段日志记录
 * @author sxfh-jcz3
 *
 */
public class Logger_FC_Web extends ILog{

	public Logger_FC_Web() {
		//按天存储
		super(VLoger.Type.DayPath, "SXFC_WEB.log");
	}
	

}
