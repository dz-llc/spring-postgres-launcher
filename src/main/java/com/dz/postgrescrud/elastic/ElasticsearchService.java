package com.dz.postgrescrud.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.MsearchRequest;
import co.elastic.clients.elasticsearch.core.MsearchResponse;
import co.elastic.clients.elasticsearch.core.msearch.MultisearchBody;
import co.elastic.clients.elasticsearch.core.msearch.MultisearchHeader;
import co.elastic.clients.elasticsearch.core.msearch.RequestItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.dz.postgrescrud.domain.Journal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticsearchService {

    Logger logger = LoggerFactory.getLogger(ElasticsearchService.class);

    ObjectMapper objectMapper = new ObjectMapper();

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

    public MsearchResponse<Object> journalEntryLookup(String text) throws Exception {
        MsearchResponse<Object> response = esClient.msearch(MsearchRequest.of(ms -> ms.searches(
            List.of(RequestItem.of(ri -> ri
                            .header(MultisearchHeader.of(mh -> mh.index("idx_journal")))
                            .body(MultisearchBody.of(msb -> msb.query(MatchAllQuery.of(ma -> ma)._toQuery()))
                            )))
            )),
                Object.class);
        response.responses().forEach(r -> logger.info("journalEntryLookup for text " + text + ": " + r.toString()));

        response.responses().forEach(r -> {
            HitsMetadata<Object> hits = r.result().hits();
            for (Hit<Object> hit : hits.hits()) {
                logger.info(hit.toString());
                // Hit: {"_index":"idx_journal","_id":"1","_score":1.0,"_source":"{id=1, version=0, journalEntry=thing, imageUrl=Test Image URL, date=1.686001262994E9}"}
            }
        });

        return response;
    }
}
