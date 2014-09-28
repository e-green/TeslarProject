/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.config.resolver;

import com.egreen.tesla.server.api.component.Component;
import com.egreen.tesla.widget.api.config.Service;
import java.util.Hashtable;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class ServiceResolver extends ClassResolver {

    private static final Logger LOGGER = LogManager.getLogger(ServiceResolver.class);

    private Map<String, CtClass> serviceClasses = new Hashtable<>();

    @Override
    protected void processAnnotations(CtClass ctClass) throws Exception {
        Service service = (Service) ctClass.getAnnotation(Service.class);
        if (service != null) {
            LOGGER.info("Load Class To Service " + ctClass.getName() + " with Key " + ctClass.getSimpleName());
            serviceClasses.put(ctClass.getSimpleName(), ctClass);
        }
    }

    public Object getCtClass(String name, Component component) throws CannotCompileException, InstantiationException, IllegalAccessException, NotFoundException {
        CtClass resolveClass = serviceClasses.get(name);
        if (resolveClass != null) {
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(component.getFile().getAbsolutePath() + "");
            Class classFormOriginal = pool.toClass(resolveClass);
            return classFormOriginal.newInstance();
        }
        return null;
    }

}
