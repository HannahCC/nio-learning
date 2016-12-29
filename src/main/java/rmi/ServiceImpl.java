package rmi;

public class ServiceImpl implements IService {

	@Override
	public String test_service(String info) {
		return "service>>" + info;
	}

}
