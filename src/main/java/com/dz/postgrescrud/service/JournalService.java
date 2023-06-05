package com.dz.postgrescrud.service;

import com.dz.postgrescrud.domain.Journal;
import com.dz.postgrescrud.elastic.ElasticsearchService;
import com.dz.postgrescrud.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JournalService {

    @Autowired
    JournalRepository journalRepository;

    @Autowired
    ElasticsearchService elasticsearchService;

    public Journal createJournal(String journalEntry) throws Exception {
        // Create a Journal Entry
        Journal journal = new Journal();
        journal.setJournalEntry(journalEntry);
        journal.setImageUrl("Test Image URL");
        journal.setDate(new Date().toInstant());

        // Save to db
        journalRepository.save(journal);

        // Save to elasticsearch
         elasticsearchService.indexJournalEntry(journal);
         return journal;
    }
}