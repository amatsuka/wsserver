package com.amatsuka.old;

import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.IOException;

@RequiredArgsConstructor
public class HttpStreamWriter {
    private final BufferedWriter out;

    public void writeHttpMessage(HttpMessage message) {
        try {
            out.write(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
