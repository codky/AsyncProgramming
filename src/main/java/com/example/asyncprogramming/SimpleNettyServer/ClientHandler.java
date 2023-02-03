package com.example.asyncprogramming.SimpleNettyServer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * 서버로부터 응답을 받아 화면에 출력해줄 handler
 * 서버로 문자열을 던지면 서버는 문자를 좀 더 붙여서 클라이언트로 던져준다.
 * 클라이언트는 서버로부터 문자열을 받아 channelRead0() 메소드를 호출한다.
 * 이 메소드는 결국 받은 문자열을 화면에 출력한다.
  */
public class ClientHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println((String)msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
