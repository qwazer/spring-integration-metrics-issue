package com.github.qwazer.springmetricsissue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public String handle(String s, Long delay) throws InterruptedException {
        logger.debug("handle() called with: s = {}, delay = {}", s, delay);
        Thread.sleep(delay);
        return s;

    }
}
