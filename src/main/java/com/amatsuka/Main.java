package com.amatsuka;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 @TODO В текущей кривой реализации написать весь процесс взаимодейсвтия с браузером. Потом уже выделять архитектуру.
 Возможно нужны отдельные потоки на чтение и запись.
 */
public class Main {
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException {
       /* ServerSocket serverSocket = new ServerSocket(8008);
        new Server().run(serverSocket);
        System.out.println("com.amatsuka.Server started");*/

        /*byte[] bytes1 = {(byte) 0x81, (byte) 0x8a, (byte) 0x82, (byte) 0x58, (byte) 0xa6, (byte) 0x10, (byte) 0xe3, (byte) 0x39, (byte) 0xc7, (byte) 0x71, (byte) 0xe3, (byte) 0x39, (byte) 0xc7, (byte) 0x71, (byte) 0xe3, (byte) 0x39};
        byte[] bytes2 = {
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

        Frame frame1_0 = Frame.fromBytes(bytes1);
        System.out.println(new String(frame1_0.getDecodedPayload()));

        Frame frame1_1 = Frame.fromPayload(frame1_0.getDecodedPayload());
        Frame frame1_2 = Frame.fromBytes(frame1_1.getFrameBytes());
        System.out.println(new String(frame1_2.getDecodedPayload()));


        Frame frame2_0 = Frame.fromBytes(bytes2);
        System.out.println(new String(frame2_0.getDecodedPayload()));

        Frame frame2_1 = Frame.fromPayload(frame2_0.getDecodedPayload());
        Frame frame2_2 = Frame.fromBytes(frame2_1.getFrameBytes());
        System.out.println(new String(frame2_2.getDecodedPayload()));*/

        String payload = Stream.generate((() -> "a")).limit(70000).collect(Collectors.joining());
        System.out.println(payload);
        Frame frame3_1 = Frame.fromPayload(payload.getBytes());
        Frame frame3_2 = Frame.fromBytes(frame3_1.getFrameBytes());
        System.out.println(new String(frame3_2.getDecodedPayload()));
    }
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
