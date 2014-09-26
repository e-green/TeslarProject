/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.example.widget.one;

import com.egreen.tesla.example.widget.one.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

/**
 *
 * @author dewmal
 */
public class UserController {

    private final Session session;

    public UserController(Session session) {
        this.session = session;
    }

    public User getUser(Long id) {
        User user = null;

        Transaction transaction = session.getTransaction();
        transaction.begin();

        session.get(User.class, id);

        transaction.commit();
        return user;
    }

    public User findUser(String firstName) {
        User user = null;
        Transaction transaction = session.getTransaction();
        transaction.begin();

        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Expression.eq("firstName", firstName));

        transaction.commit();
        return (User) criteria.uniqueResult();

    }

}
