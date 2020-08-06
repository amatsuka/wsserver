package com.amatsuka.tasks;

import com.amatsuka.IOWSocket;
import com.amatsuka.old.HttpMessage;
import com.amatsuka.old.HttpStreamReader;
import com.amatsuka.old.HttpStreamWriter;
import lombok.RequiredArgsConstructor;

import java.io.*;
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

                HttpStreamReader in = new HttpStreamReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
                HttpStreamWriter out = new HttpStreamWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

                HttpMessage httpMessage = in.readHttpMessage();

                HttpMessage responseMessage = new HttpMessage()
                        .addLine("HTTP/1.1 101 Switching Protocols")
                        .addLine("Upgrade: websocket")
                        .addLine("Connection: Upgrade")
                        .addLine("Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=")
                        .addLine("\r\n");

                out.writeHttpMessage(responseMessage);

                HttpMessage httpMessage2 = in.readHttpMessage();

                connections.add(new IOWSocket(socket.getOutputStream(), socket.getInputStream()));
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
