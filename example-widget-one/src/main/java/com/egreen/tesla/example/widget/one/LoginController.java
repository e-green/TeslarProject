/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.example.widget.one;

import com.egreen.tesla.example.widget.one.entity.User;
import com.egreen.tesla.widget.api.config.Autowired;
import com.egreen.tesla.widget.api.config.Controller;
import com.egreen.tesla.widget.api.config.Param;
import com.egreen.tesla.widget.api.config.RequestMapping;
import com.egreen.tesla.widget.api.config.ResponseBody;
import com.egreen.tesla.widget.api.config.Transactional;
import com.egreen.tesla.widget.api.service.DBService;
import java.util.Calendar;
import org.springframework.stereotype.Component;

/**
 *
 * @author dewmal
 */
@Controller(path = "/user")
@Component
public class LoginController {

    @Autowired
    private DBService dBService;

    @RequestMapping(path = "/login")
    public String loginView() {
        return "login";
    }

    @RequestMapping(path = "/user")
    @ResponseBody
    public User getUser(@Param("userid") String userid) {
        return new User(userid, "asdas", "asdas");
    }

    @RequestMapping(path = "/save")
    @ResponseBody
    @Transactional
    public User saveUser(@Param("userid") String userid) {
        User user = new User(userid, "asdas", "asdas");
        user.setId(Calendar.getInstance().getTimeInMillis());
        dBService.getSessionFactory().openSession().save(user);
        return user;
    }
}
