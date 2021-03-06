/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.example.widget.two;

import com.egreen.tesla.widget.api.config.Autowired;
import com.egreen.tesla.widget.api.config.Controller;
import com.egreen.tesla.widget.api.config.Param;
import com.egreen.tesla.widget.api.config.RequestMapping;
import com.egreen.tesla.widget.api.service.DBService;
import com.egreen.tesla.widget.api.service.ServiceBuilder;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @RequestMapping(path = "/save")
    public String postView(@Param("userid") String userid) {
        Map callService;
        try {
            callService = builder.callService("getUser", Long.parseLong(userid));            
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "post";
    }

}
