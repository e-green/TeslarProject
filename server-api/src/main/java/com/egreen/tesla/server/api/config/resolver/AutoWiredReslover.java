/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.config.resolver;

import com.egreen.tesla.server.api.component.ServicePool;
import com.egreen.tesla.widget.api.config.Autowired;
import java.lang.reflect.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class AutoWiredReslover {

    private static final Logger LOGGER = LogManager.getLogger(AutoWiredReslover.class);

    private final ServicePool servicePool;

    public AutoWiredReslover(ServicePool servicePool) {
        this.servicePool = servicePool;
    }

    public Object initObject(Object instance) throws ClassNotFoundException {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = (Autowired) field.getAnnotation(Autowired.class);
            LOGGER.info(autowired);
        }
        return instance;
    }

}
