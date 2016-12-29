package rmi;

import java.rmi.Remote;

public interface IService extends Remote {

	public String test_service(String info);
}
