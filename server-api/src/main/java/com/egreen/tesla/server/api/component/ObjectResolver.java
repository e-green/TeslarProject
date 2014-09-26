/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.component;

import com.egreen.tesla.server.api.config.resolver.AutoWiredReslover;
import java.util.Arrays;
import javassist.CtClass;
import javassist.CtField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class ObjectResolver {

    private static final Logger LOGGER = LogManager.getLogger(ObjectResolver.class);

    private final AutoWiredReslover autoWiredReslover;

    public ObjectResolver(AutoWiredReslover autoWiredReslover) {
        this.autoWiredReslover = autoWiredReslover;
    }

    /**
     *
     * Request New Instance
     *
     * @return
     */
    public Object getInstance(CtClass ctClass) throws ClassNotFoundException {

        CtField[] declaredFields = ctClass.getDeclaredFields();

        for (CtField ctField : declaredFields) {
            LOGGER.info(ctField);
            LOGGER.info(Arrays.toString(ctField.getAnnotations()));
        }

        return null;
    }

}
