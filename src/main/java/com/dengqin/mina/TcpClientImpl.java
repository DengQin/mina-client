package com.dengqin.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dengqin.mina.utils.StringUtils;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 建立TCP通信通道，与TCP服务器通信实现
 * 
 * @author dq
 * 
 */
public class TcpClientImpl implements TcpClient {

	private final static Logger log = LoggerFactory.getLogger(TcpClientImpl.class);
	private NioSocketConnector connector;
	private IoSession ioSession;

	/**
	 * 打开链接通道
	 * 
	 * @param config
	 * @return
	 */
	@Override
	public boolean open(TcpConfig config) {
		if (StringUtils.isBlank(config.getIpAddress())) {

			throw new IllegalArgumentException("ip地址为空!");
		}
		if (StringUtils.isBlank(config.getEncoding())) {

			throw new IllegalArgumentException("字符集编码为空!");
		}

		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(config.getConnectionTimeout());
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(config.getEncoding()))));
		connector.getFilterChain().addLast("threadPool", new ExecutorFilter());
		connector.getFilterChain().addLast("logger", new LoggingFilter());

		// 加上，否则无法调用下面的readFuture来从session中读取到服务端返回的信息。
		connector.getSessionConfig().setUseReadOperation(true);
		ConnectFuture future = connector.connect(new InetSocketAddress(config.getIpAddress(), config.getPort()));
		future.awaitUninterruptibly();
		ioSession = future.getSession();

		return true;
	}

	/**
	 * 采用mina发送数据
	 * 
	 * @param message
	 * @return
	 */
	@Override
	public String send(String message) {
		if (ioSession == null) {
			throw new RuntimeException("会话没有开启");
		}
		ioSession.write(message);

		ReadFuture readFuture = null;
		try {
			readFuture = ioSession.read().await();
			return (String) readFuture.getMessage();
		} catch (InterruptedException e) {
			throw new RuntimeException("发送数据被中断", e);
		}
	}

	/**
	 * 关闭通道
	 * 
	 * @return
	 */
	@Override
	public boolean close() {
		ioSession.close(true);
		return false;
	}
}
