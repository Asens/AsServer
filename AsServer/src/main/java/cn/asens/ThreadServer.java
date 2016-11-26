package cn.asens;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/23.
 */
public class ThreadServer implements Runnable {
    private Socket socket;

    public ThreadServer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        OutputStream output = null;
        try {
            System.out.println(Thread.currentThread().getName());
            output = socket.getOutputStream();
            String error = "HTTP/1.1 200\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n" +
                    "<h1>File Not Found</h1>";
            Thread.sleep(20000);
            output.write(error.getBytes());
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }
}
