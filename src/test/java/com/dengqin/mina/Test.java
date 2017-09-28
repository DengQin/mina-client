package com.dengqin.mina;

import com.dengqin.mina.service.TcpService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;

/**
 * Created by dq on 2017/9/28.
 */
public class Test {

	public static void main(String[] args) {
		System.setProperty("DWPROJECTNO", "mina");
		System.setProperty("DWENV", "dev");

		ApplicationContext content = new ClassPathXmlApplicationContext("classpath*:/spring/*.xml");
		TcpService tcpService = content.getBean(TcpService.class);
		System.out.println(tcpService.send("nihao....... ggg"));

		HashMap m = new HashMap();
	}
}
