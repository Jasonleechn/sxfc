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
 * 往GTCG发送交易
 * @author sxfh-jcz3
 *
 */
public class SendToGtcg {
	
	/**
	 * 功能描述:初始化Socket连接
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
			logger.log("连接服务器超时【" + host + "】【" + port + "】【" + timeout + "】",ex);
		}
		catch (IOException e)
		{
			logger.log("GTCG连接服务器异常【" + host + "】【" + port + "】",e);
		}

		return socket;
	}
	
	/**
	 * 功能描述:关闭服务器连接
	 */
	public static void closeMySocket(ILog logger,Socket socket)
	{
		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			logger.log("GTCG关闭服务器连接异常",e);
		}
	}
	
	/**
	 * 功能描述:设置超时时间
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
			logger.log("GTCG设置Socke超时参数失败",e);
		}

		return flag;
	}
	
	/**
	 * 功能描述:发送数据
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
			System.out.println("Socket发送数据异常");
			return flag;
		}

		return flag;
	}
	
	/**
	 * 功能描述:读取数据
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

			//System.out.println("接收长度[" + allReadLen + "],读取数据[" + new String(bytes) + "]");
		}
		catch (SocketTimeoutException ex)
		{
			logger.log("GTCG通讯异常>>>>Socket读取数据超时!",ex);
			return -1;
		}
		catch (IOException e)
		{
			logger.log("GTCG通讯异常>>>>Socket读取数据异常",e);
			return -1;
		}
		return allReadLen;
	}
	
	/**
	 * 功能描述:与外联前置"COSP联机批量接入"进行通讯
	 * 
	 * @throws Exception
	 */
	public static String commCospOnlineBatch(ILog logger,Config config ,String trxCode, String senfbuff) throws Exception
	{

		Socket socket = null;
		DataOutputStream dos = null; // 数据输出流，用于发送报文长度
		DataInputStream dis = null; // 数据输入流，用于接收返回报文长度
		

		String recvbuff = "";
		//打印配置信息
		logger.log("配置信息："+config.toString());
		
		logger.log("【"+trxCode+"】---->>>>交易请求报文："+senfbuff);
		
		try
		{

			//连接通讯前置
			socket = initMySocket(logger,config.getHostIp(), Integer.parseInt(config.getHostPort()), Integer.parseInt(config.getTimeOut()));

			//设置超时时间
			if (!setTimeOut(logger,socket, Integer.parseInt(config.getTimeOut())))
			{
				logger.log("【GTCG通讯异常】---->>>>GTCG设置超时时间 错!");
				throw new Exception("设置超时时间 错");
			}

			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			// 向GTCG发送包头，5位的交易码
			dos.write(trxCode.getBytes());

			// 发送6位业务类型
			dos.write("DSDF  ".getBytes());
			// 发送报文长度
			dos.writeInt(senfbuff.getBytes().length);
			// 向GTCG发送报文
			dos.write(senfbuff.getBytes());

			// 接收返回报文长度
			int recDataLen = dis.readInt();
			logger.log("GTCG返回报文长度【" + String.valueOf(recDataLen) + "】");

			byte[] bs = new byte[recDataLen];
			// 读取返回报文体
			readBuf(logger,socket, bs, recDataLen);
			//dis.read(bs);
			recvbuff = new String(bs);
			
			logger.log("GTCG返回报文【" + recvbuff + "】");
		}
		catch (Exception e)
		{
			logger.log("GTCG通讯异常---->>>>",e);
		}
		finally
		{
			// 关闭socket连接
			closeMySocket(logger,socket);

			if (dis != null)
				dis.close();
			if (dos != null)
				dos.close();

		}

		return recvbuff;
	}

}
