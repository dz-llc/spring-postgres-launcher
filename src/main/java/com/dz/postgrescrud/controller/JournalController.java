package com.dz.postgrescrud.controller;

import com.dz.postgrescrud.domain.Journal;
import com.dz.postgrescrud.repository.JournalRepository;
import com.dz.postgrescrud.service.JournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping(value = "/api/v1/journal", method = {RequestMethod.POST})
public class JournalController {

    Logger logger = LoggerFactory.getLogger(JournalController.class);

    @Autowired
    JournalService journalService;

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestBody String journalEntry
    ) throws Exception {
        try {
            logger.trace("A TRACE Message");
            logger.debug("A DEBUG Message");
            logger.info("An INFO Message");
            logger.warn("A WARN Message");
            logger.error("An ERROR Message");

            // Create a Journal Entry
            journalService.createJournal(journalEntry);
            return ResponseEntity.ok(journalEntry);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Error creating journal entry");
        }
    }
}
