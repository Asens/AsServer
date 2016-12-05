package cn.asens.core;

import cn.asens.*;
import cn.asens.connector.http.HttpResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by asens on 2016/12/3.
 */
public class SimpleWrapperValve implements Valve, Contained {
    protected Container container;

    public String getInfo() {
        return null;
    }

    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
        SimpleWrapper wrapper = (SimpleWrapper) getContainer();
        Servlet servlet=wrapper.allocate();
        ServletRequest sreq = request.getRequest();
        ServletResponse sres = response.getResponse();
        HttpServletRequest hreq = null;
        if (request instanceof HttpServletRequest)
            hreq = (HttpServletRequest) sreq;
        HttpServletResponse hres = null;
        if (sres instanceof HttpServletResponse)
            hres = (HttpServletResponse) sres;
        if(hreq!=null&&hres!=null)
            servlet.service(hreq,hres);
        else
            servlet.service(sreq,sres);
        ((HttpResponse) response).finishResponse();
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container=container;
    }
}
