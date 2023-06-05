package com.dz.postgrescrud.service;

import com.dz.postgrescrud.configuration.RepositoryConfiguration;
import com.dz.postgrescrud.domain.Journal;
import com.dz.postgrescrud.elastic.ElasticsearchService;
import com.dz.postgrescrud.repository.JournalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {RepositoryConfiguration.class})
public class JournalServiceTest {

    @InjectMocks
    private JournalService journalService;

    @Mock
    private JournalRepository journalRepository;

    @Mock
    private ElasticsearchService mockElasticsearchService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateJournal() throws Exception {
        Journal j = new Journal();
        j.setJournalEntry("Test Journal Entry");
        when(mockElasticsearchService.indexJournalEntry(any(Journal.class))).thenReturn(j);
        when(journalRepository.save(any(Journal.class))).thenReturn(j);

        Journal res = journalService.createJournal("Test Journal Entry");
        assert(j.getJournalEntry().equals("Test Journal Entry"));
    }
}
