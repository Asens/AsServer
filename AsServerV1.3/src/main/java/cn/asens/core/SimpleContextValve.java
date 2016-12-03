package cn.asens.core;

import cn.asens.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by asens on 2016/12/3.
 */
public class SimpleContextValve implements Valve,Contained {
    private static Logger log = Logger.getLogger(SimpleContextValve.class);
    private Container container;

    public String getInfo() {
        return null;
    }

    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
       String requestURI=((HttpServletRequest)request).getRequestURI();
       if(requestURI.startsWith("/")) requestURI=requestURI.substring(1);
       SimpleWrapper wrapper=(SimpleWrapper)getContainer().findChild(requestURI);
       wrapper.invoke(request,response);
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container=container;
    }
}
