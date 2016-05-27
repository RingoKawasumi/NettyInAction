package nia.chapter4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by zhujie on 16/5/26.
 */
public class PlainOioServer {
    public void serve(int port) throws IOException {
        // Binds the server to the specified port
        final ServerSocket socket = new ServerSocket(port);
        try {
            while (true) {
                // Accepts a connection
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                // Creates a new thread to handle the connection
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            // Writes message to the connected client
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                // Closes the connection
                                clientSocket.close();
                            } catch (IOException e) {
                                // ignore on close
                            }
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
