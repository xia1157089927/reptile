package com.xia.reptile.config;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryReptile",
        transactionManagerRef="transactionManagerReptile",
        basePackages= { "com.xia.reptile.dao" })
public class DiagnosisDBConfig {

    @Autowired @Qualifier("reptileDataSource")
    private DataSource reptileDataSource;

    @Primary
    @Bean(name = "entityManagerReptile")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryReptile(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryReptile")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryReptile (EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean s =  builder
                .dataSource(reptileDataSource)
                .properties(getVendorProperties(reptileDataSource))
                .packages("com.xia.reptile.hibernate") //设置实体类所在位置
                .persistenceUnit("reptilePersistenceUnit")
                .build();
        return s;
    }

    @Autowired
    private JpaProperties jpaProperties;

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Primary
    @Bean(name = "transactionManagerReptile")
    public PlatformTransactionManager transactionManagerReptile(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryReptile(builder).getObject());
    }
    
    @Bean(name="transactionTemplate")
    public TransactionTemplate transactionTemplate(EntityManagerFactoryBuilder builder) {
        return new TransactionTemplate(transactionManagerReptile(builder));
    }
}