package com.dz.postgrescrud.repository;

import com.dz.postgrescrud.configuration.RepositoryConfiguration;
import com.dz.postgrescrud.domain.Journal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import org.junit.jupiter.api.Assertions;

@SpringBootTest(classes = {RepositoryConfiguration.class})
public class JournalRepositoryTest {

    private JournalRepository journalRepository;

    @Autowired
    public void setProductRepository(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @Test
    public void testSaveJournal(){
        //setup journal
        Journal journal = new Journal();
        journal.setJournalEntry("Test Journal Entry");
        journal.setImageUrl("Test Image URL");
        journal.setDate(new Date().toInstant());

        //save product, verify has ID value after save
        Assertions.assertNull(journal.getId()); //null before save
        journalRepository.save(journal);
        Assertions.assertNotNull(journal.getId()); //not null after save

        //fetch from DB
        Journal fetchedJournal = journalRepository.findById(journal.getId()).orElse(null);

        //should not be null
        Assertions.assertNotNull(fetchedJournal);

        //should equal
        Assertions.assertEquals(journal.getId(), fetchedJournal.getId());
        Assertions.assertEquals(journal.getJournalEntry(), fetchedJournal.getJournalEntry());

        //update description and save
        fetchedJournal.setJournalEntry("New Journal Entry");
        journalRepository.save(fetchedJournal);

        //get from DB, should be updated
        Journal fetchedUpdatedJournal = journalRepository.findById(fetchedJournal.getId()).orElse(null);
        Assertions.assertEquals(fetchedJournal.getJournalEntry(), fetchedUpdatedJournal.getJournalEntry());

        //verify count of products in DB
        long journalCount = journalRepository.count();
        Assertions.assertEquals(journalCount, 1);

        //get all products, list should only have one
        Iterable<Journal> journals = journalRepository.findAll();
        int count = 0;
        for(Journal j : journals){
            count++;
        }
        Assertions.assertEquals(count, 1);
    }

}
