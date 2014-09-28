/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.widget.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author dewmal
 */
public class ServiceBuilder {

    private final Map<String, Method> methodMap = new Hashtable<String, Method>();

    private Object instance;

    public ServiceBuilder() {
    }

    public Map callService(String name, Object... objects) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        Method method = methodMap.get(name);
        Object invoke = method.invoke(instance, objects);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        StringWriter stringEmp = new StringWriter();
        objectMapper.writeValue(stringEmp, invoke);
        return objectMapper.readValue(stringEmp.toString(), Map.class);
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {

        Class<? extends Object> objectClass = instance.getClass();
        Method[] declaredMethods = objectClass.getDeclaredMethods();

        for (Method method : declaredMethods) {
            methodMap.put(method.getName(), method);
        }

        this.instance = instance;
    }

}
