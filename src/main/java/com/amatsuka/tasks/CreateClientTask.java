package com.amatsuka.tasks;

import com.amatsuka.ClientRegistry;
import com.amatsuka.IOWSocket;
import com.amatsuka.WSClient;
import lombok.RequiredArgsConstructor;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
public class CreateClientTask implements Runnable {
    private final BlockingQueue<IOWSocket> connections;
    private final ClientRegistry clientRegistry;

    public void run() {
        while (true) {
            try {
                IOWSocket iowSocket = connections.take();
                //TODO Генерация рандомного UUID клинета. Учесть многопоточность
                clientRegistry.add(new WSClient(iowSocket, null));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
