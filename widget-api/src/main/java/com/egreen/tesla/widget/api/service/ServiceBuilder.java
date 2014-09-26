/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.widget.api.service;

import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author dewmal
 */
public abstract class ServiceBuilder {

    private Map<String, Executor> executors = new Hashtable<String, Executor>();

    public ServiceBuilder() {
        initExecutors();
    }

    public Executor execute(String name) {
        return executors.get(name);
    }

    protected abstract void initExecutors();

    protected void addExecutor(String name, Executor executor) {
        executors.put(name, executor);
    }

}
