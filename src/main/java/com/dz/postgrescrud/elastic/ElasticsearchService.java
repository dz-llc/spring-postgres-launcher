package com.dz.postgrescrud.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.dz.postgrescrud.domain.Journal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchService {

    Logger logger = LoggerFactory.getLogger(ElasticsearchService.class);

    ElasticsearchClient esClient = ElasticsearchClientFactory.createClient("localhost", 9200);

    public Journal indexJournalEntry(Journal journal) throws Exception {
        IndexResponse response = esClient.index(i -> i
                .index("journal")
                .id(journal.getId().toString())
                .document(journal)
        );

        logger.info("Indexed Journal " + journal.toString() + " with version " + response.version());
        return journal;
    }
}
