/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.config.resolver;

import com.egreen.tesla.server.api.component.Component;
import java.net.URL;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public abstract class ClassResolver {

    private static final Logger LOGGER = LogManager.getLogger(ClassResolver.class);

    /**
     *
     */
    protected Component component;

    public void loadClassFromComponent(Component component) throws Exception {

        this.component = component;

        URL myJarFile = component.getJarFile();
       
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(component.getFile().getAbsolutePath() + "");

        // pool.
        for (String classPath : component.getControllerClassMapper().keySet()) {
         
            CtClass classLoaded = pool.get(classPath);
            processAnnotations(classLoaded);
        }

    }

    protected abstract void processAnnotations(CtClass ctClass) throws Exception;
}
