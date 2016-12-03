package cn.asens.startup;

import cn.asens.Pipeline;
import cn.asens.connector.http.HttpConnector;
import cn.asens.core.SimpleLoader;
import cn.asens.core.SimpleWrapper;
import cn.asens.valves.ClientIPValve;
import cn.asens.valves.HeaderValve;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by asens on 2016/11/26.
 */
public class BootStrap {
    private static Logger logger = Logger.getLogger(BootStrap.class);
    public static void main(String[] args){
        SimpleWrapper wrapper=new SimpleWrapper();
        wrapper.setServletClass("cn.asens.TestServlet");
        ((Pipeline)wrapper).addValve(new HeaderValve());
        ((Pipeline)wrapper).addValve(new ClientIPValve());
        SimpleLoader loader=new SimpleLoader();
        wrapper.setLoader(loader);
        HttpConnector connector=new HttpConnector();
        connector.setContainer(wrapper);
        connector.start();
        //openURL("http://127.0.0.1:9091");
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
