package com.dengqin.mina;

/**
 * TCP客户端，建立TCP通信通道，与TCP服务器通信
 * 
 * @author dq
 */
public interface TcpClient {

	/**
	 * 打开TCP连接通道
	 */
	boolean open(TcpConfig config);

	/**
	 * 发送TCP消息
	 */
	String send(String message);

	/**
	 * 关闭TCP连接通道
	 */
	boolean close();
}
