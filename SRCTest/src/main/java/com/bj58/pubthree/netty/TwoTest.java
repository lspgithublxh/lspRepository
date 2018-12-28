package com.bj58.pubthree.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.WriteCompletionEvent;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;

public class TwoTest {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ClientBootstrap boots = new ClientBootstrap(factory);
		boots.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline piline = Channels.pipeline();
				piline.addLast("decoder", new StringDecoder());
				piline.addLast("encoder", new StringDecoder());
				piline.addLast("handler", new SimpleChannelHandler() {
					@Override
					public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
						super.channelConnected(ctx, e);
						System.out.println("connect to server");
						ChannelBuffer buffer = ChannelBuffers.buffer(100);
						buffer.writeBytes("message from client".getBytes());
						ctx.getChannel().write(buffer);
					}
					
					@Override
					public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
						super.writeComplete(ctx, e);
						System.out.println("write to server ok");
					}
					
					@Override
					public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
						super.messageReceived(ctx, e);
						System.out.println("received from server:" + e.getMessage());
					}
					
					
					@Override
					public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
						super.exceptionCaught(ctx, e);
						e.getCause().printStackTrace();
						e.getChannel().close();
					}
				});
				return piline;
			}
		});
		boots.connect(new InetSocketAddress("localhost", 12111));
	}
}
