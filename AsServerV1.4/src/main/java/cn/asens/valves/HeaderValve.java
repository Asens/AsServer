package cn.asens.valves;

import cn.asens.Request;
import cn.asens.Response;
import cn.asens.Valve;
import cn.asens.ValveContext;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by asens on 2016/12/3.
 */
public class HeaderValve implements Valve {
    public String getInfo() {
        return null;
    }

    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
        System.out.println("before invoke");
        context.invokeNext(request,response);
    }
}
