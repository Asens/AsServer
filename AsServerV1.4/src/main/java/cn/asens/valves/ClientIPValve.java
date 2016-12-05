package cn.asens.valves;

import cn.asens.Request;
import cn.asens.Response;
import cn.asens.Valve;
import cn.asens.ValveContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by asens on 2016/12/3.
 */
public class ClientIPValve implements Valve {
    public String getInfo() {
        return null;
    }

    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
        context.invokeNext(request,response);
        System.out.println("after invoke");
    }
}
