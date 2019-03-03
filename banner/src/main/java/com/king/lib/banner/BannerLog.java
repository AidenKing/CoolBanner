package com.king.lib.banner;

import android.util.Log;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2019/3/1 10:16
 */
public class BannerLog {

    private static boolean isDebug=false;
    /**类名**/
    private static String className;
    /**方法名**/
    private static String methodName;

    public static void e(String message){
        if (!isDebug)
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
    }

    private static String createLog(String log){

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

}
