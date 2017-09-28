package com.dengqin.mina;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * Tcp池工厂的实现
 */
public class TcpPoolableObjectFactory implements PoolableObjectFactory {

	/** tcp配置 */
	private TcpConfig config;

	public TcpPoolableObjectFactory(TcpConfig config) {
		this.config = config;
	}

	/**
	 * "激活"对象,当Pool中决定移除一个对象交付给调用者时额外的"激活"操作,
	 * 比如可以在activateObject方法中"重置"参数列表让调用者使用时感觉像一个"新创建"的对象一样;
	 * 如果object是一个线程,可以在"激活"操作中重置"线程中断标记",或者让线程从阻塞中唤醒等;
	 * 如果object是一个socket,那么可以在"激活操作"中刷新通道,或者对socket进行链接重建(假如socket意外关闭)等.
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void activateObject(Object obj) throws Exception {
	}

	/**
	 * 销毁对象,如果对象池中检测到某个"对象"idle的时间超时,或者操作者向对象池"归还对象"时检测到"对象"已经无效,那么此时将会导致"对象销毁";
	 * "销毁对象"的操作设计相差甚远,但是必须明确:当调用此方法时,"对象"的生命周期必须结束.如果object是线程,那么此时线程必须退出;
	 * 如果object是socket操作,那么此时socket必须关闭;如果object是文件流操作,那么此时"数据flush"且正常关闭.
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void destroyObject(Object obj) throws Exception {
		TcpClient client = (TcpClient) obj;
		client.close();
	}

	/**
	 * 创建一个新对象;当对象池中的对象个数不足时,将会使用此方法来"输出"一个新的"对象",并交付给对象池管理.
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object makeObject() throws Exception {
		TcpClient client = new TcpClientImpl();
		client.open(config);
		return client;
	}

	/**
	 * "钝化"对象,当调用者"归还对象"时,Pool将会"钝化对象"; 钝化的言外之意,就是此"对象"暂且需要"休息"一下.如果object是一个
	 * socket,那么可以passivateObject中清除buffer,将socket阻塞;
	 * 如果object是一个线程,可以在"钝化"操作中将线程sleep或者将线程中的某个对象wait.
	 * 需要注意的时,activateObject和passivateObject两个方法需要对应,避免死锁或者"对象"状态的混乱.
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void passivateObject(Object obj) throws Exception {

	}

	/**
	 * 检测对象是否"有效";Pool中不能保存无效的"对象",因此"后台检测线程"会周期性的检测Pool中"对象"的有效性,如果对象无效则会导致此对象从Pool中移除,
	 * 并destroy;此外在调用者从Pool获取一个"对象"时,也会检测"对象"的有效性,确保不能讲"无效"的对象输出给调用者;当调用者使用完毕将"对象归还"到Pool时,
	 * 仍然会检测对象的有效性.所谓有效性,就是此"对象"的状态是否符合预期,是否可以对调用者直接使用;如果对象是Socket,
	 * 那么它的有效性就是socket的通道是否畅通/阻塞是否超时等.
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public boolean validateObject(Object obj) {
		// todo 需要提供一个检查有效性的接口。
		return true;
	}

}
