package com.example.asyncprogramming.SimpleNettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;

// Main Class

/**
 * Netty Server 생성
 * Netty TCP 서버를 생성하기 위해서 수행하는 과정
 * 1. EventLoopGourp 생성
 * 2. ServerBootstrap 생성 후 설정
 * 3. ChannelInitializer 생성
 */
public class EchoServer {

    private static final int SERVER_PORT = 11011;

    private final ChannelGroup allChannels = new DefaultChannelGroup("server", GlobalEventExecutor.INSTANCE);
    private EventLoopGroup bossEventLoopGroup;
    private EventLoopGroup workerEventLoopGroup;

    public void startServer() {
        // EventLoopGroup 생성
        // NIO 기반의 EventLoop 생성. bossEventLoopGroup은 서버소켓을 listen하고,
        // 여기서 만들어진 Channel에서 넘어온 데이터는 workerEventLoopGroup에서 처리된다.
        // Boss Thread 는 ServerSocket 을 Listen
        // Worker Thread 는 만들어진 Channel 에서 넘어온 이벤트를 처리
        bossEventLoopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
        workerEventLoopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("worker"));

        // Netty 서버를 생성하기 위한 헬퍼 클래스인 ServerBootstrap 인스턴스 생성
        ServerBootstrap bootstrap = new ServerBootstrap();

        // EventLoopGroup 할당
        bootstrap.group(bossEventLoopGroup, workerEventLoopGroup);

        // Channel 생성시 사용할 클래스 (NIO 소켓을 이용한 채널)
        // 채널을 생성할 때 NIO 소켓을 이용한 채널을 생성하도록 channel() 메소드에 NioServerSocket.class를 인자로 넘겨준다.
        bootstrap.channel(NioServerSocketChannel.class);

        // accept 되어 생성되는 TCP Channel 설정
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        // Client Request를 처리할 Handler 등록
        // 채널 파이프라인을 설정하기 위해 EchoServerInitializer 객체를 할당한다.
        // 서버 소켓에 연결이 들어오면 이 객체가 호출되어 소켓 채널을 초기화 해준다.
        bootstrap.childHandler(new EchoServerInitializer());

        try {
            // Channel 생성후 기다림
            // bootstrap의 bind() 메소드로 서버 소켓에 포트를 바인딩 한다.
            // sync() 메소드를 호출해서 바인딩이 완료될 때까지 기다린다. 이 코드가 지나가면 서버가 시작된다.
            ChannelFuture bindFuture = bootstrap.bind(new InetSocketAddress(SERVER_PORT)).sync();
            Channel channel = bindFuture.channel();
            allChannels.add(channel);

            // Channel이 닫힐 때까지 대기
            bindFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    private void close() {

        allChannels.close().awaitUninterruptibly();
        workerEventLoopGroup.shutdownGracefully().awaitUninterruptibly();
        bossEventLoopGroup.shutdownGracefully().awaitUninterruptibly();

    }

    public static void main(String[] args) {
        new EchoServer().startServer();
    }
}
