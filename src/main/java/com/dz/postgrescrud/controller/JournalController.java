package com.dz.postgrescrud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JournalController {

    Logger logger = LoggerFactory.getLogger(JournalController.class);

    @RequestMapping("/journal")
    public ResponseEntity<String> journal() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        return ResponseEntity.ok("Test Journal Entry");
    }
}
