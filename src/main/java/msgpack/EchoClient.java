package msgpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class EchoClient {

	public void connet(int port, String host) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
							ch.pipeline().addLast("msgpack decoder", new MsgPackDecoder());
							ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
							ch.pipeline().addLast("msgpack encoder", new MsgPackEncoder());
							ch.pipeline().addLast(new EchoClientHandler(10));
						}
					});
			// 发起异步连接操作
			ChannelFuture cf = bootstrap.connect(host, port).sync();
			// 等待客户端链路关闭
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String args[]) {
		int port = 8080;
		new EchoClient().connet(port, "127.0.0.1");
	}
}
