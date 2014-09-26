/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.bean;

import com.egreen.tesla.server.api.ScopeType;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class BeanInjectPool {

    private static final Logger LOGGER = LogManager.getLogger(BeanInjectPool.class);

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static final BeanInjectPool INSTANCE = new BeanInjectPool();

    private static boolean isNew = true;

    private BeanInjectPool() {
    }

    public static BeanInjectPool getINSTANCE() {
        return INSTANCE;
    }
//</editor-fold>

    public PoolUser createPoolUser(HttpServletRequest request) {
        PoolUser poolUser = new PoolUser(request);
        return poolUser;
    }

    private static final String OBJECTPOOL = "objectpool";

    public class PoolUser {

        private final Logger LOGGER = LogManager.getLogger(BeanInjectPool.LOGGER);

        private final HttpServletRequest request;

        private Map<CtClass, Object> objectPoolApplication;
        private Map<CtClass, Object> objectPoolSession;
        private Map<CtClass, Object> objectPoolRequest;

        private final ServletContext servletContext;
        private final HttpSession session;

        public PoolUser(HttpServletRequest request) {
            this.request = request;
            servletContext = request.getServletContext();
            session = request.getSession();

            objectPoolApplication = (Map<CtClass, Object>) servletContext.getAttribute(OBJECTPOOL);
            if (objectPoolApplication == null) {
                objectPoolApplication = new Hashtable<>();
            }

            objectPoolSession = (Map<CtClass, Object>) session.getAttribute(OBJECTPOOL);
            if (objectPoolSession == null) {
                objectPoolSession = new Hashtable<>();

            }
            objectPoolRequest = (Map<CtClass, Object>) request.getAttribute(OBJECTPOOL);
            if (objectPoolRequest == null) {
                objectPoolRequest = new Hashtable<>();
            }
            updateScopes();

        }

        private void updateScopes() {
            servletContext.setAttribute(OBJECTPOOL, objectPoolApplication);
            session.setAttribute(OBJECTPOOL, objectPoolSession);
            request.setAttribute(OBJECTPOOL, objectPoolRequest);
        }

        public void initBeans(List<CtClass> ctClasses) throws CannotCompileException, ClassNotFoundException {
            if (ctClasses != null) {
                for (CtClass ctClass : ctClasses) {
                    initBean(ctClass);
                }
            }
            updateScopes();
        }

        private Object initBean(CtClass ctClass) throws ClassNotFoundException, CannotCompileException {
            ScopeType scopeType = getScopeType(ctClass);
            Object newInstance = getNewInstance(ctClass);
            switch (scopeType) {
                case APPLICATION:
                    objectPoolApplication.put(ctClass, newInstance);
                    break;
                case SESSION:
                    objectPoolSession.put(ctClass, newInstance);
                    break;
                case REQUEST:
                    objectPoolRequest.put(ctClass, newInstance);
                    break;
                default:
                    return null;
            }
            return newInstance;
        }

        /**
         *
         * @param <T>
         * @param objectClass
         * @return
         * @throws InstantiationException
         * @throws IllegalAccessException
         * @throws CannotCompileException
         * @throws ClassNotFoundException
         */
        public <T> T get(CtClass objectClass) throws InstantiationException, IllegalAccessException, CannotCompileException, ClassNotFoundException {
            ScopeType scopeType = getScopeType(objectClass);
            T object = (T) getFromPools(objectClass, scopeType);
            if (object == null || ScopeType.REQUEST == scopeType) {
                object = (T) initBean(objectClass);
                updateScopes();
            }
            return object;
        }

        private Object getFromPools(CtClass ctClass, ScopeType scopeType) {
            switch (scopeType) {
                case APPLICATION:
                    return objectPoolApplication.get(ctClass);
                case SESSION:
                    return objectPoolSession.get(ctClass);
                case REQUEST:
                    return objectPoolRequest.get(ctClass);
                default:
                    return null;
            }
        }

        /**
         *
         * @param ctClass
         * @return
         * @throws CannotCompileException
         */
        private Object getNewInstance(CtClass ctClass) throws CannotCompileException {
            ClassPool pool = ClassPool.getDefault();
            Class<?> classFormOriginal = pool.toClass(ctClass);
            return classFormOriginal;
        }

        /**
         *
         * @param objectClass
         * @return
         * @throws ClassNotFoundException
         */
        private ScopeType getScopeType(CtClass objectClass) throws ClassNotFoundException {
            ScopeType scopeType = (ScopeType) objectClass.getAnnotation(ScopeType.class);
            return scopeType;
        }
    }
}
