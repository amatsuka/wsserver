package com.amatsuka.http;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpMessage {
    private final List<String> lines = new ArrayList<>();

    public HttpMessage addLine(String line) {
        lines.add(line);

        return this;
    }

    public boolean isFinished() {
        int size = lines.size();

        if (size < 1) return false;

        return StringUtils.equals(lines.get(size - 1), "");
    }

    public List<String> getLines() {
        return new ArrayList<>(lines);
    }

    public Map<String, String> getValuesMap() {
        HashMap<String, String> result = new HashMap<>();

        lines.stream().forEach(line -> {
            String[] pair = line.split(": ");

            if (pair.length != 2) return;

            result.put(pair[0], pair[1]);
        });

        return result;
    }

    public String toString() {
        return String.join("\r\n", lines);
    }
}
