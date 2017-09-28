package com.dengqin.mina;

import com.dengqin.mina.service.TcpServiceImpl;

/**
 * Created by dq on 2017/9/28.
 */
public class MinaTest {
	public static void main(String[] args) {
		TcpServiceImpl t = new TcpServiceImpl("127.0.0.1", 8080, 30000, "UTF-8");

		t.send("测试数据发送。。。。。。。。。。。。。。。");
	}
}
