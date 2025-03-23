package com.msdemo.oporder.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Configuration
public class DataSourceConfig {
    private static final String DB_URL = System.getenv("DB_URL");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    @Bean
    public DataSource getDataSource() {
        if (Stream.of(DB_URL, DB_USER, DB_PASSWORD).anyMatch(Objects::isNull)) {
            log.error("Database environment variables are not set: DB_URL={}, DB_USER={}, DB_PASSWORD={}", DB_URL, DB_USER, DB_PASSWORD);
            throw new IllegalStateException("Database environment variables are not set");
        }

        return  DataSourceBuilder.create()
            .url(DB_URL)
            .username(DB_USER)
            .password(DB_PASSWORD)
            .driverClassName("org.postgresql.Driver")
            .build();
    }
}
