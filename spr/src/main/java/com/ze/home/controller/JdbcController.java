package com.ze.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JdbcController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void get(){
        //Person persons = jdbcTemplate.queryForObject("select * from PERSON limit 1", Person.class);
        Map<String,Object> map = jdbcTemplate.queryForMap("select * from PERSON limit 1");
        System.out.println(map);
    }
}
