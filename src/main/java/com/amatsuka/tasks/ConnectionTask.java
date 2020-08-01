package com.amatsuka.tasks;

import com.amatsuka.IOWSocket;
import lombok.RequiredArgsConstructor;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
public class ConnectionTask implements Runnable {
    private final BlockingQueue<Socket> sockets;
    private final BlockingQueue<IOWSocket> connections;

    public void run() {
        while (true) {
            try {
                Socket socket = sockets.take();

                //Establish ws connection by http request

                connections.add(new IOWSocket(socket.getInputStream(), socket.getOutputStream()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
