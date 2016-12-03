package cn.asens.core;

import cn.asens.*;

import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by asens on 2016/12/3.
 */
public class SimpleContext implements Context,Pipeline {
    private Pipeline pipeline=new SimplePipeline(this);
    protected HashMap<String,Container> children = new HashMap<String,Container>();

    public SimpleContext(){
        pipeline.setBasic(new SimpleContextValve());
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        pipeline.invoke(request,response);
    }

    public void addValve(Valve valve) {
        pipeline.addValve(valve);
    }

    public void addChild(Container child) {
        children.put(child.getName(),child);
        child.setParent(this);
    }

    public Container findChild(String name) {
        return children.get(name);
    }

    public Container[] findChildren() {
        Container[] containers=new Container[children.size()];
        int i=0;
        for(String name:children.keySet()){
            containers[i++]=children.get(name);
        }
        return containers;
    }


    public Valve getBasic() {
        return null;
    }

    public void setBasic(Valve valve) {

    }



    public Valve[] getValves() {
        return new Valve[0];
    }



    public void removeValve(Valve valve) {

    }

    public Object[] getApplicationListeners() {
        return new Object[0];
    }

    public void setApplicationListeners(Object[] listeners) {

    }

    public boolean getAvailable() {
        return false;
    }

    public void setAvailable(boolean available) {

    }

    public boolean getConfigured() {
        return false;
    }

    public void setConfigured(boolean configured) {

    }

    public boolean getCookies() {
        return false;
    }

    public void setCookies(boolean cookies) {

    }

    public boolean getCrossContext() {
        return false;
    }

    public void setCrossContext(boolean crossContext) {

    }

    public String getDisplayName() {
        return null;
    }

    public void setDisplayName(String displayName) {

    }

    public boolean getDistributable() {
        return false;
    }

    public void setDistributable(boolean distributable) {

    }

    public String getDocBase() {
        return null;
    }

    public void setDocBase(String docBase) {

    }

    public String getPath() {
        return null;
    }

    public void setPath(String path) {

    }

    public String getPublicId() {
        return null;
    }

    public void setPublicId(String publicId) {

    }

    public boolean getReloadable() {
        return false;
    }

    public void setReloadable(boolean reloadable) {

    }

    public boolean getOverride() {
        return false;
    }

    public void setOverride(boolean override) {

    }

    public boolean getPrivileged() {
        return false;
    }

    public void setPrivileged(boolean privileged) {

    }

    public ServletContext getServletContext() {
        return null;
    }

    public int getSessionTimeout() {
        return 0;
    }

    public void setSessionTimeout(int timeout) {

    }

    public String getWrapperClass() {
        return null;
    }

    public void setWrapperClass(String wrapperClass) {

    }

    public void addApplicationListener(String listener) {

    }

    public void addInstanceListener(String listener) {

    }

    public void addMimeMapping(String extension, String mimeType) {

    }

    public void addParameter(String name, String value) {

    }

    public void addResourceEnvRef(String name, String type) {

    }

    public void addRoleMapping(String role, String link) {

    }

    public void addSecurityRole(String role) {

    }

    public void addServletMapping(String pattern, String name) {

    }

    public void addTaglib(String uri, String location) {

    }

    public void addWelcomeFile(String name) {

    }

    public void addWrapperLifecycle(String listener) {

    }

    public void addWrapperListener(String listener) {

    }

    public Wrapper createWrapper() {
        return null;
    }

    public String[] findApplicationListeners() {
        return new String[0];
    }

    public String[] findInstanceListeners() {
        return new String[0];
    }

    public String findMimeMapping(String extension) {
        return null;
    }

    public String[] findMimeMappings() {
        return new String[0];
    }

    public String findParameter(String name) {
        return null;
    }

    public String[] findParameters() {
        return new String[0];
    }

    public String[] findResourceEnvRefs() {
        return new String[0];
    }

    public String findRoleMapping(String role) {
        return null;
    }

    public boolean findSecurityRole(String role) {
        return false;
    }

    public String[] findSecurityRoles() {
        return new String[0];
    }

    public String findServletMapping(String pattern) {
        return null;
    }

    public String[] findServletMappings() {
        return new String[0];
    }

    public String findStatusPage(int status) {
        return null;
    }

    public int[] findStatusPages() {
        return new int[0];
    }

    public String findTaglib(String uri) {
        return null;
    }

    public String[] findTaglibs() {
        return new String[0];
    }

    public boolean findWelcomeFile(String name) {
        return false;
    }

    public String[] findWelcomeFiles() {
        return new String[0];
    }

    public String[] findWrapperLifecycles() {
        return new String[0];
    }

    public String[] findWrapperListeners() {
        return new String[0];
    }

    public void reload() {

    }

    public void removeApplicationListener(String listener) {

    }

    public void removeApplicationParameter(String name) {

    }

    public void removeEjb(String name) {

    }

    public void removeEnvironment(String name) {

    }

    public void removeInstanceListener(String listener) {

    }

    public void removeLocalEjb(String name) {

    }

    public void removeMimeMapping(String extension) {

    }

    public void removeParameter(String name) {

    }

    public void removeResource(String name) {

    }

    public void removeResourceEnvRef(String name) {

    }

    public void removeRoleMapping(String role) {

    }

    public void removeSecurityRole(String role) {

    }

    public void removeServletMapping(String pattern) {

    }

    public void removeTaglib(String uri) {

    }

    public void removeWelcomeFile(String name) {

    }

    public void removeWrapperLifecycle(String listener) {

    }

    public void removeWrapperListener(String listener) {

    }

    public String getInfo() {
        return null;
    }

    public Loader getLoader() {
        return null;
    }

    public void setLoader(Loader loader) {

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



    public void addMapper(Mapper mapper) {

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

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
