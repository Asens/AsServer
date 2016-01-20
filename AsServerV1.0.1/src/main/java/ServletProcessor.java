import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by Administrator on 2016/1/7.
 */
public class ServletProcessor {
    public void process(Request request,Response response)
    {
        String uri=request.getUri();
        String servletName=uri.substring(uri.lastIndexOf("/")+1);
        URLClassLoader loader=null;
        try
        {
            URL[] urls=new URL[1];
            URLStreamHandler streamHandler=null;
            File classpath=new File(Constants.WEB_ROOT);
            String repository=(new URL("file",null,
                    classpath.getCanonicalPath()+File.separator).toString());
            System.out.println(repository);
            urls[0] = new URL(null,repository,streamHandler);
            loader=new URLClassLoader(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class myClass=null;
        try{
            myClass=loader.loadClass(servletName);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Servlet servlet=null;

        try{
            servlet=(Servlet)myClass.newInstance();
            servlet.service(request,response);

        }catch (Exception e){
            System.out.println(e.toString());
        }catch (Throwable t){
            System.out.println(t.toString());
        }

    }
}
