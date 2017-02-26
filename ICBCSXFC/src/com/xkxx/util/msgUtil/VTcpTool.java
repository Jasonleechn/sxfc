package com.xkxx.util.msgUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

/**
 * Tcp socketͨѶ���࣬ԭ�����ṩ����ͨѶ��������,��������д��һ��
 * 
 * @author txq
 */
public class VTcpTool {

	private Socket socket = null;
	private InputStream input = null;
	private OutputStream output = null;
	private String charEncoding = "GBK"; // ͨѶ���뷽ʽ,Ĭ��GBK

	/**
	 * ���ӵ�������
	 * 
	 * @param serverIp
	 *            ������Ip
	 * @param serverPort
	 *            �˿�
	 */
	public void connect(String serverIp, int serverPort) throws Exception {
		if (serverIp == null || serverIp.trim().length() == 0
				|| serverPort <= 0) {
			throw new Exception("socketServer or port error");
		}
		try {
			this.socket = new Socket(serverIp.trim(), serverPort);
			this.output = this.socket.getOutputStream();
			this.input = this.socket.getInputStream();
			this.socket.setSoTimeout(60000);
		} catch (Exception e) {
			this.close();
			throw e;
		}
	}

	/**
	 * ʹ�����ر�
	 */
	public void close() {
		try {
			if (this.input != null) {
				this.input.close();
			}
		} catch (Exception e) {
		}
		try {
			if (this.output != null) {
				this.output.close();
			}
		} catch (Exception e) {
		}
		try {
			if (this.socket != null) {
				this.socket.close();
			}
		} catch (Exception e) {
		} finally {
			this.socket = null;
		}
	}

	/**
	 * ��������
	 * 
	 * @param type
	 */
	public void sendData(String xml, String type) throws Exception {
		if (this.socket == null) {
			throw new Exception("Socket���Ӳ�����");
		}
		byte[] bs = xml.getBytes(this.charEncoding);
		this.output.write(bs);
		this.output.flush();
	}

	/**
	 * ��ȡ�ַ���ͨѶ����
	 * 
	 * @param type
	 */
	public String receiveData(String type) throws Exception {
		if (this.socket == null) {
			throw new Exception("Socket���Ӳ�����");
		}
		String xml = "";
		try {
			this.socket.setSoTimeout(60000);
			InputStream is = this.socket.getInputStream();
			byte[] bc = _readStream(is); // ������
			xml = new String(bc, this.charEncoding);
			return xml;
		} catch (Exception e) {
			throw e;
		}
	}

	/** �ڲ������������ж�ȡһ�����ݣ�ֱ�������ݿɶ� */
	private byte[] _readStream(InputStream in) throws Exception {
		Vector vec = new Vector();
		try {
			// �������ݴ洢���б���
			while (true) {
				byte[] byRead = new byte[8000];
				int count = in.read(byRead);
				if (count <= 0) {
					break;
				} else {
					vec.add(byRead);
				}
			}
			if (vec.size() == 0) {
				return new byte[0];
			} else if (vec.size() == 1) {
				return (byte[]) vec.get(0);
			}
			// �����ܳ���������ƴװ��һ���µ��ֽ�����
			int length = 0;
			for (int i = 0; i < vec.size(); i++) {
				byte[] bs = (byte[]) vec.get(i);
				length += bs.length;
			}
			// ƴװ��һ���ֽ���
			byte[] ret = new byte[length];
			int pos = 0;
			for (int i = 0; i < vec.size(); i++) {
				byte[] bs = (byte[]) vec.get(i);
				System.arraycopy(bs, 0, ret, pos, bs.length);
				pos += bs.length;
			}
			return ret;
		} finally {
			vec.clear();
			vec = null;
		}
	}
}
