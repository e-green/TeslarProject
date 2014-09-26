/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.objects;

import com.egreen.tesla.server.api.ScopeType;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dewmal
 */
public class ObjectsPool {

    /**
     *
     */
    private static final Map<String, Object> APPLICATION_CONTEXT_POOL = new Hashtable<>();

    /**
     *
     */
    private static final Map<String, Map<String, Object>> SESSION_POOL = new Hashtable<>();

    /**
     *
     */
    private static final Map<String, Object> REQUEST_POOL = new Hashtable<>();

    /**
     *
     */
    private static ObjectsPool INSTANCE;

    /**
     *
     */
    private File libFile;

    private ObjectsPool() {

    }

    private static final ObjectsPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObjectsPool();
        }
        return INSTANCE;
    }

    public void init(File file) {
        libFile = file;

    }

    /**
     *
     * @param scopeType
     * @param ClassName
     * @return
     */
    public Object getObject(ScopeType scopeType, String ClassName, HttpServletRequest request) {
        switch (scopeType) {
            case APPLICATION: {
                return getObjectFromApplication(ClassName);
            }
            case SESSION: {
                return getObjectFromSession(request.getSession(), ClassName);
            }
            case REQUEST: {
                return getObjectFromRequest(request, ClassName);
            }
            default:
                return null;
        }
    }

    /**
     *
     * @param sessoionKey
     * @param name
     * @return
     */
    private Object getObjectFromSession(HttpSession httpSession, String name) {
        Map<String, Object> session = SESSION_POOL.get(httpSession.getId());
        if (session == null) {
            Object sessionObject = session.get(name);
            return sessionObject;
        }
        return null;
    }

    /**
     *
     * @param name
     * @return
     */
    private Object getObjectFromApplication(String ClassName) {
        Object get = APPLICATION_CONTEXT_POOL.get(ClassName);
        if (get == null) {
        }
        return null;
    }

    /**
     *
     * @param ClassName
     * @param ClassName0
     * @return
     */
    private Object getObjectFromRequest(HttpServletRequest request, String ClassName) {
        Object object = REQUEST_POOL.get(ClassName);
        return object;
    }

}
