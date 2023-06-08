package com.dz.postgrescrud.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.dz.postgrescrud.domain.Journal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ElasticsearchService {

    Logger logger = LoggerFactory.getLogger(ElasticsearchService.class);

    ElasticsearchClient esClient = ElasticsearchClientFactory.createClient("localhost", 9200);

    public Journal indexJournalEntry(Journal journal) throws Exception {
        IndexResponse response = esClient.index(i -> i
                .index("idx_journal")
                .id(journal.getId().toString())
                .document(journal)
        );

        logger.info("Indexed Journal " + journal.toString() + " with version " + response.version());
        return journal;
    }

    public SearchResponse<Journal> journalEntryLookup(String text) throws Exception {
        // Terms query
        List<FieldValue> list = Arrays.asList(FieldValue.of(text), FieldValue.of("ALL"));
        Query query = new Query.Builder().terms(termsQueryBuilder -> termsQueryBuilder
                .field("journalEntry")
                .terms(termQueryField -> termQueryField
                        .value(list))).build();
        List<Query> shouldQueryList = new ArrayList<>();
        shouldQueryList.add(query);

        SearchResponse<Journal> response = esClient.search(searchRequest -> searchRequest
                        .index("idx_journal")
                        .query(qBuilder -> qBuilder
                                .bool(boolQueryBuilder -> boolQueryBuilder
                                        // using should query list here
                                        .should(shouldQueryList)))
                , Journal.class);
        response.hits().hits().forEach(h -> logger.info("Search results for text '" + text + "': " + h.toString()));

        return response;
    }
}
