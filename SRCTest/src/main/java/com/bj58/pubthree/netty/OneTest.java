package com.bj58.pubthree.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;

public class OneTest {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newFixedThreadPool(10), Executors.newFixedThreadPool(10));
		ServerBootstrap boots = new ServerBootstrap(factory);
		boots.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline line = Channels.pipeline();
				line.addLast("decoder", new StringDecoder());
				line.addLast("encoder", new StringDecoder());
				line.addFirst("handler", new SimpleChannelHandler() {
					@Override
					public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
						super.channelConnected(ctx, e);
						System.out.println("连接成功---channel connected");
					}
					
					@Override
					public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
						super.messageReceived(ctx, e);
						System.out.println("message receivd");
						BigEndianHeapChannelBuffer mes = (BigEndianHeapChannelBuffer) e.getMessage();
						System.out.println(mes.toString());
						Channel chan = e.getChannel();
						chan.write(mes);
						System.out.println("server return message ok!");
					}
					
					@Override
					public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
						super.exceptionCaught(ctx, e);
						e.getCause().printStackTrace();
						e.getChannel().close();
					}
				});
				return line;
			}
		});
		boots.bind(new InetSocketAddress("localhost", 12111));
		
	}
}
