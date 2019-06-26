package com.ze.home.controller;

import com.ze.home.exception.BookException;
import com.ze.home.service.BookService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DataSource dataSource;



    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String hello() throws SQLException {
        bookService.test();
        System.out.println(dataSource.toString());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        return "hello world";
    }

    @RequestMapping(value = "/book2", method = RequestMethod.GET)
    public String hello2(){
        bookService.test();
        return restTemplate.exchange(
                "http://localhost:8080/book/", HttpMethod.GET, null, String.class).getBody();
    }

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public String exception(){
        throw new BookException("BookController /exception throws a exception");
    }

}
