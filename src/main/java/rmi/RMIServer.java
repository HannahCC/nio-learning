package rmi;

import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;

/*
 * 太老的技术了。。不知道现在应该要怎么做。。算了
 */
public class RMIServer {
	public static void main(String args[]) {
		try {
			IService service = new ServiceImpl();
			Context nameing = new InitialContext();
			LocateRegistry.createRegistry(10990);
			nameing.rebind("rmi://localhost/service/", service);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("服务器向命名表注册了一个远程服务对象。");
		
	}
	
}
