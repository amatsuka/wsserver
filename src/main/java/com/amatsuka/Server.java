package com.amatsuka;

import com.amatsuka.tasks.ConnectClientSupplier;
import com.amatsuka.tasks.CreateReadThreadFunction;
import com.amatsuka.tasks.WriteTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.*;

public class Server {

    private static int MAX_CONNECTIONS_COUNT = 100;

    /**
     * Реестр подключенных клиентов
     */
    private final ClientRegistry clientRegistry;

    /**
     * Очередь ws соединений ожидающих записи данных в сокет
     */
    private final BlockingQueue<IOWSocket> readyToWriteQueue;

    /**
     * Обработчик очереди ожидающих записи в сокет соединений
     */
    private final ExecutorService readyToWriteQueueExecutor = Executors.newFixedThreadPool(2);

    /**
     * Обработчик очереди установленных ws соединений
     */
    private final ExecutorService connectionExecutor = Executors.newFixedThreadPool(2);

    /**
     * Пул потоков на чтение из сокетов
     */
    private final ExecutorService readExecutor = Executors.newFixedThreadPool(MAX_CONNECTIONS_COUNT);

    public Server() {
        clientRegistry = new ClientRegistry();
        readyToWriteQueue = new ArrayBlockingQueue<>(100);
        init();
    }

    public void init() {
        readyToWriteQueueExecutor.submit(new WriteTask(readyToWriteQueue));
    }

    public void run(ServerSocket serverSocket) {
        try {
            while (true) {
                CompletableFuture.supplyAsync(new ConnectClientSupplier(serverSocket.accept()), connectionExecutor)
                        .thenApplyAsync(new CreateReadThreadFunction(readExecutor), connectionExecutor)
                        .thenAcceptAsync(clientRegistry::add);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
