/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.component;

import com.egreen.tesla.widget.api.service.ServiceBuilder;
import javassist.CannotCompileException;

/**
 *
 * @author dewmal
 */
public class ServicePool {

    private final ComponentManager componentManager;

    public ServicePool(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public ServiceBuilder getServiceBuilder(String componentid, String serivceName) throws CannotCompileException, InstantiationException, IllegalAccessException {
        return (ServiceBuilder) componentManager.getCOMPONENT_MAP().get(componentid).getServiceResolver().getCtClass(serivceName);
    }

}
