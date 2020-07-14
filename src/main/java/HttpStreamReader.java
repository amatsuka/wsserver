import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;

@RequiredArgsConstructor
public class HttpStreamReader {
    private final BufferedReader in;

    public HttpMessage readHttpMessage() {
        HttpMessage message = new HttpMessage();
        while (!message.isFinished()) {
            try {
                String line = in.readLine();
                message.addLine(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return message;
    }

    public void close() throws IOException {
        in.close();
    }
}
