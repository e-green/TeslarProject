/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.example.widget.one;

import com.egreen.tesla.example.widget.one.entity.User;
import com.egreen.tesla.widget.api.config.Controller;
import com.egreen.tesla.widget.api.config.Param;
import com.egreen.tesla.widget.api.config.RequestMapping;
import com.egreen.tesla.widget.api.config.ResponseBody;

/**
 *
 * @author dewmal
 */
@Controller(path = "/user")
public class LoginController {

    @RequestMapping(path = "/login")
    public String loginView() {
        return "login";
    }

    @RequestMapping(path = "/user")
    @ResponseBody
    public User getUser(@Param("userid") String userid) {
        return new User(userid, "asdas", "asdas");
    }

}
