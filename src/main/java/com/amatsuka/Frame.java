package com.amatsuka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.Arrays;

@Getter
public class Frame {
    private final byte[] frameBytes;

    private byte[] decodedPayload;

    boolean fin;
    boolean rsv1;
    boolean rsv2;
    boolean rsv3;

    int opcode;

    boolean masked;

    int length;

    private byte[] mask;

    public Frame(byte[] bytes) {
        this.frameBytes = bytes;
        decodeBytes();
    }

    private void decodeBytes() {
        fin = (frameBytes[0] & 0x80) >> 7 == 1;
        rsv1 = (frameBytes[0] & 0x40) >> 6 == 1;
        rsv2 = (frameBytes[0] & 0x20) >> 5 == 1;
        rsv3 = (frameBytes[0] & 0x10) >> 4 == 1;
        opcode = frameBytes[0] & 0x0f;

        int masked = (frameBytes[1] & 0x80) >> 7;
        int length = (frameBytes[1] & 0x7F);

        int nextByte = 2;

        if(length == 126) {
            nextByte += 2;
            length = new BigInteger(Arrays.copyOfRange(frameBytes, 2, 4)).intValue();
        } else if (length == 127) {
            nextByte += 8;
            length = new BigInteger(Arrays.copyOfRange(frameBytes, 2, 10)).intValue();
        }

        if (masked == 1) {
            mask = Arrays.copyOfRange(frameBytes, nextByte, nextByte + 4);
            nextByte += 4;

            byte[] payload = Arrays.copyOfRange(frameBytes, nextByte, nextByte + length);

            decodedPayload = new byte[payload.length];

            for (int i = 0; i < payload.length; i++) {
                decodedPayload[i] = (byte)(payload[i] ^ mask[i % 4]);
            }
        } else {
            decodedPayload = Arrays.copyOfRange(frameBytes, nextByte, nextByte + length);
        }
    }
}
