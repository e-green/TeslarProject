/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api;

import com.egreen.tesla.server.api.component.ComponentManager;
import com.egreen.tesla.server.api.component.ServicePool;
import com.egreen.tesla.server.api.configuration.Configurations;
import com.egreen.tesla.server.api.database.DataBaseRepositaryManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.SessionFactory;

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

    private Configurations configuration;
    private XMLConfiguration xmlAppConfiguration;

    public ComponentContextLoader(ServletContext context) {
        this.context = context;
        realPath = context.getRealPath("/");
        this.context.setAttribute("realpath", realPath);
        try {
            create();///init context
        } catch (ConfigurationException | IOException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | InstantiationException | NotFoundException | CannotCompileException | SQLException ex) {
            java.util.logging.Logger.getLogger(ComponentContextLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void create() throws ConfigurationException, IOException, FileNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, MalformedURLException, InstantiationException, NotFoundException, CannotCompileException, SQLException {
        String path = (String) context.getInitParameter(CONTEXT_CONFIG_LOCATION);

        xmlAppConfiguration = new XMLConfiguration(realPath + path);
        configuration = new Configurations(xmlAppConfiguration);
        context.setAttribute("configuration", configuration);

        String appName = xmlAppConfiguration.getString("server.name");
        LOGGER.info(appName);
        context.setAttribute("name", appName);
        String componentPath = realPath
                + xmlAppConfiguration.getString("server.component.base-path", "/component");
        LOGGER.info(componentPath);

        ComponentManager instance = ComponentManager.getInstance();
        instance.loadComponents(componentPath, context);
        DataBaseRepositaryManager dataBaseRepositaryManager = DataBaseRepositaryManager.getINSTANCE();
        dataBaseRepositaryManager.init(configuration.getDBSettings());
        dataBaseRepositaryManager.addEntities(instance.getEntities());
        SessionFactory sessionFactory = dataBaseRepositaryManager.buildRepo();

        context.setAttribute(ServicePool.class.getName(), new ServicePool(instance));
        context.setAttribute(SessionFactory.class.getName(), sessionFactory);
        context.setAttribute("component_manager", instance);

    }

    protected void destroy() {

    }

}
