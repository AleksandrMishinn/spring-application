package com.andersen.config;

import com.google.common.base.Preconditions;
import net.ttddyy.dsproxy.listener.logging.DefaultQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.andersen.repository", "com.andersen.domain"})
@EnableJpaRepositories(basePackages = "com.andersen.repository")
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
    @Value("${log.query.level:debug}")
    private String logQueryLevel;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(Preconditions.checkNotNull(jdbcDriver));
        dataSource.setUrl(Preconditions.checkNotNull(jdbcUrl));
        dataSource.setUsername(Preconditions.checkNotNull(jdbcUsername));
        dataSource.setPassword(Preconditions.checkNotNull(jdbcPassword));

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
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

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
}
