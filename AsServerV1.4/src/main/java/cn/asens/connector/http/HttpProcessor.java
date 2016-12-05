package cn.asens.connector.http;

import cn.asens.utils.RequestUtil;
import cn.asens.utils.StringManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by asens on 2016/11/26.
 */
public class HttpProcessor implements Runnable {
    private static Logger logger = Logger.getLogger(HttpProcessor.class);
    private HttpRequest request;
    private HttpResponse response;
    private HttpRequestLine requestLine = new HttpRequestLine();
    private HttpConnector connector;
    private Socket socket;
    private int threadName;
    private boolean available;
    private boolean keepAlive = false;
    protected StringManager sm = StringManager.getManager("cn.asens.connector.http");

    public HttpProcessor(HttpConnector connector, int threadName) {
        this.connector = connector;
        this.threadName = threadName;
    }


    private void parseHeaders(SocketInputStream input)
            throws IOException, ServletException {
        while (true) {
            HttpHeader header = new HttpHeader();
            ;

            // Read the next header
            input.readHeader(header);
            if (header.nameEnd == 0) {
                if (header.valueEnd == 0) {
                    return;
                } else {
                    throw new ServletException
                            (sm.getString("httpProcessor.parseHeaders.colon"));
                }
            }

            String name = new String(header.name, 0, header.nameEnd);
            String value = new String(header.value, 0, header.valueEnd);
            //logger.debug(name+" : "+value);
            request.addHeader(name, value);
            // do something for some headers, ignore others.
            if (name.equals("cookie")) {
                Cookie cookies[] = RequestUtil.parseCookieHeader(value);
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("jsessionid")) {
                        // Override anything requested in the URL
                        if (!request.isRequestedSessionIdFromCookie()) {
                            // Accept only the first session id cookie
                            request.setRequestedSessionId(cookies[i].getValue());
                            request.setRequestedSessionCookie(true);
                            request.setRequestedSessionURL(false);
                        }
                    }
                    request.addCookie(cookies[i]);
                }
            } else if (name.equals("content-length")) {
                int n = -1;
                try {
                    n = Integer.parseInt(value);
                } catch (Exception e) {
                    throw new ServletException(sm.getString("httpProcessor.parseHeaders.contentLength"));
                }
                request.setContentLength(n);
            } else if (name.equals("content-type")) {
                request.setContentType(value);
            }
        } //end while
    }

    private void parseRequest(SocketInputStream input, OutputStream output)
            throws IOException, ServletException {

        // Parse the incoming request line
        input.readRequestLine(requestLine);
        String method =
                new String(requestLine.method, 0, requestLine.methodEnd);
        String uri = null;
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

        // Validate the incoming request line
        if (method.length() < 1) {
            throw new ServletException("Missing HTTP request method");
        } else if (requestLine.uriEnd < 1) {
            throw new ServletException("Missing HTTP request URI");
        }
        // Parse any query parameters out of the request URI
        int question = requestLine.indexOf("?");
        if (question >= 0) {
            request.setQueryString(new String(requestLine.uri, question + 1,
                    requestLine.uriEnd - question - 1));
            uri = new String(requestLine.uri, 0, question);
        } else {
            request.setQueryString(null);
            uri = new String(requestLine.uri, 0, requestLine.uriEnd);
        }


        // Checking for an absolute URI (with the HTTP protocol)
        if (!uri.startsWith("/")) {
            int pos = uri.indexOf("://");
            // Parsing out protocol and host name
            if (pos != -1) {
                pos = uri.indexOf('/', pos + 3);
                if (pos == -1) {
                    uri = "";
                } else {
                    uri = uri.substring(pos);
                }
            }
        }

        // Parse any requested session ID out of the request URI
        String match = ";jsessionid=";
        int semicolon = uri.indexOf(match);
        if (semicolon >= 0) {
            String rest = uri.substring(semicolon + match.length());
            int semicolon2 = rest.indexOf(';');
            if (semicolon2 >= 0) {
                request.setRequestedSessionId(rest.substring(0, semicolon2));
                rest = rest.substring(semicolon2);
            } else {
                request.setRequestedSessionId(rest);
                rest = "";
            }
            request.setRequestedSessionURL(true);
            uri = uri.substring(0, semicolon) + rest;
        } else {
            request.setRequestedSessionId(null);
            request.setRequestedSessionURL(false);
        }

        // Normalize URI (using String operations at the moment)
        String normalizedUri = normalize(uri);

        // Set the corresponding request properties
        ((HttpRequest) request).setMethod(method);
        request.setProtocol(protocol);
        if (normalizedUri != null) {
            ((HttpRequest) request).setRequestURI(normalizedUri);
        } else {
            ((HttpRequest) request).setRequestURI(uri);
        }

        if (normalizedUri == null) {
            throw new ServletException("Invalid URI: " + uri + "'");
        }
    }

    protected String normalize(String path) {
        if (path == null)
            return null;
        // Create a place for the normalized path
        String normalized = path;

        // Normalize "/%7E" and "/%7e" at the beginning to "/~"
        if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
            normalized = "/~" + normalized.substring(4);

        // Prevent encoding '%', '/', '.' and '\', which are special reserved
        // characters
        if ((normalized.indexOf("%25") >= 0)
                || (normalized.indexOf("%2F") >= 0)
                || (normalized.indexOf("%2E") >= 0)
                || (normalized.indexOf("%5C") >= 0)
                || (normalized.indexOf("%2f") >= 0)
                || (normalized.indexOf("%2e") >= 0)
                || (normalized.indexOf("%5c") >= 0)) {
            return null;
        }

        if (normalized.equals("/."))
            return "/";

        // Normalize the slashes and add leading slash if necessary
        if (normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null);  // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // Declare occurrences of "/..." (three or more dots) to be invalid
        // (on some Windows platforms this walks the directory tree!!!)
        if (normalized.indexOf("/...") >= 0)
            return (null);

        // Return the normalized path that we have completed
        return (normalized);

    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            Socket socket = await();
            process(socket);
            connector.recycle(this);
        }

    }

    private void process(Socket socket) {

        boolean ok = true;
        boolean finishResponse = true;
        SocketInputStream is=null;
        OutputStream os=null;

        try {
            is = new SocketInputStream(socket.getInputStream(), 2048);
        } catch (Exception e) {
            logger.debug("process.create", e);
            ok = false;
        }
        keepAlive = true;

        while (ok&&keepAlive) {
            finishResponse = true;
            try {
                logger.debug("processor run");
                os = socket.getOutputStream();
                request = new HttpRequest(is);
                response = new HttpResponse(os);
                response.setRequest(request);
                response.setHeader("Server", "Container");
            } catch (Exception e) {
                ok = false;
            }

            try {
                if (ok) {
                    parseRequest(is, os);
                    parseHeaders(is);

                }
            } catch (EOFException e) {
                ok = false;
                finishResponse = false;
            } catch (Exception e) {
                ok = false;
            }

            try {
                if (ok) {
                    connector.getContainer().invoke(request, response);
                }
            }catch (Exception e) {
                ok = false;
            }

            try {
                if (finishResponse) {
                    logger.debug("finish response");
                    ((HttpResponse) response).finishResponse();
                    request.finishRequest();
                    if (os != null)
                        os.flush();
                }
            } catch (IOException e) {
                ok = false;
            }
            ok=false;


        }

        try {
            socket.close();
        } catch (IOException e) {
            ;
        }
        socket = null;


    }

    private synchronized Socket await() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Socket socket = this.socket;
        available = false;
        notifyAll();
        return socket;
    }

    public synchronized void assign(Socket socket) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("assign");
        this.socket = socket;
        available = true;
        notifyAll();
    }
}
