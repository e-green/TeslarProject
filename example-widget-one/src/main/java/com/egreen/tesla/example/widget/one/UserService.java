/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.example.widget.one;

import com.egreen.tesla.example.widget.one.entity.User;
import com.egreen.tesla.widget.api.config.Autowired;
import com.egreen.tesla.widget.api.config.Service;
import com.egreen.tesla.widget.api.service.DBService;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author dewmal
 */
@Service(name = "UserService")
public class UserService {

    @Autowired
    private DBService bService;

    public UserService() {
    }

    public User getUser(Long id) {
        User userFind = null;
        Session session = bService.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        userFind=(User) session.get(User.class, id);

        transaction.commit();
        return userFind;
    }

    public void find(String username) {

    }

}
