package com.amatsuka.tasks;

import com.amatsuka.WSClient;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class CreateReadThreadFunction implements Function<WSClient, WSClient> {
    private final ExecutorService readExecutor;

    public WSClient apply(WSClient wsClient) {
        try {
            readExecutor.submit(new ReadTask(wsClient.getIowSocket())).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return wsClient;
    }
}
