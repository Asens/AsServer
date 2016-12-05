package cn.asens.connector.http;

import cn.asens.Connector;
import cn.asens.Container;
import cn.asens.Request;
import cn.asens.Response;
import cn.asens.lifecycle.Lifecycle;
import cn.asens.lifecycle.LifecycleException;
import cn.asens.lifecycle.LifecycleListener;
import cn.asens.net.DefaultServerSocketFactory;
import cn.asens.net.ServerSocketFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

/**
 * Created by asens on 2016/11/26.
 */
public class HttpConnector implements Runnable,Connector,Lifecycle{
    private final static Logger log=Logger.getLogger(HttpConnector.class);
    private final static int MAX_PROCESSES=20;
    private final static int MIN_PROCESSES=5;
    private int curProcesses;
    private ServerSocketFactory factory = null;
    private Stack<HttpProcessor> processors=new Stack<HttpProcessor>();
    private String address="127.0.0.1";
    private int port=80;
    private int acceptCount=10;
    private Container container;

    public void run() {
        ServerSocket serverSocket=null;
        try {
            serverSocket=open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            Socket socket=null;
            try {
                socket=serverSocket.accept();
                HttpProcessor processor=getProcesser();
                processor.assign(socket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ServerSocket open() throws IOException {

        // Acquire the server socket factory for this Connector
        ServerSocketFactory factory = getFactory();

        // If no address is specified, open a connection on all addresses
        if (address == null) {
            return (factory.createSocket(port, acceptCount));
        }

        // Open a server socket on the specified address
        try {
            InetAddress is = InetAddress.getByName(address);
            return (factory.createSocket(port, acceptCount, is));
        } catch (Exception e) {
            return (factory.createSocket(port, acceptCount));
        }

    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public boolean getEnableLookups() {
        return false;
    }

    public void setEnableLookups(boolean enableLookups) {

    }

    public ServerSocketFactory getFactory() {

        if (this.factory == null) {
            synchronized (this) {
                this.factory = new DefaultServerSocketFactory();
            }
        }
        return (this.factory);

    }

    public void setFactory(ServerSocketFactory factory) {
        this.factory=factory;
    }

    public String getInfo() {
        return null;
    }

    public int getRedirectPort() {
        return 0;
    }

    public void setRedirectPort(int redirectPort) {

    }

    public String getScheme() {
        return null;
    }

    public void setScheme(String scheme) {

    }

    public boolean getSecure() {
        return false;
    }

    public void setSecure(boolean secure) {

    }

    public Request createRequest() {
        return null;
    }

    public Response createResponse() {
        return null;
    }

    private HttpProcessor getProcesser() {
        if(processors.size()>=MAX_PROCESSES)
            return null;
        else if(processors.size()>0){
            return processors.pop();
        }else{
            return newProcesser(this);
        }
    }

    public void addLifecycleListener(LifecycleListener listener) {

    }

    public void removeLifecycleListener(LifecycleListener listener) {

    }

    public void start(){
        Thread t=new Thread(this);
        t.start();
        for (int i = 0; i < MIN_PROCESSES; i++) {
            recycle(newProcesser(this));
        }
    }

    public void stop() throws LifecycleException {

    }

    private HttpProcessor newProcesser(HttpConnector connector) {
        HttpProcessor processor=new HttpProcessor(connector,curProcesses++);
        processor.start();
        return processor;
    }

    public void recycle(HttpProcessor httpProcessor) {
        processors.push(httpProcessor);
        log.debug("after push"+processors.size());
    }
}
