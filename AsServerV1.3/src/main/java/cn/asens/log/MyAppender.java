package cn.asens.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Priority;

/**
 * Created by Administrator on 2016/11/29.
 */
public class MyAppender extends ConsoleAppender {
    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        //只判断是否相等，而不判断优先级
        return this.getThreshold().equals(priority);
    }
}
