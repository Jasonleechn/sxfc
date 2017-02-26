package com.xkxx.util.msgUtil;

import java.io.StringReader;

import javax.swing.JOptionPane;

import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

/**
 * 交易处理之Bice报文通讯
 * 
 * @author Tangxq
 */
public class VBiceTrans {
	private String retcode = "J999";	/** 返回码 00000表示成功,其余都为失败 */
	private String retmsg = "Unknown Error";
	private String fromBiceString = "";
	private String bodyCols = "";	/** 返回包体信息 */
	
	public String getRetcode() {
		return this.retcode;
	}

	public String getRetmsg() {
		return this.retmsg;
	}
	//返回包体信息
	public String getFromBiceString (){
		return this.fromBiceString;
	}
	
	//返回包体信息
	public String getTransBody (){
		return this.bodyCols;
	}

	/** 交易是否成功 */
	public boolean isSucceed() {
		return isAllChar0(this.retcode);
	}
	
	/**
	 * 与Bice做一次报文交互，返回结果报文
	 * 
	 * @param pSendMsg
	 *            要发送的报文
	 * @throws Exception
	 */
	public boolean sendmsg(String pSendMsg, String gtcgIp, String gtcgPort) throws Exception {
	
		if (pSendMsg == null || pSendMsg.trim().length() == 0) {
			showTips("发送报文为空！");
			return false;
		}

		VTcpTool tcp = new VTcpTool();
		try {
			// 发送并接收
			tcp.connect(gtcgIp, Integer.parseInt(gtcgPort));
			tcp.sendData(pSendMsg, "bice");
			fromBiceString = tcp.receiveData("bice"); // bice返回的报文
			
			if (fromBiceString == null || fromBiceString.trim().length() == 0) {
				showTips("接收报文结果为空！");
				return false;
			}
		} catch (Exception ex)
		{
			showTips("与服务端交互失败！");
			return false;
		}
		finally {
			tcp.close();
		}
		
		String[] allCols = fromBiceString.split("\\|"); // 以|分隔的数据
		if (allCols.length >= 2) {
			this.retcode = allCols[0].trim();
			this.retmsg = allCols[1].trim();
		}else
		{
			showTips("无法读取返回信息包头！");
			return false;
		}
		
		if (this.isSucceed() == false) {
			return true; // 通过交易返回码判断交易不成功不向下处理了
		}
		
		//解析返回来的xml报文
		try{
			String strRetXML = allCols[2].trim();
			StringReader xmlReader = new StringReader(strRetXML);
			InputSource xmlSource = new InputSource(xmlReader);
			SAXBuilder builder = new SAXBuilder();
			
			org.jdom.Document doc = builder.build(xmlSource);
			org.jdom.Element elt = doc.getRootElement();
			
			this.retcode = elt.getChild("result").getText();
			this.retmsg = elt.getChild("content").getText();
		}
		catch(Exception ex){
			showTips("无法解析返回报文");
			return false;
		}
		
		
		// 除交易返回码字段外，其它字段copy一份，传给子类处理
		this.bodyCols = allCols[2].trim();

		return true;
	}
	
	// 是否全零字符串，会自动截断左右的空格
	public static boolean isAllChar0(String s) {
		if (s == null || s.trim().length() == 0) {
			return false;
		}
		String code = s.trim();
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) != '0') {
				return false;
			}
		}
		return true;
	}
	//提示
	private void showTips(String tipsMsg){	
		Object[] options = { "确定"};
		JOptionPane.showOptionDialog(null, tipsMsg, "提示对话框",
								JOptionPane.YES_OPTION , JOptionPane.INFORMATION_MESSAGE,
								null, options, options[0]);
	}
}
