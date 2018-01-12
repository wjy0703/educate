package cn.com.educate.app.rmi;

public interface IHessianService {
	/**
	 * 在负载均衡或集群服务时属性表在写操作时的数据同步
	 * @return	是否操作成功
	 */
	public boolean attrSync(String coding);
}
