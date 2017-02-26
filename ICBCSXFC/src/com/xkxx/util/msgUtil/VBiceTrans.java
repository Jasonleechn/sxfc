package com.xkxx.util.msgUtil;

import java.io.StringReader;

import javax.swing.JOptionPane;

import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

/**
 * ���״���֮Bice����ͨѶ
 * 
 * @author Tangxq
 */
public class VBiceTrans {
	private String retcode = "J999";	/** ������ 00000��ʾ�ɹ�,���඼Ϊʧ�� */
	private String retmsg = "Unknown Error";
	private String fromBiceString = "";
	private String bodyCols = "";	/** ���ذ�����Ϣ */
	
	public String getRetcode() {
		return this.retcode;
	}

	public String getRetmsg() {
		return this.retmsg;
	}
	//���ذ�����Ϣ
	public String getFromBiceString (){
		return this.fromBiceString;
	}
	
	//���ذ�����Ϣ
	public String getTransBody (){
		return this.bodyCols;
	}

	/** �����Ƿ�ɹ� */
	public boolean isSucceed() {
		return isAllChar0(this.retcode);
	}
	
	/**
	 * ��Bice��һ�α��Ľ��������ؽ������
	 * 
	 * @param pSendMsg
	 *            Ҫ���͵ı���
	 * @throws Exception
	 */
	public boolean sendmsg(String pSendMsg, String gtcgIp, String gtcgPort) throws Exception {
	
		if (pSendMsg == null || pSendMsg.trim().length() == 0) {
			showTips("���ͱ���Ϊ�գ�");
			return false;
		}

		VTcpTool tcp = new VTcpTool();
		try {
			// ���Ͳ�����
			tcp.connect(gtcgIp, Integer.parseInt(gtcgPort));
			tcp.sendData(pSendMsg, "bice");
			fromBiceString = tcp.receiveData("bice"); // bice���صı���
			
			if (fromBiceString == null || fromBiceString.trim().length() == 0) {
				showTips("���ձ��Ľ��Ϊ�գ�");
				return false;
			}
		} catch (Exception ex)
		{
			showTips("�����˽���ʧ�ܣ�");
			return false;
		}
		finally {
			tcp.close();
		}
		
		String[] allCols = fromBiceString.split("\\|"); // ��|�ָ�������
		if (allCols.length >= 2) {
			this.retcode = allCols[0].trim();
			this.retmsg = allCols[1].trim();
		}else
		{
			showTips("�޷���ȡ������Ϣ��ͷ��");
			return false;
		}
		
		if (this.isSucceed() == false) {
			return true; // ͨ�����׷������жϽ��ײ��ɹ������´�����
		}
		
		//������������xml����
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
			showTips("�޷��������ر���");
			return false;
		}
		
		
		// �����׷������ֶ��⣬�����ֶ�copyһ�ݣ��������ദ��
		this.bodyCols = allCols[2].trim();

		return true;
	}
	
	// �Ƿ�ȫ���ַ��������Զ��ض����ҵĿո�
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
	//��ʾ
	private void showTips(String tipsMsg){	
		Object[] options = { "ȷ��"};
		JOptionPane.showOptionDialog(null, tipsMsg, "��ʾ�Ի���",
								JOptionPane.YES_OPTION , JOptionPane.INFORMATION_MESSAGE,
								null, options, options[0]);
	}
}
