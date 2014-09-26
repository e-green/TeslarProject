/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.example.widget.one;

import com.egreen.tesla.widget.api.config.Autowired;
import com.egreen.tesla.widget.api.config.Service;
import com.egreen.tesla.widget.api.service.DBService;
import com.egreen.tesla.widget.api.service.Executor;
import com.egreen.tesla.widget.api.service.ServiceBuilder;
import org.hibernate.Session;

/**
 *
 * @author dewmal
 */
@Service(name = "UserService")
public class UserService extends ServiceBuilder {

    @Autowired
    private DBService bService;

    @Override
    protected void initExecutors() {
        addExecutor("get", new Executor() {
            @Override
            public Object execute(Object... params) {
                Session session = bService.getSession();
                return new UserController(session).getUser((Long) params[0]);
            }
        });

        addExecutor("find", new Executor() {
            @Override
            public Object execute(Object... params) {
                Session session = bService.getSession();
                return new UserController(session).findUser((String) params[0]);
            }
        });
    }

}
