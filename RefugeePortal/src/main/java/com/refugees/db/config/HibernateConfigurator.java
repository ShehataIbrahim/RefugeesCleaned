package com.refugees.db.config;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;

import net.hitachifbbot.utils.DBUtils;

public class HibernateConfigurator {
	private static final SessionFactory sessionFactoryPortalObj;
	private static final SessionFactory sessionFactoryHealthObj;
	static {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg-portal.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

		// Creating Hibernate SessionFactory Instance
		sessionFactoryPortalObj = configObj.buildSessionFactory(serviceRegistryObj);
	}
	static {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg-health.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

		// Creating Hibernate SessionFactory Instance
		sessionFactoryHealthObj = configObj.buildSessionFactory(serviceRegistryObj);
	}
	public static SessionFactory getSessionFactory()
	{
		return sessionFactoryPortalObj;
	}
    public static void shutdown() {
        // Close caches and connection pools
        sessionFactoryPortalObj.close();
        sessionFactoryHealthObj.close();
    }
    /**
     * 
     * @return portal database connection based on the datasource 
     */
    public static Connection getConnection()
    {
    	Connection connection =null;
    	try {
    		connection = getSessionFactory().
            getSessionFactoryOptions().getServiceRegistry().
            getService(ConnectionProvider.class).getConnection();
    	}catch(Exception e)
    	{
    		/*try {
				connection=DBUtils.instance.getConnection();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}*/
    		e.printStackTrace();
    	}
    	return connection;
    }
    /**
     * 
     * @return health database connection based on the datasource 
     */
    public static Connection getHealthConnection()
    {
    	Connection connection =null;
    	try {
    		connection = sessionFactoryHealthObj.
            getSessionFactoryOptions().getServiceRegistry().
            getService(ConnectionProvider.class).getConnection();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return connection;
    }
}
