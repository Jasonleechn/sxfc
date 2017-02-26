package com.xkxx.tcpExchange;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.xkxx.entity.Config;
import com.xkxx.log.ILog;

/**
 * ��GTCG���ͽ���
 * @author sxfh-jcz3
 *
 */
public class SendToGtcg {
	
	/**
	 * ��������:��ʼ��Socket����
	 */
	public static Socket initMySocket(ILog logger,String host, int port, int timeout)
	{
		Socket socket = null;
		socket = new Socket();
		SocketAddress endpoint = new InetSocketAddress(host, port);

		try
		{
			socket.connect(endpoint, timeout * 1000);
		}
		catch (SocketTimeoutException ex)
		{
			logger.log("���ӷ�������ʱ��" + host + "����" + port + "����" + timeout + "��",ex);
		}
		catch (IOException e)
		{
			logger.log("GTCG���ӷ������쳣��" + host + "����" + port + "��",e);
		}

		return socket;
	}
	
	/**
	 * ��������:�رշ���������
	 */
	public static void closeMySocket(ILog logger,Socket socket)
	{
		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			logger.log("GTCG�رշ����������쳣",e);
		}
	}
	
	/**
	 * ��������:���ó�ʱʱ��
	 */
	public static boolean setTimeOut(ILog logger,Socket socket, int timeout)
	{
		boolean flag = false;

		try
		{
			socket.setSoTimeout(timeout * 1000);
			flag = true;
		}
		catch (SocketException e)
		{
			logger.log("GTCG����Socke��ʱ����ʧ��",e);
		}

		return flag;
	}
	
	/**
	 * ��������:��������
	 */
	public static boolean sendBuf(Socket socket, byte[] bytes, int len)
	{
		boolean flag = false;
		OutputStream os = null;
		try
		{
			os = socket.getOutputStream();

			os.write(bytes);

			flag = true;

		}
		catch (IOException e)
		{
			System.out.println("Socket���������쳣");
			return flag;
		}

		return flag;
	}
	
	/**
	 * ��������:��ȡ����
	 */
	public static int readBuf(ILog logger,Socket socket, byte[] bytes, int len)
	{
		InputStream is = null;

		int allReadLen = 0;
		try
		{
			is = socket.getInputStream();
			int readlen = 0;
			while ((readlen = is.read(bytes, allReadLen, len - allReadLen)) > 0)
			{
				allReadLen += readlen;
			}

			//System.out.println("���ճ���[" + allReadLen + "],��ȡ����[" + new String(bytes) + "]");
		}
		catch (SocketTimeoutException ex)
		{
			logger.log("GTCGͨѶ�쳣>>>>Socket��ȡ���ݳ�ʱ!",ex);
			return -1;
		}
		catch (IOException e)
		{
			logger.log("GTCGͨѶ�쳣>>>>Socket��ȡ�����쳣",e);
			return -1;
		}
		return allReadLen;
	}
	
	/**
	 * ��������:������ǰ��"COSP������������"����ͨѶ
	 * 
	 * @throws Exception
	 */
	public static String commCospOnlineBatch(ILog logger,Config config ,String trxCode, String senfbuff) throws Exception
	{

		Socket socket = null;
		DataOutputStream dos = null; // ��������������ڷ��ͱ��ĳ���
		DataInputStream dis = null; // ���������������ڽ��շ��ر��ĳ���
		

		String recvbuff = "";
		//��ӡ������Ϣ
		logger.log("������Ϣ��"+config.toString());
		
		logger.log("��"+trxCode+"��---->>>>���������ģ�"+senfbuff);
		
		try
		{

			//����ͨѶǰ��
			socket = initMySocket(logger,config.getHostIp(), Integer.parseInt(config.getHostPort()), Integer.parseInt(config.getTimeOut()));

			//���ó�ʱʱ��
			if (!setTimeOut(logger,socket, Integer.parseInt(config.getTimeOut())))
			{
				logger.log("��GTCGͨѶ�쳣��---->>>>GTCG���ó�ʱʱ�� ��!");
				throw new Exception("���ó�ʱʱ�� ��");
			}

			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			// ��GTCG���Ͱ�ͷ��5λ�Ľ�����
			dos.write(trxCode.getBytes());

			// ����6λҵ������
			dos.write("DSDF  ".getBytes());
			// ���ͱ��ĳ���
			dos.writeInt(senfbuff.getBytes().length);
			// ��GTCG���ͱ���
			dos.write(senfbuff.getBytes());

			// ���շ��ر��ĳ���
			int recDataLen = dis.readInt();
			logger.log("GTCG���ر��ĳ��ȡ�" + String.valueOf(recDataLen) + "��");

			byte[] bs = new byte[recDataLen];
			// ��ȡ���ر�����
			readBuf(logger,socket, bs, recDataLen);
			//dis.read(bs);
			recvbuff = new String(bs);
			
			logger.log("GTCG���ر��ġ�" + recvbuff + "��");
		}
		catch (Exception e)
		{
			logger.log("GTCGͨѶ�쳣---->>>>",e);
		}
		finally
		{
			// �ر�socket����
			closeMySocket(logger,socket);

			if (dis != null)
				dis.close();
			if (dos != null)
				dos.close();

		}

		return recvbuff;
	}

}
