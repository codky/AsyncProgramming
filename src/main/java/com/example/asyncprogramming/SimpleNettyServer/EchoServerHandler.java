package com.example.asyncprogramming.SimpleNettyServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

// 클라이언트로부터 메시지를 받았을 때, 처리할 Handler 클래스
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    // 클라이언트로부터 메시지가 날라오면 실행되는 메소드
    // 문자열을 전달받아서 채널에 "Response : "문자열과 "received\n" 문자열을 앞뒤에 붙여서 다시 전달해준다.
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String message = (String) msg;

        Channel channel = ctx.channel();
        channel.writeAndFlush("Response : '" + message + "' received\n");

        if ("quit".equals(message)) {
            ctx.close();
        }
    }
}
