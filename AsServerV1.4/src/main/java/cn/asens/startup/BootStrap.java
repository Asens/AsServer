package cn.asens.startup;

import cn.asens.Pipeline;
import cn.asens.connector.http.HttpConnector;
import cn.asens.core.SimpleContext;
import cn.asens.core.SimpleContextLifecycleListener;
import cn.asens.core.SimpleLoader;
import cn.asens.core.SimpleWrapper;
import cn.asens.lifecycle.Lifecycle;
import cn.asens.lifecycle.LifecycleException;
import cn.asens.lifecycle.LifecycleListener;
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
        wrapper1.setName("LoginServlet");
        wrapper1.setServletClass("cn.asens.testServlet.LoginServlet");
        wrapper1.setLoader(loader);

        SimpleWrapper wrapper2=new SimpleWrapper();
        wrapper2.setName("CssServlet");
        wrapper2.setServletClass("cn.asens.testServlet.CssServlet");
        wrapper2.setLoader(loader);


        SimpleContext simpleContext=new SimpleContext();
        simpleContext.addChild(wrapper);
        simpleContext.addChild(wrapper1);
        simpleContext.addChild(wrapper2);

        simpleContext.addServletMapping("/TestServlet","TestServlet");
        simpleContext.addServletMapping("/login.do","LoginServlet");
        simpleContext.addServletMapping("*.css","CssServlet");
        simpleContext.addServletMapping("*.js","CssServlet");
        simpleContext.addServletMapping("*.html","CssServlet");
        simpleContext.addServletMapping("*.jpg","CssServlet");
        simpleContext.addServletMapping("*.png","CssServlet");

        simpleContext.setLoader(loader);

        LifecycleListener lifecycleListener=new SimpleContextLifecycleListener();
        simpleContext.addLifecycleListener(lifecycleListener);

        HttpConnector connector=new HttpConnector();
        connector.setContainer(simpleContext);
        try {
            simpleContext.start();
            connector.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

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
