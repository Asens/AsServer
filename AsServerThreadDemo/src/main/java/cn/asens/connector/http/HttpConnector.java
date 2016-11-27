package cn.asens.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

/**
 * Created by asens on 2016/11/27.
 */
public class HttpConnector implements Runnable {
    private Stack<HttpProcessor> stack=new Stack<HttpProcessor>();
    private static final int MAX_PROCESSORS=20;
    private static final int MIN_PROCESSORS=3;
    private int curProcessors=0;

    public void run() {

        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(80,1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            Socket socket;
            try {
                socket=serverSocket.accept();
                System.out.println("accept");
                HttpProcessor processor=getProcessor();
                if(processor==null)
                    socket.close();
                processor.assign(socket);
            }catch (Exception e){

            }
        }
    }


    private HttpProcessor getProcessor() {
        synchronized (stack) {
            if (curProcessors<MAX_PROCESSORS && stack.size() < 1){
                System.out.println("==add==");
                HttpProcessor processor=new HttpProcessor(this);
                curProcessors++;
                processor.start();
                return processor;
            }

            if(stack.size() > 0){
                HttpProcessor processor = stack.pop();
                System.out.println("pop" + stack.size());
                return processor;
            }
            return null;
        }
    }

    public void start(){
        Thread t=new Thread(this);
        t.start();
        for (int i = 0; i < MIN_PROCESSORS; i++) {
            HttpProcessor processor=new HttpProcessor(this);
            processor.start();
            curProcessors++;
            recycle(processor);
        }
    }

    public void recycle(HttpProcessor httpProcessor) {
        stack.push(httpProcessor);
        System.out.println("push"+stack.size());
    }
}
