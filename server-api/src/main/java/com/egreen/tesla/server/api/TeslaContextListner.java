/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api;

import com.egreen.tesla.server.api.scopecontroller.SessionCreateListner;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.SessionTrackingMode;

/**
 *
 * @author dewmal
 */
public class TeslaContextListner implements ServletContextListener {
    
    
    private ComponentContextLoader componentContextLoader;  
    
    

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();        
        componentContextLoader=new ComponentContextLoader(servletContext); 
        SessionCreateListner sessionCreateListner=new SessionCreateListner(componentContextLoader);
      
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(componentContextLoader!=null){
            componentContextLoader.destroy();
        }        
    }

}
