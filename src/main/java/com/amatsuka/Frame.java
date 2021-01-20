package com.amatsuka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class Frame {
    private byte[] frameBytes;

    private byte[] decodedPayload;

    boolean fin;
    boolean rsv1;
    boolean rsv2;
    boolean rsv3;

    int opcode;

    boolean masked;

    int length;

    private byte[] mask;

    public static Frame fromBytes(byte[] bytes) {
        Frame frame = new Frame();
        frame.frameBytes = bytes;
        frame.decodeBytes();

        return frame;
    }

    public static Frame fromPayload(byte[] bytes) {
        Frame frame = new Frame();
        frame.decodedPayload = bytes;
        frame.encodePayload();

        return frame;
    }

    private Frame() {

    }

    private void decodeBytes() {
        fin = (frameBytes[0] & 0b10000000) >> 7 == 1;
        rsv1 = (frameBytes[0] & 0b01000000) >> 6 == 1;
        rsv2 = (frameBytes[0] & 0b00100000) >> 5 == 1;
        rsv3 = (frameBytes[0] & 0b00010000) >> 4 == 1;
        opcode = frameBytes[0] & 0b0001111;

        boolean masked = (frameBytes[1] & 0b10000000) >> 7 == 1;
        int length = (frameBytes[1] & 0b01111111);

        int nextByte = 2;

        if (length == 126) {
            nextByte += 2;
            byte[] b = new byte[4];
            System.arraycopy(frameBytes, 2, b, 2, 2);
            ByteBuffer wrapped = ByteBuffer.wrap(b);
            length = wrapped.getInt();
        } else if (length == 127) {
            nextByte += 8;
            ByteBuffer wrapped = ByteBuffer.wrap(Arrays.copyOfRange(frameBytes, 2, 10));
            length = wrapped.getInt();
        }

        if (masked) {
            mask = Arrays.copyOfRange(frameBytes, nextByte, nextByte + 4);
            nextByte += 4;

            byte[] payload = Arrays.copyOfRange(frameBytes, nextByte, nextByte + length);

            decodedPayload = new byte[payload.length];

            for (int i = 0; i < payload.length; i++) {
                decodedPayload[i] = (byte) (payload[i] ^ mask[i % 4]);
            }
        } else {
            decodedPayload = Arrays.copyOfRange(frameBytes, nextByte, nextByte + length);
        }
    }

    private void encodePayload() {
        int len = decodedPayload.length;

        List<Byte> bytes = new ArrayList<>();
        byte firstLenPart;
        byte[] extendedLength = null;

        if (len > 65535) {
            firstLenPart = 127;
            extendedLength = ByteBuffer.allocate(8).putInt(len).array();
        } else if (len > 125) {
            firstLenPart = 126;
            extendedLength = Arrays.copyOfRange(ByteBuffer.allocate(4).putInt(len).array(), 2, 4);
        } else {
            firstLenPart = (byte)len;
        }

        bytes.add((byte) (0b10000000 | 0b1));
        bytes.add((byte) (0b10000000 | firstLenPart));

        if (extendedLength != null) {
            for (byte b : extendedLength) {
                bytes.add(b);
            }
        }

        /*byte[] mask = new byte[] {(byte) 0x82, (byte) 0x58, (byte) 0xa6, (byte) 0x10};*/
        byte[] mask = new byte[4];
        new Random().nextBytes(mask);

        for (byte b : mask) {
            bytes.add(b);
        }

        for (int i = 0; i < len; i++) {
            bytes.add((byte) (decodedPayload[i] ^ mask[i % 4]));
        }

        frameBytes = bytes.stream()
                .collect(
                        ByteArrayOutputStream::new,
                        ByteArrayOutputStream::write,
                        (a, b) -> {}).toByteArray();
    }
}
