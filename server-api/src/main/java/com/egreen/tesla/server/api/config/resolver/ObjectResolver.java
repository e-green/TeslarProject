/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.config.resolver;

import com.egreen.tesla.server.api.component.ServicePool;
import com.egreen.tesla.widget.api.config.Autowired;
import com.egreen.tesla.widget.api.service.DBService;
import com.egreen.tesla.widget.api.service.ServiceBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

/**
 *
 * @author dewmal
 */
public class ObjectResolver {

    private static final Logger LOGGER = LogManager.getLogger(ObjectResolver.class);

    private static ObjectResolver INSTANCE;

    private final ServicePool servicePool;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    private ObjectResolver(ServicePool servicePool, HttpServletRequest request, HttpServletResponse response) {
        this.servicePool = servicePool;
        this.request = request;
        this.response = response;
    }

    /**
     * Build ObjecResolver
     *
     * @param request
     * @return
     */
    public static ObjectResolver getInstance(HttpServletRequest request, HttpServletResponse response) {
        ServicePool pool = (ServicePool) request.getServletContext().getAttribute(ServicePool.class.getName());
        return new ObjectResolver(pool, request, response);
    }

    /**
     *
     * Request New Instance
     *
     * @return
     */
    public Object getInstance(Class ctClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, CannotCompileException, NotFoundException {
        Object newInstance = null;
        LOGGER.info("Load class " + ctClass);
        if (!Modifier.isInterface(ctClass.getModifiers()) && !Modifier.isAbstract(ctClass.getModifiers())) {
            LOGGER.info("Filed Is not a Interface " + ctClass);
            newInstance = ctClass.newInstance();
            intiInstance(ctClass, newInstance);
        }
        return newInstance;
    }

    private void intiInstance(Class ctClass, Object newInstance) throws CannotCompileException, IllegalAccessException, NotFoundException, InstantiationException, SecurityException, ClassNotFoundException, IllegalArgumentException {
        LOGGER.info(" Class Init " + ctClass);
        LOGGER.info(" Class Init " + newInstance);
        Field[] fields = ctClass.getDeclaredFields();
        for (Field field : fields) {
            LOGGER.info(field);
            Autowired autowired = field.getAnnotation(Autowired.class);
            LOGGER.info(ctClass + " " + autowired);
            if (autowired != null) {
                feildAssigner(field, newInstance);
            }
        }
    }

    private void feildAssigner(Field field, Object newInstance) throws IllegalAccessException, IllegalArgumentException, InstantiationException, SecurityException, ClassNotFoundException, CannotCompileException, NotFoundException {
        //Service Assign
        field.setAccessible(true);
        if (field.get(newInstance) == null) {
            //  LOGGER.info("78 " + field.getType().isAssignableFrom(DBService.class));
            Class<?> feildTypeClass = field.getType();
            Object feildObject = null;

            feildObject = getInstance(feildTypeClass);

            if (feildObject instanceof DBService) {
                SessionFactory factory = (SessionFactory) request.getServletContext().getAttribute(SessionFactory.class.getName());
                LOGGER.info("Session Factory " + factory);
                DBService dBService = (DBService) feildObject;
                dBService.init(factory);
                field.setAccessible(true);
                field.set(newInstance, dBService);
                LOGGER.info("Db Service  " + dBService);

            } else if (field.getType().isAssignableFrom(HttpServletRequest.class)) {
                field.setAccessible(true);
                field.set(newInstance, request);
            } else if (field.getType().isAssignableFrom(HttpServletResponse.class)) {
                field.setAccessible(true);
                field.set(newInstance, response);
            } else if (field.getType().isAssignableFrom(ServiceBuilder.class)) {
                Autowired autowired = field.getAnnotation(Autowired.class);

                LOGGER.info("Start Autowireing Service " + autowired.component());
                LOGGER.debug("Start Autowireing Service " + autowired.service());
                LOGGER.debug("Start Autowireing Service " + field);

                Object serviceBuilderClass = servicePool.getServiceBuilder(autowired.component(), autowired.service());

                LOGGER.info("Show methods " + Arrays.toString(serviceBuilderClass.getClass().getDeclaredMethods()));

                ServiceBuilder builder = new ServiceBuilder();
                builder.setInstance(serviceBuilderClass);

                intiInstance(serviceBuilderClass.getClass(), serviceBuilderClass);
                field.setAccessible(true);
                field.set(newInstance, builder);

            }
        }
    }

}
