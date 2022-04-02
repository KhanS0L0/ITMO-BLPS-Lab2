package com.example.config;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.util.Properties;

@Configuration
public class BitronixConfig {

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    @Bean(name = "bitronixTransactionManager")
    @DependsOn
    public BitronixTransactionManager bitronixTransactionManager() throws Throwable {
        BitronixTransactionManager bitronixTransactionManager = TransactionManagerServices.getTransactionManager();
        bitronixTransactionManager.setTransactionTimeout(10000);
        return bitronixTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({"bitronixTransactionManager"})
    public PlatformTransactionManager transactionManager(TransactionManager bitronixTransactionManager) {
        return new JtaTransactionManager(bitronixTransactionManager);
    }

    @Bean(name = "primaryPostgreSqlDataSource")
    @Primary
    public DataSource primaryPostgreSqlDataSource(){
        PoolingDataSource bitronixDataSourceBean = new PoolingDataSource();
        bitronixDataSourceBean.setMaxPoolSize(5);
        bitronixDataSourceBean.setUniqueName("primaryPostgreSqlDataSourceResource");
        bitronixDataSourceBean.setClassName("org.postgresql.xa.PGXADataSource");
        bitronixDataSourceBean.setAllowLocalTransactions(true);
        Properties properties = new Properties();
        properties.put("user",  userName);
        properties.put("password",  password);
        properties.put("url", url);
        bitronixDataSourceBean.setDriverProperties(properties);
        return bitronixDataSourceBean;
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    @DependsOn({"transactionManager", "primaryPostgreSqlDataSource"})
    public LocalContainerEntityManagerFactoryBean primaryMySqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .persistenceUnit("primaryUnit")
                .packages("com.example.entity")
                .build();
    }
}