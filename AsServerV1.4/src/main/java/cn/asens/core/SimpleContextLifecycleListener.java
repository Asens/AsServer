package cn.asens.core;

import cn.asens.lifecycle.LifecycleEvent;
import cn.asens.lifecycle.LifecycleListener;
import org.apache.log4j.Logger;

/**
 * Created by asens on 2016/12/4.
 */
public class SimpleContextLifecycleListener implements LifecycleListener {
    private static Logger log=Logger.getLogger(SimpleContextLifecycleListener.class);
    public void lifecycleEvent(LifecycleEvent event) {
        log.debug(event.getType());

    }
}
