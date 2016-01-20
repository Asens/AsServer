import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Administrator on 2016/1/7.
 */
public class TestServlet implements Servlet {
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init");
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("service");
        res.getWriter().println("red");
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
