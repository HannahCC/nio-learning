package timeserver;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {
	private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
	private byte[] req;
	private int counter;

	public TimeClientHandler() {
		req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
	}

	/**
	 * 当客户端和服务端TCP链路建立成功之后，Netty的NIO线程会调用channelActive方法将请求消息发送给服务端。
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ByteBuf msg; 
		for (int i = 0; i < 1000; i++) {
			msg = Unpooled.buffer(req.length);
			msg.writeBytes(req);
			ctx.writeAndFlush(msg);
		}
	}

	/**
	 * 当服务端回应答消息时，channelRead方法会被调用。
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
		/*
		 * ByteBuf buf = (ByteBuf) msg; byte[] req = new
		 * byte[buf.readableBytes()]; buf.readBytes(req); String body = new
		 * String(req, "UTF-8");
		 */
		String body = (String) msg;
		System.out.println("Now is : " + body + ";the counter is:" + ++counter);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.warning("Unexpected exception from downstream : " + cause.getMessage());
		ctx.close();
	}
}
