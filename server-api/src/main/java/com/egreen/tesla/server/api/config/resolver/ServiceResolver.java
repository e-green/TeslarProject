/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.config.resolver;

import com.egreen.tesla.widget.api.config.Service;
import com.egreen.tesla.widget.api.service.ServiceBuilder;
import java.util.Hashtable;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

/**
 *
 * @author dewmal
 */
public class ServiceResolver extends ClassResolver {

    private Map<String, CtClass> serviceClasses = new Hashtable<>();

    @Override
    protected void processAnnotations(CtClass ctClass) throws Exception {
        Service service = (Service) ctClass.getAnnotation(Service.class);
        if (service == null) {
            serviceClasses.put(ctClass.getSimpleName(), ctClass);
        }
    }

    public Object getCtClass(String name) throws CannotCompileException, InstantiationException, IllegalAccessException {
        CtClass resolveClass = serviceClasses.get(name);
        if (resolveClass != null) {
            ClassPool pool = ClassPool.getDefault();
            Class<?> classFormOriginal = pool.toClass(resolveClass);
            return classFormOriginal.newInstance();
        }
        return null;
    }

}
