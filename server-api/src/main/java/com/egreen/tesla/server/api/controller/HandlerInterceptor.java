/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.controller;

import com.egreen.tesla.server.api.component.Component;
import com.egreen.tesla.server.api.component.ComponentManager;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * Handle all requests
 *
 *
 *
 * @author dewmal
 */
public class HandlerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger(HandlerInterceptor.class);
    private String componentPath;
    private String requestPath;

    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        StringTokenizer st = new StringTokenizer(request.getPathInfo(), "/");

       // LOGGER.info(st.nextToken());//remove ts-api);
        ComponentManager componentManager = (ComponentManager) request.getServletContext().getAttribute("component_manager");
        if (st.hasMoreTokens()) {
            componentPath = st.nextToken();

        }
        LOGGER.info(componentPath);
        if (st.hasMoreTokens()) {
            requestPath = st.nextToken();

        }
        LOGGER.info(requestPath);
        if (componentPath != null) {
            Component component = componentManager.getComponent(componentPath+"".trim());
            LOGGER.info("load component for handle request " + component);
            if (component != null) {
                component.loadRequestController(requestPath);
            } else {
                response.getWriter().print("Invalid Request");
            }
        } else {

        }

    }

}
