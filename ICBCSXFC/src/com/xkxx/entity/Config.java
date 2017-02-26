package com.xkxx.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



/**
 * GTCG���á���־·������
 * @author sxfh-jcz3
 *
 */
@Component("config")
public class Config {
	
	private String hostIp;//gtcg IP
	private String hostPort;//gtcg PORT
	private String timeOut;//gtcg ��������
	private String logDir;//��־·������
	private String serverIp;//���ŷ�������IP
	private String serverPort;//���ŷ������Ķ˿�
	private String msgUser;//���ŷ��������û���
	private String msgPasswd;//���ŷ�����������
	
	public String getHostIp() {return hostIp;}
	public String getHostPort() {return hostPort;}
	public String getTimeOut() {return timeOut;}
	public String getLogDir() {return logDir;}
	public String getServerIp() {return serverIp;}
	public String getServerPort() {return serverPort;}
	public String getMsgUser() {return msgUser;}
	public String getMsgPasswd() {return msgPasswd;}
	
	public void setMsgUser(@Value("#{propertiesReader[msgUser]}")String msgUser) {this.msgUser = msgUser;}
	public void setMsgPasswd(@Value("#{propertiesReader[msgPasswd]}")String msgPasswd) {this.msgPasswd = msgPasswd;}
	public void setHostIp(@Value("#{propertiesReader[hostIp]}")String hostIp) {this.hostIp = hostIp;}
	public void setHostPort(@Value("#{propertiesReader[hostPort]}")String hostPort) {this.hostPort = hostPort;}
	public void setTimeOut(@Value("#{propertiesReader[timeOut]}")String timeOut) {this.timeOut = timeOut;}
	public void setLogDir(@Value("#{propertiesReader[logDir]}")String logDir) {this.logDir = logDir;}
	public void setServerIp(@Value("#{propertiesReader[serverIp]}")String serverIp) {this.serverIp = serverIp;}
	public void setServerPort(@Value("#{propertiesReader[serverPort]}")String serverPort) {this.serverPort = serverPort;}
	
	@Override
	public String toString() {
		return "Config [hostIp=" + hostIp + ", hostPort=" + hostPort
				+ ", logDir=" + logDir + ", msgPasswd=" + msgPasswd
				+ ", msgUser=" + msgUser + ", serverIp=" + serverIp
				+ ", serverPort=" + serverPort + ", timeOut=" + timeOut + "]";
	}
	
	
	
	
}
