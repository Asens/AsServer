import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/1/7.
 */
public class AsServer {
    private boolean shutdown =false;
    private static final String SHUT_DOWN="/SHUT_DOWN";
    public static void main(String[] args)
    {
        AsServer server=new AsServer();
        server.await();
    }

    public void await()
    {
        ServerSocket serverSocket=null;
        int port=80;
        try{
            serverSocket=new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        }catch (IOException e){
            e.printStackTrace();
        }

        while(!shutdown)
        {
            Socket socket=null;
            InputStream input=null;
            OutputStream output=null;
            try{
                socket=serverSocket.accept();
                input=socket.getInputStream();
                output=socket.getOutputStream();
                Request request=new Request(input);
                request.parse();

                Response response=new Response(output);
                response.setRequest(request);
                if(request.getUri().startsWith("/servlet/"))
                {
                    ServletProcessor processor=new ServletProcessor();
                    processor.process(request,response);
                }else{
                    StaticResponseProcessor processor=new StaticResponseProcessor();
                    processor.process(request,response);
                }
                socket.close();
                //shutdown=request.getUri().equals(SHUT_DOWN);
            }catch (Exception e){
                System.out.println(e);
            }
        }





    }

}
