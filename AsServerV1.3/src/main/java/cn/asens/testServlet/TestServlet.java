package cn.asens.testServlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/25.
 */
public class TestServlet implements Servlet {
    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("servlet");
        res.getWriter().write("servlet");
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
