package com.api.app.conf;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:cnaps.properties"})
@EnableJpaRepositories(
    basePackages = "com.api.app.cnaps.repository",
    entityManagerFactoryRef = "cnapsEmployeeEntityManager",
    transactionManagerRef = "cnapsEmployeeTransactionManager"
)
@AllArgsConstructor
public class CnapsEmployeePersistenceConf {
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean cnapsEmployeeEntityManager() {
        LocalContainerEntityManagerFactoryBean localContainer = new LocalContainerEntityManagerFactoryBean();
        localContainer.setDataSource(employeeDatasource());
        localContainer.setPackagesToScan("com.api.app.cnaps.model");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        localContainer.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
            environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        localContainer.setJpaPropertyMap(properties);
        return localContainer;
    }

    @Bean
    @ConfigurationProperties(prefix = "second-datasource")
    public DataSource employeeDatasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("second-datasource.url"));
        dataSource.setUsername(environment.getProperty("second-datasource.username"));
        dataSource.setPassword(environment.getProperty("second-datasource.password"));
        return dataSource;
    }


    @Bean
    public PlatformTransactionManager cnapsEmployeeTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(cnapsEmployeeEntityManager().getObject());
        return transactionManager;
    }
}
