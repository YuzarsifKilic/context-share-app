package com.yuzarsif.gameservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.yuzarsif.gameservice.repository.jpa")
public class MysqlConfig {
}
