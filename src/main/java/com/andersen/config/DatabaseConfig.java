package com.andersen.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.ttddyy.dsproxy.listener.logging.DefaultQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.andersen.repository")
@PropertySource("classpath:database.properties")
public class DatabaseConfig {

    @Value("${database.name}")
    private String databaseName;
    @Value("${jdbc.driver:com.mysql.cj.jdbc.Driver}")
    private String jdbcDriver;
    @Value("${jdbc.url://localhost:3306/universal?serverTimezone=UTC}")
    private String jdbcUrl;
    @Value("${jdbc.username:root}")
    private String jdbcUsername;
    @Value("${jdbc.password:root}")
    private String jdbcPassword;
    @Value("${connection.pool.initialPoolSize:5}")
    private String initialPoolSize;
    @Value("${connection.pool.minPoolSize:5}")
    private String minPoolSize;
    @Value("${connection.pool.maxPoolSize:20}")
    private String maxPoolSize;
    @Value("${connection.pool.maxIdleTime:3000}")
    private String maxIdleTime;
    @Value("${hibernate.packagesToScan:com.andersen.domain}")
    private String packagesToScan;
    @Value("${hibernate.dialect:org.hibernate.dialect.PostgreSQL10Dialect}")
    private String dialect;
    @Value("${log.query.level:debug}")
    private String logQueryLevel;

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(jdbcDriver);
        } catch (PropertyVetoException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUsername);
        dataSource.setPassword(jdbcPassword);

        dataSource.setInitialPoolSize(Integer.parseInt(initialPoolSize));
        dataSource.setMinPoolSize(Integer.parseInt(minPoolSize));
        dataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
        dataSource.setMaxIdleTime(Integer.parseInt(maxIdleTime));

        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
        loggingListener.setLogLevel(SLF4JLogLevel.valueOf(logQueryLevel.toUpperCase()));
        DefaultQueryLogEntryCreator creator = new DefaultQueryLogEntryCreator();
        creator.setMultiline(true);
        loggingListener.setQueryLogEntryCreator(creator);

        return ProxyDataSourceBuilder
                .create(dataSource)
                .name(databaseName)
                .listener(loggingListener)
                .build();
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(packagesToScan);

        Properties hibernateProperties = sessionFactory.getHibernateProperties();
        hibernateProperties.setProperty("hibernate.dialect", dialect);

        return sessionFactory;
    }
}
