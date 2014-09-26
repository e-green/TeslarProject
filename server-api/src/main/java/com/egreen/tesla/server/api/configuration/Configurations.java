/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.configuration;

import com.egreen.tesla.server.api.database.DBSetttings;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author dewmal
 */
public class Configurations {

    private final XMLConfiguration configuration;

    public Configurations(XMLConfiguration configuration) {
        this.configuration = configuration;
    }

    public BasicDBSettings getDBSettings() {
        return new BasicDBSettings();
    }

    public class BasicDBSettings implements DBSetttings {

        private BasicDBSettings() {

        }

        @Override
        public String getDB_URL() {
            return configuration.getString("server.database.url");
        }

        @Override
        public String getDB_USER() {
            return configuration.getString("server.database.username");
        }

        @Override
        public String getDB_PASS() {
            return configuration.getString("server.database.password");
        }

        @Override
        public String getDB_NAME() {
            return configuration.getString("server.database.name");
        }
    }
}
