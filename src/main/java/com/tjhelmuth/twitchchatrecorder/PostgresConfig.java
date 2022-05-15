package com.tjhelmuth.twitchchatrecorder;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class PostgresConfig {

    @Bean
    public JdbcMessageSaver postgresSaver(JdbcTemplate jdbcTemplate){
        return new JdbcMessageSaver(jdbcTemplate);
    }

    @Bean
    public DataSource postgresDataSource(){
        var raw =  DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://server:5433/ep")
                .username("postgres")
                .build();
        var pool = new HikariDataSource();
        pool.setDataSource(raw);
        return pool;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource postgresDataSource){
        return new JdbcTemplate(postgresDataSource);
    }
}
