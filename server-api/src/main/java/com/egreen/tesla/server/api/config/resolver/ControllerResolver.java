/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.config.resolver;

import com.egreen.tesla.server.api.component.Component;
import com.egreen.tesla.widget.api.config.Controller;
import com.egreen.tesla.widget.api.config.RequestMapping;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class ControllerResolver {

    private static final Logger LOGGER = LogManager.getLogger(ControllerResolver.class);

    private final Map<String, Method> map = new Hashtable<String, Method>();

    public void loadClassFromComponent(Component component) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        URL myJarFile = component.getJarFile();
LOGGER.info(myJarFile);
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

        Class sysClass = URLClassLoader.class;
        Method sysMethod = sysClass.getDeclaredMethod("addURL",
                new Class[]{
                    URL.class
                }
        );
        sysMethod.setAccessible(true);
        sysMethod.invoke(sysLoader, new Object[]{myJarFile});

        for (Object object : component.getControllerClassMapper().keySet()) {

            Class classFromName = sysLoader.loadClass(object + "");
                     
            
            
            for (Annotation annotation : classFromName.getAnnotations()) {
                LOGGER.info(annotation);
            }

            Annotation[] isController = classFromName.getDeclaredAnnotationsByType(Controller.class);
            //  boolean isEntity = classFromName.isAnnotationPresent(Entity.class);
            LOGGER.info(Arrays.toString(isController));
            LOGGER.info(classFromName);
            if (false) {
                Method[] methods = classFromName.getMethods();
                /**
                 * Get method
                 */
                for (Method method : methods) {
                    LOGGER.info(method);
                    boolean isRequestHandler = method.isAnnotationPresent(
                            RequestMapping.class);
                    if (isRequestHandler) {
                        LOGGER.info(method.getDeclaringClass());
                    }
                }

            }

//            else if (isEntity) {
//                component.setEntity(classFromName);
//            }
        }

    }

}
