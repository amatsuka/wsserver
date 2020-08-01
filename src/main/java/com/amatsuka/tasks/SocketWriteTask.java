package com.amatsuka.tasks;

import com.amatsuka.IOWSocket;
import lombok.RequiredArgsConstructor;

import java.util.Queue;

@RequiredArgsConstructor
public class SocketWriteTask implements Runnable {
    private final Queue<IOWSocket> hasMessagesToWriteQueue;

    @Override
    public void run() {
        //read out messages and write they to output stream
    }
}
