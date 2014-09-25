/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api;

/**
 *
 * @author dewmal
 */
public enum ScopeType {

    APPLICATION(99), SESSION(999), REQUEST(9999);

    private final long type;

    ScopeType(long type) {
        this.type = type;
    }

    public long getType() {
        return type;
    }
}
