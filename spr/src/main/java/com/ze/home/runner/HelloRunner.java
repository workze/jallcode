package com.ze.home.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class HelloRunner implements CommandLineRunner {

    @Autowired
    DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("HelloRunner is running");
        System.out.println(dataSource.toString());
    }
}
