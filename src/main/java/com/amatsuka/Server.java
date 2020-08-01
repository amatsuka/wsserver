package com.amatsuka;

import com.amatsuka.tasks.ConnectionTask;
import com.amatsuka.tasks.SocketWriteTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    /**
     * Очередь входящих сокет соединений готовых к подключению
     */
    private final BlockingQueue<Socket> socketQueue;

    /**
     * Реестр подключенных клиентов
     */
    private final ClientRegistry clientRegistry;

    /**
     * Очередь установленных ws соединений ожидающих создания потоков на чтение
     * и добавления в реестр подключенных клиентов
     */
    private final BlockingQueue<IOWSocket> connectionQueue;

    /**
     * Очередь ws соединений ожидающих записи данных в сокет
     */
    private final BlockingQueue<IOWSocket> readyToWriteQueue;

    /**
     * Обработчик очереди входящих сокет соединений
     */
    private final ExecutorService socketQueueExecutor = Executors.newFixedThreadPool(2);

    /**
     * Обработчик очереди установленных ws соединений
     */
    private final ExecutorService connectionQueueExecutor = Executors.newFixedThreadPool(2);

    /**
     * Обработчик очереди ожидающих записи в сокет соединений
     */
    private final ExecutorService readyToWriteQueueExecutor = Executors.newFixedThreadPool(2);

    public Server() {
        socketQueue = new ArrayBlockingQueue<>(100);
        clientRegistry = new ClientRegistry();
        connectionQueue = new ArrayBlockingQueue<>(100);
        readyToWriteQueue = new ArrayBlockingQueue<>(100);
    }

    public void init() {
        socketQueueExecutor.submit(new ConnectionTask(socketQueue, connectionQueue));
        readyToWriteQueueExecutor.submit(new SocketWriteTask(readyToWriteQueue));
    }

    public void run(ServerSocket serverSocket) throws IOException {
        try {
            while (true) {
                socketQueue.add(serverSocket.accept());
            }
        } catch (IOException e) {
            serverSocket.close();
        }
    }
}
