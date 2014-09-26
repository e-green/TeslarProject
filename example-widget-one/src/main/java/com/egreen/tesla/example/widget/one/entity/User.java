/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.example.widget.one.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author dewmal
 */
@Entity
public class User {

    private String name;
    private String firstName;
    private String password;
    @Id
    private Long id;

    public User() {
    }

    public User(String name, String firstName, String password) {
        this.name = name;
        this.firstName = firstName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
