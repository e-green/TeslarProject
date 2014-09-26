/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.config.resolver;

import com.egreen.tesla.widget.api.config.Autowired;
import com.egreen.tesla.widget.api.config.Param;
import com.egreen.tesla.widget.api.config.RequestMapping;
import com.egreen.tesla.widget.api.config.ResponseBody;
import com.egreen.tesla.widget.api.service.DBService;
import com.egreen.tesla.widget.api.service.ServiceBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

/**
 *
 * @author dewmal
 */
public class RequestResolver {

    private static final Logger LOGGER = LogManager.getLogger(RequestResolver.class);

    public final CtMethod ctMethod;
    private final Class ctClass;

    private RequestMapping requestMapping;
    private final TemplateResolver templateResolver;

    public RequestResolver(CtMethod ctMethod, Class ctClass, TemplateResolver templateResolver) {
        this.ctMethod = ctMethod;
        this.ctClass = ctClass;
        this.templateResolver = templateResolver;
    }

    public void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ClassNotFoundException,
            CannotCompileException, InstantiationException,
            IllegalAccessException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException,
            IOException, ServletException {
        boolean noError = true;
        if (requestMapping == null) {
            requestMapping = (RequestMapping) ctMethod.getAnnotation(RequestMapping.class);
        }
        Object newInstance = ctClass.newInstance();


        Field[] fields = ctClass.getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                feildAssigner(field, newInstance, request, response);
            }
        }

        Method method = null;
        Object methodParams[] = null;
        Class[] methodParamType = null;
        for (Object[] objects
                : ctMethod.getParameterAnnotations()) {
            if (objects != null) {
                methodParamType = new Class[objects.length];
                methodParams = new Object[objects.length];
                for (int i = 0; i < objects.length; i++) {
                    Object objectInstance = objects[i];
                    if (objectInstance instanceof Param) {
                        Param param = (Param) objectInstance;
                        methodParamType[i] = param.value().getClass();
                        String parameter = request.getParameter(param.value());
                        if (parameter == null) {
                            sendErrorMessage(response, "Invalid parameter " + param.value());
                            noError = false;
                        }
                    }
                }
            }
        }

        if (noError) {
            LOGGER.info(Arrays.toString(methodParamType));
            if (methodParamType == null || methodParamType.length == 0) {
                method = ctClass.getMethod(ctMethod.getName());
            } else {
                method = ctClass.getMethod(ctMethod.getName(), methodParamType);
            }
            Object invoke = null;
            LOGGER.info(method);
            if (methodParams == null || methodParams.length == 0) {

                invoke = method.invoke(newInstance);
            } else {
                LOGGER.info(Arrays.toString(methodParams));
                try {
                    invoke = method.invoke(newInstance, methodParams);
                    LOGGER.info(invoke);
                } catch (InvocationTargetException exception) {
                    exception.getTargetException().printStackTrace();
                }
            }

            if (method.getAnnotation(ResponseBody.class) != null) {
                // Json
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                StringWriter stringEmp = new StringWriter();
                objectMapper.writeValue(stringEmp, invoke);
                response.getWriter().write(stringEmp.toString());
            } else if (invoke instanceof String) {
                String resorcePath = templateResolver.resorcePath(invoke + "");
                LOGGER.debug(resorcePath);
                LOGGER.debug(request);
                LOGGER.debug(response);
                request.setAttribute("viewpath", resorcePath);
                request.getRequestDispatcher("/").forward(request, response);
            } else {
                sendErrorMessage(response, "Cannot resolve Response Object");
            }
        }

    }

    private
            void feildAssigner(Field field, Object newInstance, HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InstantiationException, SecurityException {
        //Service Assign

        if (field.getType().isAssignableFrom(DBService.class
        )) {
            SessionFactory factory = (SessionFactory) request.getServletContext().getAttribute(SessionFactory.class.getName());

            LOGGER.debug(
                    "78 " + factory);
            DBService dBService = DBService.class.newInstance();

            dBService.init(factory);

            field.setAccessible(
                    true);
            field.set(newInstance, dBService);

            LOGGER.debug(
                    "78 " + dBService);
        } else if (field.getType().isAssignableFrom(HttpServletRequest.class
        )) {
            field.setAccessible(
                    true);
            field.set(newInstance, request);
        } else if (field.getType().isAssignableFrom(HttpServletResponse.class
        )) {
            field.setAccessible(
                    true);
            field.set(newInstance, response);
        } else if (field.getType().isAssignableFrom(ServiceBuilder.class
        )) {
            Autowired autowired = field.getAnnotation(Autowired.class);

            LOGGER.info(autowired.component());
            LOGGER.info(autowired.service());
            LOGGER.info(field);

            field.setAccessible(
                    true);
            field.set(newInstance, response);
        }
    }

    private void sendErrorMessage(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().print("<html><head><title>Oops an error happened!</title></head>");
        resp.getWriter().print("<body>" + message + "!</body>");
        resp.getWriter().println("</html>");
    }

}
