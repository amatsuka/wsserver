package com.amatsuka;

import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Queue;

@RequiredArgsConstructor
public class IOWSocket {
    private final OutputStream out;
    private final InputStream in;

    private final Queue<String> inMessages = new ArrayDeque<>();
    private final Queue<String> outMessages = new ArrayDeque<>();

    private final boolean isClosed = false;
}
