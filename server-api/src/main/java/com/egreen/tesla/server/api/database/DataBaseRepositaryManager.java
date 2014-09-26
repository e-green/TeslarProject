/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

/**
 *
 * @author dewmal
 */
public class DataBaseRepositaryManager {
    
    private static final Logger LOGGER = LogManager.getLogger(DataBaseRepositaryManager.class);
    
    private List<Class> entities = new ArrayList<Class>();
    
    private static final DataBaseRepositaryManager INSTANCE = new DataBaseRepositaryManager();
    private RepositoryConfig repositoryConfig;
    
    private DataBaseRepositaryManager() {
        
    }
    
    public static DataBaseRepositaryManager getINSTANCE() {
        return INSTANCE;
    }
    
    public void addEntity(Class class1) {
        entities.add(class1);
    }
    
    public SessionFactory buildRepo() {
        SessionFactory sessionFactory = repositoryConfig.init(entities);
        LOGGER.info(sessionFactory);
        return sessionFactory;
    }
    
    public void addEntities(List<Class> list) {
        entities.addAll(list);
    }
    
    public void init(DBSetttings dbSetttings) throws ClassNotFoundException, SQLException {
        repositoryConfig = new RepositoryConfig(dbSetttings);
        
    }
}
