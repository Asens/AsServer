package cn.asens;

import org.apache.log4j.net.SocketServer;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/11/23.
 */
public class AsServer {
    String abc="abc";
    public static void main(String[] args){
        AsServer asServer=new AsServer();
        openURL("http://127.0.0.1:9091");
        asServer.await();
    }

    private void await()
    {
        ServerSocket serverSocket = null;
        int port = 9091;
        try {
            serverSocket =  new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while(true){
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
                ThreadServer ts=new ThreadServer(socket);

                ts.start();
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private static void openURL(String url)
    {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
