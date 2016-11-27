package cn.asens.startup;

import cn.asens.connector.http.HttpConnector;

import java.io.IOException;

/**
 * Created by asens on 2016/11/26.
 */
public class BootStrap {
    public static void main(String[] args){
        HttpConnector connector=new HttpConnector();
        connector.start();
    //    openURL("http://127.0.0.1:9091");
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
