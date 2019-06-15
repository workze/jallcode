package com.ze.home.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class);

    public void test(){
        logger.info("BookService logger print something ...");
    }
}
