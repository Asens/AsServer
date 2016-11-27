package cn.asens.connector.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by asens on 2016/11/27.
 */
public class HttpProcessor implements Runnable{
    private boolean avail=false;
    private Socket socket;
    private HttpConnector connector;

    public HttpProcessor(HttpConnector connector){
        this.connector=connector;
    }

    public void start() {
        Thread t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    public void run() {
        while(true) {
            Socket socket = await();
            try {
                System.out.println("HttpProcessor run");
                OutputStream os = socket.getOutputStream();
                Thread.sleep(5000);
                PrintWriter writer = new PrintWriter(os, true);
                writer.write("asd");
                writer.flush();
                writer.close();
                socket.close();
                connector.recycle(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized Socket await() {
        while(!avail){
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Socket socket=this.socket;
        avail=false;
        notifyAll();
        return socket;
    }

    public synchronized void assign(Socket socket) {
        while(avail){
            try {
                System.out.println("assign");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        avail=true;
        notifyAll();
        this.socket=socket;
    }
}
