/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.component;

import com.egreen.tesla.server.api.database.DataBaseRepositaryManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.NotFoundException;
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

    /**
     * Load all entities
     */
    private final List<Class> entities = new ArrayList<Class>();

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
    public Map<String, Component> loadComponents(String componentPath, final ServletContext context) throws FileNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, MalformedURLException, ConfigurationException, InstantiationException, NotFoundException, CannotCompileException {
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

            entities.addAll(component.getEntities());

            COMPONENT_MAP.put(component.getComponentBasePath(), component);
        }
        return COMPONENT_MAP;
    }

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

    public Component getComponent(String componentPath) {
        return COMPONENT_MAP.get(componentPath);
    }

    public List<Class> getEntities() {
        return entities;
    }

}
