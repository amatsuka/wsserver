package com.amatsuka.tasks;

import com.amatsuka.IOWSocket;
import com.amatsuka.old.HttpMessage;
import com.amatsuka.old.HttpStreamReader;
import com.amatsuka.old.HttpStreamWriter;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
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

                Map<String, String> headers = httpMessage.getValuesMap();
                String webSocketkey = headers.get("Sec-WebSocket-Key");

                String webSocketAccept = Base64.getEncoder()
                        .encodeToString(MessageDigest.getInstance("SHA-1").digest((webSocketkey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes("UTF-8")));

                HttpMessage responseMessage = new HttpMessage()
                        .addLine("HTTP/1.1 101 Switching Protocols")
                        .addLine("Upgrade: websocket")
                        .addLine("Connection: Upgrade")
                        .addLine("Sec-WebSocket-Accept: " + webSocketAccept)
                        .addLine("\r\n");

                out.writeHttpMessage(responseMessage);

                connections.add(new IOWSocket(socket.getOutputStream(), socket.getInputStream()));
            } catch (InterruptedException | IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
