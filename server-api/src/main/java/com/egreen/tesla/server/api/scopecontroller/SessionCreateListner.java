/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.scopecontroller;

import com.egreen.tesla.server.api.ComponentContextLoader;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author dewmal
 */
public class SessionCreateListner implements HttpSessionListener {

    private final ComponentContextLoader componentContext;

    public SessionCreateListner(ComponentContextLoader componentContext) {
        this.componentContext = componentContext;
    }

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
