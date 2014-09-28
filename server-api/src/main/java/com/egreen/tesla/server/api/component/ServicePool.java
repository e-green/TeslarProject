/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.component;

import com.egreen.tesla.server.api.config.resolver.ServiceResolver;
import com.egreen.tesla.widget.api.service.ServiceBuilder;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class ServicePool {

    private static final Logger LOGGER = LogManager.getLogger(ServicePool.class);

    private final ComponentManager componentManager;

    public ServicePool(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public Object getServiceBuilder(String componentid, String serivceName) throws CannotCompileException, InstantiationException, IllegalAccessException, NotFoundException {
        final Map<String, Component> component_map = componentManager.getCOMPONENT_MAP();
        for (String string : component_map.keySet()) {
            LOGGER.info(string);
        }
        final String hiidden_value = componentid.replace(".", "_");
        LOGGER.info("Convet id to hidden path" + hiidden_value);
        final Component component = component_map.get(hiidden_value);
        LOGGER.info("Component" + component);
        final ServiceResolver serviceResolver = component.getServiceResolver();
        LOGGER.info("ServiceResolver " + serivceName);
        return  serviceResolver.getCtClass(serivceName, component);
    }

}
