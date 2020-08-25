package com.amatsuka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WSClient {

    @Getter
    private final IOWSocket iowSocket;
    private final String id;
}
