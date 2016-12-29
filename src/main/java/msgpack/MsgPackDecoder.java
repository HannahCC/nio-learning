package msgpack;

import java.util.List;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		System.out.println("run here");
		final byte[] array;
		final int length = msg.readableBytes();
		array = new byte[length];
		msg.getBytes(msg.readerIndex(), array, 0, length);
		System.out.println(msg);
		MessagePack messagePack = new MessagePack();
		Object object = messagePack.read(array);
		System.out.println("MsgPackDecoder : " + object + " is instanceOf" + object.getClass().getName());
		out.add(object);
	}

}
