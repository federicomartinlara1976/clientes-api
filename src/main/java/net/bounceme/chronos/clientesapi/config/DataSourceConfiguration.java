package net.bounceme.chronos.clientesapi.config;

import java.beans.PropertyVetoException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import net.bounceme.chronos.clientesapi.models.entity.Cliente;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"net.bounceme.chronos.clientesapi.models.dao"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class DataSourceConfiguration {

    @Bean
    @Primary
    public DataSource dataSource(@Autowired C3P0Properties c3P0Properties) throws PropertyVetoException {
        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setDriverClass(c3P0Properties.getDriverClass());
        pooledDataSource.setUser(c3P0Properties.getUser());
        pooledDataSource.setPassword(c3P0Properties.getPassword());
        pooledDataSource.setJdbcUrl(c3P0Properties.getJdbcUrl());
        pooledDataSource.setMaxPoolSize(c3P0Properties.getMaxPoolSize());
        pooledDataSource.setMinPoolSize(c3P0Properties.getMinPoolSize());
        pooledDataSource.setMaxIdleTime(c3P0Properties.getMaxIdleTime());

        return pooledDataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages(Cliente.class)
                .persistenceUnit("clientesapi-unit")
                .build();
    }

    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
