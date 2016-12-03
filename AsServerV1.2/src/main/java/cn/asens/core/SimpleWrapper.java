package cn.asens.core;

import cn.asens.*;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Created by asens on 2016/12/3.
 */
public class SimpleWrapper implements Wrapper,Pipeline {

    private Pipeline pipeline=new SimplePipeline(this);
    private Servlet instance=null;
    private String servletClass;
    private Loader loader;

    public SimpleWrapper() {
        pipeline.setBasic(new SimpleWrapperValve());
    }


    public Valve getBasic() {
        return null;
    }

    public void setBasic(Valve valve) {
        pipeline.setBasic(valve);
    }

    public void addValve(Valve valve) {
        pipeline.addValve(valve);
    }

    public Valve[] getValves() {
        return new Valve[0];
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        pipeline.invoke(request,response);
    }

    public void removeValve(Valve valve) {

    }


    public long getAvailable() {
        return 0;
    }

    public void setAvailable(long available) {

    }

    public String getJspFile() {
        return null;
    }

    public void setJspFile(String jspFile) {

    }

    public int getLoadOnStartup() {
        return 0;
    }

    public void setLoadOnStartup(int value) {

    }

    public String getRunAs() {
        return null;
    }

    public void setRunAs(String runAs) {

    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass=servletClass;
    }

    public boolean isUnavailable() {
        return false;
    }

    public void addInitParameter(String name, String value) {

    }

    public void addSecurityReference(String name, String link) {

    }

    public Servlet allocate() throws ServletException {
        if(instance!=null)
            return instance;
        try {
            String actualClass=servletClass;
            Loader loader=getLoader();
            ClassLoader classLoader=loader.getClassLoader();
            Class clazz= classLoader.loadClass(actualClass);
            Servlet servlet=(Servlet)clazz.newInstance();
            this.instance=servlet;
            return servlet;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deallocate(Servlet servlet) throws ServletException {

    }

    public String findInitParameter(String name) {
        return null;
    }

    public String[] findInitParameters() {
        return new String[0];
    }

    public String findSecurityReference(String name) {
        return null;
    }

    public String[] findSecurityReferences() {
        return new String[0];
    }

    public void load() throws ServletException {

    }

    public void removeInitParameter(String name) {

    }

    public void removeSecurityReference(String name) {

    }

    public void unavailable(UnavailableException unavailable) {

    }

    public void unload() throws ServletException {

    }

    public String getInfo() {
        return null;
    }

    public Loader getLoader() {
        return loader;
    }

    public void setLoader(Loader loader) {
        this.loader=loader;
    }

    public Logger getLogger() {
        return null;
    }

    public void setLogger(Logger logger) {

    }

    public String getName() {
        return null;
    }

    public void setName(String name) {

    }

    public Container getParent() {
        return null;
    }

    public void setParent(Container container) {

    }

    public ClassLoader getParentClassLoader() {
        return null;
    }

    public void setParentClassLoader(ClassLoader parent) {

    }

    public DirContext getResources() {
        return null;
    }

    public void setResources(DirContext resources) {

    }

    public void addChild(Container child) {

    }

    public void addMapper(Mapper mapper) {

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    public Container findChild(String name) {
        return null;
    }

    public Container[] findChildren() {
        return new Container[0];
    }

    public Mapper findMapper(String protocol) {
        return null;
    }

    public Mapper[] findMappers() {
        return new Mapper[0];
    }



    public Container map(Request request, boolean update) {
        return null;
    }

    public void removeChild(Container child) {

    }

    public void removeMapper(Mapper mapper) {

    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
