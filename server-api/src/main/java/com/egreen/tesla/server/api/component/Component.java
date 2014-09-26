/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egreen.tesla.server.api.component;

import com.egreen.tesla.server.api.config.resolver.ControllerResolver;
import com.egreen.tesla.server.api.config.resolver.DataBaseResolver;
import com.egreen.tesla.server.api.config.resolver.RequestResolver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javax.servlet.ServletContext;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dewmal
 */
public class Component {

    private static final Logger LOGGER = LogManager.getLogger(Component.class);

    private static final String TESLAR_WIDGET_MAINIFIESTXML = "TeslarWidgetManifiest.xml";

    private List<JarEntry> allEntrys = new ArrayList<JarEntry>();

    private Map<String, String> controllerClassMapper = new Hashtable<String, String>();

    private Map<String, String> htmlFileMapper = new Hashtable<String, String>();

    private final File file;

    private URL jarFile;

    //Component id
    private String componentID;

    //Location where is save my details
    private String component_base;

    //Configurations
    private XMLConfiguration configuration;

    //Application Servlet Context
    private final ServletContext context;

    //
    private final ControllerResolver controllerResolver = new ControllerResolver();

    private final DataBaseResolver dataBaseResolver = new DataBaseResolver();

    public Component(File name, ServletContext context) throws MalformedURLException, IOException, FileNotFoundException, ConfigurationException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NotFoundException, CannotCompileException {
        this.file = name;
        this.context = context;
        init();

        try {
            controllerResolver.loadClassFromComponent(this);
            dataBaseResolver.loadClassFromComponent(this);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void setAllEntrys(List<JarEntry> allEntrys) {
        if (this.allEntrys != null) {
            this.allEntrys.addAll(allEntrys);

        } else {
            this.allEntrys = allEntrys;
        }
    }

    public void setAllEntrys(JarEntry jarEntry) {
        this.allEntrys.add(jarEntry);
    }

    public URL getJarFile() {
        return jarFile;
    }

    public void setJarFile(URL jarFile) {
        this.jarFile = jarFile;
    }

    public void LoadClass() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class sysClass = URLClassLoader.class;
        Method sysMethod = sysClass.getDeclaredMethod("addURL",
                new Class[]{
                    URL.class
                }
        );
        sysMethod.setAccessible(true);
        sysMethod.invoke(sysLoader, new Object[]{jarFile});
//        for (Object object : classeNames) {
//            Class classFromName = sysLoader.loadClass(object + "");
//            LOGGER.info(classFromName.getSimpleName());
//            component.setControllers(object + "", sysClass);
//        }
    }

    private void init() throws FileNotFoundException, IOException, ConfigurationException {

        //Init url
        this.jarFile = new URL("jar", "", "file:" + file.getAbsolutePath() + "!/");

        final FileInputStream fileInputStream = new FileInputStream(file);
        JarInputStream jarFile = new JarInputStream(fileInputStream);
        JarFile jf = new JarFile(file);

        setConfiguraton(jf);//Configuration load

        jf.getEntry(TESLAR_WIDGET_MAINIFIESTXML);

        JarEntry jarEntry;

        while (true) {
            jarEntry = jarFile.getNextJarEntry();
            if (jarEntry == null) {
                break;
            }
            if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
                final String JarNameClass = jarEntry.getName().replaceAll("/", "\\.");
                String className = JarNameClass.replace(".class", "");
                LOGGER.info(className);
                controllerClassMapper.put(className, className);

            } else if (jarEntry.getName().startsWith("webapp")) {
                final String JarNameClass = jarEntry.getName();
                LOGGER.info(JarNameClass);
                saveEntry(jf.getInputStream(jarEntry), JarNameClass);
            }
        }
    }

    /**
     *
     * Init configurations from Component
     *
     * @param jf
     * @throws IOException
     * @throws ConfigurationException
     */
    private void setConfiguraton(JarFile jf) throws IOException, ConfigurationException {
        JarEntry entry = jf.getJarEntry(TESLAR_WIDGET_MAINIFIESTXML);

        InputStream inputStream = jf.getInputStream(entry);
        configuration = new XMLConfiguration();
        configuration.load(inputStream);
        componentID = configuration.getString(Settings.COMPONENT_ID.getType());

        LOGGER.info("Loadding : " + componentID);

        component_base = context.getRealPath("/") + "/components/" + componentID;
    }

    /**
     *
     * Copy Components .html,.css,.js files to Folder
     *
     * @param inputStream
     * @param fileName
     * @throws IOException
     */
    private void saveEntry(final InputStream inputStream, String fileName) throws IOException {
        // InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            final String FileName = component_base + "/" + fileName;
            if (FilenameUtils.getExtension(FileName) != null && !FilenameUtils.getExtension(FileName).isEmpty()) {

                File fileDir = new File(FilenameUtils.getFullPath(FileName));
                fileDir.mkdirs();
                final File file1 = new File(FileName);
                file1.createNewFile();
                // write the inputStream to a FileOutputStream
                outputStream
                        = new FileOutputStream(file1);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    throw e;
                }

            }
        }

    }

    private void update() throws MalformedURLException {
        URL myJarFile = new URL("jar", "", "file:"
                + file.getAbsolutePath() + "!/");
    }

    public String getComponentBasePath() {
        return componentID.replace(".", "_");
    }

    public Map<String, String> getControllerClassMapper() {
        return controllerClassMapper;
    }

    public Map<String, String> getHtmlFileMapper() {
        return htmlFileMapper;
    }

    public File getFile() {
        return file;
    }

    public String getComponentID() {
        return componentID;
    }

    public String getComponent_base() {
        return component_base;
    }

    public XMLConfiguration getConfiguration() {
        return configuration;
    }

    public ServletContext getContext() {
        return context;
    }

    public File getName() {
        return file;
    }

    public List<JarEntry> getAllEntrys() {
        return allEntrys;
    }

    /**
     *
     *
     *
     * @param requestPath
     * @return
     */
    public RequestResolver loadRequestController(String requestPath) {
        LOGGER.info(requestPath);
        return controllerResolver.resolve(requestPath);
    }

    public List<? extends Class> getEntities() {
        return dataBaseResolver.getEntityList();
    }

    /**
     *
     * Component XML Configurations
     *
     */
    private enum Settings {

        COMPONENT_ID("component.id"),
        COMPONENT_NAME("component.name");

        private final String type;

        Settings(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

    }

    @Override
    public String toString() {
        return "Component{" + "componentID=" + componentID + '}';
    }

}
