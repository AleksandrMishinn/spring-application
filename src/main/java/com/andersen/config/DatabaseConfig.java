package com.andersen.config;

import com.google.common.base.Preconditions;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.ttddyy.dsproxy.listener.logging.DefaultQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.andersen.repository")
@ComponentScan(basePackages = {"com.andersen.repository", "com.andersen.domain"})
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
    @Value("${hibernate.dialect:org.hibernate.dialect.MySQL8Dialect}")
    private String dialect;
    @Value("${log.query.level:debug}")
    private String logQueryLevel;

//    @Bean
//    public DataSource dataSource() {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//
//        try {
//            dataSource.setDriverClass(jdbcDriver);
//        } catch (PropertyVetoException ex) {
//            throw new RuntimeException(ex.getMessage(), ex);
//        }
//
//        dataSource.setJdbcUrl(jdbcUrl);
//        dataSource.setUser(jdbcUsername);
//        dataSource.setPassword(jdbcPassword);
//
//        dataSource.setInitialPoolSize(Integer.parseInt(initialPoolSize));
//        dataSource.setMinPoolSize(Integer.parseInt(minPoolSize));
//        dataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
//        dataSource.setMaxIdleTime(Integer.parseInt(maxIdleTime));
//
//        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
//        loggingListener.setLogLevel(SLF4JLogLevel.valueOf(logQueryLevel.toUpperCase()));
//        DefaultQueryLogEntryCreator creator = new DefaultQueryLogEntryCreator();
//        creator.setMultiline(true);
//        loggingListener.setQueryLogEntryCreator(creator);
//
//        return ProxyDataSourceBuilder
//                .create(dataSource)
//                .name(databaseName)
//                .listener(loggingListener)
//                .build();
//    }
//
//    @Bean
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(sessionFactory);
//        return txManager;
//    }
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan(packagesToScan);
//
//        Properties hibernateProperties = sessionFactory.getHibernateProperties();
//        hibernateProperties.setProperty("hibernate.dialect", dialect);
//
//        return sessionFactory;
//    }
//
//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.andersen.domain");
//        factory.setDataSource(dataSource());
//        factory.afterPropertiesSet();
//
//        return factory.getObject();
//    }






    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.andersen.domain");

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }


    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull("com.mysql.cj.jdbc.Driver"));
        dataSource.setUrl(Preconditions.checkNotNull("jdbc:mysql://localhost:3306/universal?serverTimezone=UTC"));
        dataSource.setUsername(Preconditions.checkNotNull("root"));
        dataSource.setPassword(Preconditions.checkNotNull("root"));

        return dataSource;
    }

}
