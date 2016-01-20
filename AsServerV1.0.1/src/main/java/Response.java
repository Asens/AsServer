import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.nio.Buffer;
import java.util.Locale;

/**
 * Created by Administrator on 2016/1/7.
 */
public class Response implements ServletResponse{
    private static final int BUFFER_SIZE=1024;
    Request request;
    OutputStream output;
    PrintWriter writer;
    public Response(OutputStream output)
    {
        this.output=output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendSaticResource() throws IOException
    {
        byte[] bytes=new byte[BUFFER_SIZE];
        FileInputStream fis=null;
        System.out.println(Constants.WEB_ROOT);
        try{
            File file=new File(Constants.WEB_ROOT,request.getUri());
            fis=new FileInputStream(file);
            int ch=fis.read(bytes,0, BUFFER_SIZE);
            while (ch!=-1)
            {
                output.write(bytes,0,ch);
                ch=fis.read(bytes,0, BUFFER_SIZE);
            }
        }catch (FileNotFoundException e){
            String errorMessage="HTTP/1.1 404 File Not Found\r\n"+
                    "Content-Type:text/html\r\n"+
                    "Content-Length:23\r\n"+
                    "\r\n"+
                    "<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        }finally {
            if(fis!=null)
            {
                fis.close();
            }
        }
    }

    public String getCharacterEncoding() {
        return null;
    }

    public String getContentType() {
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    public PrintWriter getWriter() throws IOException {
        writer=new PrintWriter(output,true);
        return writer;
    }

    public void setCharacterEncoding(String charset) {

    }

    public void setContentLength(int len) {

    }

    public void setContentType(String type) {

    }

    public void setBufferSize(int size) {

    }

    public int getBufferSize() {
        return 0;
    }

    public void flushBuffer() throws IOException {

    }

    public void resetBuffer() {

    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {

    }

    public void setLocale(Locale loc) {

    }

    public Locale getLocale() {
        return null;
    }
}
