/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.widget.api.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author dewmal
 */
public class DBService {

    private SessionFactory sessionFactory;

    public void init(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Get Session
     *
     * @return
     */
    public Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    public void execute(DbExecuter runnable) {
        Session openSession = getSessionFactory().openSession();
        Transaction transaction = openSession.beginTransaction();
        runnable.execute(openSession);
        transaction.commit();
    }

    public interface DbExecuter {

        void execute(Session session);
    }

}
