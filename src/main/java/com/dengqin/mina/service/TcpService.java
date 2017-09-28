package com.dengqin.mina.service;

/**
 * tcp服务
 * 
 * Created by dq on 2017/9/28.
 */
public interface TcpService {

	/**
	 * 发送消息
	 * 
	 * @param message
	 * @return
	 */
	String send(String message);
}
