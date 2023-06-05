package com.dz.postgrescrud.controller;

import co.elastic.clients.elasticsearch.core.MsearchResponse;
import com.dz.postgrescrud.domain.Journal;
import com.dz.postgrescrud.elastic.ElasticsearchService;
import com.dz.postgrescrud.repository.JournalRepository;
import com.dz.postgrescrud.service.JournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping(value = "/api/v1/journal", method = {RequestMethod.POST})
public class JournalController {

    Logger logger = LoggerFactory.getLogger(JournalController.class);

    @Autowired
    JournalService journalService;

    @Autowired
    ElasticsearchService elasticsearchService;

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestBody String journalEntry
    ) throws Exception {
        try {
            // Create a Journal Entry
            journalService.createJournal(journalEntry);
            return ResponseEntity.ok(journalEntry);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Error creating journal entry");
        }
    }

    @GetMapping("/searchJournalEntries")
    public ResponseEntity<String> searchJournalEntries(
            @RequestBody String searchEntryText
    ) throws Exception {
        try {
            // Create a Journal Entry
            var res = elasticsearchService.journalEntryLookup(searchEntryText);
            if (res == null) {
                return ResponseEntity.ok("No Journal Entries found for text: " + searchEntryText);
            } else {
                return ResponseEntity.ok(res.toString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Error searching for journal entry");
        }
    }
}
