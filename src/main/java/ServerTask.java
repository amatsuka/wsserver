import java.io.*;
import java.net.Socket;

public class ServerTask implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ServerTask(Socket socket) throws IOException {
        this.socket = socket;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        System.out.println("Server thread started " + Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }

            System.out.println("Server thread stopped " + Thread.currentThread().getName());
        }
    }
}
