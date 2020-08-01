package com.amatsuka;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WSClient {
    private final IOWSocket iowSocket;
    private final String id;
}
