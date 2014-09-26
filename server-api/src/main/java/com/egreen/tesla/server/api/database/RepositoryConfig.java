/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author dewmal
 */
public class RepositoryConfig {

    //${jdbc.driverClassName}
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String url;
    private final String username;
    private final String password;

    private String hibernateDialect;
    private String hibernateShowSql;
    private String hibernateHbm2ddlAuto;

    public RepositoryConfig(DBSetttings setttings) throws ClassNotFoundException, SQLException {
        this.url = setttings.getDB_URL() + setttings.getDB_NAME();
        this.username = setttings.getDB_USER();
        this.password = setttings.getDB_PASS();
        create_db(setttings);

    }

    private boolean create_db(DBSetttings setttings) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        Connection connect = DriverManager
                .getConnection(setttings.getDB_URL(),setttings.getDB_USER(),setttings.getDB_PASS());
        Statement statement=connect.createStatement();
        return statement.execute("CREATE SCHEMA IF NOT EXISTS `"+setttings.getDB_NAME()+"` DEFAULT CHARACTER SET utf8 ;");
    }

    public SessionFactory init(List<Class> classes) {

        // Create the SessionFactory from hibernate.cfg.xml
        Configuration configuration = new AnnotationConfiguration();

        configuration.setProperty("hibernate.connection.url", url);
        configuration.setProperty("hibernate.connection.username", username);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", JDBC_DRIVER);
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        for (Class class1 : classes) {
            configuration = configuration.addAnnotatedClass(class1);
        }

        //ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        return configuration.buildSessionFactory();

    }
}
