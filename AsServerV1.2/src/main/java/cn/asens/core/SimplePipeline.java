package cn.asens.core;

import cn.asens.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by asens on 2016/12/3.
 */
public class SimplePipeline implements Pipeline{
    private Valve basic;
    private Container container;
    protected Valve valves[] = new Valve[0];

    public SimplePipeline(SimpleWrapper simpleWrapper) {
        setContainer(simpleWrapper);
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        new SimplePipelineValveContext().invokeNext(request,response);
    }

    public Valve getBasic() {
        return basic;
    }

    public void setBasic(Valve valve) {
        ((Contained) valve).setContainer(container);
        this.basic = valve;
    }

    public void addValve(Valve valve) {
        if (valve instanceof Contained)
            ((Contained) valve).setContainer(this.container);
        Valve[] newValves=new Valve[valves.length+1];
        for(int i=0;i<valves.length;i++){
            newValves[i]=valves[i];
        }
        newValves[valves.length]=valve;
        this.valves=newValves;
    }

    public Valve[] getValves() {
        return valves;
    }



    public void removeValve(Valve valve) {

    }

    protected class SimplePipelineValveContext implements ValveContext{
        private Integer stage=0;
        public String getInfo() {
            return this.getClass().getName();
        }

        public void invokeNext(Request request, Response response) throws IOException, ServletException {

            int subscript = stage;
            stage = stage + 1;
            // Invoke the requested Valve for the current request thread
            if (subscript < valves.length) {
                valves[subscript].invoke(request, response, this);
            }
            else if ((subscript == valves.length) && (basic != null)) {
                basic.invoke(request, response, this);
            }
            else {
                throw new ServletException("No valve");
            }
        }
    }
}
