/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.widget.api.service;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author dewmal
 */
@Component
public class DBService {

    private SessionFactory sessionFactory;

    public void init(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
