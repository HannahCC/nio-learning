package msgpack;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import pojo.UserInfo;

public class EchoClientHandler extends ChannelHandlerAdapter {
	private static final Logger logger = Logger.getLogger(EchoClientHandler.class.getName());
	private int counter;

	public EchoClientHandler(int counter) {
		this.counter = counter;
	}

	/**
	 * 当客户端和服务端TCP链路建立成功之后，Netty的NIO线程会调用channelActive方法将请求消息发送给服务端。
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		UserInfo[] users = new UserInfo[counter];
		for (int i = 0; i < counter; i++) {
			users[i] = new UserInfo();
			users[i].setAge(i);
			users[i].setName("ABCDEFG--->" + i);
		}
		for (UserInfo user : users) {
			ctx.writeAndFlush(user);
			System.out.println("Client send : " + user);
		}
	}

	/**
	 * 当服务端回应答消息时，channelRead方法会被调用。
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
		System.out.println("Client receive the msgback message : " + msg);
		//ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.warning("Unexpected exception from downstream : " + cause.getMessage());
		ctx.close();
	}
}
