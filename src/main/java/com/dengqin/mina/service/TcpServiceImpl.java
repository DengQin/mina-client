package com.dengqin.mina.service;

import com.dengqin.mina.TcpClient;
import com.dengqin.mina.TcpConfig;
import com.dengqin.mina.TcpPoolableObjectFactory;
import com.dengqin.mina.utils.StringUtils;
import org.apache.commons.pool.impl.StackObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dq on 2017/9/28.
 */
public class TcpServiceImpl implements TcpService {

	private final static Logger log = LoggerFactory.getLogger(TcpServiceImpl.class);

	/**
	 * StackObjectPool利用一个java.util.Stack对象来保存对象池里的对象。这种对象池的特色是：
	 * 可以为对象池指定一个初始的参考大小（当空间不够时会自动增长）。 在对象池已空的时候，调用它的borrowObject方法，会自动返回新创建的实例。
	 * 可以为对象池指定一个可保存的对象数目的上限。达到这个上限之后，再向池里送回的对象会被自动送去回收。
	 */
	private StackObjectPool pool;

	public TcpServiceImpl(String ipAddress, int port, long connectionTimeout, String encoding) {
		TcpConfig config = new TcpConfig();
		config.setConnectionTimeout(connectionTimeout);
		config.setEncoding(encoding);
		config.setIpAddress(ipAddress);
		config.setPort(port);
		TcpPoolableObjectFactory factory = new TcpPoolableObjectFactory(config);

		pool = new StackObjectPool(factory);
	}

	/**
	 * 发送消息，并进行3次重试
	 * 
	 * @param message
	 * @return
	 */
	@Override
	public String send(String message) {
		String result = null;
		int totalTimes = 3;
		for (int times = 1; result == null && times <= totalTimes; times++) {
			try {
				result = this.sendMessage(message);
			} catch (Exception e) {
				log.error("尝试次数[" + times + "]", e);
			}
		}
		return result;
	}

	/**
	 * 执行发送消息
	 */
	private String sendMessage(String message) {
		log.info("远程tcp服务，发送内容:" + message);
		String result;
		TcpClient client = null;
		try {
			client = (TcpClient) pool.borrowObject(); // 从池中获得一个对象
			result = client.send(message);
			if (StringUtils.isBlank(result)) {
				pool.invalidateObject(client);// 使对象失效，不再受池管辖（必须是已经从池中获得的对象）
				log.error("返回字符串为空，清除无用对象");
				return result;
			}
		} catch (Exception e) {
			// 清除无效对象
			if (client != null) {
				try {
					pool.invalidateObject(client);// 使对象失效，不再受池管辖（必须是已经从池中获得的对象）
				} catch (Exception e1) {
					log.error("清除无用对象出错。", e1);
				}
			}
			throw new RuntimeException(e.getMessage(), e);
		}

		// 回收有用对象
		try {
			pool.returnObject(client); // 返回一个对象给池
		} catch (Exception e) {
			log.error("回收连接对象出错。", e);
		}
		return result;
	}

	public void destory() {
		try {
			pool.close();// 关闭池，释放所有与它相关资源
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
