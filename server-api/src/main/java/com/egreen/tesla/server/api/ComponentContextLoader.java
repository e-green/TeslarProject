/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api;

import com.egreen.tesla.server.api.component.ComponentManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author dewmal
 */
public class ComponentContextLoader {

    //Logger for loggin
    private static final Logger LOGGER = LogManager.getLogger(ComponentContextLoader.class);

    private static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private final ServletContext context;
    private final String realPath;

    private File componentsLocation;

    public ComponentContextLoader(ServletContext context) {
        this.context = context;
        realPath = context.getRealPath("/");
        this.context.setAttribute("realpath", realPath);
        try {
            create();///init context
        } catch (ConfigurationException ex) {
            java.util.logging.Logger.getLogger(ComponentContextLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ComponentContextLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            java.util.logging.Logger.getLogger(ComponentContextLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ComponentContextLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger(ComponentContextLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(ComponentContextLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ComponentContextLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ComponentContextLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void create() throws ConfigurationException, IOException, FileNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, MalformedURLException, InstantiationException {
        String path = (String) context.getInitParameter(CONTEXT_CONFIG_LOCATION);
        XMLConfiguration configuration = new XMLConfiguration(realPath + path);
        String appName = configuration.getString("server.name");
        LOGGER.info(appName);
        context.setAttribute("name", appName);
        String componentPath = realPath
                + configuration.getString("server.component.base-path", "/component");
        LOGGER.info(componentPath);

        ComponentManager instance = ComponentManager.getInstance();
        instance.loadComponents(componentPath, context);

        context.setAttribute("component_manager", instance);

    }

    protected void destroy() {

    }

}
