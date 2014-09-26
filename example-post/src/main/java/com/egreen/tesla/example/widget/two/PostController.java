/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.example.widget.two;

import com.egreen.tesla.widget.api.config.Autowired;
import com.egreen.tesla.widget.api.config.Controller;
import com.egreen.tesla.widget.api.config.RequestMapping;
import com.egreen.tesla.widget.api.service.DBService;
import com.egreen.tesla.widget.api.service.Executor;
import com.egreen.tesla.widget.api.service.ServiceBuilder;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dewmal
 */
@Controller(path = "/post")
public class PostController {

    @Autowired
    private DBService dBService;

    @Autowired
    private HttpServletRequest request;

    @Autowired(component = "com.name.example.one", service = "UserService")
    private ServiceBuilder builder;

    @RequestMapping(path = "/show")
    public String postView() {
        Executor execute = builder.execute("find");
        Object execute1 = execute.execute("name");
        System.out.println(execute1);
        return "post";
    }

}
