package com.amatsuka;

import java.io.IOException;
import java.net.ServerSocket;
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


    }
}
