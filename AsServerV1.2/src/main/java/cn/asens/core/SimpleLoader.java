package cn.asens.core;

import cn.asens.Container;
import cn.asens.Loader;
import cn.asens.connector.http.Constants;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by asens on 2016/12/3.
 */
public class SimpleLoader implements Loader {
    public ClassLoader getClassLoader() {
        URLClassLoader loader = null;
        try {
            // create a URLClassLoader
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.WEB_ROOT);

            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        }
        catch (IOException e) {
            System.out.println(e.toString() );
        }
        return loader;
    }

    public Container getContainer() {
        return null;
    }

    public void setContainer(Container container) {

    }

    public boolean getDelegate() {
        return false;
    }

    public void setDelegate(boolean delegate) {

    }

    public String getInfo() {
        return null;
    }

    public boolean getReloadable() {
        return false;
    }

    public void setReloadable(boolean reloadable) {

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    public void addRepository(String repository) {

    }

    public String[] findRepositories() {
        return new String[0];
    }

    public boolean modified() {
        return false;
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
