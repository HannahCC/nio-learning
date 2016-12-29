package msgpack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

public class Demo {
	
	
	
	public static void demo() throws IOException {
		List<String> src = new ArrayList<>();
		src.add("john");
		src.add("mike");
		src.add("jenny");

		MessagePack msgpack = new MessagePack();
		byte[] raw = msgpack.write(src);
		List<String> dst = msgpack.read(raw, Templates.tList(Templates.TString));
		System.out.println(dst.get(0) + "," + dst.get(1) + "," + dst.get(2));
	}

}
