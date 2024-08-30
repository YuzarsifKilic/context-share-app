package com.yuzarsif.gameservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.yuzarsif.gameservice.repository.mongodb")
public class MongoConfig {

}
