package cn.asens;

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
        res.getWriter().write("<h1>as</h1><br/><p>aaa</p>");
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
