package rmi;

import javax.naming.Context;
import javax.naming.InitialContext;

public class RMIClient {

	public static void main(String[] args){
		try {
			Context naming = new InitialContext();
			IService service = (IService) naming.lookup("rmi://localhost/service");
			Class stubclass =  service.getClass();
			System.out.println(" 是谁的实例 : "+stubclass.getName());
			Class[] interfaces = stubclass.getInterfaces();
			for(Class inter : interfaces){
				System.out.println("实现了接口：" + inter.getName());
			}
			System.out.println(service.test_service("hello"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
