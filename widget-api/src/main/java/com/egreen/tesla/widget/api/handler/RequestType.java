/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.widget.api.handler;

/**
 *
 * @author dewmal
 */
public enum RequestType {

    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private final String type;

    RequestType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
