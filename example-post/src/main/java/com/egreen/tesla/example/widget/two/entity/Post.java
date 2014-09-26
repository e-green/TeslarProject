/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.egreen.tesla.example.widget.two.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author dewmal
 */
@Entity
public class Post {
    @Id
    private Long id;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
