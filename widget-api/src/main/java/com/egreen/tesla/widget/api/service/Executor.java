/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.widget.api.service;

/**
 *
 * @author dewmal
 */
public interface Executor<T> {

    T execute(Object... params);

}
