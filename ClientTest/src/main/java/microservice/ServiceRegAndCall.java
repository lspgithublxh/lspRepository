package microservice;

/**
 * 
 * @ClassName:ServiceRegAndCall
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月20日
 * @Version V1.0
 * @Package microservice
 */
public class ServiceRegAndCall {

	static ServiceRegAndCall instance = new ServiceRegAndCall();
	
	public static void main(String[] args) {
		instance.start();
	}

	private void start() {
		//监听注册端口 、 调用端口;; 注册端口存储服务名-服务调用路径等配置信息 
		//
	}
}
