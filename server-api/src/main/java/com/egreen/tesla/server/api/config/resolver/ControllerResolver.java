/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.config.resolver;

import com.egreen.tesla.server.api.component.Component;
import com.egreen.tesla.widget.api.config.Controller;
import com.egreen.tesla.widget.api.config.RequestMapping;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class ControllerResolver implements TemplateResolver {

    private static final Logger LOGGER = LogManager.getLogger(ControllerResolver.class);

    private final Map<String, RequestResolver> map = new Hashtable<String, RequestResolver>();

    private Component component;

    public void loadClassFromComponent(Component component) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NotFoundException, CannotCompileException {
        this.component = component;
        
        URL myJarFile = component.getJarFile();
        LOGGER.info(myJarFile);
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(component.getFile().getAbsolutePath() + "");

        // pool.
        for (String classPath : component.getControllerClassMapper().keySet()) {
            LOGGER.info(classPath);
            CtClass classLoaded = pool.get(classPath);
            processAnnotations(classLoaded);
        }

    }

    private void processAnnotations(CtClass classFromName) throws ClassNotFoundException, CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        Class<?> classFormOriginal = pool.toClass(classFromName);

        Object controllerAnnotation = classFromName.getAnnotation(Controller.class);
        LOGGER.info(controllerAnnotation);

        if (controllerAnnotation != null) {

            for (CtMethod ctMethod : classFromName.getMethods()) {
                RequestMapping annotation = (RequestMapping) ctMethod.getAnnotation(RequestMapping.class);
                if (annotation != null) {
                    LOGGER.info(annotation.path());
                    map.put(annotation.path(), new RequestResolver(ctMethod, classFormOriginal, this));
                }
            }

        }

    }

    /**
     *
     *
     *
     * @param requestPath
     * @return
     */
    public RequestResolver resolve(String requestPath) {
        return map.get("/" + requestPath);
    }

    @Override
    public String resorcePath(String name) {
        LOGGER.info("87 " + name);

        name = "components/" + component.getComponentBasePath() + "/webapp/" + name + ".html";
        return name;
    }

}
