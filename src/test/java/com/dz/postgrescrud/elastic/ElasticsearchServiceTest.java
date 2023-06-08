package com.dz.postgrescrud.elastic;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.dz.postgrescrud.domain.Journal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ElasticsearchServiceTest {

    @Autowired
    ElasticsearchService elasticsearchService;

    @Test
    public void testSearchNoResult() throws Exception {
        SearchResponse<Journal> res = elasticsearchService.journalEntryLookup("no result");
        List<Hit<Journal>> hits = res.hits().hits();
        assert(hits.size() == 0);
    }

    // TODO: add test for search with result
}
