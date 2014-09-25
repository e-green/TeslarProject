/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class ComponentManager {

    /**
     *
     */
    private static final Logger LOGGER = LogManager.getLogger(ComponentManager.class);

    /**
     *
     */
    private final Map<String, Component> COMPONENT_MAP = new HashMap<String, Component>();

    private static ComponentManager INSTANCE;

    private ComponentManager() {

    }

    public static ComponentManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ComponentManager();
        }
        return INSTANCE;
    }

    /**
     *
     * @param componentPath
     * @param context
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     * @throws java.net.MalformedURLException
     * @throws org.apache.commons.configuration.ConfigurationException
     */
    public Map<String, Component> loadComponents(String componentPath, final ServletContext context) throws FileNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, MalformedURLException, ConfigurationException, InstantiationException {
        LOGGER.info(context);

        File componentsLocation = new File(componentPath);
        File locations[] = componentsLocation.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File directory, String fileName) {
                LOGGER.info(fileName);
                return fileName.endsWith(".jar");
            }
        });
        for (File file : locations) {
            Component component = new Component(file, context);
            COMPONENT_MAP.put(component.getComponentBasePath(), component);
        }
        return COMPONENT_MAP;
    }

//    /**
//     *
//     * @param file
//     * @return
//     * @throws FileNotFoundException
//     * @throws IOException
//     * @throws NoSuchMethodException
//     * @throws IllegalAccessException
//     * @throws InvocationTargetException
//     * @throws ClassNotFoundException
//     */
//    private static final Component buildComponent(File file) throws FileNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
//        Component component = new Component(file.get);
//     component.init(file);
//        return component;
//    }
    /**
     *
     * Access Loaded Components
     *
     *
     * @return
     */
    public Map<String, Component> getCOMPONENT_MAP() {
        return COMPONENT_MAP;
    }

    /**
     *
     * @param path
     * @return
     */
    public Class<?> getRequestClass(String path) {
        return null;
    }

    public Component getComponent(String componentPath) {
//        for (String string : COMPONENT_MAP.keySet()) {
//            LOGGER.info("121 "+string);
//            LOGGER.info("122 "+string+"".equals(componentPath));
//            LOGGER.info("122 "+string+"".length()+" "+(componentPath)+"".length());
//        }
//        
//        
        return COMPONENT_MAP.get(componentPath);

    }

}
