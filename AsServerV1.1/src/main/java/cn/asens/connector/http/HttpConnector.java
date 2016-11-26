package cn.asens.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by asens on 2016/11/26.
 */
public class HttpConnector implements Runnable{

    public void run() {
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(9091,1,InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            Socket socket=null;
            try {
                socket=serverSocket.accept();
                HttpProcessor processor=new HttpProcessor();
                processor.process(socket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        Thread t=new Thread(this);
        t.start();;
    }
}
