package com.dengqin.mina;

/**
 * TCP配置
 */
public class TcpConfig {

	/** 服务器IP */
	private String ipAddress;

	/** 服务器端口 */
	private int port;

	/** 超时时间 毫秒 */
	private long connectionTimeout;

	/** 字符集 */
	private String encoding;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String toString() {
		return "TcpConfig{" + "ipAddress='" + ipAddress + '\'' + ", port=" + port + ", connectionTimeout="
				+ connectionTimeout + ", encoding='" + encoding + '\'' + '}';
	}
}
