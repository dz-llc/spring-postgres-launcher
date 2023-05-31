package com.dz.postgrescrud.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.dz.postgrescrud.domain", "com.dz.postgrescrud.auth.token", "com.dz.postgrescrud.auth.user"})
@EnableJpaRepositories(basePackages = {"com.dz.postgrescrud.repository", "com.dz.postgrescrud.auth.token", "com.dz.postgrescrud.auth.user"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}