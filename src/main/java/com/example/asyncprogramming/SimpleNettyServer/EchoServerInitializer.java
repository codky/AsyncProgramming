package com.example.asyncprogramming.SimpleNettyServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

// Server 측 채널 파이프라인을 구성하는 Initializer Class
public class EchoServerInitializer extends ChannelInitializer<SocketChannel> {


    // ChannelInitializer의 핵심은 initChannel() 메소드
    // 이 메소드의 역할은 채널 파이프라인을 만들어주는 것이다.
    // TCP 연결이 accept 되었을 때 실행된다.
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LineBasedFrameDecoder(65536));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new EchoServerHandler());
    }
}
