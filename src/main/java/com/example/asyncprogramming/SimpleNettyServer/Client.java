package com.example.asyncprogramming.SimpleNettyServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * 서버에 문자열을 전송하는 클라이언트 구현 메인 클래스
 * 1. EventLoopGroup 생성
 * 2. Bootstrap 생성 및 설정
 * 3. ChannelInitializer 생성
 * 4. Client 시작
 */
public class Client {
    private static final int SERVER_PORT = 11011;
    private final String host;
    private final int port;

    private Channel serverChannel;
    private EventLoopGroup eventLoopGroup;


    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws InterruptedException {
        // EventLoopGroup 생성
        // 서버와 마찬가지로 NIO를 사용하기 위해 EventLoopGroup을 생성한다.
        // 조금 다른점은 클라이언트라서 서버소켓에 listen하기 위한 boss 그룹은 없다는 점이다.
        eventLoopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("client"));

        // Bootstrap 생성 및 설정
        Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup);

        // 클라이언트 생성을 위해서 마찬가지로 bootstrap 설정들을 해준다.
        // remoteAddress() 메소드로 접속할 서버 소켓의 주소와 포트를 입력해준다.
        // handler() 메소드로 ClientInitialzer()를 넘겨준다.
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.remoteAddress(new InetSocketAddress(host, port));
        bootstrap.handler(new ClientInitializer());

        // connect() 메소드로 서버 소켓에 연결을 하고 sync() 메소드로 기다린다.
        serverChannel = bootstrap.connect().sync().channel();
    }

    private void start() throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        String message;
        ChannelFuture future;

        while (true) {
            // 사용자 입력
            message = sc.nextLine();

            // Server로 전송
            future = serverChannel.writeAndFlush(message.concat("\n"));

            if ("quit".equals(message)) {
                serverChannel.closeFuture().sync(); // 종료
                break;
            }
        }

        // 종료되기 전 모든 메시지가 flush 될때까지 기다린다.
        if (future != null) {
            future.sync();
        }
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client("127.0.0.1", SERVER_PORT);

        try {
            client.connect(); // 연결
            client.start(); // 시작
        } finally {
            client.close();
        }
    }
}
