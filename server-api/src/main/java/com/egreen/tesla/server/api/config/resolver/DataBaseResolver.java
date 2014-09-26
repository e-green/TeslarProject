/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.config.resolver;

import java.util.ArrayList;
import java.util.List;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javax.persistence.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class DataBaseResolver extends ClassResolver {

    private static final Logger LOGGER = LogManager.getLogger(DataBaseResolver.class);

    private List<Class> entityList = new ArrayList();


  

    /**
     * Process Annotation
     *
     * @param classLoaded
     * @throws ClassNotFoundException
     * @throws CannotCompileException
     */
    @Override
    protected void processAnnotations(CtClass classLoaded) throws ClassNotFoundException, CannotCompileException {
        if (classLoaded.getAnnotation(Entity.class) != null) {
            ClassPool pool = ClassPool.getDefault();
            Class<?> classFormOriginal = pool.toClass(classLoaded);
            entityList.add(classFormOriginal);
        }
    }

    public List<Class> getEntityList() {
        return entityList;
    }

}
