package cn.asens.testServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by asens on 2016/12/3.
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resourcePath=Thread.currentThread().getContextClassLoader().getResource("").toString();
        String contextPath=resourcePath.replace("/target/classes/","");
        contextPath+="/src/main/webapps/ROOT/";
        contextPath=contextPath.replace("file:/","");
        File indexHtml=new File(contextPath+"index.html");
        FileInputStream fs=new FileInputStream(indexHtml);
        BufferedInputStream bis=new BufferedInputStream(fs);
        int i=0;
        PrintWriter pw=resp.getWriter();
        while((i=bis.read())!=-1){
            pw.write(i);
        }
        fs.close();
        bis.close();
    }
}
