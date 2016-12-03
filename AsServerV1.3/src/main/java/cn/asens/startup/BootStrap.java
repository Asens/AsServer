package cn.asens.startup;

import cn.asens.Pipeline;
import cn.asens.connector.http.HttpConnector;
import cn.asens.core.SimpleContext;
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
        SimpleLoader loader=new SimpleLoader();

        SimpleWrapper wrapper=new SimpleWrapper();
        wrapper.setName("TestServlet");
        wrapper.setServletClass("cn.asens.testServlet.TestServlet");
        ((Pipeline)wrapper).addValve(new HeaderValve());
        ((Pipeline)wrapper).addValve(new ClientIPValve());
        wrapper.setLoader(loader);

        SimpleWrapper wrapper1=new SimpleWrapper();
        wrapper1.setName("login.do");
        wrapper1.setServletClass("cn.asens.testServlet.LoginServlet");
        wrapper1.setLoader(loader);


        SimpleContext simpleContext=new SimpleContext();
        simpleContext.addChild(wrapper);
        simpleContext.addChild(wrapper1);

        HttpConnector connector=new HttpConnector();
        connector.setContainer(simpleContext);
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
