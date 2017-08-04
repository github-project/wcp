package com.farm.wcp.util;

import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 2017-8-1
 * 日志文件的保存
 * @author Zhang_Java
 *
 */
public class Log4jlistener implements ServletContextListener {  
    public static final String log4jdirkey = "log4jdir";  
    public void contextDestroyed(ServletContextEvent servletcontextevent) {  
        System.getProperties().remove(log4jdirkey);  
    }  
    public void contextInitialized(ServletContextEvent servletcontextevent) {  
    String log4jdir = servletcontextevent.getServletContext().getRealPath("/");  
    //System.out.println("log4jdir:"+log4jdir);  
    System.out.println("服务器的ip是"+Utils.getLocalIP());
    System.setProperty(log4jdirkey, log4jdir);	
   
    }  
}  