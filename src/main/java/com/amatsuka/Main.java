package com.amatsuka;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 @TODO В текущей кривой реализации написать весь процесс взаимодейсвтия с браузером. Потом уже выделять архитектуру.
 Возможно нужны отдельные потоки на чтение и запись.
 */
public class Main {
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8008);
        new Server().run(serverSocket);
        System.out.println("com.amatsuka.Server started");

       /* byte[] bytes = {(byte) 0x81, (byte) 0x8a, (byte) 0x82, (byte) 0x58, (byte) 0xa6, (byte) 0x10, (byte) 0xe3, (byte) 0x39, (byte) 0xc7, (byte) 0x71, (byte) 0xe3, (byte) 0x39, (byte) 0xc7, (byte) 0x71, (byte) 0xe3, (byte) 0x39};
        byte[] bytes1 = {
                (byte) 0x81, (byte) 0xfe, (byte) 0x00, (byte) 0x7e, (byte) 0xe4, (byte) 0x15, (byte) 0xf1, (byte) 0xe5, (byte) 0x85, (byte) 0x74,
                (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84,
                (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74,
                (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84,
                (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74,
                (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84,
                (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74,
                (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84,
                (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74,
                (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84,
                (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74,
                (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84,
                (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74, (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74,
                (byte) 0x90, (byte) 0x84, (byte) 0x85, (byte) 0x74};

        Frame frame = new Frame(bytes);
        System.out.println(new String(frame.getDecodedPayload()));*/
    }
}
