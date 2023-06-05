package com.dz.postgrescrud.service;

import com.dz.postgrescrud.domain.Journal;
import com.dz.postgrescrud.elastic.ElasticsearchService;
import com.dz.postgrescrud.repository.JournalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JournalService {

    Logger logger = LoggerFactory.getLogger(JournalService.class);

    @Autowired
    JournalRepository journalRepository;

    @Autowired
    ElasticsearchService elasticsearchService;

    // TODO: create DTO for journal entry
    public Journal createJournal(String journalEntry) throws Exception {
        // Create a Journal Entry
        Journal journal = new Journal();
        journal.setJournalEntry(journalEntry);
        journal.setImageUrl("Test Image URL");
        journal.setDate(new Date().toInstant());

        logger.info("Creating Journal: " + journal);

        // Save to db
        Journal savedJournal = journalRepository.save(journal);

        logger.info("Saved Journal to db: " + savedJournal);

        // Save to elasticsearch
        elasticsearchService.indexJournalEntry(savedJournal);
        return savedJournal;
    }
}
