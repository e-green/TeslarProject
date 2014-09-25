/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class ClassHandlerUtil {

    private static final Logger LOGGER = LogManager.getLogger(ClassHandlerUtil.class);

    /**
     *
     *
     *
     * @param jarName
     * @return
     * @throws IOException
     */
    public static final List<JarEntry> jarItemLoad(FileInputStream jarFileInputStream) throws IOException {

        ArrayList<JarEntry> classes = new ArrayList();
        JarInputStream jarFile = new JarInputStream(jarFileInputStream);
        JarEntry jarEntry;

        while (true) {
            jarEntry = jarFile.getNextJarEntry();

            if (jarEntry == null) {
                break;
            }

            LOGGER.info(jarEntry.getName());
            classes.add(jarEntry);


        }

        return classes;
    }
}
