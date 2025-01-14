package com.sambet.webapp.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.sambet.Constants;
import com.sambet.service.GenericManager;
import com.sambet.service.LookupManager;
import com.sambet.webapp.util.schedulerQuartz.SchedulerQuartz;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * <p>StartupListener class used to initialize and database settings
 * and populate any application-wide drop-downs.
 * <p/>
 * <p>Keep in mind that this listener is executed outside of OpenSessionInViewFilter,
 * so if you're using Hibernate you'll have to explicitly initialize all loaded data at the
 * GenericDao or service level to avoid LazyInitializationException. Hibernate.initialize() works
 * well for doing this.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class StartupListener implements ServletContextListener {
    private static final Log log = LogFactory.getLog(StartupListener.class);

    private Scheduler scheduler;
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent event) {
    	log.debug("Initializing context...");
        ServletContext context = event.getServletContext();
        
        log.info("context.getRealPath: "+context.getRealPath("/"));
        
    	try{
	        // Se la directory non esiste creo la cartella upload
	        File theDir = new File( context.getRealPath(Constants.STORAGE_DIRECTORY_UPLOADS) );
			if(!theDir.exists()) {
			    theDir.mkdir();
			}
			//log.debug("Creo la Sitemap.xml...");
			//Sitemap.CreaSitemapUrl( context ); 
			
			
			Iterator<@NonNull Driver> driversIterator = ServiceLoader.load(Driver.class).iterator();
		      while (driversIterator.hasNext()) {
		         try {
		            // Instantiates the driver
		            driversIterator.next();
		         } catch (Throwable t) {
		            event.getServletContext().log("JDBC Driver registration failure.", t);
		         }
		      }
			
			//log.debug("Avvio Scedulatore Quartz...");
			scheduler = SchedulerQuartz.AvviaScdulatoriQuartz(context);
			
		}catch(Exception exc){
			exc.printStackTrace();
		}
		
        // Orion starts Servlets before Listeners, so check if the config
        // object already exists
        Map<String, Object> config = (HashMap<String, Object>)context.getAttribute(Constants.CONFIG);
        if (config == null) {
            config = new HashMap<>();
        }
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        PasswordEncoder passwordEncoder = null;
        try {
            ProviderManager provider = (ProviderManager)ctx.getBean("org.springframework.security.authentication.ProviderManager#0");
            for (Object o : provider.getProviders()) {
                AuthenticationProvider p = (AuthenticationProvider) o;
                if (p instanceof RememberMeAuthenticationProvider) {
                    config.put("rememberMeEnabled", Boolean.TRUE);
                } else if (ctx.getBean("passwordEncoder") != null) {
                    passwordEncoder = (PasswordEncoder) ctx.getBean("passwordEncoder");
                }
            }
        } catch (NoSuchBeanDefinitionException n) {
            log.debug("authenticationManager bean not found, assuming test and ignoring...");
            // ignore, should only happen when testing
        }
        context.setAttribute(Constants.CONFIG, config);
        // output the retrieved values for the Init and Context Parameters
        if (log.isDebugEnabled()) {
            log.debug("Remember Me Enabled? " + config.get("rememberMeEnabled"));
            if (passwordEncoder != null) {
                log.debug("Password Encoder: " + passwordEncoder.getClass().getSimpleName());
            }
            log.debug("Populating drop-downs...");
        }
        setupContext(context);
        // Determine version number for CSS and JS Assets
        String appVersion = null;
        try {
            InputStream is = context.getResourceAsStream("/META-INF/MANIFEST.MF");
            if (is == null) {
                log.warn("META-INF/MANIFEST.MF not found.");
            } else {
                Manifest mf = new Manifest();
                mf.read(is);
                Attributes atts = mf.getMainAttributes();
                appVersion = atts.getValue("Implementation-Version");
            }
        } catch (IOException e) {
            log.error("I/O Exception reading manifest: " + e.getMessage());
        }

        // If there was a build number defined in the war, then use it for
        // the cache buster. Otherwise, assume we are in development mode
        // and use a random cache buster so developers don't have to clear
        // their browser cache.
        if (appVersion == null || appVersion.contains("SNAPSHOT")) {
            appVersion = "" + new Random().nextInt(100000);
        }
//        log.info("Application version set to: " + appVersion);
        context.setAttribute(Constants.ASSETS_VERSION, appVersion);
    }

    /**
     * This method uses the LookupManager to lookup available roles from the data layer.
     *
     * @param context The servlet context
     */
    @SuppressWarnings("rawtypes")
	public static void setupContext(ServletContext context) {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        LookupManager mgr = (LookupManager) ctx.getBean("lookupManager");
        
        // get list of possible roles
        context.setAttribute(Constants.AVAILABLE_ROLES, mgr.getAllRoles());
        context.setAttribute(Constants.AVAILABLE_TIPO_RUOLI, mgr.getAllTipoRuoli());
        
        log.debug("Drop-down initialization complete [OK]");

        // Any manager extending GenericManager will do:
        GenericManager manager = (GenericManager) ctx.getBean("userManager");
        doReindexing(manager);
        log.debug("Full text search reindexing complete [OK]");
    }

    @SuppressWarnings("rawtypes")
	private static void doReindexing(GenericManager manager) {
        manager.reindexAll(false);
    }

    /**
     * Shutdown servlet context (currently a no-op method).
     *
     * @param servletContextEvent The servlet context event
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	
    	log.info("SONO IN contextDestroyed");
    	
    	try {
			scheduler.shutdown(true);
			log.info("Eliminati i thered appesi su sheduler quartz");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//  
    	log.info("elimino i drivers java.sql appesi");
    	final ClassLoader cl = servletContextEvent.getServletContext().getClassLoader();
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
           final Driver driver = drivers.nextElement();
           // We deregister only the classes loaded by this application's classloader
           if (driver.getClass().getClassLoader() == cl) {
              try {
                 DriverManager.deregisterDriver(driver);
              } catch (SQLException e) {
            	  servletContextEvent.getServletContext().log("JDBC Driver deregistration failure.", e);
              }
           }
        }
    	
    	
    	
        //LogFactory.release(Thread.currentThread().getContextClassLoader());
        //Commented out the above call to avoid warning when SLF4J in classpath.
        //WARN: The method class org.apache.commons.logging.impl.SLF4JLogFactory#release() was invoked.
        //WARN: Please see http://www.slf4j.org/codes.html for an explanation.
    }
}
